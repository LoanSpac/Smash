package fr.loanspac.smash.listeners;

import fr.loanspac.smash.Smash;
import fr.loanspac.smash.game.GameSettings;
import fr.loanspac.smash.game.GameType;
import fr.loanspac.smash.managers.ScoreboardManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;

public class PlayerListener implements Listener {
    private final ScoreboardManager scoreboard = new ScoreboardManager();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(null);
        Bukkit.broadcastMessage("§7[§2+§7] §b" + player.getName());

        scoreboard.createScoreboard(player);
        player.sendTitle("§c§lSmash", "§fBienvenue " + player.getName());

        if(GameType.getCurrentState().equals(GameType.WAITING)) {
            player.removePotionEffect(PotionEffectType.SLOW);
            player.removePotionEffect(PotionEffectType.SPEED);
            player.removePotionEffect(PotionEffectType.JUMP);
            player.setWalkSpeed(0.2f);
            player.setMaxHealth(20);
            player.setHealth(20);
            player.showPlayer(Smash.instance(), player);
            player.setGlowing(false);
            player.getInventory().clear();
            player.setExp(0);
            player.setLevel(64);
            player.sendMessage("§8§m----------------------------------------");
            player.sendMessage("§7Bienvenue sur §c§lSmash");
            player.sendMessage("");
            player.sendMessage("§fClick sur le panneau pour jouer au jeu.");
            player.sendMessage("§7Tu peux aussi utiliser §6§l/§espawn §7!)");
            player.sendMessage("§8§m----------------------------------------");
            if(!(player.getGameMode().equals(GameMode.ADVENTURE))){
                player.setGameMode(GameMode.ADVENTURE);
            }
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1);

            player.teleport(GameSettings.spawn);
            player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_LARGE_BLAST, 1, 1);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        event.setQuitMessage(null);
        Bukkit.broadcastMessage("§7[§4-§7] §b" + player.getName());
        scoreboard.deleteScoreboard(player);
    }

    @EventHandler
    public void onChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        event.setFormat(player.getCustomName() + " §8§l» §7" + event.getMessage());
    }
}
