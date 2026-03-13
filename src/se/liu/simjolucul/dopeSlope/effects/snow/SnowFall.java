package se.liu.simjolucul.dopeSlope.effects.snow;

import se.liu.simjolucul.dopeSlope.effects.ParticleConfig;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnowFall {
    static Random RND = new Random();

    int INITIAL_PARTICLES = 250;
    int SPREAD = 2;
    int ALPHA_MAX = 140;
    int ALPHA_MIN = 100;

    ParticleConfig conf;

    int spawnrate;
    private int width, height; // store both dimensions for reset

    private final List<SnowParticle> snowParticles = new ArrayList<>();

    public SnowFall(int spawnrate, double angle, int width) {
        this.spawnrate = spawnrate;
        this.width = width;

        conf = new ParticleConfig();
        conf.y = 0;
        conf.angle = angle;
        conf.spread = SPREAD;
        conf.radiusSizeMax = 8;
        conf.radiusSizeMin = 4;
        conf.lifeMax = 200;
        conf.lifeMin = 100;
        conf.alphaMax = ALPHA_MAX;
        conf.alphaMin = ALPHA_MIN;
        conf.color = Color.WHITE;
    }

    public List<SnowParticle> getSnowParticles() {
        return snowParticles;
    }

    public void update(int speed) {
        for (int i = 0; i < spawnrate; i++) {
            conf.x = RND.nextInt(width);
            conf.y = 0;
            snowParticles.add(new SnowParticle(conf));
        }
        snowParticles.removeIf(p -> !p.isAlive());
        for (SnowParticle p : snowParticles) {
            p.update(speed);
        }
    }

    public void initializeSnowfall(int width, int height) {
        this.width = width;   // update in case width changed (though it shouldn't)
        this.height = height; // store for reset

        for (int i = 0; i < INITIAL_PARTICLES; i++) {
            conf.x = RND.nextInt(width);
            conf.y = RND.nextInt(height);
            snowParticles.add(new SnowParticle(conf));
        }
    }

    public void reset() {
        snowParticles.clear();
        initializeSnowfall(width, height);
    }
}