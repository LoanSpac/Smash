package fr.loanspac.smash.champion.bario.spell;

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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class BarioSpell {
    public void offensif(Player player, ItemStack it) {
        if(it.getAmount() == 1) {
            it.setAmount(6);
            it.removeEnchantment(Enchantment.DURABILITY);

            Location loc = player.getLocation();
            Projectile projectile = player.launchProjectile(Fireball.class,loc.getDirection().normalize());
            projectile.setVelocity(projectile.getVelocity().multiply(2.0));


            new RepeatingTask(Smash.getInstance(), 0, 1) {

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

    public void defensif(Player player, ItemStack it) {
        if(it.getAmount() == 1) {
            it.setAmount(12);
            it.removeEnchantment(Enchantment.DURABILITY);

            new RepeatingTask(Smash.getInstance(), 0, 1) {

                int ticks = 0;

                @Override
                public void run() {
                    Smash.player.get(player).setKbmod(0.5d);
                    //player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 1f, 1f);
                    if(ticks == 80) {
                        Smash.player.get(player).setKbmod(1d);
                        cancel();
                    }
                }
            };
        }
    }

    public void recovery(Player player, ItemStack it) {
        if(it.getAmount() == 1) {
            it.setAmount(14);
            it.removeEnchantment(Enchantment.DURABILITY);

            new RepeatingTask(Smash.getInstance(), 0, 1) {

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
                            break;
                        default:
                            if(ticks < 11) {
                                player.setVelocity(player.getVelocity().multiply(0.3));
                                if(ticks % 2 == 0) {
                                    degree += 60;
                                    location.setYaw(degree);
                                    player.teleport(location);
                                }
                            }if(ticks > 32) {  // Pour smooth : 32/1.7 == 28/1.5
                            player.setVelocity(player.getEyeLocation().getDirection().normalize().multiply(0.45).setY(-0.2));  // ON HIT CONFIG
                        }
                            break;
                    }
                    if(ticks > 11) {
                        if(player.isSneaking() || player.isOnGround()) {
                            cancel();
                        }
                    }
                    ticks ++;
                }
            };
        }
    }

    public void passif(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
        player.setWalkSpeed(0.25f); // 0.2 par défaut
    }
}
