package fr.loanspac.smash.tasks;

import fr.loanspac.smash.Smash;
import fr.loanspac.smash.champion.Champion;
import fr.loanspac.smash.champion.bario.Bario;
import fr.loanspac.smash.game.EGames;
import fr.loanspac.smash.game.GameSettings;
import fr.loanspac.smash.manager.ScoreboardManager;
import fr.loanspac.smash.team.Team;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

/*
 * Nom de classe : SmashTask
 * Description   : SmashTask class
 * Version       : 1.0
 * Date          : 04/04/2023
 * Copyright     : LoanSpac
 */

public class SmashTask extends BukkitRunnable implements Listener {
    public static int time = 0;
    private static List<Player> inPVP = new ArrayList<>();
    public static ArrayList<Player> alives = new ArrayList<>();

    public static List<Player> getInPVP() {
        return inPVP;
    }

    public static void equipPlayer(Player player) {
        player.getInventory().clear();
        Champion champ = Smash.champion.get(player);
        if(champ == null) {
            champ = new Bario();
            Smash.champion.put(player, new Bario());
        }
        champ.setArmor(player);
        champ.setItems(player);
    }

    private static void teleportPlayers(List<Player> players) {
        for(Player player : players) {
            if(Smash.team.get(player).equals(Team.RED)) {
                player.teleport(GameSettings.redSpawn);
            }
            else {
                player.teleport(GameSettings.blueSpawn);
            }
        }
    }

    public void equalsTeam(Player player) {
        int countRed = 0;
        int countBlue = 0;
        for(Player players : Bukkit.getOnlinePlayers()) {
            if(Smash.team.get(players).equals(Team.RED)) {
                countRed ++;
            }
            if(Smash.team.get(players).equals(Team.BLUE)) {
                countBlue ++;
            }
        }
        if(countRed > countBlue) {
            Smash.team.put(player, Team.BLUE);
        }
        else if(countRed < countBlue) {
            Smash.team.put(player, Team.RED);
        }
        else {
            Smash.team.put(player, Team.RED);
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if(EGames.getCurrentState().equals(EGames.SMASH)) {
            if(event.getEntity() instanceof Player) {
                Player player = (Player) event.getEntity();
                if(getInPVP().contains(player)) {
                    alives.remove(player);
                    getInPVP().remove(player);
                    event.getDrops().clear();
                    event.getDrops().add(new ItemStack(Material.GOLDEN_APPLE, 2));
                    event.setDroppedExp(0);
                    Bukkit.broadcastMessage("§c" + player.getName() + " §6est mort !");
                    player.setGameMode(GameMode.SPECTATOR);
                    if(alives.size() > 1){
                        Bukkit.getScheduler().runTaskLater(Smash.getInstance(), () -> {
                            player.teleport(alives.get(0));
                        }, 10);
                    }

                }
            }
        }
    }

    @EventHandler
    public static void onLeave(PlayerQuitEvent event){
        if(EGames.getCurrentState().equals(EGames.SMASH)){
            if(alives.contains(event.getPlayer())){
                alives.remove(event.getPlayer());
            }
            getInPVP().remove(event.getPlayer());
        }
    }

    @EventHandler
    public static void onConnect(PlayerJoinEvent event){
        if(EGames.getCurrentState().equals(EGames.SMASH)) {
            Player player = event.getPlayer();
            if (alives.contains(player)) {
                alives.remove(player);
            }
            getInPVP().remove(player);
            player.setGameMode(GameMode.SPECTATOR);
            player.playSound(player.getLocation(), Sound.ENTITY_CAT_HISS, 1, 1);
            player.sendMessage(GameSettings.prefix + "§cLa partie est déjà en cours !");
        } else if(EGames.getCurrentState().equals(EGames.END)) {
            Player player = event.getPlayer();
            if (alives.contains(player)) {
                alives.remove(player);
            }
            getInPVP().remove(player);
            player.setGameMode(GameMode.SPECTATOR);
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1, 1);
            player.sendMessage(GameSettings.prefix + "§cLa partie est déjà terminée !");
        }
    }

    @Override
    public void run() {
        if(time == 0) {
            for(Player players : Bukkit.getOnlinePlayers()) {
                players.playSound(players.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                alives.add(players);
                getInPVP().add(players);
                if(Smash.team.get(players).equals(Team.NEUTRAL)) {
                    equalsTeam(players);
                }
                teleportPlayers(alives);
                equipPlayer(players);
                players.setAllowFlight(true);
            }
            Bukkit.getWorld("world").setPVP(true);
            //new SmashGame(0).startSmash(0);
        }
        if(time > 0) {
            for (Player players : GameSettings.getGamePlayers()) {
                if(players.getHealth() <= 0) {
                    players.setHealth(20);
                    players.setFoodLevel(20);
                    players.setGameMode(GameMode.SPECTATOR);
                    alives.remove(players);
                }
            }
            if(alives.size() == 99) { // Modifier le nombre -> 1
                for (Player players : GameSettings.getGamePlayers()) {
                    players.sendTitle("§cPVP terminé !", "§6Victoire de §e" + alives.get(0).getDisplayName(), 10, 100, 10);
                    players.playSound(players.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1, 1);
                    players.getInventory().clear();
                    players.setGameMode(GameMode.ADVENTURE);
                }
                getInPVP().remove(alives.get(0));
                EGames.setState(EGames.END);
                cancel();
            }
        }
        for (Player players : Bukkit.getOnlinePlayers()) {
            ScoreboardManager.updateScoreboard(players, time);
            IntStream.rangeClosed(1,3)
                    .mapToObj(players.getInventory()::getItem)
                    .filter(Objects::nonNull)
                    .filter(item -> item.getAmount() > 1)
                    .peek(item -> item.setAmount(Math.max(1, item.getAmount()-1)))
                    .filter(item -> item.getAmount() == 1)
                    .forEach(item -> {
                        ItemMeta itM = item.getItemMeta();
                        itM.addEnchant(Enchantment.DURABILITY, 1, true);
                        itM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        item.setItemMeta(itM);
                    });
        }
        time++;
    }
}
