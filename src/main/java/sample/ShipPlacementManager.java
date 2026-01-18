package sample;

import javafx.scene.image.Image;

/**
 * Manages ship placement, validation, and ship fleet setup
 */
public class ShipPlacementManager {
    private GameController gameController;
    private UIManager uiManager;
    private ImageShip[] player1Ships;
    private ImageShip[] player2Ships;
    private Image[] shipImages;
    
    public ShipPlacementManager(GameController gameController) {
        this.gameController = gameController;
        initializeShipImages();
        createShipFleets();
    }
    
    public void setUIManager(UIManager uiManager) {
        this.uiManager = uiManager;
    }
    
    private void initializeShipImages() {
        shipImages = new Image[GameConstants.SHIP_IMAGES.length];
        for (int i = 0; i < GameConstants.SHIP_IMAGES.length; i++) {
            shipImages[i] = new Image(GameConstants.SHIP_IMAGES[i]);
        }
    }
    
    private void createShipFleets() {
        createPlayer1Ships();
        createPlayer2Ships();
    }
    
    private void createPlayer1Ships() {
        player1Ships = new ImageShip[10];
        int index = 0;
        
        // 4 ships of length 2
        for (int i = 0; i < GameConstants.SHIP_LENGTH_2_COUNT; i++) {
            player1Ships[index++] = new ImageShip(GameConstants.PLAYER2_SHIPS_X, 
                                                 GameConstants.SHIP_START_Y, 2, shipImages[0]);
        }
        
        // 3 ships of length 3
        for (int i = 0; i < GameConstants.SHIP_LENGTH_3_COUNT; i++) {
            player1Ships[index++] = new ImageShip(GameConstants.PLAYER2_SHIPS_X, 
                                                 GameConstants.SHIP_START_Y + 80, 3, shipImages[1]);
        }
        
        // 2 ships of length 4
        for (int i = 0; i < GameConstants.SHIP_LENGTH_4_COUNT; i++) {
            player1Ships[index++] = new ImageShip(GameConstants.PLAYER2_SHIPS_X, 
                                                 GameConstants.SHIP_START_Y + 160, 4, shipImages[2]);
        }
        
        // 1 ship of length 5
        player1Ships[index] = new ImageShip(GameConstants.PLAYER2_SHIPS_X, 
                                           GameConstants.SHIP_START_Y + 240, 5, shipImages[3]);
    }
    
    private void createPlayer2Ships() {
        player2Ships = new ImageShip[10];
        int index = 0;
        
        // 4 ships of length 2
        for (int i = 0; i < GameConstants.SHIP_LENGTH_2_COUNT; i++) {
            player2Ships[index++] = new ImageShip(GameConstants.PLAYER1_SHIPS_X, 
                                                 GameConstants.SHIP_START_Y, 2, shipImages[0]);
        }
        
        // 3 ships of length 3
        for (int i = 0; i < GameConstants.SHIP_LENGTH_3_COUNT; i++) {
            player2Ships[index++] = new ImageShip(GameConstants.PLAYER1_SHIPS_X, 
                                                 GameConstants.SHIP_START_Y + 80, 3, shipImages[1]);
        }
        
        // 2 ships of length 4
        for (int i = 0; i < GameConstants.SHIP_LENGTH_4_COUNT; i++) {
            player2Ships[index++] = new ImageShip(GameConstants.PLAYER1_SHIPS_X, 
                                                 GameConstants.SHIP_START_Y + 160, 4, shipImages[2]);
        }
        
        // 1 ship of length 5
        player2Ships[index] = new ImageShip(GameConstants.PLAYER1_SHIPS_X, 
                                           GameConstants.SHIP_START_Y + 240, 5, shipImages[3]);
    }
    
    public void savePlayer1Ships() {
        saveShips(player2Ships, gameController.getPlayer1(), 
                 GameConstants.PLAYER1_FIELD_X, GameConstants.PLAYER1_FIELD_Y,
                 GameConstants.PLAYER1_FIELD_X + GameConstants.FIELD_WIDTH,
                 GameConstants.PLAYER1_FIELD_Y + GameConstants.FIELD_HEIGHT);
        checkShipsComplete();
    }
    
    public void savePlayer2Ships() {
        saveShips(player1Ships, gameController.getPlayer2(),
                 2 * GameConstants.FIELD_WIDTH + GameConstants.GRID_SIZE + GameConstants.GRID_SIZE,
                 GameConstants.PLAYER1_FIELD_Y,
                 GameConstants.FIELD_WIDTH + GameConstants.FIELD_WIDTH + GameConstants.GRID_SIZE + GameConstants.FIELD_WIDTH,
                 GameConstants.PLAYER1_FIELD_Y + GameConstants.FIELD_HEIGHT);
        checkShipsComplete();
    }
    
    private void saveShips(ImageShip[] ships, Player player, int p1x, int p1y, int p2x, int p2y) {
        for (ImageShip ship : ships) {
            if (!ship.isDisable()) {
                int[] coords = calculateXY(ship.getX(), ship.getY(), p1x, p1y, p2x, p2y);
                
                if (coords != null) {
                    if (player.area.setShip(coords[0], coords[1], ship.getLength(), 
                                          ship.getDirection(), ship.getDiffvectorx(), ship.getDiffvectory())) {
                        ship.lock();
                    } else {
                        resetShipPosition(ship);
                    }
                } else {
                    resetShipPosition(ship);
                }
            }
        }
        
        if (player.area.isFleetComplete()) {
            gameController.handleFleetComplete(player);
            if (uiManager != null) {
                uiManager.handleFleetComplete(player);
            }
        }
    }
    
    private void resetShipPosition(ImageShip ship) {
        ship.changePosition(0, 0);
        ship.rotateTo(Direction.RIGHT);
    }
    
    private void checkShipsComplete() {
        gameController.checkShipsComplete();
    }
    
    public void resetShips() {
        for (int i = 0; i < player1Ships.length; i++) {
            player1Ships[i].rotateTo(Direction.RIGHT);
            player2Ships[i].rotateTo(Direction.RIGHT);
            player1Ships[i].reset();
            player2Ships[i].reset();
        }
    }
    
    private int[] calculateXY(int shipX, int shipY, int p1x, int p1y, int p2x, int p2y) {
        int result[] = new int[2];
        
        if (shipX >= p1x && shipX <= p2x && shipY >= p1y && shipY <= p2y) {
            int vectorx = shipX - p1x;
            int vectory = shipY - p1y;
            result[0] = vectorx / GameConstants.GRID_SIZE;
            result[1] = vectory / GameConstants.GRID_SIZE;
            return result;
        }
        return null;
    }
    
    // Getters
    public ImageShip[] getPlayer1Ships() { return player1Ships; }
    public ImageShip[] getPlayer2Ships() { return player2Ships; }
}