package fr.loanspac.smash.game;

public enum GameType {
    WAITING("En attente", -1),
    SMASH("En jeu", -1),
    BUILD("Build", -1),
    END("Fin", -1);

    private final String name;
    private static GameType currentState;
    private final Integer duration;

    GameType(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    public static GameType getCurrentState() {
        return currentState;
    }

    public static void setState(GameType currentState) {
        GameType.currentState = currentState;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getName() {
        return name;
    }
}
