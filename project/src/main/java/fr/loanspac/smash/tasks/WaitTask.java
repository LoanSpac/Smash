package fr.loanspac.smash.tasks;

import fr.loanspac.smash.Smash;
import fr.loanspac.smash.champion.ChampionType;
import fr.loanspac.smash.game.EGames;
import fr.loanspac.smash.game.GameSettings;
import fr.loanspac.smash.manager.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WaitTask extends BukkitRunnable implements Listener {
    public static Map<UUID, ChampionType> choice = new HashMap<>();
    private static int counter = 20;

    @EventHandler
    public static void onConnect(PlayerJoinEvent event){
        if(EGames.getCurrentState().equals(EGames.WAITING)) {
            Player player = event.getPlayer();
            //player.getActivePotionEffects().clear();
            player.removePotionEffect(PotionEffectType.SLOW);
            player.removePotionEffect(PotionEffectType.SPEED);
            player.removePotionEffect(PotionEffectType.JUMP);
            player.setWalkSpeed(0.2f);
            player.setMaxHealth(20);
            player.setHealth(20);
            player.showPlayer(Smash.instance(), player);
            player.setGlowing(false);
            if(!(player.getGameMode().equals(GameMode.ADVENTURE))){
                player.setGameMode(GameMode.ADVENTURE);
            }
            player.teleport(GameSettings.spawn);
        }
    }

    @Override
    public void run() {
        /////////////////// LAUNCH MESSAGE ///////////////////
        for (Player players : Bukkit.getOnlinePlayers()) {
            ScoreboardManager.updateScoreboard(players, 0);
            switch(counter) {
                case 10:
                    players.sendTitle("§cAttention", "§6La partie commence dans 10s..", 10, 20, 10);
                    break;
                case 5:
                    players.sendTitle("§95", "", 10, 20, 10);
                    players.playNote(players.getLocation(), Instrument.BELL, Note.natural(1, Note.Tone.E));
                    break;
                case 4:
                    players.sendTitle("§24", "", 10, 20, 10);
                    players.playNote(players.getLocation(), Instrument.BELL, Note.natural(1, Note.Tone.D));
                    break;
                case 3:
                    players.sendTitle("§e3", "", 10, 20, 10);
                    players.playNote(players.getLocation(), Instrument.BELL, Note.natural(1, Note.Tone.C));
                    break;
                case 2:
                    players.sendTitle("§62", "", 10, 20, 10);
                    players.playNote(players.getLocation(), Instrument.BELL, Note.natural(1, Note.Tone.B));
                    break;
                case 1:
                    players.sendTitle("§c1", "", 10, 20, 10);
                    players.playNote(players.getLocation(), Instrument.BELL, Note.natural(1, Note.Tone.A));
                    break;
            }
        } /////////////////////////////////////////////////////////

        if (counter == 0) { // Lancement du jeu
            EGames.setState(EGames.SMASH);
            SmashTask smashTask = new SmashTask();
            smashTask.runTaskTimer(Smash.instance(), 0, 20);
            cancel();
        }

        if(Bukkit.getOnlinePlayers().size() > 0) { // -> supérieur à 1 joueur
            counter--;
        } else if(counter != 20) {
            counter = 20;
            Bukkit.broadcastMessage("En attente de joueur..");
        }
    }
}