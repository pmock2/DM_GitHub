package teamHarambe;
import javafx.application.Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket s = new Socket("127.0.0.1", 1234);
		BufferedReader fromServer = new BufferedReader(new InputStreamReader(s.getInputStream()));
		PrintStream toServer = new PrintStream(s.getOutputStream());

		//START INITIAL CHECK FOR SERVER SETUP
		if (MethodProvider.checkForSetup() == false)
		{
			Application.launch(GUI.SuperUserSetup_1.class, args);
		}
		else
		{
			Application.launch(GUI.Main_3.class, args);
		}



		toServer.println("Get_Schedule");
		System.out.println("Sent request for schedule to server.");
		while (true) {
			System.out.println(fromServer.readLine());
		}
	}
}
