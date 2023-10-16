package fr.loanspac.smash.managers;

import fr.loanspac.smash.Smash;
import fr.loanspac.smash.game.GameType;
import fr.loanspac.smash.utils.FastBoard;
import fr.loanspac.smash.utils.Time;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ScoreboardManager { // supprimer les static
    private static Map<Player, FastBoard> boards = new HashMap<>();

    public void createScoreboard(Player player){
        boards.put(player, new FastBoard(player));
        //boards.get(player).updateTitle("§c§nSmash");
    }

    public void deleteScoreboard(Player player){
        boards.remove(player);
    }

    public void updateScoreboard(Player player, int time) {

        if (GameType.getCurrentState().equals(GameType.WAITING)) {
            boards.get(player).updateTitle("§c§nSmash");
            boards.get(player).updateLines(
                    "§7§m------------------",
                    "§8■ §fStatus §7➢ §cEn attente...",
                    "",
                    "§8■ §fJoueurs §7➢ §e" + Bukkit.getOnlinePlayers().size(),
                    "§7§m------------------",
                    "§6localhost"
            );
        }
        if (GameType.getCurrentState().equals(GameType.SMASH)) {
            boards.get(player).updateTitle("§c§nSmash");
            boards.get(player).updateLines(
                    "§7§m------------------",
                    "§8■ §fStatus §7➢ §cEn Jeu",
                    "",
                    "§8■ §fChampion §7➢ §d"/* + Smash.champion.get(player).getName()*/,
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
}
