package fr.loanspac.smash.commands;

import fr.loanspac.smash.Smash;
import fr.loanspac.smash.game.EGames;
import fr.loanspac.smash.tasks.SmashTask;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/*
 * Nom de classe : StartCommand
 * Description   : Start Game class
 * Version       : 1.0
 * Date          : 26/03/2023
 * Copyright     : HepickStudio
 */

public class StartCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player) {
            if (((Player) sender).getDisplayName().equals("LoanSpac")) {
                if(cmd.getName().equalsIgnoreCase("start")) {
                    sender.sendMessage(ChatColor.YELLOW + "Démarrage..");
                    EGames.setState(EGames.SMASH);
                    SmashTask pvpTask = new SmashTask();
                    pvpTask.runTaskTimer(Smash.getInstance(), 0, 20);
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }
}