package fr.loanspac.smash.guis.button;

import fr.loanspac.smash.Smash;
import fr.loanspac.smash.champion.ChampionType;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ChampionButtonGUI extends ButtonGUI {

    private final ChampionType championType;

    public ChampionButtonGUI(ChampionType championType) {
        super(Material.GOLD_AXE, championType.name());
        this.championType = championType;
    }

    @Override
    public void play(Player player) {
        Smash.instance().getChampionDistributor().applyChampion(player, championType);
    }

}
