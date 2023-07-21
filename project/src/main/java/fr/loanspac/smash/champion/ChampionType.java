package fr.loanspac.smash.champion;

import fr.loanspac.smash.champion.bario.Bario;
import fr.loanspac.smash.champion.marte.Marte;

public enum ChampionType {
    BARIO(Bario.class, 1., ChampionHeadType.BARIO),
    MARTE(Marte.class,1., ChampionHeadType.MARTE);

    private final double weight;
    private final Class<? extends Champion> klass;
    private final ChampionHeadType head;

    ChampionType(Class<? extends Champion> klass, double weight, ChampionHeadType head) {
        this.klass = klass;
        this.weight = weight;
        this.head = head;
    }

    public ChampionHeadType getHeadType() {
        return head;
    }

    public double getWeight() {
        return weight;
    }

    public Class<? extends Champion> getKlass() {
        return klass;
    }
}