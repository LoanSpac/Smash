package fr.loanspac.smash.game;

/*
 * Nom de classe : EGames
 * Description   : List of games
 * Version       : 1.0
 * Date          : 04/04/2023
 * Copyright     : LoanSpac
 */

public enum EGames {
    ///////////////////////GAME ENUMERATION///////////////////////
    WAITING("En attente", -1),
    SMASH("En jeu", -1),
    BUILD("Build", -1),
    END("Fin", -1);
    /////////////////////////////////////////////////////////////

    ///////////////////////GAME VARIABLES////////////////////////
    private final String name;
    private static EGames currentState;
    private final Integer duration;
    /////////////////////////////////////////////////////////////

    ///////////////////////GAME CONSTRUCTOR//////////////////////
    EGames(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }
    /////////////////////////////////////////////////////////////

    ///////////////////////GAME METHODS//////////////////////////
    public static EGames getCurrentState() {
        return currentState;
    }

    public static void setState(EGames currentState) {
        EGames.currentState = currentState;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getName() {
        return name;
    }
    /////////////////////////////////////////////////////////////
}
