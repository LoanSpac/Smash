package fr.loanspac.smash.champion.bario.spell;

import fr.loanspac.smash.Smash;
import fr.loanspac.smash.utils.RepeatingTask;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;

public class BarioOffensif {
    public void fireball(Player player, ItemStack it) {
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
                        //projectile.getLastDamageCause();
                        //projectile.getLastDamageCause().getCause().toString();
                        cancel();
                    }
                }
            };
            //new SpellCouldown(it, 7).startCooldown(it, 7);
        }
    }
}
