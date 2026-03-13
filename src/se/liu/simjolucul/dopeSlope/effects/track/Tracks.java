package se.liu.simjolucul.dopeSlope.effects.track;

import se.liu.simjolucul.dopeSlope.effects.ParticleConfig;
import se.liu.simjolucul.dopeSlope.gameObjects.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tracks {
    private static final Random RND = new Random();

    private final int ALPHA_MAX = 255;
    private final int ALPHA_MIN = 255;

    private final ParticleConfig conf;
    private final int spawnrate;
    private final List<trackParticle> trackParticles = new ArrayList<>();

    public Tracks(int spawnrate, double angle, int width) {
        this.spawnrate = spawnrate;

        conf = new ParticleConfig();
        conf.y = 0;
        conf.angle = angle;
        conf.recWidthMin = 6;
        conf.recWidthMax = 6;
        conf.recHeightMin = 15;
        conf.recHeightMax = 15;
        conf.lifeMax = 200;
        conf.lifeMin = 100;
        conf.alphaMax = ALPHA_MAX;
        conf.alphaMin = ALPHA_MIN;
        conf.color = Color.GRAY;
    }

    public List<trackParticle> getTrackParticles() {
        return trackParticles;
    }

    public void updateMovement(int speed) {
        trackParticles.removeIf(p -> !p.isAlive());
        for (trackParticle p : trackParticles) {
            p.update(speed);
        }
    }
    public void spawnTracks(Player player, double moveAngle) {
        Point[] tips = player.getSkiTipPositions();
        Point leftTip = tips[0];
        Point rightTip = tips[1];

        // Orient particles along the movement direction
        conf.angle = moveAngle + Math.PI / 2;

        for (int i = 0; i < spawnrate; i++) {
            // Left ski
            conf.x = leftTip.x - conf.recWidthMax / 2;
            conf.y = leftTip.y - conf.recHeightMax / 2;
            trackParticles.add(new trackParticle(conf));

            // Right ski
            conf.x = rightTip.x - conf.recWidthMax / 2;
            conf.y = rightTip.y - conf.recHeightMax / 2;
            trackParticles.add(new trackParticle(conf));
        }
    }

    public void clear() {
        trackParticles.clear();
    }
}