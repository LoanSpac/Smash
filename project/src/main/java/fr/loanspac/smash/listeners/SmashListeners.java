package fr.loanspac.smash.listeners;

import fr.loanspac.smash.Smash;
import fr.loanspac.smash.champion.Champion;
import fr.loanspac.smash.game.EGames;
import fr.loanspac.smash.utils.RepeatingTask;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Arrays;

/*
 * Nom de classe : SmashListeners
 * Description   : Game listeners class
 * Version       : 1.0
 * Date          : 04/04/2023
 * Copyright     : LoanSpac
 */

public class SmashListeners implements Listener  {
    @EventHandler
    public void onJump(PlayerToggleFlightEvent event) {
        if(!(EGames.getCurrentState().equals(EGames.SMASH))) return;
        Player player = event.getPlayer();
        new RepeatingTask(Smash.getInstance(), 0, 1) {

            int ticks = 0;
            @Override
            public void run() {
                ticks++;
                if(ticks == 1) {
                    player.setAllowFlight(false);

                    double weightModifier = 1;
                    double jumpModifier = 0.85 / weightModifier;

                    Vector jump = player.getLocation().getDirection().multiply(0.5).setY(jumpModifier);
                    player.setVelocity(jump);
                    player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1f, 0.85f);
                }

                if(player.isOnGround()) {
                    player.setAllowFlight(true);
                    cancel();
                }
            }
        };
    }

    @EventHandler
    public void setSpell(PlayerInteractEvent event) {
        if(!(EGames.getCurrentState().equals(EGames.SMASH))) return;
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        Action action = event.getAction();
        Champion champ = Smash.champion.get(player);

        if(item == null) return;
        if(Arrays.asList(Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK).contains(action)) {
            switch(player.getInventory().getHeldItemSlot()) {
                case 1:
                    champ.setOffensif(player, item);
                case 2:
                    champ.setDefensif(player, item);
                case 3:
                    champ.setRecovery(player, item);
            }
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if(!(EGames.getCurrentState().equals(EGames.SMASH))) return;

        if (!(event.getEntity() instanceof Player) && !(event.getDamager() instanceof Projectile)) { // Si ce n'est pas un joueur ou projo
            return;
        }

        if (!(event.getDamager() instanceof Player) && !(event.getDamager() instanceof Projectile)) { // si ne c'est pas un dégat de joueur ou projo
            return;
        }
        if (event.getDamager() instanceof Fireball && event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
            event.setCancelled(true);
            return;
        }

        Entity damagedE = event.getEntity();
        Entity damagerE = event.getDamager();

        if(!(damagedE instanceof Player)) return;

        Player damaged = (Player) event.getEntity();

        if(!(damagerE instanceof Player)) return;

        Player damager = (Player) event.getDamager();


        double damage = Math.round(6.5*Smash.weight.getOrDefault(damager, 1d));

        Smash.percentage.put(damaged, Smash.percentage.getOrDefault(damaged, 0d) + damage);

        double health = Smash.percentage.getOrDefault(damaged, 0d);
        double weightMod = Smash.weight.getOrDefault(damaged, 1d);
        double kbMod =  Smash.kbmod.getOrDefault(damaged, 1d);
        double multiplier = Math.min(10, damage / 7.5 + (Math.exp(0.0072 * (health - 950)) * (Math.pow(damage, 0.59) * Math.pow(health, 1.3))) * kbMod / (weightMod * 1.1));

        Vector direction = damager.getEyeLocation().getDirection();

        direction.setY(((direction.getY() >= 0.45 || direction.getY() <= -0.5) ? direction.getY() : 0.20))
                .normalize()
                .setX(direction.getX() * multiplier)
                .setZ(direction.getZ() * multiplier)
                .setY(direction.getY() * (multiplier / 1.5));

        damaged.setVelocity(direction);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if(!(EGames.getCurrentState().equals(EGames.SMASH))) return;
        event.setDamage(0);
    }

}
