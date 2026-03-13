package se.liu.simjolucul.dopeSlope.Game;

import se.liu.simjolucul.dopeSlope.Slopes.CombeDeCaron;
import se.liu.simjolucul.dopeSlope.Slopes.Endless;
import se.liu.simjolucul.dopeSlope.Slopes.GameMode;
import se.liu.simjolucul.dopeSlope.effects.snow.SnowFall;
import se.liu.simjolucul.dopeSlope.effects.snow.SnowParticle;
import se.liu.simjolucul.dopeSlope.effects.snow.SnowSpray;
import se.liu.simjolucul.dopeSlope.effects.track.Tracks;
import se.liu.simjolucul.dopeSlope.effects.track.trackParticle;
import se.liu.simjolucul.dopeSlope.gameObjects.*;
import se.liu.simjolucul.dopeSlope.handlers.GameTimer;
import se.liu.simjolucul.dopeSlope.handlers.ImageLoader;
import se.liu.simjolucul.dopeSlope.handlers.collision.ObjectCollision;
import se.liu.simjolucul.dopeSlope.handlers.interaction.InputHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameBase {

    private final int MARGIN = 30;
    private final int width;
    private final int height;

    GameMode gameMode;
    GameModeType gameModeType = GameModeType.Endless;
    String resourcePack = "standardPixel";

    private final Player player;
    private final BufferedImage player_img;

    private final GameTimer gameTimer = new GameTimer();
    private final ObjectCollision objectCollision = new ObjectCollision();
    private InputHandler inputHandler;

    private final SnowFall snowFall;
    private final Tracks playerTracks;
    private final SnowSpray snowSpray;

    private final List<Obstacle> obstacles = new ArrayList<>();
    private final List<Gate> gates = new ArrayList<>();
    private final List<Finishline> finishline = new ArrayList<>();

    private boolean gameOver = false;
    private boolean gamePaused = false;
    private boolean newGame = false;
    private boolean finishedRace = false;

    private final List<WorldObserver> observers;

    //--  CONSTRUCTOR --//

    public GameBase(int width, int height) {

        this.height = height;
        this.width = width;

        Point spawnPoint = new Point(width / 2, height / 3);
        double PLAYER_START_ROTATION = 0.5 * Math.PI;

        this.player_img = ImageLoader.loadTextureSize(getResourcePack(), "player", 2, 2);
        this.player = new Player(spawnPoint, PLAYER_START_ROTATION, player_img);

        this.observers = new ArrayList<>();

        if (gameModeType == GameModeType.Endless) {
            gameMode = new Endless(this);
        } else if (gameModeType == GameModeType.CombeDeCaron) {
            gameMode = new CombeDeCaron(this);
        }

        snowFall = new SnowFall(1, 2, width);
        snowFall.initializeSnowfall(width, height);

        snowSpray = new SnowSpray(3.0, 11.8);
        playerTracks = new Tracks(20, player.getRotation(), player.getSize());
    }

    //-- GETTERS --//

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean getGamePaused() {
        return gamePaused;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public List<Gate> getGates() {
        return gates;
    }

    public List<Finishline> getFinishline() {
        return finishline;
    }

    public String getResourcePack() {
        return resourcePack;
    }

    public List<SnowParticle> getSnowParticles() {
        return snowFall.getSnowParticles();
    }

    public List<trackParticle> getTrackParticles() {
        return playerTracks.getTrackParticles();
    }

    public List<SnowSpray.SprayParticle> getSprayParticles() {
        return snowSpray.getParticles();
    }

    public int getMargin() {
        return MARGIN;
    }

    public double getGameTime() {
        return gameTimer.getTime();
    }

    public String getFormattedGameTime() {
        return gameTimer.getFormattedTime();
    }

    public boolean hasFinishedRace() {
        return finishedRace;
    }

    // Used for alpine highscores
    public int getElapsedCentiseconds() {
        return (int) (gameTimer.getTime() * 100);
    }

    //-- SETTERS --//

    public void setGameOver(boolean gameOver) {
        if (gameOver) {
            gameTimer.pause();
        }
        this.gameOver = gameOver;
        notifyObservers();
    }

    public void setInputHandler(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    public void setFinishedRace(boolean finishedRace) {
        this.finishedRace = finishedRace;
    }

    public void setGamePaused(boolean gamePaused) {
        this.gamePaused = gamePaused;

        if (gamePaused) {
            gameTimer.pause();
        } else {
            gameTimer.resume();
        }

        notifyObservers();
    }

    //-- WORLD UPDATE --//

    public void update() {

        if (getGameOver() || getGamePaused()) return;

        player.update();
        player.moveHorizontally(width, MARGIN);

        if (inputHandler != null) {

            if (inputHandler.isLeftPressed()) {
                player.rotate(Direction.LEFT);
            }

            if (inputHandler.isRightPressed()) {
                player.rotate(Direction.RIGHT);
            }

            if (inputHandler.isQuitPressed()) {
                System.exit(0);
                inputHandler.resetQuit();
            }
        }

        gameMode.update();

        handleCollision();
        filterObstacles();

        updateParticles();

        notifyObservers();
    }

    private void updateParticles() {

        snowFall.update((int) player.getCurrentSpeed());
        playerTracks.updateMovement((int) player.getYSpeed());

        double moveAngle = Math.atan2(player.getYSpeed(), player.getXSpeed());

        playerTracks.spawnTracks(player, moveAngle);

        if (player.getCurrentSpeed() > 8.0) {
            Point[] tips = player.getSkiTipPositions();

            for (Point tip : tips) {
                snowSpray.spawn(tip.x, tip.y, player.getCurrentSpeed(), moveAngle);
            }
        }

        snowSpray.update((int) player.getYSpeed());
    }

    private void handleCollision() {

        for (Obstacle obstacle : obstacles) {

            if (objectCollision.checkCollision(player, obstacle)) {
                setGameOver(true);
            }
        }
    }

    private void filterObstacles() {

        Iterator<Obstacle> itO = obstacles.iterator();
        while (itO.hasNext()) {
            Obstacle o = itO.next();
            o.update(player.getYSpeed());

            if (o.getPosition().y + o.getHeight() < 0) {
                itO.remove();
            }
        }

        Iterator<Gate> itG = gates.iterator();
        while (itG.hasNext()) {
            Gate g = itG.next();
            g.update(player.getYSpeed());

            if (g.getPosition().y + g.getHeight() < 0) {
                itG.remove();
            }
        }

        Iterator<Finishline> itF = finishline.iterator();
        while (itF.hasNext()) {
            Finishline f = itF.next();
            f.update(player.getYSpeed());

            if (f.getPosition().y + f.getHeight() < 0) {
                itF.remove();
            }
        }
    }

    //-- OBSERVERS --//

    public void addObserver(WorldObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers() {

        for (WorldObserver observer : observers) {
            observer.worldUpdated();
        }
    }

    //-- GAME RESET --//

    public void restart(GameModeType gameModeType) {

        gameOver = false;
        gamePaused = false;
        newGame = true;
        finishedRace = false;
        this.gameModeType = gameModeType;

        inputHandler.setLeftPressed(false);
        inputHandler.setRightPressed(false);

        obstacles.clear();
        gates.clear();
        finishline.clear();

        gameTimer.reset();
        gameTimer.start();

        Point spawnPoint = new Point(width / 2, height / 3);
        double startRotation = 0.5 * Math.PI;

        player.reset(spawnPoint, startRotation);

        if (gameModeType == GameModeType.Endless) {
            gameMode = new Endless(this);
        } else if (gameModeType == GameModeType.CombeDeCaron) {
            gameMode = new CombeDeCaron(this);
        }

        playerTracks.clear();
        snowSpray.clear();
        snowFall.reset();

        notifyObservers();
    }

    public boolean newGame() {
        boolean holder = newGame;
        newGame = false;
        return holder;
    }

    public GameModeType getGameModeType() {
        return gameModeType;
    }
}