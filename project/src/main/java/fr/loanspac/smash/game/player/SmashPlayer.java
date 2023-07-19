package fr.loanspac.smash.game.player;

public class SmashPlayer {
    private Double percent;
    private Double weight;
    private Double kbmod;

    public void setPercent(Double percent) {
        this.percent = percent;
    }
    public void setWeight(Double weight) {
        this.weight = weight;
    }
    public void setKbmod(Double kbmod) {
        this.kbmod = kbmod;
    }

    public Double getPercent() {
        return percent;
    }
    public Double getWeight() {
        return weight;
    }
    public Double getKbmod() {
        return kbmod;
    }
}
