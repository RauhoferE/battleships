package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class Main extends Application
{
    private GameController gameController;
    private SoundManager soundManager;
    private UIManager uiManager;
    private AttackHandler attackHandler;
    private ShipPlacementManager shipPlacementManager;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        initializeManagers();
        setupApplication(primaryStage);
        
        Scene scene = new Scene(uiManager.getBattleshipContainer(), 
                               GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void initializeManagers() {
        soundManager = new SoundManager();
        gameController = new GameController(soundManager);
        
        // Create a temporary pane for initialization
        Pane tempPane = new Pane();
        attackHandler = new AttackHandler(gameController, tempPane);
        
        uiManager = new UIManager(gameController);
        shipPlacementManager = new ShipPlacementManager(gameController);
        
        // Set up cross-references
        uiManager.setShipPlacementManager(shipPlacementManager);
        uiManager.setAttackHandler(attackHandler);
        shipPlacementManager.setUIManager(uiManager);
        
        // Update attack handler with real container
        attackHandler.setBattleshipContainer(uiManager.getBattleshipContainer());
    }
    
    private void setupApplication(Stage primaryStage) {
        uiManager.setupUI();
        uiManager.setupEventHandlers(primaryStage);
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}