package fr.loanspac.smash.champion;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public abstract class ChampionActiveSpell implements Listener {

    private final Material icon;
    private final ChampionSpellType type;
    private final int cooldown;

    protected ChampionActiveSpell(
            Material icon,
            ChampionSpellType type,
            int cooldown
    ) {
        this.icon = icon;
        this.type = type;
        this.cooldown = cooldown;
    }

    public abstract void play(Player player);

    public int getCooldown() {
        return cooldown;
    }

    public Material getIcon() {
        return icon;
    }

    public ChampionSpellType getType() {
        return type;
    }

}
