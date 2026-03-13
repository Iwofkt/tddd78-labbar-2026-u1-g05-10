package se.liu.simjolucul.dopeslope.menu;

import se.liu.simjolucul.dopeslope.effects.snow.SnowFall;
import se.liu.simjolucul.dopeslope.effects.snow.SnowParticle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuModel {
    private final int SNOW_SPEED = 10;
    private final int width;
    private final int height;
    private int selectedIndex = 0;
    private int hoveredIndex = -1;
    private String title;
    private List<MenuItem> items;
    private final List<Runnable> changeListeners = new ArrayList<>();
    private final SnowFall snowFall;

    public MenuModel(int width, int height) {
        this.width = width;
        this.height = height;
        setScreen(MenuScreen.MAIN);
        snowFall = new SnowFall(1, 2, width);
        snowFall.initializeSnowfall(width, height);
    }

    public void setScreen(MenuScreen screen) {
        switch (screen) {
            case MAIN:
                title = "Dope Slope";
                items = Arrays.asList(
                        new MenuItem("Play", "PLAY"),
                        new MenuItem("Options", "OPTIONS"),
                        new MenuItem("Exit", "EXIT")
                );
                break;
            case MODE_SELECT:
                title = "Select Game Mode";
                items = Arrays.asList(
                        new MenuItem("Endless", "MODE_ENDLESS"),
                        new MenuItem("Combe De Caron", "MODE_COMBE"),
                        new MenuItem("Back", "BACK")
                );
                break;
            case OPTIONS:
                title = "Options";
                items = Arrays.asList(
                        new MenuItem("Sound", "OPTION_SOUND"),
                        new MenuItem("Graphics", "OPTION_GRAPHICS"),
                        new MenuItem("Back", "BACK")
                );
                break;
            // add more cases as needed
        }
        notifyChange();
    }

    public void update() {
        snowFall.update(10);
    }

    public List<SnowParticle> getSnow() {
        return snowFall.getSnowParticles();
    }

    public String getTitle() { return title; }
    public List<MenuItem> getItems() { return items; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }


    // Inner class for menu items
    public static class MenuItem {
        public final String label;
        public final String command;
        public MenuItem(String label, String command) {
            this.label = label;
            this.command = command;
        }
    }

    private void notifyChange() {
        for (Runnable r : changeListeners) {
            r.run();
        }
    }
    public int getSelectedIndex() { return selectedIndex; }
    public void setSelectedIndex(int index) { this.selectedIndex = index; }
    public int getHoveredIndex() { return hoveredIndex; }
    public void setHoveredIndex(int index) { this.hoveredIndex = index; }

    // Also update selectNext/Previous:
    public void selectNext() {
        if (items != null && !items.isEmpty()) {
            selectedIndex = (selectedIndex + 1) % items.size();
        }
    }
    public void selectPrevious() {
        if (items != null && !items.isEmpty()) {
            selectedIndex = (selectedIndex - 1 + items.size()) % items.size();
        }
    }


}