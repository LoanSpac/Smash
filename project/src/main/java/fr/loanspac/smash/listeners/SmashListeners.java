package fr.loanspac.smash.listeners;

import fr.loanspac.smash.Smash;
import fr.loanspac.smash.champion.Champion;
import fr.loanspac.smash.champion.bario.spell.BarioSpell;
import fr.loanspac.smash.champion.marte.Marte;
import fr.loanspac.smash.champion.marte.spell.MarteSpell;
import fr.loanspac.smash.game.EGames;
import fr.loanspac.smash.utils.RepeatingTask;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class SmashListeners implements Listener  {

    private MarteSpell marte = new MarteSpell();
    private BarioSpell bario = new BarioSpell();

    /*
    HandlerList.unregisterAll(this); -> Permet d'enregister/désenregister un listener (on/off)
     */

    @EventHandler
    public void onJump(PlayerToggleFlightEvent event) {
        if(!(EGames.getCurrentState().equals(EGames.SMASH))) return;
        Player player = event.getPlayer();
        new RepeatingTask(Smash.instance(), 0, 1) {

            int ticks = 0;
            @Override
            public void run() {
                ticks++;
                if(ticks == 1) {
                    player.setAllowFlight(false);

                    double weightModifier = 1;
                    double jumpModifier = 0.7 / weightModifier;

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
    public void onClick(PlayerInteractEvent event) {
        if(!(EGames.getCurrentState().equals(EGames.SMASH))) return;
        Player player = event.getPlayer();
        Action action = event.getAction();

        if(action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) player.getInventory().setHeldItemSlot(0);
    }


    @EventHandler
    public void onSpell(PlayerInteractEvent event) {
        if(!(EGames.getCurrentState().equals(EGames.SMASH))) return;
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        Action action = event.getAction();
        Champion champ = Smash.champion.get(player);

        if(item == null) return;
        if((action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) || (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK))) {
            switch(player.getInventory().getHeldItemSlot()) {
                case 1:
                    champ.applyOffensif(player, item);
                    break;
                case 2:
                    champ.applyUtilitaire(player, item);
                    break;
                case 3:
                    champ.applyRecovery(player, item);
                    break;
                default:
                    break;
            }
        }
    }

    /*
    @EventHandler
    public void onHeldSwitch(PlayerItemHeldEvent event) {
        if(!(EGames.getCurrentState().equals(EGames.SMASH))) return;
        Player player = event.getPlayer();

        if (event.getNewSlot() == 1) {
            ChampionSpell spell = Smash.instance().getChampionDistributor().getSpellOfPlayer(player, ChampionSpellType.OFFENSIVE);
            if (spell instanceof ChampionActiveSpell) {
                ChampionActiveSpell activeSpell = (ChampionActiveSpell) spell;
                activeSpell.play(player);
            }
        }

        event.setCancelled(true);
    }
     */

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if(!(EGames.getCurrentState().equals(EGames.SMASH))) return;

        if(event.getDamager() instanceof  Fireball) {
            Fireball fireball = (Fireball) event.getDamager();
            if(event.getEntity().equals(fireball.getShooter())) {
                event.setCancelled(true);
                return;
            }
            if(event.getEntity() instanceof Player) {
                Player damaged = (Player) event.getEntity();

                double damage = 8;
                Smash.player.get(damaged).setPercent(Smash.player.get(damaged).getPercent() + damage);

                double health = Smash.player.get(damaged).getPercent();
                //double weightMod = Smash.player.get(damaged).getWeight();
                double weightMod = 1;
                double kbMod =  Smash.player.get(damaged).getKbmod();

                double multiplier = Math.min(10, damage / 7.5 + (Math.exp(0.0072 * (health - 950)) * (Math.pow(damage, 0.59) * Math.pow(health, 1.3))) * kbMod / (weightMod * 1.1));

                Vector direction = fireball.getDirection();

                direction.setY(((direction.getY() >= 0.45 || direction.getY() <= -0.5) ? direction.getY() : 0.20))
                        .normalize()
                        .setX(direction.getX() * multiplier)
                        .setZ(direction.getZ() * multiplier)
                        .setY(direction.getY() * (multiplier / 1.5));

                damaged.setVelocity(direction);

                return;
            }
        }

        Entity damagedE = event.getEntity();
        Entity damagerE = event.getDamager();

        if(!(damagedE instanceof Player)) return;

        Player damaged = (Player) event.getEntity();

        if(!(damagerE instanceof Player)) return;

        Player damager = (Player) event.getDamager();

        ///////////////////

        //double damage = Math.round(6.5*Smash.player.get(damager).getWeight());
        //double damage = Math.round(6.5*1);

        double damage = 6.5*1;

        if(Smash.champion.get(damager).equals(new Marte())) {
            if(damager.getLocation().distance(damaged.getLocation()) <= 1) {
                damage = 6;
            }
            if(damager.getLocation().distance(damaged.getLocation()) > 1 && damager.getLocation().distance(damaged.getLocation()) <= 2) {
                damage = 6.5;
            }
            if(damager.getLocation().distance(damaged.getLocation()) > 2) {
                damage = 7;
            }
        }

        //System.out.println(damager.getLocation().distance(damaged.getLocation()));
        //double damage = 6.5*1;

        if(bario.getPlanner()) {
            bario.setPlanner(false);
        }

        if(!(marte.getContre())) {
            Smash.player.get(damaged).setPercent(Smash.player.get(damaged).getPercent() + damage);
            smashKb(damaged, damager, damage);
        } else {
            Smash.player.get(damager).setPercent(Smash.player.get(damager).getPercent() + damage);
            smashKb(damager, damager, damage);
            marte.setContre(false);
        }
    }

    private void smashKb(Player damaged, Player damager, Double damage) {
        double health = Smash.player.get(damaged).getPercent();
        //double weightMod = Smash.player.get(damaged).getWeight();
        double weightMod = 1;
        double kbMod =  Smash.player.get(damaged).getKbmod();

        double multiplier = Math.min(10, damage / 7.5 + (Math.exp(0.0072 * (health - 950)) * (Math.pow(damage, 0.59) * Math.pow(health, 1.3))) * kbMod / (weightMod * 1.1));

        Vector direction = damager.getEyeLocation().getDirection();

        if(marte.getContre()) {
            direction = damager.getEyeLocation().getDirection().multiply(-1);
        }

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
