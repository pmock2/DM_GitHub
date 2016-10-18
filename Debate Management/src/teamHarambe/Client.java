package teamHarambe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Client {
	static List<String> teamNames = new LinkedList<>();
	static Scanner console = new Scanner(System.in);
	static String globalPassword;

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket s = new Socket("127.0.0.1", 1234);
		BufferedReader fromServer = new BufferedReader(new InputStreamReader(s.getInputStream()));
		PrintStream toServer = new PrintStream(s.getOutputStream());
		
		promptTeams();
		toServer.println("Team_Names");
		for (int i=0; i < teamNames.size(); i++) {
			toServer.println(teamNames.get(i));
		}
		toServer.println("End_Team_Names");
		System.out.println("Sent info to server");
		while (true) {
			System.out.println(fromServer.readLine());
		}
	}
	
	static void promptTeams() {
        System.out.println("Please enter the ten team names, each followed by the enter key.");
        for (int i = 0; i < 10; i++) {
            System.out.print((i + 1) + ". ");
            teamNames.add(console.next());
        }
    }
	
	static int userPick() throws InterruptedException
    {
        int choice = 0;
        try
        {
            choice = console.nextInt();
        }
        catch(InputMismatchException e)
        {
            System.out.println("Please input a number");
            TimeUnit.SECONDS.sleep(1);
            console.next();
        }
        return choice;
    }
	
	static String userString() throws InterruptedException {
        String newString = null;
        try {
            newString = console.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Please type a number");
            TimeUnit.SECONDS.sleep(1);
            console.next();
        }
        return newString;
    }

    static void passwordSet() throws InterruptedException
    {
        System.out.println("Please set a super referee password:");
        System.out.println();
        globalPassword = userString();
        System.out.println("Password is set to: " + globalPassword);
        TimeUnit.SECONDS.sleep(1);
        int userPick;
        System.out.println("Are you sure you want to use this password?\n1. yes\n2. no");
        userPick = userPick();
        while (userPick != 1)
        {
            System.out.println("Please set a super referee password:");
            System.out.println();
            globalPassword = userString();
            System.out.println("Password is set to: " + globalPassword);
            TimeUnit.SECONDS.sleep(1);
            System.out.println("Are you sure you want to use this password?\n1. yes\n2. no");
            userPick = userPick();
        }
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Password is set");
        TimeUnit.SECONDS.sleep(1);
    }

}