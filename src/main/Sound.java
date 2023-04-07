package main;

import javax.sound.sampled.*;
import java.net.URL;
import java.util.Random;

public class Sound {
    // SONG
    public static final int MENU = 0;
    public static final int PLAYING = 1;

    // EFFECTS
    public static final int FIRE = 0;
    public static final int BUTTON_HOVER = 1;
    public static final int BUTTON_CLICK = 2;
    public static final int PAUSE = 2;
    public static final int GAME_OVER = 3;

    private Clip[] songs, effects;
    private int currSongId;
    private float volume = 0.8f;
    private boolean songMute, effectMute;
    private Random rand = new Random();

    public Sound() {
        loadSongs();
        loadEffects();
    }

    private void loadSongs() {
        String[] names = {"MenuSound", "GameSound"};
        songs = new Clip[names.length];

        for(int i = 0; i < songs.length; i++) {
            songs[i] = getClip(names[i]);
        }
    }

    private void loadEffects() {
        String[] names = {"LaserGun", "ButtonHover", "ButtonClick"};
        effects = new Clip[names.length];

        for(int i = 0; i < effects.length; i++) {
            effects[i] = getClip(names[i]);
        }

        updateEffectsVolume();
    }

    private Clip getClip(String name) {
        URL url = getClass().getResource("/sounds/" + name + ".wav");
        AudioInputStream audio;

        try {
            audio = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            return clip;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void setVolume(float volume) {
        this.volume = volume;
        updateSongVolume();
        updateEffectsVolume();
    }

    public void stopSong() {
        if(songs[currSongId].isActive()) {
            songs[currSongId].stop();
        }
    }

    public void playSong(int song) {
        stopSong();

        currSongId = song;
        updateSongVolume();
        songs[currSongId].setMicrosecondPosition(0);
        songs[currSongId].loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void playEffect(int effect) {
        if(effects[effect].getMicrosecondPosition() > 0){
            effects[effect].setMicrosecondPosition(0);
        }
        effects[effect].start();
    }

    public void toggleSongMute() {
        this.songMute = !songMute;

        for(Clip c: songs) {
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(songMute);
        }
    }

    public void toggleEffectsMute() {
        this.effectMute = !effectMute;

        for(Clip c: effects) {
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(effectMute);
        }

        if(!effectMute) {
            playEffect(FIRE);
        }
    }

    private void updateSongVolume() {
        FloatControl gainControl = (FloatControl) songs[currSongId].getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (range * volume) + gainControl.getMinimum();
        gainControl.setValue(gain);
    }

    private void updateEffectsVolume() {
        for(Clip c: effects) {
            FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        }
    }
}
