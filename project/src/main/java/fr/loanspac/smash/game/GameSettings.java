package fr.loanspac.smash.game;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class GameSettings {

    ///////////////////////////////////////////////////////////////FINAL VARIABLES///////////////////////////////////////////////////////////////
    public static final String prefix = ChatColor.translateAlternateColorCodes('&', "§cSmash §8» ");
    public static final Location spawn = new Location(Bukkit.getWorld("world"), 3.5, 75, 216.5, 0, 0);
    public static final Location redSpawn = new Location(Bukkit.getWorld("world"), -8.5, 175, 216.5, -90, 0);
    public static final Location blueSpawn = new Location(Bukkit.getWorld("world"), 15.5, 175, 216.5, 90, 0);
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////PLAYERS LISTS////////////////////////////////////////////////////////////////
    private static ArrayList<Player> gamePlayers = new ArrayList<>();
    private static ArrayList<Player> hostPlayers = new ArrayList<>();

    //------------------
    public static ArrayList<Player> getGamePlayers() {
        return gamePlayers;
    }

    public static ArrayList<Player> getHostPlayers() {
        return hostPlayers;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}