package Main_package;

import Database.DataRetriever;
import Database.DataSaver;
import Database.DataUpdater;
import Team.Team;
import Team.SoccerTeam;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static List<Team> members = new ArrayList<>();

    public static void main(String[] args) throws ClassNotFoundException {
        // Team creation
        Scanner sc = new Scanner(System.in);
        boolean finished = false;
        DataRetriever.getTeams();

        while (!finished) {
            boolean exit = false;
            String message = "To create a Team input 'add', to continue to the Team management dialogue name 'manage', to end the program name 'end'.";
            String input = Manager.takeInput(message, sc);

            if (input.equalsIgnoreCase("add")) {
                Team team = Team.addTeam();
                members.add(team);
                DataSaver.saveTeam(team);
                System.out.println(team.getGame() + " Team added.");
            } else if (input.equalsIgnoreCase("manage")) {

                while (!exit) {
                    message = "Choose from available options: update, retrieve, exit";
                    Team.listTeam(members);
                    String action = Manager.takeInput(message, sc);

                    switch (action.toLowerCase()) {

                        case "update":
                            message = "Enter the name of the Team to update:";
                            String updateName = Manager.takeInput(message, sc);
                            Team teamToUpdate = Team.findTeamByName(members, updateName);

                            if (teamToUpdate != null) {
                                Team.retrieveTeamInfo(teamToUpdate);

                                // Ensure it's a soccer team or any other type
                                if (teamToUpdate instanceof SoccerTeam) {
                                    SoccerTeam soccerTeam = (SoccerTeam) teamToUpdate;

                                    // Ensure the playerMap is not null
                                    if (soccerTeam.getPlayerMap() == null) {
                                        System.out.println("No players found for this team.");
                                    } else {
                                        // Call updateTeam to update both players and general attributes
                                        Team updatedTeam = Team.updateTeam(soccerTeam, sc);

                                        DataUpdater.updateTeam(updatedTeam);
                                        members.remove(teamToUpdate);
                                        members.add(updatedTeam);
                                    }
                                } else {
                                    // Update other team types
                                    Team updatedTeam = Team.updateTeam(teamToUpdate, sc);
                                    members.add(updatedTeam);
                                    DataUpdater.updateTeam(updatedTeam);
                                }
                            } else {
                                System.out.println("Team not found.");
                            }
                            break;

                        case "retrieve":
                            System.out.println("Enter the name of the Team to retrieve:");
                            String retrieveName = sc.nextLine();
                            Team teamToRetrieve = Team.findTeamByName(members, retrieveName);
                            if (teamToRetrieve != null) {
                                Team.retrieveTeamInfo(teamToRetrieve);
                            } else {
                                System.out.println("Team not found.");
                            }
                            break;

                        case "exit":
                            System.out.println("To end the program type 'end', to return type 'return'.");
                            String loop = sc.nextLine();
                            if (loop.equalsIgnoreCase("end")) {
                                finished = true; // End the outer loop
                            } else if (loop.equalsIgnoreCase("return")) {
                                exit = true; // Return to the main menu
                            }
                            break;

                        default:
                            System.out.println("Invalid action. Please try again.");
                            break;
                    }
                }
            } else if (input.equalsIgnoreCase("end")) {
                finished = true;
            } else {
                System.out.println("Invalid input please try again");
            }
        }
        sc.close();
    }
}
