package fr.loanspac.smash.game.champion.bario.spell;

import fr.loanspac.smash.Smash;
import fr.loanspac.smash.game.champion.ChampionActiveSpell;
import fr.loanspac.smash.game.champion.ChampionSpellType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class BarioOffensiveSpell extends ChampionActiveSpell {

    private final Map<Fireball, BukkitTask> projectileAnimation = new HashMap<>();

    public BarioOffensiveSpell() {
        super(Material.REDSTONE_COMPARATOR, ChampionSpellType.OFFENSIVE, 14);
    }

    @Override
    public void play(Player player) {
        Location loc = player.getLocation();
        Fireball projectile = player.launchProjectile(Fireball.class, loc.getDirection().normalize());
        projectile.setVelocity(projectile.getVelocity().multiply(2.0));

        playEffect(projectile);
    }

    protected void playEffect(Fireball projectile) {
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                Location location = projectile.getLocation();
                projectile.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, location, 1);
            }
        }.runTaskTimer(Smash.instance(), 0, 1L);

        projectileAnimation.put(projectile, task);
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();

        if (projectile instanceof Fireball && projectileAnimation.containsKey(projectile)) {
            projectileAnimation.remove(projectile).cancel();
        }
    }

}