class Tile {

    public static final int PATH_NONE = 9;
    public static final int PATH_DOWN = -1;
    public static final int PATH_FLAT = 0;
    public static final int PATH_UP = +1;

    public static final int DIR_NORTH = 0;
    public static final int DIR_EAST = 1;
    public static final int DIR_SOUTH = 2;
    public static final int DIR_WEST = 3;

    int[] dir;
    Location location;

    public Tile(Location l)
    {
        this.location = l;
        this.dir = new int[4];
        this.dir[DIR_NORTH] = PATH_NONE;
        this.dir[DIR_EAST] = PATH_NONE;
        this.dir[DIR_SOUTH] = PATH_NONE;
        this.dir[DIR_WEST] = PATH_NONE;
    }

    public Tile connect(int direction)
    {
        this.setDirection(direction, PATH_FLAT);
        Tile newTile = new Tile(this.getNeighbourLocation(direction));
        newTile.setDirection(getOppositeDirection(direction), PATH_FLAT);

        return newTile;
    }

    public Location getNeighbourLocation(int direction)
    {
        Location neighbourLocation = new Location(this.location.getRow(), this.location.getCol(), this.location.getDepth());

        switch (direction) {
            case Tile.DIR_NORTH:
                neighbourLocation.setRow(neighbourLocation.getRow()-1);
                break;
            case Tile.DIR_SOUTH:
                neighbourLocation.setRow(neighbourLocation.getRow()+1);
                break;
            case Tile.DIR_EAST:
                neighbourLocation.setCol(neighbourLocation.getCol()+1);
                break;
            case Tile.DIR_WEST:
                neighbourLocation.setCol(neighbourLocation.getCol()-1);
                break;
        }
        return neighbourLocation;
    }

    public void setLocation(Location l)
    {
        this.location = l;
    }

    public void setLocation(int row, int col, int depth)
    {
        this.location = new Location(row, col, depth);
    }

    public Location getLocation()
    {
        return this.location;
    }

    public String getKey()
    {
        return this.location.getHashMapKey();
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

    public static int getOppositeDirection(int direction)
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

    public String toString() {

        String str = "North:\t" + ((this.getNorth() == Tile.PATH_NONE)?"Wall":"Path") + "\n";
        str += "East:\t" + ((this.getEast() == Tile.PATH_NONE)?"Wall":"Path") + "\n";
        str += "South:\t" + ((this.getSouth() == Tile.PATH_NONE)?"Wall":"Path") + "\n";
        str += "West:\t" + ((this.getWest() == Tile.PATH_NONE)?"Wall":"Path") + "\n";

        return str;
    }
}