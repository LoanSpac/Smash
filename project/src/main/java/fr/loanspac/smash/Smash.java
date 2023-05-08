package fr.loanspac.smash;

import fr.loanspac.smash.champion.Champion;
import fr.loanspac.smash.game.EGames;
import fr.loanspac.smash.manager.RegisterManager;
import fr.loanspac.smash.tasks.WaitTask;
import fr.loanspac.smash.team.Team;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

/*
 * Nom de classe : Smash
 * Description   : Main class
 * Version       : 1.0
 * Date          : 04/04/2023
 * Copyright     : LoanSpac
 */

public final class Smash extends JavaPlugin {
    private static Smash instance;
    public static HashMap<Player, Double> percentage = new HashMap<>();
    public static HashMap<Player, Double> weight = new HashMap<>();
    public static HashMap<Player, Double> kbmod = new HashMap<>();
    public static HashMap<Player, Team> team = new HashMap<>();
    public static HashMap<Player, Champion> champion = new HashMap<>();

    ///////////// Activation /////////////
    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getLogger().info("===========================");
        Bukkit.getLogger().info("Starting Smash by LoanSpac");
        Bukkit.getLogger().info(" ");
        Bukkit.getLogger().info("All is good, have a good day !");
        Bukkit.getLogger().info("===========================");

        EGames.setState(EGames.WAITING);
        RegisterManager registrationManager = new RegisterManager();
        registrationManager.registration();

        Bukkit.getWorld("world").setPVP(false);
        WaitTask waitTask = new WaitTask();
        waitTask.runTaskTimer(this, 0, 20);
    }

    ///////////// Désactivation /////////////
    @Override
    public void onDisable() {
        Bukkit.getLogger().info("===========================");
        Bukkit.getLogger().info("Disabling Smash by LoanSpac");
        Bukkit.getLogger().info(" ");
        Bukkit.getLogger().info("All is good, see you soon !");
        Bukkit.getLogger().info("===========================");
    }

    public static Smash getInstance() {
        return instance;
    }
}
