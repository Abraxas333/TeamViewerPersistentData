package Team;

import Main_package.Manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public abstract class Team {
    // class fields
    private String name;
    private String trainer;
    private Integer score;
    private String game;
    private int id;
    private static int instanceCount = 0;

    // class constructor
    public Team(String name, String trainer, int score, String game, int id) {
        this.name = name;
        this.trainer = trainer;
        this.score = score;
        this.game = game;
        this.id = id;
        instanceCount++;
    }

    // getter and setter
    public static int getCount() {
        return instanceCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    // class methods
    public static Team addTeam() {
        Scanner sc = new Scanner(System.in);

        // Input team name
        String message = "Input team name: ";
        String name = Manager.takeInput(message, sc);

        // Input team trainer
        message = "Please input trainer (only alphanumeric characters allowed): ";
        String trainer = Manager.takeInput(message, sc);

        // Input team id
        message = "Please input team ID (only numbers allowed): ";
        int id = Manager.takeIntegerInput(message, sc);

        // Input team score
        message = "Please input score (only numbers allowed):";
        int score = Manager.takeIntegerInput(message, sc);
        sc.nextLine();
        // Input team game
        message = "Please input game (either soccer or car race): ";
        String game = Manager.takeInput(message, sc);

        // Add the team based on the game type
        switch (game.toLowerCase()) {
            case "soccer":
                return addSoccerTeam(name, trainer, score, game, id);
            case "car race":
                return addCarRaceTeam(name, trainer, score, game, id);
            default:
                System.out.println("Unexpected game type.");
                return null;
        }
    }

    // Method to create soccer Team
    public static SoccerTeam addSoccerTeam(String name, String trainer, int score, String game, int id) {
        Scanner sc = new Scanner(System.in);
        HashMap<String, List<Player>> playersMap = new HashMap<>();  // map from team name to a list of players
        List<Player> players = new ArrayList<>();  // the list for storing players for the currents team
        boolean finished = false;

        while (!finished) {
            String message = "Please enter player name:";
            String playerName = Manager.takeInput(message, sc);

            message = "Please enter player role: ";
            String playerRole = Manager.takeInput(message, sc);

            message = "Please enter player number: ";
            int playerNumber = Manager.takeIntegerInput(message, sc);
            sc.nextLine();

            /
            Player player = new Player(playerName, playerRole, playerNumber);
            players.add(player);  // add player to the list

            // Ask if user wants to add another player
            System.out.println("For creating an additional player press any key, to exit type 'q': ");
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("q")) {
                finished = true;
            }
        }
        // after adding all players, set the team name as key for the players list
        playersMap.put(name, players);

        return new SoccerTeam(name, trainer, score, game, id, playersMap);
    }



    // Method to create car race team
    public static Team addCarRaceTeam(String name, String trainer, int score, String game, int id) {
        Scanner sc = new Scanner(System.in);

        String message = "Please enter driver name: ";
        String driver = Manager.takeInput(message, sc);

        message = "Please enter car name: ";
        String car = Manager.takeInput(message, sc);

        Team carRace = new carRace(name, trainer, score, game, id, driver, car);

        return carRace;
    }

    // Method to update Team
    public static Team updateTeam(Team teamToUpdate, Scanner sc) {
        System.out.println("Updating team: " + teamToUpdate.getName());

        // update general team information
        System.out.print("Enter new team name (leave blank to keep the same): ");
        String newTeamName = sc.nextLine();
        if (!newTeamName.isEmpty()) {
            teamToUpdate.setName(newTeamName);
        }

        System.out.print("Enter new trainer (leave blank to keep the same): ");
        String newTrainer = sc.nextLine();
        if (!newTrainer.isEmpty()) {
            teamToUpdate.setTrainer(newTrainer);
        }

        System.out.print("Enter new score: ");
        int newScore = sc.nextInt();
        sc.nextLine();
        teamToUpdate.setScore(newScore);

        System.out.print("Enter new game: ");
        String newGame = sc.nextLine();
        teamToUpdate.setGame(newGame);

        // if a SoccerTeam, handle player updates
        if (teamToUpdate instanceof SoccerTeam) {
            SoccerTeam soccerTeam = (SoccerTeam) teamToUpdate;
            List<Player> players = soccerTeam.getPlayerMap().get(soccerTeam.getName());

            // if player list is null, initialize a new list
            if (players == null) {
                players = new ArrayList<>();
                soccerTeam.getPlayerMap().put(soccerTeam.getName(), players);
            }

            // iterate through each player and update
            for (int i = 0; i < players.size(); i++) {
                Player player = players.get(i);
                System.out.println("Updating player: " + player.getPlayerName());

                System.out.print("Enter new player name (leave blank to keep the same): ");
                String newPlayerName = sc.nextLine();
                if (!newPlayerName.isEmpty()) {
                    player.setPlayerName(newPlayerName);
                }

                String message = "Enter new player role: ";
                String newPlayerRole = Manager.takeInput(message, sc);
                player.setPlayerRole(newPlayerRole);

                message = "Enter new player number: ";
                int newPlayerNumber = Manager.takeIntegerInput(message, sc);
                player.setPlayerNumber(newPlayerNumber);
            }
        }

        return teamToUpdate;
    }

    public static Team findTeamByName(List<Team> teams, String name) {
        for (Team team : teams) {
            if (team.getName().equalsIgnoreCase(name)) {
                return team;
            }
        }
        return null;
    }

    public static void listTeam(List<Team> teams) {
        if (teams.isEmpty()) {
            System.out.println("No teams available.");
            return;
        }

        System.out.println("Available teams:");
        for (Team team : teams) {
            System.out.println("- " + team.getName() + " (" + team.getGame() + ")");
        }
    }

    public static void retrieveTeamInfo(Team team) {
        if (team instanceof SoccerTeam) {
            SoccerTeam soccerTeam = (SoccerTeam) team;

            System.out.println("name: " + soccerTeam.getName());
            System.out.println("trainer: " + soccerTeam.getTrainer());
            System.out.println("score: " + soccerTeam.getScore());
            System.out.println("game: " + soccerTeam.getGame());

            // Retrieve the list of players associated with the team name
            List<Player> players = soccerTeam.getPlayerMap().get(soccerTeam.getName());

            // Iterate over the list of players and print their details
            for (Player player : players) {
                System.out.println("Player name: " + player.getPlayerName());
                System.out.println("Player role: " + player.getPlayerRole());
                System.out.println("Player number: " + player.getPlayerNumber());
            }
        }
    }
}
