package fr.loanspac.smash.champion;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Map;

public abstract class Champion {

    protected final String name;
    protected final double weight;
    protected final Map<ChampionSpellType, ChampionSpell> spells = new HashMap<>();

    protected Champion(ChampionType type) {
        this.name = type.name();
        this.weight = type.getWeight();

        loadSpells();
    }

    protected abstract void loadSpells();

    public ChampionSpell getSpell(ChampionSpellType spellType) {
        return spells.get(spellType);
    }

    public abstract ItemStack[] getArmorContents();
    public abstract ItemStack getMainHandItem();

    public void apply(Player player) {
        PlayerInventory inventory = player.getInventory();
        inventory.setArmorContents(getArmorContents());
        inventory.setHeldItemSlot(0);
        inventory.setItem(0, getMainHandItem());
    }

    public String getName() {
        return name;
    }

    public Double getWeight() {
        return weight;
    }

}
