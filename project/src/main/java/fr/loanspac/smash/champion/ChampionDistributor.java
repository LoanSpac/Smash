package fr.loanspac.smash.champion;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChampionDistributor {

    private final Map<ChampionType, Champion> champions = new HashMap<>();
    private final Map<UUID, ChampionType> championOfPlayer = new HashMap<>();

    public ChampionDistributor() {
        loadChampions();
    }

    protected void loadChampions() {
        for (ChampionType type : ChampionType.values()) {
            try {
                Class<? extends Champion> klass = type.getKlass();
                Champion champion = klass.getDeclaredConstructor().newInstance();
                champions.put(type, champion);
            } catch (Exception e) {
                System.out.println(String.format("%s n'est pas chargé", type.name()));
            }
        }
    }

    public void applyChampion(Player player, ChampionType type) {
        if (champions.containsKey(type)) {
            championOfPlayer.put(player.getUniqueId(), type);
            champions.get(type).apply(player);
        } else {
            System.out.println(String.format("%s n'a pas pu devenir %s", player.getName(), type));
        }
    }

    public ChampionType getChampionOfPlayer(Player player) {
        return championOfPlayer.get(player.getUniqueId());
    }

    public ChampionSpell getSpellOfPlayer(Player player, ChampionSpellType spellType) {
        ChampionType type = championOfPlayer.get(player.getUniqueId());
        Champion champion = champions.get(type);
        return champion.getSpell(spellType);
    }

}