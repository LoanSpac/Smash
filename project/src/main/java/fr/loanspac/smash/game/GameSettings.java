package fr.loanspac.smash.game;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GameSettings {
    public static final String prefix = ChatColor.translateAlternateColorCodes('&', "§cSmash §8» ");

    // LOCALHOST

    //public static final Location spawn = new Location(Bukkit.getWorld("world"), 3.5, 75, 216.5, 0, 0);
    //public static final Location redSpawn = new Location(Bukkit.getWorld("world"), -8.5, 175, 216.5, -90, 0);
    //public static final Location blueSpawn = new Location(Bukkit.getWorld("world"), 15.5, 175, 216.5, 90, 0);

    // ONLINE

    public static final Location spawn = new Location(Bukkit.getWorld("world"), 16.5, 66, 225.5, -90, 0);
    public static final Location redSpawn = new Location(Bukkit.getWorld("world"), 16.5, 66, 249.5, 180, 0);
    public static final Location blueSpawn = new Location(Bukkit.getWorld("world"), 16.5, 66, 201.5, 0, 0);

    //------------------
    public static ArrayList<Player> getGamePlayers() {
        return gamePlayers;
    }
}
