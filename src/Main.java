import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        ProxyGroups proxyGroups = new ProxyGroups();
        boolean exit = false;
        boolean back;
        ListeningThread listeningThread = new ListeningThread(proxyGroups);
        listeningThread.start();

        while (!exit) {

            back = false;
            System.out.println("!!MY OWN PROXY SERVER!!");
            System.out.println("Press M to Manage groups");
            Scanner sc = new Scanner(System.in);
            String choice = sc.next();

            if (choice.equals("M") || choice.equals("m")) {
                while (!back) {
                    System.out.println("-----------------------------");
                    System.out.println("1.List of existing groups");
                    System.out.println("2.Add a new group");
                    System.out.println("3.Add a host to a group");
                    System.out.println("4.Delete a group");
                    System.out.println("5.Delete a host from a group");
                    System.out.println("6.back");
                    System.out.println("-----------------------------");
                    int n = sc.nextInt();
                    switch (n) {
                        case 1:
                            proxyGroups.printGroups();
                            break;
                        case 2:
                            System.out.println("enter group name");
                            String newGroup = sc.next();
                            int groupNumber = proxyGroups.allGroups.size();
                            System.out.println("do you want me to filter this group?! Y/N");
                            boolean isActive = true;
                            String res = sc.next();
                            if (res.startsWith("Y") || res.startsWith("y")) {
                                isActive = false;
                            }
                            HostGroup hostGroup = new HostGroup(groupNumber, newGroup, isActive);
                            proxyGroups.add(hostGroup);
                            break;
                        case 3:
                            System.out.println("enter host domain");
                            String newHost = sc.next();
                            System.out.println("enter the name of desired group");
                            String gpname = sc.next();
                            HostGroup gp = proxyGroups.search(gpname);
                            if (gp != null) {
                                gp.addHost(newHost);
                            } else {
                                System.out.println("group not found");
                            }
                            break;
                        case 4:
                            System.out.println("enter group name");
                            String group = sc.next();
                            proxyGroups.deleteGroup(group);
                            break;
                        case 5:
                            System.out.println("enter host name");
                            String host = sc.next();
                            System.out.println("enter it's group");
                            String groupname = sc.next();
                            HostGroup hostGroup1 = proxyGroups.search(groupname);
                            if (hostGroup1 != null) {
                                hostGroup1.deleteHost(host);
                            } else {
                                System.out.println("group not found");
                            }
                            break;
                        case 6:
                            back = true;
                            break;
                    }
                }

            } else if (choice.equals("E") || choice.equals("e")) {
                exit = true;
            }


        }
    }
}
