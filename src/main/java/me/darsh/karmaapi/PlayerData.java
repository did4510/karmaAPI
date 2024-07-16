package me.darsh.karmaapi;

public class PlayerData {
    private int punyaPoints;
    private int papPoints;

    public PlayerData(int punyaPoints, int papPoints) {
        this.punyaPoints = punyaPoints;
        this.papPoints = papPoints;
    }

    public int getPunyaPoints() {
        return punyaPoints;
    }

    public void addPunyaPoints(int points) {
        this.punyaPoints += points;
    }

    public int getPapPoints() {
        return papPoints;
    }

    public void addPapPoints(int points) {
        this.papPoints += points;
    }
}
