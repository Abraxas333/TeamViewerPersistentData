package Team;

public class Player {
    private String playerName;     // The current (possibly updated) player name
    private String oldPlayerName;  // The original name fetched from the database
    private String playerRole;
    private int playerNumber;

    // Constructor sets both playerName and oldPlayerName initially the same
    public Player(String playerName, String playerRole, int playerNumber) {
        this.playerName = playerName;
        this.oldPlayerName = playerName;  // Save the original name from the database (only set once)
        this.playerRole = playerRole;
        this.playerNumber = playerNumber;
    }

    // Getter for old player name (should not be updated by the user)
    public String getOldPlayerName() {
        return oldPlayerName;  // Old name remains unchanged
    }

    // Setter for old player name (used when retrieving player from the database)
    public void setOldPlayerName(String oldPlayerName) {
        this.oldPlayerName = oldPlayerName;  // Set the old player name, e.g., when loading from the database
    }

    // Getter and setter for current player name (can be updated)
    public String getPlayerName() {
        return playerName;  // The current name, which might be updated by the user
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;  // Only updates the new player name, NOT the old name
    }

    // Getters and setters for role and number
    public String getPlayerRole() {
        return playerRole;
    }

    public void setPlayerRole(String playerRole) {
        this.playerRole = playerRole;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }
}
