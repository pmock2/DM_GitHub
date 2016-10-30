package teamHarambe;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
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
							toClient.println(Server.schedule.toJSON());
							toClient.println("End_Schedule");
							System.out.println("Sent schedule to client");
							break;
					}
					case "Login":
					{
						String email = fromClient.readLine();
						String password = fromClient.readLine();
						Referee loginAs = refereeFromEmail(email);

						if (loginAs != null && loginAs.verifyPassword(password)) {
							userAccount = loginAs;
							permissionLevel = (userAccount.isSuperReferee ? 2 : 1);
							toClient.println("Login_Success");
							toClient.println(permissionLevel);
						} else {
							toClient.println("Login_Fail");
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
						if (dbFile.exists())
						{
							toClient.println("true");
						}
						else
						{
							toClient.println("false");
						}
						break;
					}
					case "Does_Schedule_Exist":
					{
						if (dbFile.exists())
						{
							JSONObject database = new JSONObject(Server.readFile(databasePath, StandardCharsets.UTF_8));
							if (database.getJSONObject("Schedule") != null)
							{
								toClient.println("true");
								break;
							}
						}
						toClient.println("false");
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
			if (Server.referees.get(i).getEmail().equals(email)) {
				return Server.referees.get(i);
			}
		}
		
		return referee;
	}
}
