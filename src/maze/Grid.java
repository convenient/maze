import java.util.ArrayList;

public class Grid {

    public Tile[][] grid;

    private int columnCount;
    private int rowCount;

    public Grid(int rowCount, int columnCount)
    {
        this.rowCount = rowCount;
        this.columnCount = columnCount;

        this.grid = new Tile[rowCount][columnCount];
    }

    public void generate()
    {
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
        this.grid[tile.getRow()][tile.getCol()].setDirection(direction, path);

        Location newTileLocation = this.getNeighbourTileLocation(tile, direction);

        Tile newTile = new Tile();
        newTile.setDirection(Tile.reverseDirection(direction), path);

        this.grid[newTileLocation.getRow()][newTileLocation.getCol()] = newTile;

        return newTileLocation;
    }

    public ArrayList<Integer> getUnvisitedNeighbours(Location tile)
    {
        ArrayList<Integer> available_directions = new ArrayList<Integer>();

        int rowLocation = tile.getRow();
        int colLocation = tile.getCol();

        if (rowLocation != 0 && grid[rowLocation-1][colLocation] == null) {
            available_directions.add(Tile.DIR_NORTH);
        }

        if(rowLocation < rowCount - 1 && grid[rowLocation+1][colLocation] == null){
            available_directions.add(Tile.DIR_SOUTH);
        }

        if(colLocation != 0 && grid[rowLocation][colLocation-1] == null){
            available_directions.add(Tile.DIR_WEST);
        }

        if(colLocation < columnCount - 1 && grid[rowLocation][colLocation+1] == null){
            available_directions.add(Tile.DIR_EAST);
        }

        return available_directions;
    }

}
