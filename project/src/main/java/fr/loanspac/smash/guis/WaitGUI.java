package fr.loanspac.smash.guis;

import fr.loanspac.smash.Smash;
import fr.loanspac.smash.champion.ChampionType;
import fr.loanspac.smash.guis.button.ChampionButtonGUI;
import fr.loanspac.smash.tasks.WaitTask;
import fr.loanspac.smash.team.Team;
import fr.loanspac.smash.utils.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class WaitGUI {
    private final ItemStack grayGlass = ItemManager.getByteItem(Material.STAINED_GLASS_PANE, " ", false, false, (byte)7);

    public void renderGUI(Player player, ItemStack it, Action action) {

        boolean rightClick = (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK));

        if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§6Main") && rightClick) {
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


        if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§4Retour Lobby") && rightClick) {
            player.kickPlayer("§x§1§f§f§b§f§4§l§nS§x§2§1§e§d§f§5§l§nk§x§2§3§d§e§f§6§l§ni§x§2§5§d§0§f§6§l§nb§x§2§6§c§2§f§7§l§ni§x§2§8§b§4§f§8§l§nd§x§2§a§a§5§f§9§l§ni §x§2§c§9§7§f§a§l§nS§x§2§e§8§9§f§b§l§nk§x§3§0§7§a§f§b§l§ni§x§3§2§6§c§f§c§l§nb§x§3§3§5§e§f§d§l§ni§x§3§5§4§f§f§e§l§nd§x§3§7§4§1§f§f§l§ni §x§3§d§3§c§f§e§l§nS§x§4§7§3§f§f§c§l§nk§x§5§1§4§3§f§a§l§ni§x§5§b§4§6§f§8§l§nb§x§6§5§4§a§f§6§l§ni§x§6§f§4§e§f§4§l§nd§x§7§9§5§1§f§2§l§ni §x§8§4§5§5§e§f§l§nS§x§8§e§5§8§e§d§l§nk§x§9§8§5§c§e§b§l§ni§x§a§2§5§f§e§9§l§nb§x§a§c§6§3§e§7§l§ni§x§b§6§6§6§e§5§l§nd§x§c§0§6§a§e§3§l§ni");
            //HEX 1 : #1FFBF4
            //HEX 2 : #383AFF
            //HEX 3 : #C06AE3
        }

        if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§6Team") && rightClick) {
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
    }

    public void clickGUI(Player player, ItemStack current, Inventory inv) throws InstantiationException, IllegalAccessException {
        if(inv.getName().equalsIgnoreCase("§8Team")) {
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

        if(inv.getName().equalsIgnoreCase("§8Sélection de champion")) {
            ChampionType[] championTypes = ChampionType.values();
            int i = 0;
            while(i != championTypes.length) {
                if (current.equals(new ChampionButtonGUI(championTypes[i]).render())) {
                    WaitTask.choice.put(player.getUniqueId(), championTypes[i]);
                    Smash.champion.put(player, championTypes[i].getKlass().newInstance());
                }
                i++;
            }

            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
        }
    }
}
