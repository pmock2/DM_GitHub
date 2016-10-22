package teamHarambe;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Server {
	static List<Team> teams = new LinkedList<>();
	static Scanner console = new Scanner(System.in);
	static String globalPassword;
	
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(1234);
		int numTeams = 0;		
		
		System.out.println("Welcome to first time setup!");
		System.out.print("Please create a password: ");
		System.out.println("Your password is " + console.nextLine());
		System.out.println("How many teams will play in the debate season?");
		boolean valid = false;
		while(numTeams < 2)
		{
			try
			{
				numTeams =  (int) Double.parseDouble(console.nextLine());

			}
			//checks input to make sure it is a number
			catch (NumberFormatException ex)
			{
				System.out.println("Invalid input, try again:");
			}
		}
		
//		try 
//		{
//			numTeams = console.nextInt();
//		}
//		catch(InputMismatchException e)
//		{
//			console.next();
//		}
		promptTeams(numTeams);
		
		List<Referee> referees = generateRefereeList();
		Schedule schedule = new Schedule(teams, referees);
		
		System.out.println("Server ready to accept clients.");
		
		while (true) {
			Socket s = server.accept();
			Runnable connectionHandler = new ConnectionHandler(s, schedule);
			new Thread(connectionHandler).start();
		}
	}
	
	static void promptTeams(int numTeams) {
        System.out.println("Please enter " + numTeams + " team names.");
        for (int i = 0; i < numTeams; i++) {
            System.out.print((i + 1) + ". ");
            
            teams.add(new Team(console.next()));
            
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
	
	/*
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
    }*/
}