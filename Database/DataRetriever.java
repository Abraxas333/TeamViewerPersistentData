package Database;

import Team.Player;
import Team.SoccerTeam;
import Team.carRace;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static Main_package.Main.members;

public class DataRetriever {

    public static void getTeams() {
        try (Connection conn = DatabaseConnection.connect()) {
            if (conn != null) {
                // Query to retrieve soccer teams
                String soccerTeamQuery = "SELECT * from team_soccer";

                // Query to retrieve players based on team_name
                String playerQuery = "SELECT * from soccer_player WHERE team_name = ?";

                // Query to retrieve car race teams
                String carRaceQuery = "SELECT * from team_car";

                // Retrieve soccer teams
                PreparedStatement soccerStmt = conn.prepareStatement(soccerTeamQuery);
                ResultSet soccerRs = soccerStmt.executeQuery();

                while (soccerRs.next()) {
                    // Get team details from the result set
                    String name = soccerRs.getString("name");
                    String trainer = soccerRs.getString("trainer");
                    int score = soccerRs.getInt("score");
                    int id = soccerRs.getInt("id");
                    String game = soccerRs.getString("game");

                    if (game.equalsIgnoreCase("soccer")) {
                        // Prepare the statement to query players for this soccer team
                        PreparedStatement playerStmt = conn.prepareStatement(playerQuery);
                        playerStmt.setString(1, name);  // Bind the team_name value

                        // Execute the query for players
                        ResultSet playerRs = playerStmt.executeQuery();

                        // Initialize the player map to pass to the constructor
                        HashMap<String, List<Player>> playerMap = new HashMap<>();
                        List<Player> players = new ArrayList<>();  // List to hold players for this team

                        // Loop through the player results and add them to the players list
                        while (playerRs.next()) {
                            String playerName = playerRs.getString("player_name");
                            String playerRole = playerRs.getString("player_role");
                            int playerNumber = playerRs.getInt("player_number");

                            // Create a new player and set both playerName and oldPlayerName explicitly
                            Player player = new Player(playerName, playerRole, playerNumber);
                            player.setOldPlayerName(playerName);  // Explicitly set oldPlayerName to the original name
                            players.add(player);  // Add player to the list
                        }

                        // Add the list of players to the playerMap using the team name as the key
                        playerMap.put(name, players);

                        // Create SoccerTeam with the correct constructor
                        SoccerTeam team = new SoccerTeam(name, trainer, score, game, id, playerMap);
                        members.add(team);  // Add the soccer team to the members list

                        // Close player-related resources
                        playerRs.close();
                        playerStmt.close();
                    }
                }

                // Retrieve car race teams
                PreparedStatement carRaceStmt = conn.prepareStatement(carRaceQuery);
                ResultSet carRaceRs = carRaceStmt.executeQuery();

                while (carRaceRs.next()) {
                    String name = carRaceRs.getString("name");
                    String trainer = carRaceRs.getString("trainer");
                    int score = carRaceRs.getInt("score");
                    int id = carRaceRs.getInt("id");
                    String game = carRaceRs.getString("game");
                    String driver = carRaceRs.getString("driver");
                    String car = carRaceRs.getString("car");

                    // Create the car race team
                    carRace team = new carRace(name, trainer, score, game, id, driver, car);
                    members.add(team);  // Add car race team to the members list
                }

                // Close car race related resources
                carRaceRs.close();
                carRaceStmt.close();

                // Close the main result set and statement for teams
                soccerRs.close();
                soccerStmt.close();
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving teams: " + e.getMessage());
        }
    }
}
