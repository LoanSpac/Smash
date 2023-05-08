package fr.loanspac.smash.commands;

import fr.loanspac.smash.game.EGames;
import fr.loanspac.smash.game.GameSettings;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/*
 * Nom de classe : PlayerCommand
 * Description   : PlayerCMD class
 * Version       : 1.0
 * Date          : 04/04/2023
 * Copyright     : LoanSpac
 */

public class PlayerCommand implements CommandExecutor  {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if(cmd.getName().equalsIgnoreCase("spawn")) {
                if (EGames.getCurrentState() == EGames.WAITING) {
                    player.teleport(GameSettings.spawn);
                } else {
                    player.sendMessage("§cVous ne pouvez pas vous téléporter au spawn en cours de partie !");
                    player.sendTitle(ChatColor.translateAlternateColorCodes('&', "§cErreur !"), ChatColor.translateAlternateColorCodes('&', "§eUne épreuve est en cours !"), 10, 40, 10);
                    player.playSound(player.getLocation(), Sound.ENTITY_CAT_HISS, 1, 1);
                }
            }
            return false;
        }
        return false;
    }
}