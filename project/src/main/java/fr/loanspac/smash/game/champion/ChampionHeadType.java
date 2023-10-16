package fr.loanspac.smash.game.champion;

import fr.loanspac.smash.utils.Skull;
import org.bukkit.inventory.ItemStack;

public enum ChampionHeadType {
    BARIO(Skull.getSkull("§bBario", "http://textures.minecraft.net/texture/af50fc40cbffeaaa8dcc1274c18671b552e73648f6b2336e13057606141d47f")),
    MARTE(Skull.getSkull("§bMarte", "http://textures.minecraft.net/texture/968b446b3315130f35e4e0d5daeed0562a1565a11f16b5c2a90f5fad15a089be"));

    private final ItemStack head;

    ChampionHeadType(ItemStack head) {
        this.head = head;
    }

    public ItemStack getHead() {
        return head;
    }

}

