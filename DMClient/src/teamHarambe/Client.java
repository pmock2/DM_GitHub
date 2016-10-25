package teamHarambe;

import javafx.application.Application;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

public class Client {

	static Socket s;
	static BufferedReader fromServer;
	static PrintStream toServer;
	static boolean serverRunning = false;

	public static void main(String[] args) throws UnknownHostException, IOException {
		try {
			s = new Socket("127.0.0.1", 1234);
			fromServer = new BufferedReader(new InputStreamReader(s.getInputStream()));
			toServer = new PrintStream(s.getOutputStream());
			serverRunning = true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "No server detected. Please make sure the server is running.", "Server error", JOptionPane.ERROR_MESSAGE);
		}

		if (serverRunning) {

			if (MethodProvider.checkForSetup()) {
				(new Thread() {
					public void run() {
						Application.launch(GUI.Main_3.class, args);
					}
				}).start();
			} else {
				(new Thread(){
					public void run(){
						Application.launch(GUI.SuperUserSetup_1.class, args);
					}
				}).start();
			}

			toServer.println("Get_Schedule");
			System.out.println("Sent request for schedule to server.");
			List<String> lines = new LinkedList<>();
			String message = fromServer.readLine();
			while (!message.equals("End_Schedule")) {
				System.out.println(message);
				lines.add(message);
				message = fromServer.readLine();
			}
			Schedule schedule = new Schedule(lines);
			System.out.println("Received schedule");
		}
	}
}
