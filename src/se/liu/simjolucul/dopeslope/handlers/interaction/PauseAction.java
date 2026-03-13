package se.liu.simjolucul.dopeslope.handlers.interaction;

import se.liu.simjolucul.dopeslope.game.GBase;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PauseAction extends AbstractAction {
    private final GBase gameBase;

    public PauseAction(GBase gameBase) {
        this.gameBase = gameBase;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        gameBase.setGamePaused(!gameBase.getGamePaused());
    }
}