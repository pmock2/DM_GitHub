package teamHarambe;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;

public class ConnectionHandler implements Runnable {
	Socket s;
	BufferedReader fromClient;
	PrintStream toClient;
	private static String databasePath = "resources/Database.json";
	File dbFile = new File(databasePath);
	
	//TODO store schedule in database and have ConnectionHandler read from db
	ConnectionHandler(Socket s) throws IOException {
		this.s = s;
		this.fromClient = new BufferedReader(new InputStreamReader(s.getInputStream()));
		this.toClient = new PrintStream(s.getOutputStream());
	}
	
	public void run() {
		int permissionLevel = 0;
		Referee userAccount = null;
		
		while (true) {
			try {
				String message = fromClient.readLine();
				System.out.println("Command from client: " + message);

				switch(message)
				{
					case "Create_DB":
					{
						int numTeams = Integer.parseInt(fromClient.readLine());
						List<Team> teams = new LinkedList<>();
						for (int i = 0; i < numTeams; i++)
						{
							teams.add(new Team(i, fromClient.readLine()));
						}
						String dateYear = fromClient.readLine();
						String dateMonth = fromClient.readLine();
						String dateDay = fromClient.readLine();
						Calendar selectedDate = Calendar.getInstance();
						selectedDate.set(Integer.parseInt(dateYear), Integer.parseInt(dateMonth), Integer.parseInt(dateDay));
						
						JSONObject referees = new JSONObject(fromClient.readLine());
						String[] refereeIds = JSONObject.getNames(referees);
						for (int i=0; i < refereeIds.length; i++) {
							int refereeId = Integer.parseInt(refereeIds[i]);
							JSONObject data = referees.getJSONObject(refereeIds[i]);
							boolean found = false;;
							
							for (int j=0; j < Server.referees.size(); j++) {
								if (Server.referees.get(j).getId() == refereeId) {
									found = true;
									Server.referees.get(j).setEmail(data.getString("Email"));
									break;
								}
							}
							if (!found) {
								Server.referees.add(new Referee(refereeId, data.getString("Email"), false));
							}
						}
						
						for (int i=Server.referees.size()-1; i >= 0 ; i--) {
							boolean found = false;
							for (int v=0; v < refereeIds.length; v++) {
								int refereeId = Integer.parseInt(refereeIds[v]);
								if (refereeId == Server.referees.get(i).getId()) {
									found = true;
									break;
								}
							}
							
							if (!(found || Server.referees.get(i).isSuperReferee)) {
								Server.referees.remove(i);
							}
						}
						
						Server.teams = teams;
						Server.schedule = new Schedule(Server.teams, Server.referees, selectedDate);
						Server.saveData();

						if (dbFile.exists()) {
							toClient.println("Success");
						} else {
							toClient.println("Failure");
						}
						break;
					}
					case "Get_Schedule":
					{
						JSONObject clientSchedule = new JSONObject(Server.schedule.toJSON());
						String[] keyNames = JSONObject.getNames(clientSchedule);
						for (int i=0; i < keyNames.length; i++) {
							JSONObject match = clientSchedule.getJSONObject(keyNames[i]);
							match.put("Team0Name", Server.teams.get(match.getInt("Team0")).getName());
							match.put("Team1Name", Server.teams.get(match.getInt("Team1")).getName());
							match.put("RefereeName", Server.referees.get(match.getInt("Referee")).getEmail());
						}
						toClient.println(clientSchedule.toString());
						toClient.println("End_Schedule");
						System.out.println("Sent schedule to client");
						break;
					}
					case "Login":
					{
						String email = fromClient.readLine();
						String password = fromClient.readLine();
						Referee loginAs = refereeFromEmail(email);
						
						if (loginAs == null) {
							toClient.println("Login_Fail");
							break;
						}

						if (loginAs.verifyLoginCode(password)) {
							toClient.println("Login_CodeSuccess");
							String newPassword = fromClient.readLine();
							password = newPassword;
							loginAs.setPassword(password);
						}
						
						if (loginAs.verifyPassword(password)) {
							userAccount = loginAs;
							userAccount.clearLoginCode();
							permissionLevel = (userAccount.isSuperReferee ? 2 : 1);
							toClient.println("Login_Success");
							toClient.println(permissionLevel);
						} else {
							System.out.println("Verification failed");
							toClient.println("Login_Fail");
						}
						break;
					}
					case "RequestPasswordReset":
					{
						String email = fromClient.readLine();
						Referee referee = refereeFromEmail(email);
						if (referee != null) {
							referee.sendPasswordReset();
							toClient.println("Reset_Success");
						} else {
							toClient.println("Reset_Fail");
						}
						break;
					}
					case "Get_Standings":
					{
						toClient.println(Server.rankingsFromSchedule().toString());
						Server.rankingsFromSchedule().remove("");
						break;
					}
					case "Does_DB_Exist":
					{
						toClient.println("true");
						break;
					}
					case "Does_Schedule_Exist":
					{
						toClient.println(Server.schedule == null ? "false" : "true");
						break;
					}
					case "Get_RefereedMatches":
					{
						Calendar today = Calendar.getInstance();
						List<Match> seasonMatches = Server.schedule.getMatches();
						JSONObject jsonMatches = new JSONObject();
						
						for(int i=0; i < seasonMatches.size(); i++) {
							if (seasonMatches.get(i).getReferee() == userAccount && seasonMatches.get(i).isMatchOnDate(today)) {
								jsonMatches.put(seasonMatches.get(i).getId()+"", seasonMatches.get(i).toJSON());
							}
						}
						
						toClient.println(jsonMatches.toString());
						break;
					}
					case "Get_Referees":
					{
						if (permissionLevel < 2) {
							toClient.println("Exception_InsufficientPermissions");
							break;
						}
						
						JSONObject referees = new JSONObject();
						for (int i=0; i < Server.referees.size(); i++) {
							Referee referee = Server.referees.get(i);
							if (!referee.isSuperReferee) {
								JSONObject refereeJSON = new JSONObject();
								refereeJSON.put("Email", referee.getEmail());
								referees.put(referee.getId()+"", refereeJSON);
							}
						}
						
						toClient.println(referees.toString());
						break;
					}
					case "Set_MatchScore":
					{	
						if (permissionLevel < 1) {
							toClient.println("Exception_InsufficientPermissions");
							break;
						}
						
						String argsString = fromClient.readLine();
						JSONObject args = new JSONObject(argsString);
						
						int matchId = args.getInt("MatchId");
						double team0Score = args.getDouble("Team0Score");
						double team1Score = args.getDouble("Team1Score");
						boolean team0Forfiet = args.getBoolean("Team0Forfiet");
						boolean team1Forfiet = args.getBoolean("Team1Forfiet");
						
						Match match = Server.schedule.getMatches().get(matchId);
						if (permissionLevel >= 2 || (match.getReferee() == userAccount && !match.isScored())) {
							match.setTeam1Score(team0Score);
							match.setTeam2Score(team1Score);
							Server.saveData();
						} else {
							toClient.println("Exception_InsufficientPermissions");
						}
						
						break;
					}
					case "Set_MatchDate":
					{
						if (permissionLevel < 2) {
							toClient.println("Exception_InsufficientPermissions");
							break;
						}
						
						String argsString = fromClient.readLine();
						JSONObject args = new JSONObject(argsString);
						
						int matchId = args.getInt("MatchId");
						int year = args.getInt("Year");
						int month = args.getInt("Year");
						int day = args.getInt("Day");
						Calendar newDate = Calendar.getInstance();
						newDate.set(year, month, day);
						
						List<Match> matches = Server.schedule.getMatches();
						Match match = matches.get(matchId);
						for (int i=0; i < matches.size(); i++) {
							Match other = matches.get(i);
							if (i != matchId && match.isMatchOnDate(other.getDate())) {
								String team0Name = match.getTeam1().getName();
								String team1Name = match.getTeam2().getName();
								String team2Name = other.getTeam1().getName();
								String team3Name = other.getTeam2().getName();
								
								if (team0Name.equals(team2Name) || team0Name.equals(team3Name) ||
								    team1Name.equals(team2Name) || team1Name.equals(team3Name)
								) {
									toClient.println("Exception_ConflictingDate");
									break;
								}
							}
						}
						
						match.setDate(newDate);
						Server.saveData();
					}
				}

			} catch (SocketException e) {
				System.out.println("Client disconnected.");
				break;
			} catch (IOException e) {
				System.out.println("There was an issue: " + e);
				break;
			}
		}
	}
	
	private Referee refereeFromEmail(String email) {
		Referee referee = null;
		for (int i=0; i < Server.referees.size(); i++) {
			if (Server.referees.get(i).getEmail().toLowerCase().equals(email.toLowerCase())) {
				return Server.referees.get(i);
			}
		}
		
		return referee;
	}
}
