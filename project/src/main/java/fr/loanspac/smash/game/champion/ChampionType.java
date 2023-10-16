package fr.loanspac.smash.game.champion;

import fr.loanspac.smash.game.champion.bario.Bario;
import fr.loanspac.smash.game.champion.marte.Marte;

public enum ChampionType {
    BARIO(Bario.class, "Bario", 1., ChampionHeadType.BARIO),
    MARTE(Marte.class, "Marte", 1., ChampionHeadType.MARTE);

    private final double weight;
    private final String name;
    private final Class<? extends Champion> klass;
    private final ChampionHeadType head;

    ChampionType(Class<? extends Champion> klass, String name, double weight, ChampionHeadType head) {
        this.klass = klass;
        this.name = name;
        this.weight = weight;
        this.head = head;
    }

    public ChampionHeadType getHeadType() {
        return head;
    }
    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public Class<? extends Champion> getKlass() {
        return klass;
    }
}