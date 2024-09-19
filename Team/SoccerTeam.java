package Team;

import java.util.HashMap;
import java.util.List;

public class SoccerTeam extends Team {
    private HashMap<String, List<Player>> playerMap;

    public SoccerTeam(String name, String trainer, int score, String game, int id, HashMap<String, List<Player>> playerMap) {
        super(name, trainer, score, game, id);
        this.playerMap = playerMap != null ? playerMap : new HashMap<>();  // make sure its initialized
    }

    // Getter for playerMap
    public HashMap<String, List<Player>> getPlayerMap() {
        return playerMap;
    }

    // Setter for playerMap
    public void setPlayerMap(HashMap<String, List<Player>> playerMap) {
        this.playerMap = playerMap;
    }
}