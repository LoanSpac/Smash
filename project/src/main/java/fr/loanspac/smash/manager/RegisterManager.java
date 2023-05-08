package fr.loanspac.smash.manager;

import fr.loanspac.smash.Smash;
import fr.loanspac.smash.champion.bario.Bario;
import fr.loanspac.smash.commands.PlayerCommand;
import fr.loanspac.smash.commands.StaffCommand;
import fr.loanspac.smash.commands.StartCommand;
import fr.loanspac.smash.listeners.GlobalListeners;
import fr.loanspac.smash.listeners.PlayerListeners;
import fr.loanspac.smash.listeners.SmashListeners;
import fr.loanspac.smash.listeners.WaitingListeners;
import fr.loanspac.smash.tasks.SmashTask;
import fr.loanspac.smash.tasks.WaitTask;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
 * Nom de classe : RegisterManager
 * Description   : Manager class
 * Version       : 1.0
 * Date          : 04/04/2023
 * Copyright     : LoanSpac
 */

public class RegisterManager {
    Smash main = Smash.getInstance();

    private List<Listener> listeners = new ArrayList<>();

    public void registration(){

        Objects.requireNonNull(main.getCommand("spawn")).setExecutor(new PlayerCommand());
        Objects.requireNonNull(main.getCommand("start")).setExecutor(new StartCommand());
        Objects.requireNonNull(main.getCommand("staff")).setExecutor(new StaffCommand());

        this.listeners.add(new PlayerListeners());
        this.listeners.add(new GlobalListeners());
        this.listeners.add(new WaitingListeners());
        this.listeners.add(new SmashListeners());
        this.listeners.add(new WaitTask());
        this.listeners.add(new SmashTask());
        this.listeners.add(new Bario());
        this.listeners.forEach((listener -> {
            Bukkit.getPluginManager().registerEvents(listener, main);
        }));
    }
}
