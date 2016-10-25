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
	Schedule schedule;
	BufferedReader fromClient;
	PrintStream toClient;
	
	//TODO store schedule in database and have ConnectionHandler read from db
	ConnectionHandler(Socket s, Schedule schedule) throws IOException {
		this.s = s;
		this.schedule = schedule;
		this.fromClient = new BufferedReader(new InputStreamReader(s.getInputStream()));
		this.toClient = new PrintStream(s.getOutputStream());
	}
	
	public void run() {
		while (true) {
			try {
				String message = fromClient.readLine();
				System.out.println("Command from client: " + message);
				if (message.equals("Get_Schedule")) {
					toClient.println(schedule.toString());
					toClient.println("End_Schedule");
					System.out.println("Sent schedule to client");
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
}
