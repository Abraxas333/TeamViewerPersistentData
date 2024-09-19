package Team;

public class carRace extends Team {

    private static String driver;
    private static String car;

    public carRace(String name, String trainer, int score, String game, int id, String driver, String car) {
        super(name, trainer, score, game, id);
        this.driver = driver;
        this.car = car;
    }

    public static String getDriver() {
        return driver;
    }
    public void setDriver(String driver) {
        this.driver = driver;
    }

    public static String getCar() {
        return car;
    }
    public void setCar(String car) {
        this.car = car;
    }
}
