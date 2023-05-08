package fr.loanspac.smash.champion.bario.spell;

import fr.loanspac.smash.Smash;
import fr.loanspac.smash.utils.RepeatingTask;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BarioDefensif {
    public void champi(Player player, ItemStack it) {
        if(it.getAmount() == 1) {
            it.setAmount(12);
            it.removeEnchantment(Enchantment.DURABILITY);

            new RepeatingTask(Smash.getInstance(), 0, 1) {

                int ticks = 0;

                @Override
                public void run() {
                    Smash.kbmod.put(player, 0.5d);
                    //player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 1f, 1f);
                    if(ticks == 80) {
                        Smash.kbmod.put(player, 1d);
                        cancel();
                    }
                }
            };
            //new SpellCouldown(it, 13).startCooldown(it, 13);
        }
    }
}
