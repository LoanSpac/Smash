package fr.loanspac.smash.game.champion.bario;

import fr.loanspac.smash.game.champion.Champion;
import fr.loanspac.smash.game.champion.ChampionHeadType;
import fr.loanspac.smash.game.champion.ChampionType;
import fr.loanspac.smash.game.champion.bario.spell.BarioSpell;
import fr.loanspac.smash.utils.ItemManager;
import fr.loanspac.smash.utils.Skull;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Bario extends Champion {
    private BarioSpell spell = new BarioSpell();

    public Bario() {
        super(ChampionType.BARIO);
    }

    /*
    @Override
    protected void loadSpells() {
        spells.put(ChampionSpellType.OFFENSIVE, new BarioOffensiveSpell());
    }
     */

    @Override
    public ItemStack getMainHandItem() {
        return null;
    }

    @Override
    public ItemStack[] getArmorContents() {
        ItemStack helmet = ChampionHeadType.BARIO.getHead();
        ItemStack chestplate = ItemManager.getArmor(Material.LEATHER_CHESTPLATE, "§bTunique de Bario", 6,6,213);
        ItemStack leggings = ItemManager.getArmor(Material.LEATHER_LEGGINGS, "§bPantalon de Bario", 6,6,150);
        ItemStack boots = ItemManager.getArmor(Material.LEATHER_BOOTS, "§bBottes de Bario", 79, 52, 23);

        return new ItemStack[] { boots, leggings, chestplate, helmet };
    }

    @Override
    public List<ItemStack> getSpells() {
        ItemStack offensif = ItemManager.getItem(Material.FIREBALL, "§dBoule de feu", true, false);
        ItemStack defensif = Skull.getSkull("§dSuper Champignon", "http://textures.minecraft.net/texture/ff96b8d01f5835ed38afd4530228d0b5abb7d45a355519ea680c40ffca32edf2");
        ItemStack recovery = ItemManager.getItem(Material.FEATHER, "§d360 Jump", true, false);
        ItemStack passif = ItemManager.getItem(Material.SUGAR, "§dSpeed", false, false);

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