package fr.loanspac.smash.event;

import fr.loanspac.smash.champion.ChampionType;
import fr.loanspac.smash.champion.ChampionSpellType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class StartChampionSpellEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final ChampionType championType;
    private final Player player;
    private final ChampionSpellType spellType;

    public StartChampionSpellEvent(ChampionType championType, Player player, ChampionSpellType spellType) {
        this.championType = championType;
        this.player = player;
        this.spellType = spellType;
    }

    public ChampionType getChampionType() {
        return championType;
    }

    public Player getPlayer() {
        return player;
    }

    public ChampionSpellType getSpellType() {
        return spellType;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}