package fr.loanspac.smash;

import fr.loanspac.smash.champion.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Smash extends JavaPlugin implements Listener {
    private static Smash INSTANCE;

    /*
    public static Map<Player, SmashPlayer> player = new HashMap<>();
    public static Map<Player, Team> team = new HashMap<>();
    public static Map<Player, Champion> champion = new HashMap<>();
     */

    private ChampionDistributor championDistributor;

    ///////////// Activation /////////////
    @Override
    public void onEnable() {
        INSTANCE = this;

        Bukkit.getLogger().info("===========================");
        Bukkit.getLogger().info("Enable Smash 1.0");
        Bukkit.getLogger().info("===========================");

        this.championDistributor = new ChampionDistributor();

        /*
        EGames.setState(EGames.WAITING);
        RegisterManager registrationManager = new RegisterManager();
        registrationManager.registration();

        Bukkit.getWorld("world").setPVP(false);
        WaitTask waitTask = new WaitTask();
        waitTask.runTaskTimer(this, 0, 20);

         */

        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        championDistributor.applyChampion(player, ChampionType.BARIO);
    }

    @EventHandler
    public void onHeldSwitch(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();

        if (event.getNewSlot() == 1) {
            ChampionSpell spell = championDistributor.getSpellOfPlayer(player, ChampionSpellType.OFFENSIVE);
            if (spell instanceof ChampionActiveSpell) {
                ChampionActiveSpell activeSpell = (ChampionActiveSpell) spell;
                activeSpell.play(player);
            }
        }

        event.setCancelled(true);
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("===========================");
        Bukkit.getLogger().info("Disable Smash 1.0");
        Bukkit.getLogger().info("===========================");
    }

    public static Smash instance() {
        return INSTANCE;
    }

    public ChampionDistributor getChampionDistributor() {
        return championDistributor;
    }

}
