package se.liu.simjolucul.dopeslope.Slopes;

import se.liu.simjolucul.dopeslope.handlers.ImageLoader;
import se.liu.simjolucul.dopeslope.Game.GameBase;
import se.liu.simjolucul.dopeslope.gameObjects.*;
import se.liu.simjolucul.dopeslope.handlers.collision.GateCollisionEndless;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

public class Endless implements GameMode {
    // --- Constants for spawn logic ---
    private static final int SPAWN_DISTANCE_THRESHOLD = 10;          // player distance units between spawn checks
    private static final int OBSTACLE_CHANCE_MAX = 30;               // random range [0, 30)
    private static final int GATE_CHANCE_MAX = 100;                  // random range [0, 100)
    private static final int GATE_SPAWN_THRESHOLD = 0;                // exact value that triggers gate spawn
    private static final int GATE_MIN_X_OFFSET = 60;                  // minimum x for gate (from left)
    private static final int GATE_MAX_X_OFFSET = 170;                 // maximum x offset from right edge

    // Texture scaling factors
    private static final int OBSTACLE_TEXTURE_SCALE = 2;              // trees and rocks
    private static final int GATE_TEXTURE_SCALE = 1;                  // gates

    private final Random RND = new Random();
    private final GameBase gameBase;
    private final Player player;
    private final List<Obstacle> obstacles;
    private final List<Gate> gates;
    private final GateCollisionEndless gateCollisionEndless = new GateCollisionEndless();
    private final BufferedImage treeImg;
    private final BufferedImage rockImg;
    private final BufferedImage gateLImg;
    private final BufferedImage gateRImg;

    private double deltaDistanceTraveled = 0;
    private double oldPlayerDistance = 0;

    public Endless(GameBase gameBase) {
        this.gameBase = gameBase;
        this.player = gameBase.getPlayer();
        this.obstacles = gameBase.getObstacles();
        this.gates = gameBase.getGates();

        this.treeImg = ImageLoader.loadTextureSize(gameBase.getResourcePack(), "tree",
                OBSTACLE_TEXTURE_SCALE, OBSTACLE_TEXTURE_SCALE);
        this.rockImg = ImageLoader.loadTextureSize(gameBase.getResourcePack(), "rock",
                OBSTACLE_TEXTURE_SCALE, OBSTACLE_TEXTURE_SCALE);
        this.gateLImg = ImageLoader.loadTextureSize(gameBase.getResourcePack(), "gateL",
                GATE_TEXTURE_SCALE, GATE_TEXTURE_SCALE);
        this.gateRImg = ImageLoader.loadTextureSize(gameBase.getResourcePack(), "gateR",
                GATE_TEXTURE_SCALE, GATE_TEXTURE_SCALE);
    }

    @Override
    public void update() {
        handleCollision();

        deltaDistanceTraveled = player.getDistanceTraveled() - oldPlayerDistance;
        if (deltaDistanceTraveled > SPAWN_DISTANCE_THRESHOLD) {
            oldPlayerDistance = player.getDistanceTraveled();

            int obstacleChance = RND.nextInt(OBSTACLE_CHANCE_MAX);
            int margin = gameBase.getMargin();

            if (obstacleChance == 0) {
                int x = RND.nextInt(margin, gameBase.getWidth() - margin);
                obstacles.add(new Rock(x, gameBase.getHeight(), rockImg));

            } else if (obstacleChance == 1) {
                int x = RND.nextInt(margin, gameBase.getWidth() - margin);
                obstacles.add(new Tree(x, gameBase.getHeight(), treeImg));

            } else if (obstacleChance >= 20) {
                int leftX = RND.nextInt(-treeImg.getWidth(), margin);
                obstacles.add(new Tree(leftX, gameBase.getHeight(), treeImg));

                int rightX = RND.nextInt(gameBase.getWidth() - margin- treeImg.getWidth(), gameBase.getWidth());
                obstacles.add(new Tree(rightX, gameBase.getHeight(), treeImg));
            }

            int gateChance = RND.nextInt(GATE_CHANCE_MAX);
            if (gateChance == GATE_SPAWN_THRESHOLD) {
                int x = RND.nextInt(GATE_MIN_X_OFFSET, gameBase.getWidth() - GATE_MAX_X_OFFSET);
                gates.add(new Gate(x, gameBase.getHeight(), gameBase.getWidth(),
                        gateLImg, gateRImg));
            }
        }
    }

    private void handleCollision() {
        for (Gate gate : gates) {
            if (gateCollisionEndless.checkCollision(player, gate)) {
                player.speedBoost();
            }
        }
    }
}