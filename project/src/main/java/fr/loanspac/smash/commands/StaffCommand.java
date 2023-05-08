package fr.loanspac.smash.commands;

import fr.loanspac.smash.game.EGames;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/*
 * Nom de classe : StaffCommand
 * Description   : StaffCMD class
 * Version       : 1.0
 * Date          : 04/04/2023
 * Copyright     : LoanSpac
 */

public class StaffCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player) {
            if (((Player) sender).getDisplayName().equals("LoanSpac")) {
                if(cmd.getName().equalsIgnoreCase("staff")) {
                    sender.sendMessage(ChatColor.RED + "Vous pouvez editer le terrain.");
                    EGames.setState(EGames.BUILD);
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