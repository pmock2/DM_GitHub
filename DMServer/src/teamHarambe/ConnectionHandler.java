package teamHarambe;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
						Map<Integer, Team> teams = new HashMap<>();
						List<Team> teamList = new LinkedList<>();
						for (int i = 0; i < numTeams; i++)
						{
							Team team = new Team(i, fromClient.readLine());
							teams.put(i, team);
							teamList.add(team);
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

							if (!(Server.referees.get(refereeId) == null)) {
								System.out.println("Updating: "+refereeId);
								Server.referees.get(refereeId).setEmail(data.getString("Email"));
							} else {
								System.out.println("Creating: "+refereeId);
								Server.referees.put(refereeId, new Referee(refereeId, data.getString("Email"), false));
							}
						}
						
						if (permissionLevel < 2) {
							toClient.println("Exception_InsufficientPermissions");
							break;
						}
						
						for (Map.Entry<Integer, Referee> entry : Server.referees.entrySet()) {
							boolean found = false;
							for (int v=0; v < refereeIds.length; v++) {
								int refereeId = Integer.parseInt(refereeIds[v]);
								if (refereeId == entry.getKey()) {
									found = true;
									break;
								}
							}
							
							if (!found && entry.getValue().isSuperReferee == false) {
								System.out.println("Removing: "+entry.getKey());
								Server.referees.remove(entry.getKey());
							}
						}

						Server.teams = teams;
						Server.schedule = new Schedule(teamList, Server.getNonSupers(), selectedDate);
						Server.saveData();
						Server.auditLog.logAction("CreateNewSeason", userAccount);
						
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
					case "Update_Schedule":
					{
						JSONObject changedSchedule = new JSONObject(fromClient.readLine());
						Map<Integer,Match> currentSchedule = Server.schedule.getMatches();
						Map<Integer,Match> newSchedule = new HashMap<>();
						
						if (permissionLevel < 2) {
							toClient.println("Exception_InsufficientPermissions");
							break;
						}
						
						boolean conflictingDate = false;
						String matchIds[] = JSONObject.getNames(changedSchedule);
						for (int i=0; i < matchIds.length; i++) {
							int matchId = Integer.parseInt(matchIds[i]);
							JSONObject changedMatch = changedSchedule.getJSONObject(matchIds[i]);
							Match currentMatch = currentSchedule.get(matchId);

							int team0Score = changedMatch.getInt("Team0Score");
							int team1Score = changedMatch.getInt("Team1Score");
							
							JSONObject date = changedMatch.getJSONObject("Date");
							int year = date.getInt("Year"), month = date.getInt("Month"), day = date.getInt("Day");
							Calendar newDate = Calendar.getInstance();
							newDate.set(year, month, day);
							if (dateConflicts(matchId, newDate)) {
								conflictingDate = true;
								break;
							}
							
							String refereeEmail = changedMatch.getString("RefereeName");
							Referee referee = refereeFromEmail(refereeEmail);
							
							boolean changed = (team0Score != currentMatch.getTeam1Score()) || (team1Score != currentMatch.getTeam2Score());
							boolean scored = changed ? true : currentMatch.getScored();
							
							newSchedule.put(matchId,
									new Match(matchId, currentMatch.getTeam1(), currentMatch.getTeam2(), referee, newDate, 
										      team0Score, team1Score, scored)
							);
						}
						
						Server.auditLog.logAction("ModifySeasonData", userAccount);
						
						if (!conflictingDate) {
							Server.schedule = new Schedule(newSchedule);
							Server.saveData();
							toClient.println("Update_Success");
						} else {
							toClient.println("Exception_ConflictingDate");
						}
						break;
					}
					case "Login":
					{
						String email = fromClient.readLine();
						String password = fromClient.readLine();
						Referee loginAs = refereeFromEmail(email);
						System.out.println("Logging in, "+email+", "+loginAs.getEmail());
						
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
					case "Logout":
					{
						userAccount = null;
						permissionLevel = 0;
						toClient.println("Logout_Success");
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
						Map<Integer,Match> seasonMatches = Server.schedule.getMatches();
						JSONObject jsonMatches = new JSONObject();
						
						for (Map.Entry<Integer, Match> entry : Server.schedule.getMatches().entrySet()) {
							int id = entry.getKey();
							Match match = entry.getValue();
							if (match.getReferee() == userAccount && !match.isScored()) {
								JSONObject matchesJSON = new JSONObject();
								matchesJSON.put("MatchId", match.getId());
								matchesJSON.put("Team0Name", match.getTeam1().getName());
								matchesJSON.put("Team1Name", match.getTeam2().getName());
								matchesJSON.put("Scored", match.getScored());
								matchesJSON.put("Score0", match.getTeam1Score());
								matchesJSON.put("Score1", match.getTeam2Score());
								
								jsonMatches.put(match.getId()+"", matchesJSON);
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
						for (Map.Entry<Integer, Referee> entry : Server.referees.entrySet()) {
							int id = entry.getKey();
							Referee referee = entry.getValue();
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
						int team0Score = args.getInt("Team0Score");
						int team1Score = args.getInt("Team1Score");
						//boolean team0Forfeit = args.getBoolean("Team0Forfeit");
						//boolean team1Forfeit = args.getBoolean("Team1Forfeit");
						boolean reschedule = args.getBoolean("Reschedule");
						
						Match match = Server.schedule.getMatches().get(matchId);
						if (permissionLevel >= 2 || (match.getReferee() == userAccount)) {
							match.setTeam1Score(team0Score);
							match.setTeam2Score(team1Score);
							match.scored = true;
							Server.saveData();
							toClient.println("Success");
						} else {
							toClient.println("Exception_InsufficientPermissions");
						}
						
						Server.auditLog.logAction("SetMatchScore: ID " + matchId + " " + team0Score + " - " + team1Score + " Reschedule: " + reschedule, userAccount);
						
						break;
					}
					case "Get_AuditLog":
					{
						if (permissionLevel < 2) {
							toClient.println("Exception_InsufficientPermissions");
							break;
						}
						
						toClient.println(Server.auditLog.getJSONLogs().toString());
						break;
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
		Referee referee;
		
		for (Map.Entry<Integer, Referee> entry : Server.referees.entrySet()) {
			referee = entry.getValue();
			if (referee.getEmail().equals(email)) {
				return referee;
			}
		}
		return null;
	}
	
	private boolean dateConflicts(int matchId, Calendar newDate) {
		Map<Integer,Match> matches = Server.schedule.getMatches();
		Match match = matches.get(matchId);
		
		for (Map.Entry<Integer, Match> entry : Server.schedule.getMatches().entrySet()) {
			int matchId2 = entry.getKey();
			Match match2 = entry.getValue();
			if (matchId2 != matchId && match.isMatchOnDate(match2.getDate())) {
				String team0Name = match.getTeam1().getName();
				String team1Name = match.getTeam2().getName();
				String team2Name = match2.getTeam1().getName();
				String team3Name = match2.getTeam2().getName();
				
				if (team0Name.equals(team2Name) || team0Name.equals(team3Name) ||
				    team1Name.equals(team2Name) || team1Name.equals(team3Name)
				) {
					return true;
				}
			}
		}
		
		return false;
	}
}
