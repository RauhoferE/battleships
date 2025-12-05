package sample;

public class AIsave
{
    private Position position;
    private Direction direction;

    public int getX()
    {
        return position.getX();
    }

    public int getY()
    {
        return position.getY();
    }

    public Direction getDirection()
    {
        return direction;
    }

    public void setDirection(Direction direction)
    {
        this.direction = direction;
    }

    public AIsave(Position position)
    {
        this.position = position;
        direction=null;
    }

    public AIsave(Position position, Direction direction )
    {
        this.position = position;
        this.direction = direction;
    }
}
