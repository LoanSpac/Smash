package fr.loanspac.smash.guis.buttons;

import fr.loanspac.smash.Smash;
import fr.loanspac.smash.game.champion.ChampionType;
import org.bukkit.entity.Player;

public class ChampionButtonGUI extends ButtonGUI {

    private final ChampionType championType;

    public ChampionButtonGUI(ChampionType championType) {
        super(championType.getHeadType().getHead());
        this.championType = championType;
    }

    @Override
    public void play(Player player) {
        Smash.instance().getChampionDistributor().applyChampion(player, championType);
    }

}