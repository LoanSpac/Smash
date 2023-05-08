package fr.loanspac.smash.heads;

import fr.loanspac.smash.utils.Skull;
import org.bukkit.inventory.ItemStack;

import java.util.Dictionary;
import java.util.Hashtable;

public class ChampionHeads {
    public ItemStack headOf(String name) {
        Dictionary heads = new Hashtable();
        heads.put("Bario", Skull.getSkull("Bario", "http://textures.minecraft.net/texture/d70d3ece62a3b12344c4b1304a839c692ddde3370bc76a38fc0cca79ec5f2ab8"));
        heads.put("Marte", Skull.getSkull("Marte", "http://textures.minecraft.net/texture/968b446b3315130f35e4e0d5daeed0562a1565a11f16b5c2a90f5fad15a089be"));
        heads.put("Pakichu", Skull.getSkull("Pakichu", "http://textures.minecraft.net/texture/c3599f6bad7d3d8705c85e0e651739f434a6d7bd4e4348cedecc08012b106b41"));
        heads.put("Kirpy", Skull.getSkull("Kirpy", "http://textures.minecraft.net/texture/e9cb59e0f0b3d0dc8b21cd2abff8f267fb6caad97f7b1d0b2b078557e66ccd1"));
        heads.put("Lynk", Skull.getSkull("Lynk", "http://textures.minecraft.net/texture/6bb2e69b3870fe2f4f1ba14a8f9ca8acc9a7520e4e4a9784c19a3a0c9446e4d"));
        heads.put("Bowzer", Skull.getSkull("Bowzer", "http://textures.minecraft.net/texture/1b45ebdda16f0f0645216ceab59f512315f47358b6aba60d07904b6674d644ef"));
        heads.put("Sonyc", Skull.getSkull("Sonyc", "http://textures.minecraft.net/texture/e4aaedea54c6a6029f4f9cc991f927534bc21098cccb4496206e94eff341b42b"));
        heads.put("Foks", Skull.getSkull("Foks", "http://textures.minecraft.net/texture/71be29750ddec80994bda79653e21ed70d5b2eb793da51d5a87b89bf67dcb96"));
        heads.put("Waryo", Skull.getSkull("Waryo", "http://textures.minecraft.net/texture/ca8b3a1af2d8fc37729f6f63fdba5d5e209758ded4b2fcf6add4b85bf67edd72"));
        return (ItemStack) heads.get(name);
    }
}
