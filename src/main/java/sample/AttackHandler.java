package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Handles all attack logic including hit detection, miss handling, and ship destruction
 */
public class AttackHandler {
    private GameController gameController;
    private Pane battleshipContainer;
    
    public AttackHandler(GameController gameController, Pane battleshipContainer) {
        this.gameController = gameController;
        this.battleshipContainer = battleshipContainer;
    }
    
    public void setBattleshipContainer(Pane container) {
        this.battleshipContainer = container;
    }
    
    public void handleAttack(int x, int y) {
        if (gameController.isGameOver() || !gameController.areShipsComplete()) {
            return;
        }
        
        System.out.println("Schiffe fertig");
        
        if (gameController.getGameRound() % 2 == 1) {
            handlePlayer1Attack(x, y);
        } else {
            handlePlayer2Attack(x, y);
        }
    }
    
    private void handlePlayer1Attack(int x, int y) {
        int[] coords = calculateXY(x, y, GameConstants.PLAYER1_FIELD_X, GameConstants.PLAYER1_FIELD_Y, 
                                  GameConstants.PLAYER1_FIELD_X + GameConstants.FIELD_WIDTH, 
                                  GameConstants.PLAYER1_FIELD_Y + GameConstants.FIELD_HEIGHT);
        
        Player player1 = gameController.getPlayer1();
        Player player2 = gameController.getPlayer2();
        
        if (coords != null && player1.isAttackPossibleOn(new Position(coords[0], coords[1]))) {
            if (player2.area.attack(coords[0], coords[1])) {
                drawAttack(coords[0], coords[1], x, y, player2);
                player1.SaveAttackOnPosition(coords[0], coords[1]);
                gameController.getSoundManager().playBombSound();
            } else {
                drawMiss(x, y);
                player1.SaveAttackOnPosition(coords[0], coords[1]);
                gameController.getSoundManager().playMissSound();
            }
        }
    }
    
    private void handlePlayer2Attack(int x, int y) {
        int[] coords = calculateXY(x, y, GameConstants.PLAYER2_FIELD_X, GameConstants.PLAYER2_FIELD_Y,
                                  GameConstants.PLAYER2_FIELD_X + GameConstants.FIELD_WIDTH, 
                                  GameConstants.PLAYER2_FIELD_Y + GameConstants.FIELD_HEIGHT);
        
        Player player1 = gameController.getPlayer1();
        Player player2 = gameController.getPlayer2();
        
        if (coords != null && player2.isAttackPossibleOn(new Position(coords[0], coords[1]))) {
            if (player1.area.attack(coords[0], coords[1])) {
                drawAttack(coords[0], coords[1], x, y, player1);
                player2.SaveAttackOnPosition(coords[0], coords[1]);
                gameController.getSoundManager().playBombSound();
            } else {
                drawMiss(x, y);
                player2.SaveAttackOnPosition(coords[0], coords[1]);
                gameController.getSoundManager().playMissSound();
            }
        }
    }
    
    private void drawMiss(double x, double y) {
        int diffx = (int) x % GameConstants.GRID_SIZE;
        x -= diffx;
        
        int diffy = (int) y % GameConstants.GRID_SIZE;
        y -= diffy;
        
        ImageView miss = new ImageView(GameConstants.MISS_IMAGE);
        miss.setX(x);
        miss.setY(y);
        battleshipContainer.getChildren().add(miss);
        gameController.nextTurn();
    }
    
    private void drawAttack(int gridX, int gridY, double screenX, double screenY, Player player) {
        int diffx = (int) screenX % GameConstants.GRID_SIZE;
        screenX -= diffx;
        
        int diffy = (int) screenY % GameConstants.GRID_SIZE;
        screenY -= diffy;
        
        ImageView hit = new ImageView(GameConstants.HIT_IMAGE);
        hit.setX(screenX);
        hit.setY(screenY);
        battleshipContainer.getChildren().add(hit);
        
        Ship ship = player.area.isDestroyed(gridX, gridY);
        if (ship != null) {
            drawDestroyedShip(ship, player);
            System.out.println("zerstört");
        }
    }
    
    private void drawDestroyedShip(Ship ship, Player player) {
        Image image = getDestroyedShipImage(ship.getLength());
        
        int x = ship.getxCoordinate() * GameConstants.GRID_SIZE;
        int y = ship.getyCoordinate() * GameConstants.GRID_SIZE;
        
        // Position on opponent's field
        if (player == gameController.getPlayer1()) {
            x += 2 * GameConstants.FIELD_WIDTH + GameConstants.GRID_SIZE + GameConstants.GRID_SIZE;
            y += 2 * GameConstants.GRID_SIZE;
        } else {
            x += GameConstants.FIELD_WIDTH + GameConstants.GRID_SIZE;
            y += 2 * GameConstants.GRID_SIZE;
        }
        
        ImageShip imageShip = new ImageShip(
            x - ship.getDifferenceVectorX(), 
            y - ship.getDifferenceVectorY(), 
            ship.getLength(), 
            image
        );
        
        battleshipContainer.getChildren().add(imageShip.getImageView());
        imageShip.rotateTo(ship.getDirection());
        imageShip.lock();
    }
    
    private Image getDestroyedShipImage(int length) {
        switch (length) {
            case 2: return new Image(GameConstants.DESTROYED_SHIP_IMAGES[0]);
            case 3: return new Image(GameConstants.DESTROYED_SHIP_IMAGES[1]);
            case 4: return new Image(GameConstants.DESTROYED_SHIP_IMAGES[2]);
            case 5: return new Image(GameConstants.DESTROYED_SHIP_IMAGES[3]);
            default: return new Image(GameConstants.DESTROYED_SHIP_IMAGES[0]);
        }
    }
    
    private int[] calculateXY(int imageshipx, int imageshipy, int p1x, int p1y, int p2x, int p2y) {
        int result[] = new int[2];
        
        if (imageshipx >= p1x && imageshipx <= p2x && imageshipy >= p1y && imageshipy <= p2y) {
            int vectorx = imageshipx - p1x;
            int vectory = imageshipy - p1y;
            result[0] = vectorx / GameConstants.GRID_SIZE;
            result[1] = vectory / GameConstants.GRID_SIZE;
            return result;
        }
        return null;
    }
}