package teamHarambe;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Debate_Management {

    static String password;
    static Scanner in;

    static List<Team> teamlist = new LinkedList<>();
    static List<Match> matchlist = new LinkedList<>();
    static List<Match> schedulelist = new LinkedList<>();
    static boolean add = false;


    public static void main(String args[]) throws InterruptedException {
        System.out.println("Welcome to the DM system.");
        TimeUnit.SECONDS.sleep(1);
        passwordSet();
        teamSet();
        createSchedule();
        printSchedule();

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
            teamlist.add(new Team(userString(), 0, false));
        }
        System.out.println("Thank you. Your teams are: ");
        TimeUnit.SECONDS.sleep(1);
        for (int i = 0; i < teamlist.size(); i++) {
            System.out.println((i + 1) + ". " + teamlist.get(i).getName());
        }
    }

    static void createSchedule() throws InterruptedException {
        System.out.println("Creating schedule");
        TimeUnit.SECONDS.sleep(1);
                int r1 = (int) (Math.random() * teamlist.size());
                int r2 = (int) (Math.random() * teamlist.size());
                    while (r1 == r2)
                    {
                        r2 = (int) (Math.random() * teamlist.size());
                    }
                    Match temp = new Match(teamlist.get(r1), teamlist.get(r2));
                    matchlist.add(temp);
                while (matchlist.size() < 45)
                {
                    //create random temporary match
                    r1 = (int) (Math.random() * teamlist.size());
                    r2 = (int) (Math.random() * teamlist.size());
                    while (r1 == r2)
                    {
                        r2 = (int) (Math.random() * teamlist.size());
                    }
                    temp = new Match(teamlist.get(r1), teamlist.get(r2));
                    add = true;

                    //compare temporary match to every match in the match list
                    for (Match m : matchlist)
                    {
                        if (m.getTeam1().getName().equals(temp.getTeam1().getName()) && m.getTeam2().getName().equals(temp.getTeam2().getName()))
                        {
                            add = false;
                        }
                        else if (m.getTeam1().getName().equals(temp.getTeam2().getName()) && m.getTeam2().getName().equals(temp.getTeam1().getName()))
                        {
                            add = false;
                        }
                    }
                    if (add)
                    {
                        matchlist.add(temp);
                    }
                }
                    System.out.println("Total amount of matches: " + matchlist.size());
            }



    static void printSchedule() throws InterruptedException {
        for(int j = 0; j < matchlist.size(); j++)
        {
            schedulelist.add(matchlist.get(j));
        }

        for (int i = 0; i < 9; i++)
        {
            System.out.println();
            System.out.println("Week " + (i + 1) + ": ");
            TimeUnit.SECONDS.sleep(1);
            resetIsPlaying(schedulelist);
            for (int h = 0; h < 5; h++)
            {
                Match temp = getFreshMatch(schedulelist);
                System.out.println(temp.getTeam1().getName() + " vs " + temp.getTeam2().getName());
                temp.getTeam1().isPlayingCurrently = true;
                temp.getTeam2().isPlayingCurrently = true;
                schedulelist.remove(temp);
            }
        }
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

    static Match getFreshMatch(List<Match> e)
    {
        while(true)
        {
        for (Match m : e) {
            if (!m.getTeam1().isPlayingCurrently && !m.getTeam2().isPlayingCurrently) {
                return m;
            }
        }

        }
    }

    static void resetIsPlaying(List<Match> e)
    {
        for (Match m : e)
        {
            m.getTeam1().isPlayingCurrently = false;
            m.getTeam2().isPlayingCurrently = false;
        }
    }
        }
