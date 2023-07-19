package fr.loanspac.smash.guis;

import fr.loanspac.smash.Smash;
import fr.loanspac.smash.champion.bario.Bario;
import fr.loanspac.smash.champion.marte.Marte;
import fr.loanspac.smash.heads.ChampionHeads;
import fr.loanspac.smash.team.Team;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WaitGUI {
    public void GUI(Player player, ItemStack it, Action action) {

        boolean rightClick = (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK));

        if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§6Main") && rightClick) {
            Inventory inv = Bukkit.createInventory(null, 54, "§8Sélection de champion");

            ItemStack grayGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)7); // EDIT ITEMMANAGER
            ItemMeta grayGlassMeta = grayGlass.getItemMeta();
            grayGlassMeta.setDisplayName(" ");
            grayGlass.setItemMeta(grayGlassMeta);

            String[] names = {"Bario", "Marte", "Pakichu", "Kirpy", "Lynk", "Bowzer", "Sonyc", "Foks", "Waryo"};

            for(int i = 0; i < 54; i++) {
                if(i < 9) {
                    inv.setItem(i, grayGlass);
                }
                if(i > 8 && i < 18) {
                    inv.setItem(i, new ChampionHeads().headOf(names[i-9]));
                }
                if(i > 44) {
                    inv.setItem(i, grayGlass);
                }
            }

            player.openInventory(inv);
        }


        if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§4Retour Lobby") && rightClick) {
            player.kickPlayer("Retour Lobby. XDDDD");
        }

        if(it.getItemMeta().getDisplayName().equalsIgnoreCase("§6Team") && rightClick) {
            Inventory inv = Bukkit.createInventory(null, 27, "§8Team");

            ItemStack gray_glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)7); // EDIT ITEMMANAGER
            ItemMeta gray_glassMeta = gray_glass.getItemMeta();
            gray_glassMeta.setDisplayName(" ");
            gray_glass.setItemMeta(gray_glassMeta);

            for(int i = 0; i < 27; i++) {
                if(i < 10) {
                    inv.setItem(i, gray_glass);
                }
                if(i > 16) {
                    inv.setItem(i, gray_glass);
                }
            }

            ItemStack blueTeam = new ItemStack(Material.WOOL, 1, (byte)11); // EDIT ITEMMANAGER
            ItemMeta blueTeamMeta = gray_glass.getItemMeta();
            blueTeamMeta.setDisplayName("§1Blue Team");
            blueTeam.setItemMeta(blueTeamMeta);

            inv.setItem(11, blueTeam);

            ItemStack redTeam = new ItemStack(Material.WOOL, 1, (byte)14); // EDIT ITEMMANAGER
            ItemMeta redTeamMeta = gray_glass.getItemMeta();
            redTeamMeta.setDisplayName("§4Red Team");
            redTeam.setItemMeta(redTeamMeta);

            inv.setItem(15, redTeam);

            player.openInventory(inv);
        }
    }

    public void GUIClick(Player player, ItemStack current, Inventory inv) {
        if(inv.getName().equalsIgnoreCase("§8Team")) {
            if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§1Blue Team")) {
                Smash.team.replace(player, Team.BLUE);
                //player.setDisplayName("§1" + player.getDisplayName() + "§f");
            }

            if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§4Red Team")) {
                Smash.team.replace(player, Team.RED);
                //player.setDisplayName("§4" + player.getDisplayName() + "§f");
            }

            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
        }

        if(inv.getName().equalsIgnoreCase("§8Sélection de champion")) {
            String[] names = {"Bario", "Marte", "Pakichu", "Kirpy", "Lynk", "Bowzer", "Sonyc", "Foks", "Waryo"};

            for(String name : names) {
                if(current.getItemMeta().getDisplayName().equalsIgnoreCase(name)) {
                    if(name.equals("Bario")) {
                        Smash.champion.put(player, new Bario());
                    }
                    if(name.equals("Marte")) {
                        Smash.champion.put(player, new Marte());
                    }
                }
            }
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
        }
    }
}
