package sample;

public class ShipPart
{
    /*Jeder Teil vom Schiff (in unserem Fall ist jeder Teil genau 40pixel lang) hat die Eigenschaften von der Klasse
    ShipPart*/

    private final Position position;
    private boolean receivedDamage;

    public int getXCoordinate()
    {
        return position.getX();
    }

    public int getYCoordinate()
    {
        return position.getY();
    }

    public ShipPart(Position position)
    {
        this.position = position;
        this.receivedDamage = false;

        /*Dient der Ausgabe für uns, zum testen*/
        System.out.println(" schiffteil an X= " + this.getXCoordinate() + " Y =" + this.getYCoordinate() + " schaden= " + this.receivedDamage);
    }

    /*ist dieser Teil vom Schiff zerstört?*/
    public boolean isDamaged()
    {
        return receivedDamage;
    }

    /*Wird in der Ship klasse, in der attack Methode aufgerufen!*/
    public boolean isShipPartWithCoordinateDestroyed(int x, int y)
    {
        return this.receivedDamage = x ==this.getXCoordinate() && y == this.getYCoordinate();
    }

}