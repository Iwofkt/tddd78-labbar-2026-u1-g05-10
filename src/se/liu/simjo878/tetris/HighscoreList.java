package se.liu.simjo878.tetris;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class HighscoreList {

    private ArrayList<Highscore> highscores;
    private static final String DEFAULT_FILENAME = "highscores.json";


    public HighscoreList() {
	highscores = new ArrayList<>();
    }

    public void addScore(Highscore score) throws IOException {
	highscores.add(score);
	save(); // Spara automatiskt när listan ändras
    }

    // -- GETTERS -- //

    public ArrayList<Highscore> getHighscores() {
	return highscores;
    }

    public int getLength() {
	return highscores.size();
    }

    // -- SAVE & LOAD HIGHSCORES -- //

    public void save() throws IOException {
	saveToFile(getFullPath(DEFAULT_FILENAME));
    }

    public void saveToFile(String filename) throws IOException {
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	try (FileWriter writer = new FileWriter(filename)) {
	    gson.toJson(this, writer);
	}
    }

    public static HighscoreList load() throws IOException {
	return loadFromFile(getFullPath(DEFAULT_FILENAME));
    }

    public static HighscoreList loadFromFile(String filename) throws IOException {
	Gson gson = new Gson();
	try (FileReader reader = new FileReader(filename)) {
	    return gson.fromJson(reader, HighscoreList.class);
	}
    }

    private static String getFullPath(String filename) {
	return System.getProperty("user.home") + "/" + filename;
    }

}
