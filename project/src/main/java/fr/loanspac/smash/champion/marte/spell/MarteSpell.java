package fr.loanspac.smash.champion.marte.spell;

import fr.loanspac.smash.Smash;
import fr.loanspac.smash.utils.RepeatingTask;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MarteSpell {
    private boolean contre = false;

    public boolean getContre() {
        return contre;
    }

    public void setContre(boolean disable) {
        this.contre = disable;
    }

    public void offensif(Player player, ItemStack item) {

    }

    public void utilitaire(Player player, ItemStack item) {
        if(item.getAmount() == 1) {
            item.setAmount(11);
            item.removeEnchantment(Enchantment.DURABILITY);

            new RepeatingTask(Smash.instance(), 0, 1) {

                int ticks = 0;

                @Override
                public void run() {
                    if(ticks == 0) {
                        for(Player players : Bukkit.getOnlinePlayers()) {
                            players.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 1f, 1f);
                        }

                        player.removePotionEffect(PotionEffectType.SPEED);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1));
                        contre = true;
                    }
                    if(ticks == 2*20) {
                        for(Player players : Bukkit.getOnlinePlayers()) {
                            players.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_CHAIN, 1f, 1f);
                        }

                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
                        player.removePotionEffect(PotionEffectType.SLOW);
                        contre = false;
                        cancel();
                    }
                    if(!contre) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
                        player.removePotionEffect(PotionEffectType.SLOW);
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

            //player.setVelocity(new Vector(0D, 1.8D, 0D));
            player.setVelocity(player.getEyeLocation().getDirection().normalize().multiply(0.45).setY(1.8D));
        }
    }

    public void passif(Player player) {
        //player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
        //player.setWalkSpeed(0.25f); // 0.2 par défaut
    }

}
