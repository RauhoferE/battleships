package sample;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Manages all UI components, layouts, and visual elements
 */
public class UIManager {
    private GameController gameController;
    private AttackHandler attackHandler;
    private ShipPlacementManager shipPlacementManager;
    
    // UI Components
    private Pane battleshipContainer;
    private Button buttonSaveShipsLeft;
    private Button buttonSaveShipsRight;
    private Button newGame;
    private Button exit;
    private Button reset;
    private Button seeShips1;
    private Button seeShips2;
    private Button cont;
    
    // Image Views
    private ImageView startmenu;
    private ImageView wonleft;
    private ImageView wonright;
    private ImageView maskleftfield;
    private ImageView maskrightfield;
    
    // UI Elements
    private Rectangle indicate1;
    private Rectangle indicate2;
    
    public UIManager(GameController gameController) {
        this.gameController = gameController;
        initializeComponents();
    }
    
    public void setShipPlacementManager(ShipPlacementManager manager) {
        this.shipPlacementManager = manager;
    }
    
    public void setAttackHandler(AttackHandler handler) {
        this.attackHandler = handler;
    }
    
    private void initializeComponents() {
        battleshipContainer = new Pane();
        
        // Initialize buttons
        buttonSaveShipsLeft = new Button("Schiffe speichern");
        buttonSaveShipsRight = new Button("Schiffe Speichern");
        newGame = new Button("Neues Spiel");
        exit = new Button("Ka Lust mehr! EXIT");
        reset = new Button("Neustart");
        seeShips1 = new Button("Zeige meine Schiffe");
        seeShips2 = new Button("Zeige meine Schiffe");
        cont = new Button("Hier gehts weiter");
        
        // Initialize image views
        startmenu = new ImageView(GameConstants.START_IMAGE);
        wonleft = new ImageView(GameConstants.PLAYER1_WON_IMAGE);
        wonright = new ImageView(GameConstants.PLAYER2_WON_IMAGE);
        maskleftfield = new ImageView(GameConstants.MASK_LEFT_IMAGE);
        maskrightfield = new ImageView(GameConstants.MASK_RIGHT_IMAGE);
        
        // Initialize rectangles
        indicate1 = new Rectangle(439, 481, 442, 7);
        indicate2 = new Rectangle(919, 481, 442, 7);
    }
    
    public void setupUI() {
        setupBackground();
        setupShipContainer();
        setupMouseEvents();
        setupButtons();
        setupVisualElements();
    }
    
    private void setupBackground() {
        BackgroundImage background = new BackgroundImage(
            new Image(GameConstants.BACKGROUND_IMAGE, GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT,
                    true, true), 
            BackgroundRepeat.NO_REPEAT, 
            BackgroundRepeat.NO_REPEAT, 
            BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT
        );
        
        maskleftfield.setX(GameConstants.MASK_LEFT_X);
        maskleftfield.setY(GameConstants.MASK_LEFT_Y);
        maskrightfield.setX(GameConstants.MASK_RIGHT_X);
        maskrightfield.setY(GameConstants.MASK_RIGHT_Y);
        
        battleshipContainer.setBackground(new Background(background));
        gameController.getSoundManager().startBackgroundMusic();
    }
    
    private void setupShipContainer() {
        if (shipPlacementManager != null) {
            ImageShip[] player1Ships = shipPlacementManager.getPlayer1Ships();
            ImageShip[] player2Ships = shipPlacementManager.getPlayer2Ships();
            
            for (int i = 0; i < player1Ships.length && i < player2Ships.length; i++) {
                battleshipContainer.getChildren().add(player2Ships[i].getImageView());
                battleshipContainer.getChildren().add(player1Ships[i].getImageView());
            }
        }
    }
    
