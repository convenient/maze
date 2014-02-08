import java.util.ArrayList;

abstract class AbstractMaze {

    public Tile[][] maze;

    public int columnCount;
    public int rowCount;

    Location start = new Location();

    abstract void generate();

    AbstractMaze(int rowCount, int columnCount, Location start)
    {
        this.rowCount = rowCount;
        this.columnCount = columnCount;

        this.start = start;
    }

    private Location getNeighbourTileLocation(Location tile, int direction)
    {
        Location location = new Location(tile.getRow(), tile.getCol());

        switch (direction) {
            case Tile.DIR_NORTH:
                location.row--;
                break;
            case Tile.DIR_SOUTH:
                location.row++;
                break;
            case Tile.DIR_EAST:
                location.col++;
                break;
            case Tile.DIR_WEST:
                location.col--;
                break;
        }

        return location;
    }

    public Location makePath(Location tile, int direction, int path)
    {
        this.maze[tile.getRow()][tile.getCol()].setDirection(direction, path);

        Location newTileLocation = this.getNeighbourTileLocation(tile, direction);

        Tile newTile = new Tile();
        newTile.setDirection(Tile.reverseDirection(direction), path);

        this.maze[newTileLocation.getRow()][newTileLocation.getCol()] = newTile;

        return newTileLocation;
    }

    public ArrayList<Integer> getUnvisitedNeighbours(Location tile)
    {
        ArrayList<Integer> available_directions = new ArrayList<Integer>();

        int rowLocation = tile.getRow();
        int colLocation = tile.getCol();

        if (rowLocation != 0 && maze[rowLocation-1][colLocation] == null) {
            available_directions.add(Tile.DIR_NORTH);
        }

        if(rowLocation < rowCount - 1 && maze[rowLocation+1][colLocation] == null){
            available_directions.add(Tile.DIR_SOUTH);
        }

        if(colLocation != 0 && maze[rowLocation][colLocation-1] == null){
            available_directions.add(Tile.DIR_WEST);
        }

        if(colLocation < columnCount - 1 && maze[rowLocation][colLocation+1] == null){
            available_directions.add(Tile.DIR_EAST);
        }

        return available_directions;
    }

}
