package teamHarambe;

import javafx.application.Application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.Calendar;

public class Server {
	static List<Team> teams = new LinkedList<>();
	static Scanner console = new Scanner(System.in);
	static String globalPassword;
	
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(1234);

		(new Thread(){
			public void run(){
				Application.launch(GUI.ServerMenu.class, args);
			}
		}).start();
		
		System.out.println("Welcome to first time setup!");
		System.out.print("Please create a password: ");
		System.out.println("Your password is " + console.nextLine());
		
		System.out.println("How many teams will play in the debate season?");
		int numTeams = promptInt(2);
		promptTeams(numTeams);
		
		System.out.println("How many referees will manage the season?");
		int numReferees = promptInt(numTeams/2);		
		List<Referee> referees = generateRefereeList(numReferees);
		
		System.out.println("Enter the season start date.");
		Calendar startDate = promptDate();
	
		Schedule schedule = new Schedule(teams, referees);
		
		System.out.println("Server ready to accept clients.");
		
		while (true) {
			Socket s = server.accept();
			Runnable connectionHandler = new ConnectionHandler(s, schedule);
			new Thread(connectionHandler).start();
		}
	}
	
	static int promptInt(int minValue) {
		int input = 0;
		
		do {
			try {
				input =  console.nextInt();
				if (input < minValue) {
					System.out.print("Please input an integer >= " + minValue + ": ");
				}
			}
			catch (NumberFormatException e) {
				System.out.print("Please input an integer >= " + minValue + ": ");
			}
		} while (input < minValue);
		
		return input;
	}
	
	static void promptTeams(int numTeams) {
        System.out.println("Please enter " + numTeams + " team names.");
        for (int i = 0; i < numTeams; i++) {
            System.out.print((i + 1) + ". ");
            teams.add(new Team(console.next()));
            
        }
    }
	
	static Calendar promptDate() {
		Calendar selectedDate = Calendar.getInstance();//shitty Java library
		int selectedYear, selectedMonth, selectedDay;
		int currentYear = selectedDate.get(Calendar.YEAR);
		int currentMonth = selectedDate.get(Calendar.MONTH);
		int currentDay = selectedDate.get(Calendar.DAY_OF_MONTH);
		
		System.out.print("Enter the season start year: ");
		selectedYear = promptInt(currentYear);
		System.out.print("Enter the season start month: ");
		selectedMonth = promptInt(selectedYear == currentYear ? currentMonth+1 : 1)-1;
		System.out.print("Enter the season start day: ");
		selectedDay = promptInt(selectedYear == currentYear && selectedMonth == currentMonth ? currentDay : 1);
		
		selectedDate.set(selectedYear, selectedMonth, selectedDay);
		
		return selectedDate;
	}
	
	private static List<Referee> generateRefereeList(int numReferees) {
		List<Referee> referees = new LinkedList<>();
		Random r = new Random();
		
		for (int i=0; i < numReferees; i ++) {
			referees.add(new Referee(r.nextInt(999999999) + 111111111, i==0));
		}
		
		return referees;
	}
}
