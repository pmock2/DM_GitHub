package teamHarambe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Server {

	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(1234);
		Socket s = server.accept();
		BufferedReader fromClient = new BufferedReader(new InputStreamReader(s.getInputStream()));
		PrintStream toClient = new PrintStream(s.getOutputStream());
		
		while (true) {
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
		}
	}

	private static List<Referee> generateRefereeList() {
		List<Referee> referees = new LinkedList<>();
		Random r = new Random();
		int numReferees = 5;
		
		for (int i=0; i < numReferees; i ++) {
			referees.add(new Referee(r.nextInt(999999999) + 111111111, i==0));
		}
		
		return referees;
	}
}