    private void setupMouseEvents() {
        battleshipContainer.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                    double pressedX = event.getSceneX();
                    double pressedY = event.getSceneY();
                    if (attackHandler != null) {
                        attackHandler.handleAttack((int) Math.round(pressedX), (int) Math.round(pressedY));
                    }
                }
            }
        });
    }
    
    private void setupButtons() {
        setupSaveShipButtons();
        setupShowShipButtons();
        setupGameControlButtons();
    }
    
    private void setupSaveShipButtons() {
        buttonSaveShipsLeft.setLayoutX(GameConstants.PLAYER1_SHIPS_X);
        buttonSaveShipsLeft.setLayoutY(500);
        buttonSaveShipsLeft.setPrefSize(GameConstants.BUTTON_WIDTH, GameConstants.BUTTON_HEIGHT);
        buttonSaveShipsLeft.setOnAction(event -> {
            hideMainMenuButtons();  // Hide menu when starting to place ships
            if (shipPlacementManager != null) {
                shipPlacementManager.savePlayer1Ships();
            }
        });
        
        buttonSaveShipsRight.setLayoutX(GameConstants.PLAYER2_SHIPS_X);
        buttonSaveShipsRight.setLayoutY(500);
        buttonSaveShipsRight.setPrefSize(GameConstants.BUTTON_WIDTH, GameConstants.BUTTON_HEIGHT);
        buttonSaveShipsRight.setOnAction(event -> {
            hideMainMenuButtons();  // Hide menu when starting to place ships
            if (shipPlacementManager != null) {
                shipPlacementManager.savePlayer2Ships();
            }
        });
    }
    
    private void setupShowShipButtons() {
        seeShips1.setLayoutX(GameConstants.PLAYER2_SHIPS_X);
        seeShips1.setLayoutY(550);
        seeShips1.setPrefSize(GameConstants.BUTTON_WIDTH, GameConstants.BUTTON_HEIGHT);
        seeShips1.setOnAction(event -> changeMask());
        
        seeShips2.setLayoutX(160);
        seeShips2.setLayoutY(550);
        seeShips2.setPrefSize(GameConstants.BUTTON_WIDTH, GameConstants.BUTTON_HEIGHT);
        seeShips2.setOnAction(event -> changeMask());
    }
    
    private void setupGameControlButtons() {
        Font font = new Font(GameConstants.MAIN_MENU_FONT_SIZE);
        
        // New Game button
        newGame.setLayoutX(700);
        newGame.setLayoutY(300);
        newGame.setMinSize(GameConstants.MAIN_MENU_BUTTON_WIDTH, GameConstants.MAIN_MENU_BUTTON_HEIGHT);
        newGame.setFont(font);
        
        // Exit button
        exit.setLayoutX(700);
        exit.setLayoutY(500);
        exit.setMinSize(GameConstants.MAIN_MENU_BUTTON_WIDTH, GameConstants.MAIN_MENU_BUTTON_HEIGHT);
        exit.setFont(font);
        exit.setOnAction(event -> System.exit(0));
        
        // Reset button
        reset.setLayoutX(440);
        reset.setLayoutY(10);
        reset.setPrefHeight(GameConstants.BUTTON_HEIGHT);
    }
    
    private void setupVisualElements() {
        startmenu.setVisible(true);
        indicate1.setFill(Color.RED);
        indicate2.setFill(Color.RED);
        
        battleshipContainer.getChildren().addAll(
            seeShips1, seeShips2, buttonSaveShipsLeft, buttonSaveShipsRight,
            maskleftfield, maskrightfield, startmenu, indicate1, indicate2,
            newGame, exit, reset
        );
        
        setInitialVisibility();
        changeMask();
    }
    
    private void setInitialVisibility() {
        reset.setVisible(false);
        maskleftfield.setVisible(false);
        maskrightfield.setVisible(false);
        seeShips1.setVisible(false);
        seeShips2.setVisible(false);
        indicate1.setVisible(false);
        indicate2.setVisible(false);
        newGame.setVisible(true);  // Visible on start screen
        exit.setVisible(true);     // Visible on start screen
        cont.setVisible(false);
    }
    
    public void activateMask() {
        maskleftfield.setVisible(true);
        maskrightfield.setVisible(true);
    }
    
    public void deactivateMask() {
        maskleftfield.setVisible(false);
        maskrightfield.setVisible(false);
    }
    
    public void changeMask() {
        if (gameController.getGameRound() % 2 == 1) {
            maskleftfield.setVisible(false);
            maskrightfield.setVisible(true);
        } else {
            maskleftfield.setVisible(true);
            maskrightfield.setVisible(false);
        }
    }
    
    public void hideMainMenuButtons() {
        newGame.setVisible(false);
        exit.setVisible(false);
        startmenu.setVisible(false);
        reset.setVisible(true);
    }
    
    public void showMainMenuButtons() {
        newGame.setVisible(true);
        exit.setVisible(true);
        startmenu.setVisible(true);
        reset.setVisible(false);
    }
    
    public void showGameOverScreen(Player winner) {
        deactivateMask();
        seeShips1.setVisible(false);
        seeShips2.setVisible(false);
        reset.setVisible(false);
        
        ImageView winImage = (winner == gameController.getPlayer1()) ? wonleft : wonright;
        int imageX = (winner == gameController.getPlayer1()) ? 50 : 1450;
        int buttonX = (winner == gameController.getPlayer1()) ? 160 : 1520;
        
        battleshipContainer.getChildren().add(winImage);
        winImage.setX(imageX);
        winImage.setY(520);
        
        gameController.getSoundManager().playWinnerSound();
        
        battleshipContainer.getChildren().add(cont);
        cont.setLayoutX(buttonX);
        cont.setLayoutY(850);
        cont.setVisible(true);
    }
    
    public void setupEventHandlers(Stage primaryStage) {
        reset.setOnAction(event -> {
            resetGame();
            Scene scene = new Scene(battleshipContainer, GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT);
            primaryStage.setScene(scene);
            primaryStage.show();
        });
        
        newGame.setOnAction(event -> {
            resetGame();
            hideMainMenuButtons();  // Hide menu buttons when starting game
            Scene scene = new Scene(battleshipContainer, GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT);
            primaryStage.setScene(scene);
            primaryStage.show();
        });
        
        cont.setOnAction(event -> {
            resetGame();
            showMainMenuButtons();  // Show menu buttons when returning to main menu
            Scene scene = new Scene(battleshipContainer, GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT);
            primaryStage.setScene(scene);
            primaryStage.show();
        });
    }
    
    private void resetGame() {
        gameController.resetGame();
        if (shipPlacementManager != null) {
            shipPlacementManager.resetShips();
        }
        
        buttonSaveShipsRight.setVisible(true);
        buttonSaveShipsLeft.setVisible(true);
        
        battleshipContainer = new Pane();
        setupUI();
        showMainMenuButtons();  // Show main menu after reset
    }
    
    public void handleFleetComplete(Player player) {
        if (player == gameController.getPlayer1()) {
            changeMask();
            buttonSaveShipsLeft.setVisible(false);
        } else {
            buttonSaveShipsRight.setVisible(false);
            changeMask();
            seeShips1.setVisible(true);
            seeShips2.setVisible(true);
            indicate1.setVisible(true);
        }
        
        if (gameController.getPlayer1().area.isFleetComplete() && 
            gameController.getPlayer2().area.isFleetComplete()) {
            activateMask();
        }
    }
    
    // Getters
    public Pane getBattleshipContainer() { return battleshipContainer; }
    public Button getButtonSaveShipsLeft() { return buttonSaveShipsLeft; }
    public Button getButtonSaveShipsRight() { return buttonSaveShipsRight; }
}