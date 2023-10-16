package fr.loanspac.smash.commands.staff;

import fr.loanspac.smash.game.GameType;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Build implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player) {
            if (((Player) sender).getDisplayName().equals("LoanSpac")) {
                if(cmd.getName().equalsIgnoreCase("build")) {
                    sender.sendMessage(ChatColor.RED + "Vous pouvez editer le terrain.");
                    GameType.setState(GameType.BUILD);
                    ((Player) sender).setGameMode(GameMode.CREATIVE);
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }
}
