package fr.loanspac.smash.listeners;

import fr.loanspac.smash.Smash;
import fr.loanspac.smash.game.EGames;
import fr.loanspac.smash.guis.WaitGUI;
import fr.loanspac.smash.team.Team;
import fr.loanspac.smash.utils.ItemManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class WaitingListeners implements Listener {
    @EventHandler
    public void onFireTick(EntityDamageEvent event){
        if(!(EGames.getCurrentState().equals(EGames.WAITING))) return;
        if(event.getCause().equals(EntityDamageEvent.DamageCause.FIRE) || event.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK)){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if(!(EGames.getCurrentState().equals(EGames.WAITING))) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(!(EGames.getCurrentState().equals(EGames.WAITING))) return;
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        Action action = event.getAction();
        if(item == null) return;
        new WaitGUI().GUI(player, item, action);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(!(EGames.getCurrentState().equals(EGames.WAITING))) return;
        Player player = (Player)event.getWhoClicked();
        Inventory inv = event.getInventory();
        ItemStack current = event.getCurrentItem();
        if(current == null) return;
        new WaitGUI().GUIClick(player, current, inv);
    }

    @EventHandler
    public static void onConnect(PlayerJoinEvent event){
        if(!(EGames.getCurrentState().equals(EGames.WAITING))) return;
        Player player = event.getPlayer();

        Smash.team.put(player, Team.NEUTRAL);

        ItemStack mainMenu = ItemManager.getItem(Material.NETHER_STAR, "§6Main", false);
        player.getInventory().setItem(0, mainMenu);

        ItemStack team = ItemManager.getItem(Material.WOOL, "§6Team", false);
        player.getInventory().setItem(4, team);

        ItemStack lobby = ItemManager.getItem(Material.DARK_OAK_DOOR_ITEM, "§4Retour Lobby", false);
        player.getInventory().setItem(8, lobby);

        player.updateInventory();
    }
}
