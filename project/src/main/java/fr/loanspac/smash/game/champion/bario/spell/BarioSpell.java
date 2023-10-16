package fr.loanspac.smash.game.champion.bario.spell;


import fr.loanspac.smash.Smash;
import fr.loanspac.smash.utils.RepeatingTask;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class BarioSpell {
    private boolean planner = false;
    public boolean getPlanner() {
        return planner;
    }

    public void setPlanner(boolean disable) {
        this.planner = disable;
    }

    public void offensif(Player player, ItemStack item) {
        if(item.getAmount() == 1) {
            item.setAmount(6);
            item.removeEnchantment(Enchantment.DURABILITY);

            Location loc = player.getLocation();
            Projectile projectile = player.launchProjectile(Fireball.class,loc.getDirection().normalize());
            //projectile.setVelocity(projectile.getVelocity().multiply(2.0));


            new RepeatingTask(Smash.instance(), 0, 1) {

                @Override
                public void run() {

                    Location loc2 = projectile.getLocation();
                    projectile.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc2, 1);
                    if(projectile.isDead()) {
                        cancel();
                    }
                }
            };
        }
    }

    public void utilitaire(Player player, ItemStack item) {
        if(item.getAmount() == 1) {
            item.setAmount(12);
            item.removeEnchantment(Enchantment.DURABILITY);

            new RepeatingTask(Smash.instance(), 0, 1) {

                int ticks = 0;

                @Override
                public void run() {
                    if(ticks == 0) {
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 1f, 1f);
                        Smash.player.get(player).setKbmod(0.5d);
                    }
                    if(ticks == 3*20) {
                        Smash.player.get(player).setKbmod(1d);
                        cancel();
                    }
                    ticks++;
                }
            };
        }
    }

    public void recovery(Player player, ItemStack item) {
        if(item.getAmount() == 1) {
            item.setAmount(15);
            item.removeEnchantment(Enchantment.DURABILITY);

            new RepeatingTask(Smash.instance(), 0, 1) {

                int ticks = 0;

                @Override
                public void run() {
                    final Location location = player.getLocation().clone();
                    float degree = location.getYaw() + 180;

                    switch (ticks) {
                        case 11:
                            //player.spawnParticle(Particle.BLOCK_DUST, player.getLocation(), 0, 1.275, 0, 2, 0);
                            player.setVelocity(new Vector(0D, 1.7D, 0D));
                            player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1f, 0.1f);
                            planner = true;
                            break;
                        default:
                            if(ticks < 11) {
                                player.setVelocity(player.getVelocity().multiply(0.3));
                                if(ticks % 2 == 0) {
                                    degree += 60;
                                    location.setYaw(degree);
                                    player.teleport(location);
                                }
                            }
                            if(ticks > 32) {  // Pour smooth : 32/1.7 == 28/1.5
                                player.setVelocity(player.getEyeLocation().getDirection().normalize().multiply(0.45).setY(-0.2));  // ON HIT CONFIG
                            }
                            break;
                    }
                    if(ticks > 11) {
                        if(player.isSneaking() || player.isOnGround()) {
                            cancel();
                        }
                        if(!planner) {
                            cancel();
                        }
                    }
                    ticks ++;
                }
            };
        }
    }

    public void passif(Player player) {
        //player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
        //player.setWalkSpeed(0.25f); // 0.2 par défaut
    }

}
