package sample;

import java.util.ArrayList;

public class Ship
{
    private final Position position;
    private ArrayList<ShipPart> shipparts = new ArrayList<>();
    private int length;
    private Direction direction;
    private int differenceVectorX;
    private int differenceVectorY;

    public int getxCoordinate()
    {
        return position.getX();
    }

    public int getyCoordinate()
    {
        return position.getY();
    }

    public int getDifferenceVectorX()
    {
        return differenceVectorX;
    }

    public int getDifferenceVectorY()
    {
        return differenceVectorY;
    }

    public int getLength()
    {
        return length;
    }

    public Direction getDirection()
    {
        return direction;
    }

    public ArrayList<ShipPart> getShipParts()
    {
        return shipparts;
    }

    /*ist das unterste Level vom Schiff erstellen. Wird in der Ship Methode aufgerufen. Dort bekommt es die
    Übergabeparameter und hier können dann die ShipParts zur ArrayList hinzugefügt werden. Kette geht bis zu main
    Methode saveShips hinauf.*/
    private void generateShipParts(Position position, int length, Direction directions)
    {
        for (int i = 0; i < length; i++)
        {
            shipparts.add(new ShipPart(position.getX(), position.getY()));
            switch (directions)
            {
                case UP:
                    position.setY(position.getY() - 1);
                    break;

                case RIGHT:
                    position.setX(position.getX() + 1);
                    break;

                case LEFT:
                    position.setX(position.getX() - 1);
                    break;

                case DOWN:
                    position.setY(position.getY() + 1);
                    break;
            }

        }
    }

    /*Kriegt die Koordinaten von setShip (Field Klasse), welche die Koordinaten von saveShip(main) übergeben bekommt.
     Also man muss mehrere Methoden "überstehen", um wirklich hier zu landen. Es wird dazwischen überprüft ob man
     setzen darf, ob Platz frei ist usw. Am Rande: Vergleichbar mit GUI am PC. Man macht was und es geht viele
     Schichten hinunter. */
    public Ship(Position position,
                int length,
                Direction directions,
                int diffvectorx,
                int diffvectory)
    {
        this.position = position;
        this.direction = directions;
        this.length = length;
        this.differenceVectorX = diffvectorx;
        this.differenceVectorY = diffvectory;

        generateShipParts(position, length, directions);

        /*Dient nur der Ausgabe für uns zum testen*/
        System.out.println("ich generiere schiff an X= " + this.getxCoordinate() + " Y =" + this.getyCoordinate() + " richtung" + this.direction + " länge =" + this.length);
    }

    /*Die Schleife geht jeden part vom Schiff durch. Die if Bedienung checkt für jeden part (das macht es bei jedem
    Schleifendurchgang) ob die x und y Koordinate vom Part, das gerade in der For Schleife durchlaufen wird, den
    übergebenen x und y Koordinaten entspricht. (Die übergebenen, werden in der attack Methode in Field übergeben).
    Wenn es zutrifft, setzt es damage von dem Part auf true (das macht die destroy Methode in ShipPart)*/
    public boolean attack(int x, int y)
    {
        for (ShipPart shippart : this.shipparts)
        {
            if (shippart.isShipPartWithCoordinateDestroyed(x, y))
            {
                return true;
            }
        }
        return false;
    }

/*Das macht im Prinzip das gleiche wie die attack Methode. Nur diesmal schaut es, ob das ganze Schiff zerstört ist. Wie?
 Es prüft, für jeden Part (mit der For Schleife), ob es damaged ist. Wenn nicht damaged (das macht das Rufzeichen
 vorm ShipPart in der If - Bedienung) für einen part, false. Wenn für alle parts true zurückgeliefert wird, kommt bei
  der Methode true raus. Sprich, Schiff ist zerstört.*/
    public boolean checkIfDestroyed()
    {
        for (ShipPart shippart : this.shipparts)
        {
            if (!shippart.isDamaged())
            {
                return false;
            }
        }
        return true;
    }
}
