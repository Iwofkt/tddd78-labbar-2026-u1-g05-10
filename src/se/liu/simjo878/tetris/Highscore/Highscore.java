package se.liu.simjo878.tetris.Highscore;

public class Highscore {

    private String name;
    private int points;

    public Highscore() {
	// Tom konstruktor behövs för Gson
    }

    public Highscore(String name, int points) {
	this.name = name;
	this.points = points;
    }

    public String getName() {
	return name;
    }

    public int getPoints() {
	return points;
    }
}


