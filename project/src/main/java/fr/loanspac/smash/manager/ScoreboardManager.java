package fr.loanspac.smash.manager;

import fr.loanspac.smash.Smash;
import fr.loanspac.smash.game.EGames;
import fr.loanspac.smash.utils.FastBoard;
import fr.loanspac.smash.utils.Time;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ScoreboardManager {
    /*

    private static FastBoard board;
    private static HashMap<Player, FastBoard> boards = new HashMap<>();

    public static void createScoreboard(Player player){
        boards.put(player, new FastBoard(player));
        boards.get(player).updateTitle("§c§nSmash");
    }

    public static void deleteScoreboard(Player player){
        boards.remove(player);
    }

    public static void updateScoreboard(Player player, int time) {

        if (EGames.getCurrentState() == EGames.WAITING) {
            boards.get(player).updateTitle("§c§nSmash");
            boards.get(player).updateLines(
                    "§7§m------------------", // Empty line
                    "§8■ §fStatus §7➢ §cEn attente...",
                    "",
                    "§8■ §fJoueurs §7➢ §e" + Bukkit.getOnlinePlayers().size(),
                    "§7§m------------------",
                    "§6localhost"
            );
        }
        if (EGames.getCurrentState() == EGames.SMASH) {
            boards.get(player).updateTitle("§c§nSmash");
            boards.get(player).updateLines(
                    "§7§m------------------", // Empty line
                    "§8■ §fStatus §7➢ §cEn Jeu",
                    "",
                    "§8■ §fPourcentage §7➢ §6" + Smash.player.get(player).getPercent(),
                    "",
                    "§8■ §fTemps §7➢ §3" + Time.convert(time),
                    "",
                    "§8■ §fJoueurs §7➢ §e" + Bukkit.getOnlinePlayers().size(),
                    "§7§m------------------",
                    "§6localhost"
            );
        }
    }

    public static FastBoard getScoreboard(Player player){
        return boards.get(player);
    }

     */
}
