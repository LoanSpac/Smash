package fr.loanspac.smash.managers;

import fr.loanspac.smash.Smash;
import fr.loanspac.smash.game.SmashGame;
import fr.loanspac.smash.listeners.GlobalListener;
import fr.loanspac.smash.listeners.PlayerListener;
import fr.loanspac.smash.listeners.SmashListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class ListenerManager {
    Smash main = Smash.instance();

    private List<Listener> listeners = new ArrayList<>();

    public void registerEvents(){
        this.listeners.add(new PlayerListener());
        this.listeners.add(new GlobalListener());
        this.listeners.add(new SmashListener());
        this.listeners.add(new SmashGame());
        this.listeners.forEach((listener -> {
            Bukkit.getPluginManager().registerEvents(listener, main);
        }));
    }
}
