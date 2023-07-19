package fr.loanspac.smash.team;

public enum Team {
    ///////////////////////TEAM ENUMERATION///////////////////////
    RED("Red Team"),
    BLUE("Blue Team"),
    NEUTRAL("No Team");
    /////////////////////////////////////////////////////////////

    ///////////////////////TEAM VARIABLES////////////////////////
    private final String name;
    private static fr.loanspac.smash.team.Team currentState;
    /////////////////////////////////////////////////////////////

    ///////////////////////TEAM CONSTRUCTOR//////////////////////
    Team(String name) {
        this.name = name;
    }
    /////////////////////////////////////////////////////////////

    ///////////////////////TEAM METHODS//////////////////////////
    public static fr.loanspac.smash.team.Team getCurrentState() {
        return currentState;
    }

    public static Team setState(Team currentState) {
        fr.loanspac.smash.team.Team.currentState = currentState;
        return currentState;
    }

    public String getName() {
        return name;
    }
    /////////////////////////////////////////////////////////////
}