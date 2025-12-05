package sample;

import java.util.ArrayList;

//In der Klasse sind alle Eigenschaften, die ein Player hat
public class Player
{
    Field area = new Field();

    private ArrayList<Position> attackpositions = new ArrayList<>();

    /*SaveAttack speichert alle Attacken in die Arraylist*/
    public void SaveAttackOnPosition(int x, int y)
    {
        this.attackpositions.add(new Position(x, y));
    }

    /*Wir verhindern doppelten Angriff. Wir schauen, mit der foreach Schleife, ob die Übergebenen x,y von attackPossible
    schon in einer der gespeicherten Stellen in unserer ArrayList attackpositions enthalten ist.*/
    boolean isAttackPossibleOn(Position position)
    {
        for (Position attackPosition : this.attackpositions)
        {
            if (attackPosition.arePositionsEqual(position))
            {
                return false;
            }
        }
        return true;
    }

    /*Reset überschreibt unsere Klassenarraylist, die wir oben erstellt haben, mit einer Leeren Arraylist --> Resetet es*/
    public void ResetAttackPositions()
    {
        this.attackpositions = new ArrayList<>();
    }

}
