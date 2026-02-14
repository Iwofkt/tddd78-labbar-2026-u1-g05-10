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

    public void addScore(Highscore score) {
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

    public void save() {
	saveToFile(getFullPath(DEFAULT_FILENAME));
    }

    public void saveToFile(String filename) {
	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	try (FileWriter writer = new FileWriter(filename)) {
	    gson.toJson(this, writer);
	}
	catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public static HighscoreList load() {
	return loadFromFile(getFullPath(DEFAULT_FILENAME));
    }

    public static HighscoreList loadFromFile(String filename) {
	Gson gson = new Gson();

	try (FileReader reader = new FileReader(filename)) {
	    return gson.fromJson(reader, HighscoreList.class);
	} catch (IOException e) {
	    e.printStackTrace();
	    return new HighscoreList(); // om filen inte finns
	}
    }

    private static String getFullPath(String filename) {
	return System.getProperty("user.home") + "/" + filename;
    }

}
