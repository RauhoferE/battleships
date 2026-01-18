package sample;

/**
 * Contains all game constants and configuration values
 */
public class GameConstants {
    // Screen dimensions
    public static final int SCREEN_WIDTH = 1800;
    public static final int SCREEN_HEIGHT = 1000;
    
    // Grid and field dimensions
    public static final int GRID_SIZE = 40;
    public static final int FIELD_WIDTH = 440;
    public static final int FIELD_HEIGHT = 440;
    
    // Player 1 field coordinates
    public static final int PLAYER1_FIELD_X = 440 + 40;
    public static final int PLAYER1_FIELD_Y = 40 + 40;
    public static final int PLAYER1_FIELD_WIDTH = 440;
    public static final int PLAYER1_FIELD_HEIGHT = 440;
    
    // Player 2 field coordinates  
    public static final int PLAYER2_FIELD_X = 440 + 40 + 10 * 40 + 2 * 40;
    public static final int PLAYER2_FIELD_Y = 40 + 40;
    public static final int PLAYER2_FIELD_WIDTH = 440 + 440;
    public static final int PLAYER2_FIELD_HEIGHT = 440;
    
    // Ship placement coordinates
    public static final int PLAYER1_SHIPS_X = 1800 - 1520 - 3 * 40;
    public static final int PLAYER2_SHIPS_X = 1520;
    public static final int SHIP_START_Y = 640;
    
    // UI element positions
    public static final int MASK_LEFT_X = 439;
    public static final int MASK_LEFT_Y = 39 + 440 + 40;
    public static final int MASK_RIGHT_X = 439 + 440 + 40;
    public static final int MASK_RIGHT_Y = 39 + 440 + 40;
    
    // Button dimensions
    public static final int BUTTON_WIDTH = 120;
    public static final int BUTTON_HEIGHT = 10;
    public static final int MAIN_MENU_BUTTON_WIDTH = 400;
    public static final int MAIN_MENU_BUTTON_HEIGHT = 150;
    
    // Font sizes
    public static final int MAIN_MENU_FONT_SIZE = 30;
    
    // Audio settings
    public static final int MUSIC_CYCLE_COUNT = 500;
    
    // Ship fleet composition
    public static final int SHIP_LENGTH_2_COUNT = 4;
    public static final int SHIP_LENGTH_3_COUNT = 3;
    public static final int SHIP_LENGTH_4_COUNT = 2;
    public static final int SHIP_LENGTH_5_COUNT = 1;
    
    // Resource paths
    public static final String RESOURCE_PATH = "file:res/";
    public static final String BACKGROUND_IMAGE = RESOURCE_PATH + "BattleshipsBackground.png";
    public static final String START_IMAGE = RESOURCE_PATH + "start.png";
    public static final String PLAYER1_WON_IMAGE = RESOURCE_PATH + "spieler1_gewonnen.png";
    public static final String PLAYER2_WON_IMAGE = RESOURCE_PATH + "spieler2_gewonnen.png";
    public static final String MASK_LEFT_IMAGE = RESOURCE_PATH + "Insel_Unten_1.png";
    public static final String MASK_RIGHT_IMAGE = RESOURCE_PATH + "Insel_Unten_2.png";
    public static final String HIT_IMAGE = RESOURCE_PATH + "Hit.png";
    public static final String MISS_IMAGE = RESOURCE_PATH + "Waterhitmarker.png";
    
    // Audio paths
    public static final String BOMB_SOUND = "res/bomb.wav";
    public static final String MISS_SOUND = "res/miss.wav";
    public static final String MUSIC_SOUND = "res/music.wav";
    public static final String WINNER_SOUND = "res/winner.wav";
    
    // Ship image paths
    public static final String[] SHIP_IMAGES = {
        RESOURCE_PATH + "1x2_Schiff_Horizontal_1_Fertig.png",
        RESOURCE_PATH + "1x3_Schiff_Horizontal_1_Fertig.png",
        RESOURCE_PATH + "1x4_Schiff_Horizontal_1_Fertig.png",
        RESOURCE_PATH + "1x5_Schiff_Horizontal_1_Fertig.png"
    };
    
    // Destroyed ship image paths
    public static final String[] DESTROYED_SHIP_IMAGES = {
        RESOURCE_PATH + "1x2_Ship_Destroyed.png",
        RESOURCE_PATH + "1x3_Ship_Destroyed.png", 
        RESOURCE_PATH + "1x4_Ship_Destroyed.png",
        RESOURCE_PATH + "1x5_Ship_Destroyed.png"
    };
}