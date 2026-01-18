package sample;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

/**
 * Manages all audio/sound functionality in the game
 */
public class SoundManager {
    private MediaPlayer bombPlayer;
    private MediaPlayer missPlayer;
    private MediaPlayer musicPlayer;
    private MediaPlayer winnerPlayer;
    
    public SoundManager() {
        initializeSounds();
    }
    
    private void initializeSounds() {
        try {
            Media bomb = new Media(new File(GameConstants.BOMB_SOUND).toURI().toString());
            bombPlayer = new MediaPlayer(bomb);
            
            Media miss = new Media(new File(GameConstants.MISS_SOUND).toURI().toString());
            missPlayer = new MediaPlayer(miss);
            
            Media music = new Media(new File(GameConstants.MUSIC_SOUND).toURI().toString());
            musicPlayer = new MediaPlayer(music);
            
            Media winner = new Media(new File(GameConstants.WINNER_SOUND).toURI().toString());
            winnerPlayer = new MediaPlayer(winner);
        } catch (Exception e) {
            System.err.println("Error loading audio files: " + e.getMessage());
        }
    }
    
    public void startBackgroundMusic() {
        if (musicPlayer != null) {
            musicPlayer.setCycleCount(GameConstants.MUSIC_CYCLE_COUNT);
            musicPlayer.play();
        }
    }
    
    public void stopBackgroundMusic() {
        if (musicPlayer != null) {
            musicPlayer.stop();
        }
    }
    
    public void playBombSound() {
        if (bombPlayer != null) {
            bombPlayer.stop();
            bombPlayer.play();
        }
    }
    
    public void playMissSound() {
        if (missPlayer != null) {
            missPlayer.stop();
            missPlayer.play();
        }
    }
    
    public void playWinnerSound() {
        if (winnerPlayer != null) {
            winnerPlayer.stop();
            winnerPlayer.play();
        }
    }
    
    public void stopAllSounds() {
        if (bombPlayer != null) bombPlayer.stop();
        if (missPlayer != null) missPlayer.stop();
        if (musicPlayer != null) musicPlayer.stop();
        if (winnerPlayer != null) winnerPlayer.stop();
    }
}