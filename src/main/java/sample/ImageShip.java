package sample;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;

public class ImageShip
{
    private int x, y, length;
    private int rotate = 1;
    private int beginX, beginY;
    /*Wichtige Vektoren: Sind dafür da, dass die Bilder und Schiffe gleich rotiert sind und richtig liegen. Da wir es
     händisch hinein schreiben müssen, dass die "digitalen" ships (nicht die Bilder) auch rotiert sind quasi. Noch
     bestätigen, ob das stimmt bitte!!*/
    private int diffvectorx, diffvectory;
    private double startX, startY, moveX, moveY, setX, setY, newX, newY;

    private ImageView imageView;
    private Image image;
    private Direction direction;

    private boolean disable = false;


    private void setDiffvectorx(int diffvectorx)
    {
        this.diffvectorx = diffvectorx;
    }

    private void setDiffvectory(int diffvectory)
    {
        this.diffvectory = diffvectory;
    }

    private void setX(int x)
    {
        this.x = x;
    }

    private void setY(int y)
    {
        this.y = y;
    }

    public int getDiffvectorx()
    {
        return diffvectorx;
    }


    public int getDiffvectory()
    {
        return diffvectory;
    }


    public Direction getDirection()
    {
        return direction;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getLength()
    {
        return length;
    }

    public ImageView getImageView()
    {
        return imageView;
    }

    public int getBeginX()
    {
        return beginX;
    }

    public int getBeginY()
    {
        return beginY;
    }

    public Image getImage()
    {
        return image;
    }


    public ImageShip(int x, int y, int length, Image image)
    {
        this.x = x;
        this.y = y;
        this.beginX = this.x;
        this.beginY = this.y;
        this.length = length;
        this.image = image;
        this.direction = Direction.RIGHT;

        this.imageView = new ImageView(image);
        imageView.setX(this.x);
        imageView.setY(this.y);
        this.setDiffvectorx(0);
        this.setDiffvectory(0);

        setupMouseEventHandler();
    }

    private void setupMouseEventHandler() {
        imageView.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                if (!disable) {
                    handleMouseEvent(event);
                }
            }
        });
    }
    
    private void handleMouseEvent(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_PRESSED && event.getButton().equals(MouseButton.PRIMARY)) {
            handleMousePressed(event);
        } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED && event.getButton().equals(MouseButton.PRIMARY)) {
            handleMouseDragged(event);
        } else if (event.getEventType() == MouseEvent.MOUSE_CLICKED && event.getButton().equals(MouseButton.SECONDARY)) {
            rotate();
        }
    }
    
    private void handleMousePressed(MouseEvent event) {
        startX = event.getSceneX();
        startY = event.getSceneY();
        moveX = ((ImageView) (event.getSource())).getTranslateX();
        moveY = ((ImageView) (event.getSource())).getTranslateY();
    }
    
    private void handleMouseDragged(MouseEvent event) {
        calculateNewPosition(event);
        snapToGrid();
        updateImagePosition(event);
        updateShipCoordinates();
        logCoordinatesIfInBounds();
    }
    
    private void calculateNewPosition(MouseEvent event) {
        setX = event.getSceneX() - startX;
        setY = event.getSceneY() - startY;
        newX = moveX + setX;
        newY = moveY + setY;
    }
    
    private void snapToGrid() {
        int diffx = (int) newX % GameConstants.GRID_SIZE;
        newX = newX - diffx;
        int diffy = (int) newY % GameConstants.GRID_SIZE;
        newY = newY - diffy;
    }
    
    private void updateImagePosition(MouseEvent event) {
        ((ImageView) (event.getSource())).setTranslateX(newX);
        ((ImageView) (event.getSource())).setTranslateY(newY);
    }
    
    private void updateShipCoordinates() {
        setX(beginX + getDiffvectorx() + (int) newX);
        setY(beginY + getDiffvectory() + (int) newY);
    }
    
    private void logCoordinatesIfInBounds() {
        int a[] = calculateXY(getX(), getY(), 440 + 40, 40 + 440 + 40 + 40, 440 + 440, 40 + 920);
        if (a != null) {
            System.out.println("x= " + (a[0] + 1) + "y= " + (a[1] + 1));
        }
    }

    /*Gelocked wird, wenn saveShips in der main ein Schiff gespeichert wird oder wenn ein zerstörtes Schiff
    gezeichnet wird. Dient dafür, dass man es nicht mehr draggen kann.*/
    public void lock()
    {
        this.disable = true;
    }


    public boolean isDisable()
    {
        return this.disable;
    }

    /*Wir übergeben zwar x und y = 0 wenn wir die Methode aufrufen, bedeuetet aber nur, dass es zur
    Ursprungskoordinate zurückspringt (wird von dort alles relativ gerechnet). Ermöglicht durch this.x=xx...*/
    public void changePosition(int xx, int yy)//Position muss von den ursprugort angegeben werden und nicht von 0/0
    {

        this.imageView.setTranslateX(xx);
        this.imageView.setTranslateY(yy);
        this.x = xx + this.beginX + diffvectorx;
        this.y = yy + this.beginY + diffvectory;
        //   System.out.println("x= " + this.x + "y= " + this.y);
    }

    /*Nach dem reseten, soll das Schiff wieder zum Ursprungsort zurück*/
    public void reset()
    {
        this.disable = false;
        this.changePosition(0, 0);
    }

    //rotiert das Bild und das im code angelegte Schiff
    private void rotate()
    {
        rotateImageView();
        updateDirectionAndPosition();
        logCoordinates();
    }

    private void rotateImageView()
    {
        if (getLength() % 2 == 1)
        {
            rotateOddLengthShip();
        }
        else
        {
            rotateEvenLengthShip();
        }
        rotate++;
    }

    private void rotateOddLengthShip()
    {
        double value = imageView.getRotate();
        imageView.setRotate(value - 90);
    }

    private void rotateEvenLengthShip()
    {
        double value = imageView.getRotate();
        imageView.setRotate(value - 90);
        
        if (rotate % 2 == 1)
        {
            imageView.setX(imageView.getX() + 20);
            imageView.setY(imageView.getY() - 20);
        }
        else
        {
            imageView.setX(imageView.getX() - 20);
            imageView.setY(imageView.getY() + 20);
        }
    }

    private void updateDirectionAndPosition()
    {
        switch (direction)
        {
            case UP:
                updateFromUpDirection();
                break;
            case DOWN:
                updateFromDownDirection();
                break;
            case LEFT:
                updateFromLeftDirection();
                break;
            case RIGHT:
                updateFromRightDirection();
                break;
        }
    }

    private void updateFromUpDirection()
    {
        direction = Direction.LEFT;
        if (getLength() % 2 == 1)
        {
            int offset = 40 * (getLength() / 2);
            adjustPosition(offset, -offset);
        }
        else if (getLength() != 2)
        {
            adjustPosition(40, -40);
        }
    }

    private void updateFromDownDirection()
    {
        direction = Direction.RIGHT;
        if (getLength() % 2 == 1)
        {
            int offset = 40 * (getLength() / 2);
            adjustPosition(-offset, offset);
        }
        else
        {
            if (getLength() == 2)
            {
                adjustPosition(-40, 40);
            }
            else
            {
                adjustPosition(-80, 80);
            }
        }
    }

    private void updateFromLeftDirection()
    {
        direction = Direction.DOWN;
        if (getLength() % 2 == 1)
        {
            int offset = 40 * (getLength() / 2);
            adjustPosition(-offset, -offset);
        }
        else
        {
            if (getLength() == 2)
            {
                adjustPosition(0, -40);
            }
            else
            {
                adjustPosition(-40, -80);
            }
        }
    }

    private void updateFromRightDirection()
    {
        direction = Direction.UP;
        if (getLength() % 2 == 1)
        {
            int offset = 40 * (getLength() / 2);
            adjustPosition(offset, offset);
        }
        else
        {
            if (getLength() == 2)
            {
                adjustPosition(40, 0);
            }
            else
            {
                adjustPosition(80, 40);
            }
        }
    }

    private void adjustPosition(int deltaX, int deltaY)
    {
        setX(getX() + deltaX);
        setY(getY() + deltaY);
        setDiffvectorx(getDiffvectorx() + deltaX);
        setDiffvectory(getDiffvectory() + deltaY);
    }

    private void logCoordinates()
    {
        int[] a = calculateXY(getX(), getY(), 440 + 40, 40 + 440 + 40 + 40, 440 + 440, 40 + 920);
        if (a != null)
        {
            System.out.println("x= " + (a[0] + 1) + "y= " + (a[1] + 1));
        }
    }


    /*Verwenden wir beim reset button in der Main Methode, um auf RIGHT zu rotieren z.B. Es dreht solange, bis es der
     übergebenen Direction entspricht.*/
    public void rotateTo(Direction directionRotate)
    {
        while (this.direction != directionRotate)
        {
            this.rotate();
        }
    }


    //Dient nur zum testen als Ausgabe
    private int[] calculateXY(int x, int y, int p1x, int p1y, int p2x, int p2y)
    {
        int result[] = new int[2];
        if (x >= p1x && x <= p2x && y >= p1y && y <= p2y)
        {
            int vectorx, vectory;
            vectorx = x - p1x;
            vectory = y - p1y;
            result[0] = vectorx / 40;
            result[1] = vectory / 40;
            return result;
        }
        return null;
    }


}
