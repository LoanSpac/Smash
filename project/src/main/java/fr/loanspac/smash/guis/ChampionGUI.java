package fr.loanspac.smash.guis;

import fr.loanspac.smash.Smash;
import fr.loanspac.smash.game.SmashGame;
import fr.loanspac.smash.game.champion.ChampionType;
import fr.loanspac.smash.guis.buttons.ChampionButtonGUI;
import fr.loanspac.smash.utils.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ChampionGUI {
    private final ItemStack grayGlass = ItemManager.getByteItem(Material.STAINED_GLASS_PANE, " ", false, false, (byte)7);

    public void renderGUI(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, "§8Sélection de champion");

        ChampionType[] championTypes = ChampionType.values();

        for (int i = 0; i < inv.getSize(); i++) {
            if (i >= 9 &&  i-9 < championTypes.length) {
                inv.setItem(i, new ChampionButtonGUI(championTypes[i-9]).render());
            } else  {
                inv.setItem(i, grayGlass);
            }
        }

        player.openInventory(inv);
    }

    public void clickGUI(Player player, ItemStack current) throws InstantiationException, IllegalAccessException {
        ChampionType[] championTypes = ChampionType.values();
        int i = 0;
        while(i != championTypes.length) {
            if (current.equals(new ChampionButtonGUI(championTypes[i]).render())) {
                SmashGame.choice.put(player.getUniqueId(), championTypes[i]);
                Smash.champion.put(player, championTypes[i].getKlass().newInstance());
            }
            i++;
        }

        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
    }
}
