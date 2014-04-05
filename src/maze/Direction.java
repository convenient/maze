public final class Direction
{
    public static final int UP = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;

    public static int getOpposite(int direction)
    {
        int returnDirection = DOWN;
        switch(direction) {
            case UP:
                returnDirection = DOWN;
                break;
            case RIGHT:
                returnDirection = LEFT;
                break;
            case DOWN:
                returnDirection = UP;
                break;
            case LEFT:
                returnDirection = RIGHT;
                break;
        }
        return returnDirection;
    }
}
