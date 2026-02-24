package se.liu.simjo878.tetris.highscore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class HighscoreList {

    private List<Highscore> highscores;
    private static final String DEFAULT_FILENAME = "highscores.json";


    public HighscoreList() {
	highscores = new ArrayList<>();
    }

    public void addScore(Highscore score) throws IOException {
	highscores.add(score);
	highscores.sort(new ScoreComparator());
	save();
    }

    // -- GETTERS -- //

    public List<Highscore> getHighscores() {
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



    public static HighscoreList load() throws FileNotFoundException {
	return loadFromFile(getFullPath(DEFAULT_FILENAME));
    }

    public static HighscoreList loadFromFile(String filename) throws FileNotFoundException {
	Gson gson = new Gson();
	try (FileReader reader = new FileReader(filename)) {
	    return gson.fromJson(reader, HighscoreList.class);
	} catch (IOException ignored) {
	    throw new FileNotFoundException();
	}
    }

    private static String getFullPath(String filename) {
	return System.getProperty("user.home") + "/tetrisData/" + filename;
    }

}
