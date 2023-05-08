package fr.loanspac.smash.listeners;

import fr.loanspac.smash.game.EGames;
import fr.loanspac.smash.game.GameSettings;
import fr.loanspac.smash.manager.ScoreboardManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/*
 * Nom de classe : PlayerListeners
 * Description   : Player Listeners class
 * Version       : 1.0
 * Date          : 04/04/2023
 * Copyright     : LoanSpac
 */

public class PlayerListeners implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(null);
        Bukkit.broadcastMessage("§7[§2+§7] §b" + player.getName());

        ScoreboardManager.createScoreboard(player);
        player.sendTitle("§c§lSmash", "§fBienvenue " + player.getName());

        if(EGames.getCurrentState() == EGames.WAITING){
            player.sendMessage("§8§m----------------------------------------");
            player.sendMessage("§7Bienvenue sur §c§lSmash");
            player.sendMessage("");
            player.sendMessage("§fTu es actuellement dans la §cphase de d'attente§f de lancement.");
            player.sendMessage("§fNous éspérons que tu iras jusqu'au bout de cette aventure !");
            player.sendMessage("");
            player.sendMessage("§7(Si tu es bloqué dans le spawn, tu peux utiliser §6§l/§espawn §7!)");
            player.sendMessage("§8§m----------------------------------------");
            player.setGameMode(GameMode.ADVENTURE);
            player.setHealth(20);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1);
            player.getInventory().clear();
            player.setExp(0);
            player.setLevel(2023);
            player.teleport(GameSettings.spawn);
            player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_LARGE_BLAST, 1, 1);

            WorldBorder worldBorder = Bukkit.getWorld("world").getWorldBorder();
            worldBorder.setSize(999999999);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        event.setQuitMessage(null);
        Bukkit.broadcastMessage("§7[§4-§7] §b" + player.getName());
        ScoreboardManager.deleteScoreboard(player);
    }

    @EventHandler
    public void onChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        event.setFormat(player.getName() + " §8§l» §7" + event.getMessage());
    }
}