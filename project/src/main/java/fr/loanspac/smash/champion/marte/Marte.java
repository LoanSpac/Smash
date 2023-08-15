package fr.loanspac.smash.champion.marte;

import fr.loanspac.smash.champion.Champion;
import fr.loanspac.smash.champion.ChampionHeadType;
import fr.loanspac.smash.champion.ChampionType;
import fr.loanspac.smash.champion.marte.spell.MarteSpell;
import fr.loanspac.smash.utils.ItemManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Marte extends Champion {
    private MarteSpell spell = new MarteSpell();

    public Marte() {
        super(ChampionType.MARTE);
    }

    @Override
    public ItemStack getMainHandItem() {
        return ItemManager.getItem(Material.IRON_SWORD, "§aFalchion", false, true);
    }

    @Override
    public ItemStack[] getArmorContents() {
        ItemStack helmet = ChampionHeadType.MARTE.getHead();
        ItemStack chestplate = ItemManager.getArmor(Material.LEATHER_CHESTPLATE, "§bTunique de Marte", 98,102,178);
        ItemStack leggings = ItemManager.getArmor(Material.LEATHER_LEGGINGS, "§bPantalon de Marte", 19,19,158);
        ItemStack boots = ItemManager.getArmor(Material.LEATHER_BOOTS, "§bBottes de Marte", 79,52,23);

        return new ItemStack[] { boots, leggings, chestplate, helmet };
    }

    @Override
    public List<ItemStack> getSpells() {
        ItemStack offensif = ItemManager.getItem(Material.GHAST_TEAR, "§dChope", true, false);
        ItemStack defensif = ItemManager.getItem(Material.CHAINMAIL_CHESTPLATE, "§dContre Total", true, false);
        ItemStack recovery = ItemManager.getItem(Material.FEATHER, "§dCharge du Dauphin", true, false);
        ItemStack passif = ItemManager.getItem(Material.FLINT, "§dPrécision", false, false);

        ArrayList<ItemStack> items = new ArrayList<>();

        items.add(offensif);
        items.add(defensif);
        items.add(recovery);
        items.add(passif);

        return items;
    }

    @Override
    public void applyOffensif(Player player, ItemStack item) {
        spell.offensif(player, item);
    }

    @Override
    public void applyUtilitaire(Player player, ItemStack item) {
        spell.utilitaire(player, item);
    }

    @Override
    public void applyRecovery(Player player, ItemStack item) {
        spell.recovery(player, item);
    }

    @Override
    public void applyPassif(Player player) {
        spell.passif(player);
    }
}