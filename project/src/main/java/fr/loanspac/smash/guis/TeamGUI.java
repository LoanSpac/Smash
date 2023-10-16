package fr.loanspac.smash.guis;

import fr.loanspac.smash.Smash;
import fr.loanspac.smash.game.team.Team;
import fr.loanspac.smash.utils.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TeamGUI {
    private final ItemStack grayGlass = ItemManager.getByteItem(Material.STAINED_GLASS_PANE, " ", false, false, (byte)7);

    public void renderGUI(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "§8Team");

        for(int i = 0; i < 27; i++) {
            if(i < 10) {
                inv.setItem(i, grayGlass);
            }
            if(i > 16) {
                inv.setItem(i, grayGlass);
            }
        }

        ItemStack blueWool = ItemManager.getByteItem(Material.WOOL, "§1Blue Team", false, false, (byte)11);
        inv.setItem(11, blueWool);

        ItemStack redWool = ItemManager.getByteItem(Material.WOOL, "§4Red Team", false, false, (byte)14);
        inv.setItem(15, redWool);

        player.openInventory(inv);
    }

    public void clickGUI(Player player, ItemStack current) {
        if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§1Blue Team")) {
            Smash.team.replace(player, Team.BLUE);
            player.setCustomName("§1" + player.getDisplayName());
        }

        if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§4Red Team")) {
            Smash.team.replace(player, Team.RED);
            player.setCustomName("§4" + player.getDisplayName());
        }

        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
    }
}
