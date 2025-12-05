package sample;

public class ShipPart
{
    /*Jeder Teil vom Schiff (in unserem Fall ist jeder Teil genau 40pixel lang) hat die Eigenschaften von der Klasse
    ShipPart*/

    private int xCoordinate;
    private int yCoordinate;
    private boolean receivedDamage;

    public int getxCoordinate()
    {
        return xCoordinate;
    }

    public int getyCoordinate()
    {
        return yCoordinate;
    }

    public ShipPart(int xCoordinate, int yCoordinate)
    {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.receivedDamage = false;

        /*Dient der Ausgabe für uns, zum testen*/
        System.out.println(" schiffteil an X= " + this.xCoordinate + " Y =" + this.yCoordinate + " schaden= " + this.receivedDamage);
    }

    /*ist dieser Teil vom Schiff zerstört?*/
    public boolean isDamaged()
    {
        return receivedDamage;
    }

    /*Wird in der Ship klasse, in der attack Methode aufgerufen!*/
    public void destroy()
    {
        this.receivedDamage = true;
    }

}