package fr.loanspac.smash.tasks;

import fr.loanspac.smash.Smash;
import fr.loanspac.smash.champion.Champion;
import fr.loanspac.smash.champion.bario.Bario;
import fr.loanspac.smash.game.EGames;
import fr.loanspac.smash.game.GameSettings;
import fr.loanspac.smash.game.player.SmashPlayer;
import fr.loanspac.smash.manager.ScoreboardManager;
import fr.loanspac.smash.team.Team;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static fr.loanspac.smash.Smash.player;

public class SmashTask extends BukkitRunnable implements Listener {
    public static int time = 0;
    private static final List<Player> inPVP = new ArrayList<>();
    public static ArrayList<Player> alives = new ArrayList<>();

    public static List<Player> getInPVP() {
        return inPVP;
    }


    // set blast zone


    public static void equipPlayer(Player player) {
        player.getInventory().clear();
        Champion champ = Smash.champion.get(player);
        if(champ == null) {
            champ = new Bario();
            Smash.champion.put(player, new Bario());
        }
        player.getInventory().setArmorContents(champ.getArmorContents());

        List<Integer> item = new ArrayList<>();
        item.add(1);
        item.add(2);
        item.add(3);
        item.add(8);
        for(Integer i : item) {
            player.getInventory().setItem(i, champ.getSpells().get(item.indexOf(i)));
        }

        if(champ.getMainHandItem() != null) player.getInventory().setItem(0, champ.getMainHandItem());
    }

    public static void enablePassif(Player player) {
        player.getActivePotionEffects().clear();
        Champion champ = Smash.champion.get(player);
        champ.applyPassif(player);
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
                continue;
            }
            countBlue ++;
        }
        if(countRed > countBlue) {
            Smash.team.put(player, Team.BLUE);
            return;
        }
        Smash.team.put(player, Team.RED);
    }

    @EventHandler
    public static void onLeave(PlayerQuitEvent event){
        if(EGames.getCurrentState().equals(EGames.SMASH)){
            alives.remove(event.getPlayer());
            getInPVP().remove(event.getPlayer());
        }
    }

    @EventHandler
    public static void onConnect(PlayerJoinEvent event){
        if(EGames.getCurrentState().equals(EGames.SMASH)) {
            Player player = event.getPlayer();
            alives.remove(player);

            getInPVP().remove(player);
            player.setGameMode(GameMode.SPECTATOR);
            player.playSound(player.getLocation(), Sound.ENTITY_CAT_HISS, 1, 1);
            player.sendMessage(GameSettings.prefix + "§cLa partie est déjà en cours !");
        } else if(EGames.getCurrentState().equals(EGames.END)) {
            Player player = event.getPlayer();
            alives.remove(player);

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
                players.getInventory().clear();

                players.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
                players.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 0));

                equipPlayer(players);
                enablePassif(players);

                //System.out.println(new WaitTask().choice.get(players.getUniqueId()));
                /*
                if(!(WaitTask.choice.get(players.getUniqueId()).equals(null))) {
                    new ChampionButtonGUI(WaitTask.choice.get(players.getUniqueId())).play(players);
                }
                */

                players.setAllowFlight(true);
                players.setMaxHealth(6);
                player.put(players, new SmashPlayer());
                player.get(players).setKbmod(1d);
                player.get(players).setPercent(0d);
                //player.get(players).setWeight(Smash.champion.get(players).getWeight());
            }
            Bukkit.getWorld("world").setPVP(true);
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
