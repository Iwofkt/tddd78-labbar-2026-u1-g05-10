package se.liu.simjolucul.dopeslope.Slopes;

import se.liu.simjolucul.dopeslope.Game.GameBase;
import se.liu.simjolucul.dopeslope.gameObjects.*;
import se.liu.simjolucul.dopeslope.handlers.GateSpawn;
import se.liu.simjolucul.dopeslope.handlers.ImageLoader;
import se.liu.simjolucul.dopeslope.handlers.collision.GateCollisionAlpine;
import se.liu.simjolucul.dopeslope.handlers.collision.GateCollisionEndless;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

public class CombeDeCaron implements GameMode {
    Random RND = new Random();
    GameBase gameBase;
    Player player;
    double deltaDistanceTraveled = 0;
    double oldPlayerDistance = 0;
    private final List<Obstacle> obstacles;
    private final List<Gate> gates;
    private final List<Finishline> finishline;
    private final GateCollisionAlpine gateCollisionAlpine = new GateCollisionAlpine();
    private final GateCollisionEndless gateCollisionEndless = new GateCollisionEndless();
    private final BufferedImage tree_img;
    private final BufferedImage gateL_img;
    private final BufferedImage gateR_img;

    private final List<GateSpawn> gateCourse = List.of(
            new GateSpawn(200, 100),
            new GateSpawn(450, 500),
            new GateSpawn(200, 1000),
            new GateSpawn(450, 1500),
            new GateSpawn(200, 2000),
            new GateSpawn(450, 2500),
            new GateSpawn(200, 3000),
            new GateSpawn(450, 3500)
    );

    private int nextGateIndex = 0;

    private final int finishLineDistance = 4000;
    private boolean finishLineSpawned = false;


    public CombeDeCaron(GameBase gameBase) {
        this.player = gameBase.getPlayer();
        this.gameBase = gameBase;
        this.obstacles = gameBase.getObstacles();
        this.gates = gameBase.getGates();
        this.finishline = gameBase.getFinishline();
        this.tree_img = ImageLoader.loadTextureSize(gameBase.getResourcePack(), "tree", 2, 2);
        this.gateL_img = ImageLoader.loadTextureSize(gameBase.getResourcePack(), "gateL", 1, 1);
        this.gateR_img = ImageLoader.loadTextureSize(gameBase.getResourcePack(), "gateR", 1, 1);
    }


    public void update() {

        handleCollision();

        deltaDistanceTraveled = player.getDistanceTraveled() - oldPlayerDistance;
        if (deltaDistanceTraveled > 10) {
            oldPlayerDistance = player.getDistanceTraveled();

            int obstacleChance = RND.nextInt(0, 30);

            if (obstacleChance >=20) {
                obstacles.add(new Tree(
                        RND.nextInt(-10, 30),
                        gameBase.getHeight(),
                        tree_img
                ));

                obstacles.add(new Tree(
                        RND.nextInt(gameBase.getWidth() - 70, gameBase.getWidth()), // 30
                        gameBase.getHeight(),
                        tree_img
                ));
            }

            while (nextGateIndex < gateCourse.size() &&
                    player.getDistanceTraveled() >= gateCourse.get(nextGateIndex).getDistance()) {

                GateSpawn gateSpawn = gateCourse.get(nextGateIndex);

                gates.add(new Gate(
                        gateSpawn.getX(),
                        gameBase.getHeight(),
                        gameBase.getWidth(),
                        gateL_img,
                        gateR_img
                ));

                nextGateIndex++;
            }

            if (!finishLineSpawned && player.getDistanceTraveled() >= finishLineDistance) {
                System.out.println("finishline!");

                finishline.add(new Finishline(
                        235,
                        gameBase.getHeight(),
                        15,
                        50,
                        gameBase.getResourcePack()
                ));

                finishLineSpawned = true;
            }
        }
    }

    private void handleCollision() {
        for (Gate gatesAlpine : gates) {
            if (gateCollisionAlpine.checkCollision(player, gatesAlpine)) {
                gameBase.setGameOver(true);
            }
        }

        for (Finishline finishLineAlpine : finishline) {
            if (gateCollisionEndless.checkCollision(player, finishLineAlpine)) {
                gameBase.setFinishedRace(true);
                gameBase.setGameOver(true);
            }
        }
    }
}
