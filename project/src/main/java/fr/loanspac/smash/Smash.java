package fr.loanspac.smash;

import fr.loanspac.smash.game.GameType;
import fr.loanspac.smash.game.SmashGame;
import fr.loanspac.smash.game.champion.Champion;
import fr.loanspac.smash.game.champion.ChampionDistributor;
import fr.loanspac.smash.game.player.SmashPlayer;
import fr.loanspac.smash.managers.CommandManager;
import fr.loanspac.smash.managers.ListenerManager;
import fr.loanspac.smash.game.team.Team;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class Smash extends JavaPlugin {
    private static Smash INSTANCE;
    private ChampionDistributor championDistributor;


    public static Map<Player, SmashPlayer> player = new HashMap<>();
    public static Map<Player, Team> team = new HashMap<>();
    public static Map<Player, Champion> champion = new HashMap<>();

    ///////////// Activation /////////////
    @Override
    public void onEnable() {
        INSTANCE = this;
        Bukkit.getLogger().info("===========================");
        Bukkit.getLogger().info("Enable Smash 1.1");
        Bukkit.getLogger().info("===========================");

        this.championDistributor = new ChampionDistributor();

        GameType.setState(GameType.WAITING);

        CommandManager commands = new CommandManager();
        commands.setCommand();

        ListenerManager listeners = new ListenerManager();
        listeners.registerEvents();

        Bukkit.getWorld("world").setPVP(false);
        SmashGame game = new SmashGame();
        game.runTaskTimer(this, 0, 20);

    }

    ///////////// Désactivation /////////////
    @Override
    public void onDisable() {
        Bukkit.getLogger().info("===========================");
        Bukkit.getLogger().info("Disable Smash 1.0");
        Bukkit.getLogger().info("===========================");
    }

    public ChampionDistributor getChampionDistributor() {
        return championDistributor;
    }

    public static Smash instance() {
        return INSTANCE;
    }
}
