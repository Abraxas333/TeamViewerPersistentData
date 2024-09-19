package Database;

import Team.Player;
import Team.SoccerTeam;
import Team.Team;
import Team.carRace;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class DataUpdater {
    public static void updateTeam(Team team) throws ClassNotFoundException {
        String query = "";

        // If the team is a SoccerTeam
        if (team instanceof SoccerTeam) {
            SoccerTeam soccerTeam = (SoccerTeam) team;

            // Prepare the SQL query for updating team info
            query = "UPDATE team_soccer SET name = ?, trainer = ?, score = ?, game = ? WHERE id = ?";

            try (Connection conn = DatabaseConnection.connect()) {
                if (conn != null) {
                    // First, update the basic team info
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, team.getName());  // name
                    stmt.setString(2, team.getTrainer());  // trainer
                    stmt.setDouble(3, team.getScore());  // score
                    stmt.setString(4, team.getGame());  // game
                    stmt.setInt(5, team.getId());  // id (WHERE clause)

                    int rowsUpdated = stmt.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("Soccer team updated successfully!");
                    }

                    // Now, update each player in the team
                    String playerQuery = "UPDATE soccer_player SET player_role = ?, player_number = ?, player_name = ? WHERE team_name = ? AND player_name = ?";

                    // Prepare the player update statement
                    PreparedStatement playerStmt = conn.prepareStatement(playerQuery);

                    HashMap<String, List<Player>> playerMap = soccerTeam.getPlayerMap();
                    if (playerMap != null) {
                        for (List<Player> players : playerMap.values()) {
                            if (players != null) {
                                for (Player player : players) {
                                    // Print out debugging information for troubleshooting

                                    System.out.println("Old Player Name: " + player.getOldPlayerName());  // Used in WHERE clause
                                    System.out.println("New Player Name: " + player.getPlayerName());    // Used in SET clause
                                    System.out.println("Team Name: " + team.getName());
                                    System.out.println("Player Role: " + player.getPlayerRole());
                                    System.out.println("Player Number: " + player.getPlayerNumber());

                                    // Set the new values, including the new player name
                                    playerStmt.setString(1, player.getPlayerRole());  // player_role
                                    playerStmt.setInt(2, player.getPlayerNumber());   // player_number
                                    playerStmt.setString(3, player.getPlayerName());  // new player_name (to be updated)
                                    playerStmt.setString(4, team.getName());          // team_name
                                    playerStmt.setString(5, player.getOldPlayerName());  // old player_name (used for matching)

                                    int playerRowsUpdated = playerStmt.executeUpdate();
                                    if (playerRowsUpdated > 0) {
                                        System.out.println("Player " + player.getPlayerName() + " updated successfully!");
                                    } else {
                                        System.out.println("No player updated for: " + player.getOldPlayerName());
                                    }
                                }
                            }
                        }
                    } else {
                        System.out.println("No player map found for the team.");
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error while updating SoccerTeam: " + e.getMessage());
            }
        }

        // If the team is a carRace
        else if (team instanceof carRace) {
            carRace carRaceTeam = (carRace) team;
            query = "UPDATE team_car SET name = ?, trainer = ?, score = ?, game = ?, driver_name = ?, car = ? WHERE id = ?";

            try (Connection conn = DatabaseConnection.connect()) {
                if (conn != null) {
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, team.getName());  // name
                    stmt.setString(2, team.getTrainer());  // trainer
                    stmt.setDouble(3, team.getScore());  // score
                    stmt.setString(4, team.getGame());  // game
                    stmt.setString(5, carRaceTeam.getDriver());  // driver_name
                    stmt.setString(6, carRaceTeam.getCar());  // car
                    stmt.setInt(7, team.getId());  // id (WHERE clause)

                    int rowsUpdated = stmt.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("Car race team updated successfully!");
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error while updating carRace: " + e.getMessage());
            }
        }
    }
}
