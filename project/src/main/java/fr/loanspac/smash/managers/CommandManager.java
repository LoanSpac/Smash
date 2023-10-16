package fr.loanspac.smash.managers;

import fr.loanspac.smash.Smash;
import fr.loanspac.smash.commands.player.Spawn;
import fr.loanspac.smash.commands.staff.Build;

import java.util.Objects;

public class CommandManager {
    Smash main = Smash.instance();

    public void setCommand(){
        Objects.requireNonNull(main.getCommand("spawn")).setExecutor(new Spawn());
        Objects.requireNonNull(main.getCommand("build")).setExecutor(new Build());
    }
}
