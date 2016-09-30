 import java.util.*;
import java.util.concurrent.TimeUnit;

public class Debate {

    static Scanner in;

    static List<Team> teamlist = new LinkedList<>();
    static List<Match> matchlist = new LinkedList<>();
    static List<Match> schedulelist = new LinkedList<>();
    static boolean add = false;


    public static void main(String args[]) throws InterruptedException {
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
            teamlist.add(new Team(userString(), 0));
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
                    r1 = (int) (Math.random() * teamlist.size());
                    r2 = (int) (Math.random() * teamlist.size());
                    while (r1 == r2)
                    {
                        r2 = (int) (Math.random() * teamlist.size());
                    }
                    temp = new Match(teamlist.get(r1), teamlist.get(r2));
                    add = true;
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
            for (int h = 0; h < 5; h++)
            {
                System.out.println(schedulelist.get(0).getTeam1().getName() + " vs " + schedulelist.get(0).getTeam2().getName());
                schedulelist.remove(0);
            }
        }
    }
        }
