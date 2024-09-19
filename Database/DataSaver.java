package Database;

import Team.Player;
import Team.SoccerTeam;
import Team.Team;
import Team.carRace;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DataSaver {

    public static void saveTeam(Team team) throws ClassNotFoundException {
        String query = "";
        String query2 = "";

        if (team instanceof SoccerTeam) {
            query = "INSERT INTO team_soccer(name, trainer, score, game, id) VALUES(?,?,?,?,?)";
            query2 = "INSERT INTO soccer_player(team_name, player_name, player_role, player_number) VALUES(?,?,?,?)";
        } else if (team instanceof carRace) {
            query = "INSERT INTO team_car(name, trainer, score, game, id, driver, car) VALUES(?,?,?,?,?,?,?)";
        }

        Connection conn = null;
        try {
            // Get the database connection and start transaction
            conn = DatabaseConnection.connect();
            conn.setAutoCommit(false); // Disable auto-commit for transaction management

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, team.getName());
            stmt.setString(2, team.getTrainer());
            stmt.setDouble(3, team.getScore());
            stmt.setString(4, team.getGame());
            stmt.setInt(5, team.getId());

            // If it's a carRace team, set the additional parameters (driver and car)
            if (team instanceof carRace) {
                carRace raceTeam = (carRace) team;
                stmt.setString(6, raceTeam.getDriver());
                stmt.setString(7, raceTeam.getCar());
            }

            stmt.executeUpdate();  // Execute the statement for both soccer and car race teams

            // If it's a soccer team, save the players
            if (team instanceof SoccerTeam) {
                SoccerTeam soccerTeam = (SoccerTeam) team;

                // Prepare statement for players
                PreparedStatement stmt2 = conn.prepareStatement(query2);

                // Iterate through the playerMap to save players
                for (List<Player> players : soccerTeam.getPlayerMap().values()) {
                    for (Player player : players) {
                        stmt2.setString(1, team.getName());  // Use team name for team association
                        stmt2.setString(2, player.getPlayerName());
                        stmt2.setString(3, player.getPlayerRole());
                        stmt2.setInt(4, player.getPlayerNumber());

                        stmt2.executeUpdate();
                    }
                }
            }

            // Commit the transaction if everything succeeds
            conn.commit();

            System.out.println("New Team inserted successfully!");

        } catch (SQLException e) {
            // Roll back the transaction if something goes wrong
            if (conn != null) {
                try {
                    conn.rollback();
                    System.err.println("Transaction rolled back due to error: " + e.getMessage());
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }
        } finally {
            // Ensure connection is closed properly
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Restore auto-commit mode
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
