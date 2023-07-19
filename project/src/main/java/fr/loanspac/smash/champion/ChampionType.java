package fr.loanspac.smash.champion;

import fr.loanspac.smash.champion.bario.Bario;
import fr.loanspac.smash.champion.marte.Marte;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public enum ChampionType {
    BARIO(Bario.class, 1.),
    MARTE(Marte.class,1.);

    private final double weight;
    private final Class<? extends Champion> klass;

    ChampionType(Class<? extends Champion> klass, double weight) {
        this.klass = klass;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public Class<? extends Champion> getKlass() {
        return klass;
    }
}
