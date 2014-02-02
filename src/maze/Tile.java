class Tile {

    public static final int PATH_NONE = 0;
    public static final int PATH_DOWN = 1;
    public static final int PATH_FLAT = 2;
    public static final int PATH_UP = 3;

    public static final int DIR_NORTH = 0;
    public static final int DIR_EAST = 1;
    public static final int DIR_SOUTH = 2;
    public static final int DIR_WEST = 3;

    int[] dir;

    public Tile() {
        this.dir = new int[4];
        this.dir[DIR_NORTH] = PATH_NONE;
        this.dir[DIR_EAST] = PATH_NONE;
        this.dir[DIR_SOUTH] = PATH_NONE;
        this.dir[DIR_WEST] = PATH_NONE;
    }

    public int getNumberOfPaths() {
        int paths = 0;
        if (this.getNorth() != PATH_NONE)
            paths++;
        if (this.getEast() != PATH_NONE)
            paths++;
        if (this.getSouth() != PATH_NONE)
            paths++;
        if (this.getWest() != PATH_NONE)
            paths++;

        return paths;
    }
    public void setDirection(int dir, int path)
    {
        this.dir[dir] = path;
    }

    public void setNorth(int path)
    {
        this.setDirection(DIR_NORTH, path);
    }

    public void setSouth(int path)
    {
        this.setDirection(DIR_SOUTH, path);
    }

    public void setEast(int path)
    {
        this.setDirection(DIR_EAST, path);
    }

    public void setWest(int path)
    {
        this.setDirection(DIR_WEST, path);
    }

    public int getDirection(int dir)
    {
        return this.dir[dir];
    }

    public int getNorth()
    {
        return this.getDirection(DIR_NORTH);
    }

    public int getSouth()
    {
        return this.getDirection(DIR_SOUTH);
    }

    public int getWest()
    {
        return this.getDirection(DIR_WEST);
    }

    public int getEast()
    {
        return this.getDirection(DIR_EAST);
    }

    public static int reverseDirection(int direction)
    {
        int returnDirection = DIR_SOUTH;
        switch(direction) {
            case DIR_NORTH:
                returnDirection = DIR_SOUTH;
                break;
            case DIR_EAST:
                returnDirection = DIR_WEST;
                break;
            case DIR_SOUTH:
                returnDirection = DIR_NORTH;
                break;
            case DIR_WEST:
                returnDirection = DIR_EAST;
                break;
        }
        return returnDirection;
    }
}