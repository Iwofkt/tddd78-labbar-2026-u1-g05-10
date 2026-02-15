package se.liu.simjo878.tetris;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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

	File originalFile = new File(filename);
	File tempFile = new File(filename + ".tmp");

	//  Fix map
	File parentDir = originalFile.getParentFile();
	if (parentDir != null && !parentDir.exists()) {
	    if (!parentDir.mkdirs()) {
		throw new IOException("Kunde inte skapa katalog: " + parentDir);
	    }
	}

	//write to temp file
	try (FileWriter writer = new FileWriter(tempFile)) {
	    gson.toJson(this, writer);
	}

	// Move temp to originalfile
	Files.move(tempFile.toPath(),
		   originalFile.toPath(),
		   StandardCopyOption.REPLACE_EXISTING,
		   StandardCopyOption.ATOMIC_MOVE);
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
	return System.getProperty("user.home") + "/tetrisData/" + filename;
    }

}
