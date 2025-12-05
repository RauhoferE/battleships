package sample;

public class AIsave
{
    private int x,y;
    private Direction direction;

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public Direction getDirection()
    {
        return direction;
    }

    public void setDirection(Direction direction)
    {
        this.direction = direction;
    }

    public AIsave(int x, int y)
    {
        this.x = x;
        this.y = y;
        direction=null;
    }

    public AIsave(int x, int y, Direction direction )
    {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
}
