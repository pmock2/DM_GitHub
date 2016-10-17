package teamHarambe;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Debate_Management {

    static String password;
    static Scanner in;

    static List<Team> teamlist = new LinkedList<>();
    static List<Referee> refereelist = new LinkedList<>();


    public static void main(String args[]) throws InterruptedException {
        System.out.println("Welcome to the DM system.");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("For testing purposes,");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("the system will create 5 referee objects with random ID numbers.");
        TimeUnit.SECONDS.sleep(3);
        //passwordSet();
        createReferees();
        teamSet();
        createSchedule();
    }

    static String userString() throws InterruptedException {
        in = new Scanner(System.in);
        String newString = null;
        try {
            newString = in.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Please type a number");
            TimeUnit.SECONDS.sleep(1);
            in.next();
        }
        return newString;
    }


    static void teamSet() throws InterruptedException {
        System.out.println("Please enter the ten team names, each followed by the enter key");
        for (int i = 0; i < 10; i++) {
            System.out.print((i + 1) + ". ");
            teamlist.add(new Team(userString(), 0));
        }
        System.out.println("Thank you. Your teams are: ");
        TimeUnit.SECONDS.sleep(1);
        for (int i = 0; i < teamlist.size(); i++) {
            System.out.println((i + 1) + ". " + teamlist.get(i).getName());
        }
    }

    static void createSchedule() throws InterruptedException {
        Schedule schedule = new Schedule(teamlist, refereelist);
        System.out.println("Schedule: ");
        System.out.println(schedule.toString());
            }


    static int userPick() throws InterruptedException
    {
        in = new Scanner(System.in);
        int choice = 0;
        try
        {
            choice = in.nextInt();
        }
        catch(InputMismatchException e)
        {
            System.out.println("Please input a number");
            TimeUnit.SECONDS.sleep(1);
            in.next();
        }
        return choice;
    }

    static void passwordSet() throws InterruptedException
    {
        System.out.println("Please set a super referee password:");
        System.out.println();
        password = userString();
        System.out.println("Password is set to: " + password);
        TimeUnit.SECONDS.sleep(1);
        int userPick;
        System.out.println("Are you sure you want to use this password?\n1. yes\n2. no");
        userPick = userPick();
        while (userPick != 1)
        {
            System.out.println("Please set a super referee password:");
            System.out.println();
            password = userString();
            System.out.println("Password is set to: " + password);
            TimeUnit.SECONDS.sleep(1);
            System.out.println("Are you sure you want to use this password?\n1. yes\n2. no");
            userPick = userPick();
        }
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Password is set");
        TimeUnit.SECONDS.sleep(1);
    }

    static void createReferees() throws InterruptedException
    {
        Random r = new Random();
        Referee a = new Referee(r.nextInt(999999999) + 111111111, true);
        Referee b = new Referee(r.nextInt(999999999) + 111111111, false);
        Referee c = new Referee(r.nextInt(999999999) + 111111111, false);
        Referee d = new Referee(r.nextInt(999999999) + 111111111, false);
        Referee e = new Referee(r.nextInt(999999999) + 111111111, false);

        refereelist.add(a);
        refereelist.add(b);
        refereelist.add(c);
        refereelist.add(d);
        refereelist.add(e);


    }

        }
