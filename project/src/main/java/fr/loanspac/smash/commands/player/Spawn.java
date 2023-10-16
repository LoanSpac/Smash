package fr.loanspac.smash.commands.player;

import fr.loanspac.smash.game.GameSettings;
import fr.loanspac.smash.game.GameType;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spawn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if(cmd.getName().equalsIgnoreCase("spawn")) {
                if (GameType.getCurrentState() == GameType.WAITING) {
                    player.teleport(GameSettings.spawn);
                } else {
                    player.sendMessage("§cVous ne pouvez pas vous téléporter au spawn lorsqu'une partie est en cours !");
                    player.sendTitle(ChatColor.translateAlternateColorCodes('&', "§cErreur !"), ChatColor.translateAlternateColorCodes('&', "§eUne partie est en cours !"), 10, 40, 10);
                    player.playSound(player.getLocation(), Sound.ENTITY_CAT_HISS, 1, 1);
                }
            }
            return false;
        }
        return false;
    }
}
