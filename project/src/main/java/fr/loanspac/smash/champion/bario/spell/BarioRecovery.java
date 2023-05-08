package fr.loanspac.smash.champion.bario.spell;

import fr.loanspac.smash.Smash;
import fr.loanspac.smash.utils.RepeatingTask;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class BarioRecovery {
    public void featherJump(Player player, ItemStack it) {
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
                                    degree %= 360;

                                    location.setYaw(degree - 180);
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
            //new SpellCouldown(it, 15).startCooldown(it, 15);
        }
    }
}
