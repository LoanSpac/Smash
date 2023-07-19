package fr.loanspac.smash;

import fr.loanspac.smash.champion.Champion;
import fr.loanspac.smash.game.EGames;
import fr.loanspac.smash.game.player.SmashPlayer;
import fr.loanspac.smash.manager.RegisterManager;
import fr.loanspac.smash.tasks.WaitTask;
import fr.loanspac.smash.team.Team;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class Smash extends JavaPlugin {
    private static Smash instance;
    public static HashMap<Player, SmashPlayer> player = new HashMap<>();
    public static HashMap<Player, Team> team = new HashMap<>();
    public static HashMap<Player, Champion> champion = new HashMap<>();

    ///////////// Activation /////////////
    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getLogger().info("===========================");
        Bukkit.getLogger().info("Enable Smash 1.0");
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
        Bukkit.getLogger().info("Disable Smash 1.0");
        Bukkit.getLogger().info("===========================");
    }

    public static Smash getInstance() {
        return instance;
    }
}
