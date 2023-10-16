package fr.loanspac.smash.game;

import fr.loanspac.smash.Smash;
import fr.loanspac.smash.game.champion.Champion;
import fr.loanspac.smash.game.champion.ChampionType;
import fr.loanspac.smash.game.champion.bario.Bario;
import fr.loanspac.smash.game.player.SmashPlayer;
import fr.loanspac.smash.managers.ScoreboardManager;
import fr.loanspac.smash.game.team.Team;
import org.bukkit.*;
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

import java.util.*;
import java.util.stream.IntStream;

import static fr.loanspac.smash.Smash.player;

public class SmashGame extends BukkitRunnable implements Listener {
    private final ScoreboardManager scoreboard = new ScoreboardManager();
    public static Map<UUID, ChampionType> choice = new HashMap<>();
    public static int time = -20;
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
    public void onLeave(PlayerQuitEvent event){
        if(GameType.getCurrentState().equals(GameType.SMASH)){
            alives.remove(event.getPlayer());
            getInPVP().remove(event.getPlayer());
        }
    }

    @EventHandler
    public void onConnect(PlayerJoinEvent event){
        if(GameType.getCurrentState().equals(GameType.SMASH)) {
            Player player = event.getPlayer();
            alives.remove(player);

            getInPVP().remove(player);
            player.setGameMode(GameMode.SPECTATOR);
            player.playSound(player.getLocation(), Sound.ENTITY_CAT_HISS, 1, 1);
            player.sendMessage(GameSettings.prefix + "§cLa partie est déjà en cours !");
        } else if(GameType.getCurrentState().equals(GameType.END)) {
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

        for (Player players : Bukkit.getOnlinePlayers()) {
            scoreboard.updateScoreboard(players, 0);
            switch(time) {
                case -10:
                    players.sendTitle("§cAttention", "§6La partie commence dans 10s..", 10, 20, 10);
                    break;
                case -5:
                    players.sendTitle("§95", "", 10, 20, 10);
                    players.playNote(players.getLocation(), Instrument.BELL, Note.natural(1, Note.Tone.E));
                    break;
                case -4:
                    players.sendTitle("§24", "", 10, 20, 10);
                    players.playNote(players.getLocation(), Instrument.BELL, Note.natural(1, Note.Tone.D));
                    break;
                case -3:
                    players.sendTitle("§e3", "", 10, 20, 10);
                    players.playNote(players.getLocation(), Instrument.BELL, Note.natural(1, Note.Tone.C));
                    break;
                case -2:
                    players.sendTitle("§62", "", 10, 20, 10);
                    players.playNote(players.getLocation(), Instrument.BELL, Note.natural(1, Note.Tone.B));
                    break;
                case -1:
                    players.sendTitle("§c1", "", 10, 20, 10);
                    players.playNote(players.getLocation(), Instrument.BELL, Note.natural(1, Note.Tone.A));
                    break;
            }
        }

        if(time == 0) {
            GameType.setState(GameType.SMASH);
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
                    players.sendTitle("§cFINISH !", "§6Victoire de §e" + alives.get(0).getDisplayName(), 10, 100, 10);
                    players.playSound(players.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1, 1);
                    players.getInventory().clear();
                    players.setGameMode(GameMode.ADVENTURE);
                }
                getInPVP().remove(alives.get(0));
                GameType.setState(GameType.END);
                cancel();
            }
        }
        for (Player players : Bukkit.getOnlinePlayers()) {
            scoreboard.updateScoreboard(players, time);
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

        if(Bukkit.getOnlinePlayers().size() > 0) { // -> supérieur à 1 joueur
            time++;
        } else if(time != -20) {
            time = -20;
            Bukkit.broadcastMessage("En attente de joueur..");
        }
    }
}
