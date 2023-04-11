package ui;

import main.GamePanel;
import java.awt.*;
import java.awt.event.MouseEvent;

public class AudioOptions {
    private GamePanel gp;
    private SoundButton musicButton, sfxButton;
    private VolumeButton volumeButton;

    public AudioOptions(GamePanel gp) {
        this.gp = gp;
        createSoundButtons();
        createVolumeButtons();
    }

    private void createSoundButtons() {
        int soundX = gp.getScreenWidth()/2 + gp.getTileSize()*3/2;
        int musicY = gp.getScreenHeight()/2 - gp.getTileSize()*5/3;
        int sfxY = gp.getScreenHeight()/2 - gp.getTileSize()*3/5;

        musicButton = new SoundButton(soundX, musicY, PauseButton.SOUND_SIZE, PauseButton.SOUND_SIZE);
        sfxButton = new SoundButton(soundX, sfxY, PauseButton.SOUND_SIZE, PauseButton.SOUND_SIZE);
    }

    private void createVolumeButtons() {
        int vX = gp.getScreenWidth()/2 - gp.getTileSize();
        int vY = gp.getScreenHeight()/2 + gp.getTileSize()*3/4;
        volumeButton = new VolumeButton(vX, vY, PauseButton.SLIDER_WIDTH, PauseButton.VOLUME_HEIGHT);
    }

    public void update() {
        // Sound Buttons
        musicButton.update();
        sfxButton.update();

        // Volume Slider
        volumeButton.update();
    }

    public void draw(Graphics2D g2d) {
        // Sound Buttons
        musicButton.draw(g2d);
        sfxButton.draw(g2d);

        // Volume Slider
        volumeButton.draw(g2d);
    }
    private boolean isIn(MouseEvent e, PauseButton button) {
        return button.getBounds().contains(e.getX(), e.getY());
    }
    public void mouseDragged(MouseEvent e) {
        if(volumeButton.isMousePressed()) {
            float valueBefore = volumeButton.getFloatValue();
            volumeButton.changeX(e.getX());
            float valueAfter = volumeButton.getFloatValue();
            if(valueBefore != valueAfter) {
                gp.getSound().setVolume(valueAfter);
            }
        }
    }

    public void mousePressed(MouseEvent e) {
        if(isIn(e, musicButton)) {
            musicButton.setMousePressed(true);
        } else if(isIn(e, sfxButton)) {
            sfxButton.setMousePressed(true);
        } else if(isIn(e, volumeButton)) {
            volumeButton.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if(isIn(e, musicButton)) {
            if(musicButton.isMousePressed()) {
                musicButton.setMuted(!musicButton.isMuted());
                gp.getSound().toggleSongMute();
            }
        } else if(isIn(e, sfxButton)) {
            if(sfxButton.isMousePressed()) {
                sfxButton.setMuted(!sfxButton.isMuted());
                gp.getSound().toggleEffectsMute();
            }
        }

        musicButton.resetBools();
        sfxButton.resetBools();
        volumeButton.resetBools();
    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
        volumeButton.setMouseOver(false);

        if(isIn(e, musicButton)) {
            musicButton.setMouseOver(true);
        } else if(isIn(e, sfxButton)) {
            sfxButton.setMouseOver(true);
        } else if(isIn(e, volumeButton)) {
            volumeButton.setMouseOver(true);
        }
    }

}
