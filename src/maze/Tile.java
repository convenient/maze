import java.util.ArrayList;

class Tile {

    public static final int PATH_NONE = 9;
    public static final int PATH_DOWN = -1;
    public static final int PATH_FLAT = 0;
    public static final int PATH_UP = +1;

    int[] dir;
    Location location;

    public Tile(Location l)
    {
        this.location = l;
        this.dir = new int[4];
        this.dir[Direction.UP] = PATH_NONE;
        this.dir[Direction.RIGHT] = PATH_NONE;
        this.dir[Direction.DOWN] = PATH_NONE;
        this.dir[Direction.LEFT] = PATH_NONE;
    }

    public Tile connect(int direction)
    {
        setDirection(direction, PATH_FLAT);
        Tile newTile = new Tile(getNeighbourLocation(direction));
        newTile.setDirection(Direction.getOpposite(direction), PATH_FLAT);

        return newTile;
    }

    public ArrayList<Location> getNeighbourTileLocations()
    {
        ArrayList<Location> neighbours = new ArrayList<Location>();

        if (this.getNorth() != PATH_NONE)
            neighbours.add(getNeighbourLocation(Direction.UP));
        if (this.getEast() != PATH_NONE)
            neighbours.add(getNeighbourLocation(Direction.RIGHT));
        if (this.getSouth() != PATH_NONE)
            neighbours.add(getNeighbourLocation(Direction.DOWN));
        if (this.getWest() != PATH_NONE)
            neighbours.add(getNeighbourLocation(Direction.LEFT));

        return neighbours;
    }

    public ArrayList<Integer> getPathDirections()
    {
        ArrayList<Integer> paths = new ArrayList<Integer>();

        if (this.getNorth() != PATH_NONE)
            paths.add(Direction.UP);
        if (this.getEast() != PATH_NONE)
            paths.add(Direction.RIGHT);
        if (this.getSouth() != PATH_NONE)
            paths.add(Direction.DOWN);
        if (this.getWest() != PATH_NONE)
            paths.add(Direction.LEFT);

        return paths;

    }

    public Location getNeighbourLocation(int direction)
    {
        Location neighbourLocation = location.clone();

        switch (direction) {
            case Direction.UP:
                neighbourLocation.setRow(neighbourLocation.getRow()-1);
                break;
            case Direction.DOWN:
                neighbourLocation.setRow(neighbourLocation.getRow()+1);
                break;
            case Direction.RIGHT:
                neighbourLocation.setCol(neighbourLocation.getCol()+1);
                break;
            case Direction.LEFT:
                neighbourLocation.setCol(neighbourLocation.getCol()-1);
                break;
        }
        return neighbourLocation;
    }

    public void setLocation(Location l)
    {
        this.location = l;
    }

    public void setLocation(int row, int col)
    {
        this.location = new Location(row, col);
    }

    public Location getLocation()
    {
        return this.location;
    }

    public String getKey()
    {
        return this.location.getHashMapKey();
    }

    public void setDirection(int dir, int path)
    {
        this.dir[dir] = path;
    }

    public void setNorth(int path)
    {
        this.setDirection(Direction.UP, path);
    }

    public void setSouth(int path)
    {
        this.setDirection(Direction.DOWN, path);
    }

    public void setEast(int path)
    {
        this.setDirection(Direction.RIGHT, path);
    }

    public void setWest(int path)
    {
        this.setDirection(Direction.LEFT, path);
    }

    public int getDirection(int dir)
    {
        return this.dir[dir];
    }

    public int getNorth()
    {
        return this.getDirection(Direction.UP);
    }

    public int getSouth()
    {
        return this.getDirection(Direction.DOWN);
    }

    public int getWest()
    {
        return this.getDirection(Direction.LEFT);
    }

    public int getEast()
    {
        return this.getDirection(Direction.RIGHT);
    }

    public String toString() {

        String str = "North:\t" + ((this.getNorth() == Tile.PATH_NONE)?"Wall":"Path") + "\n";
        str += "East:\t" + ((this.getEast() == Tile.PATH_NONE)?"Wall":"Path") + "\n";
        str += "South:\t" + ((this.getSouth() == Tile.PATH_NONE)?"Wall":"Path") + "\n";
        str += "West:\t" + ((this.getWest() == Tile.PATH_NONE)?"Wall":"Path") + "\n";

        return str;
    }
}