package teamHarambe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ConnectionHandler implements Runnable {
	Socket s;
	BufferedReader fromClient;
	PrintStream toClient;
	
	ConnectionHandler(Socket s) throws IOException {
		this.s = s;
		this.fromClient = new BufferedReader(new InputStreamReader(s.getInputStream()));
		this.toClient = new PrintStream(s.getOutputStream());
	}
	
	public void run() {
		while (true) {
			try {
				String message = fromClient.readLine();
				System.out.println("Command from client: " + message);
				if (message.equals("Team_Names")) {
					List<Team> teams = new LinkedList<>();
					List<Referee> referees = generateRefereeList();
					Schedule schedule;
					
					while (true) {
						message = fromClient.readLine();
						if (message.equals("End_Team_Names")) {
							break;
						} else {
							teams.add(new Team(message));
						}
					}
					
					schedule = new Schedule(teams, referees);
					toClient.println(schedule.toString());
					System.out.println("Sent info to client");
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
	
	private static List<Referee> generateRefereeList() {
		List<Referee> referees = new LinkedList<>();
		Random r = new Random();
		int numReferees = 10;
		
		for (int i=0; i < numReferees; i ++) {
			referees.add(new Referee(r.nextInt(999999999) + 111111111, i==0));
		}
		
		return referees;
	}
}
