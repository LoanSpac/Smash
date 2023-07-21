package fr.loanspac.smash.champion;

import fr.loanspac.smash.utils.Skull;
import org.bukkit.inventory.ItemStack;

public enum ChampionHeadType {
    BARIO(Skull.getSkull("§bBario", "http://textures.minecraft.net/texture/d70d3ece62a3b12344c4b1304a839c692ddde3370bc76a38fc0cca79ec5f2ab8")),
    MARTE(Skull.getSkull("§bMarte", "http://textures.minecraft.net/texture/968b446b3315130f35e4e0d5daeed0562a1565a11f16b5c2a90f5fad15a089be"));

    private final ItemStack head;

    ChampionHeadType(ItemStack head) {
        this.head = head;
    }

    public ItemStack getHead() {
        return head;
    }

}

