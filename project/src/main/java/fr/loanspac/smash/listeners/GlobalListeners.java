package fr.loanspac.smash.listeners;

import fr.loanspac.smash.game.EGames;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class GlobalListeners implements Listener {
    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBurn(BlockBurnEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        event.setCancelled(true);
    } // A VERIF

    @EventHandler
    public void onBlockBreak(EntityChangeBlockEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(EGames.getCurrentState().equals(EGames.WAITING) || EGames.getCurrentState().equals(EGames.SMASH)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrink(PlayerItemConsumeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if(EGames.getCurrentState().equals(EGames.WAITING) || EGames.getCurrentState().equals(EGames.SMASH)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(EGames.getCurrentState().equals(EGames.WAITING) || EGames.getCurrentState().equals(EGames.SMASH)) {
            event.setCancelled(true);
        }
    }
}
