import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

abstract class AbstractMaze {

    public Map<String, Tile> map = new HashMap<String, Tile>();

    public int columns;
    public int rows;

    Location start = new Location();

    abstract void generate();

    AbstractMaze(int rows, int columns, Location start)
    {
        this.rows = rows;
        this.columns = columns;

        this.start = start;
    }

    public void setRows(int rows)
    {
        if(rows >= 1) {
            this.rows = rows;
        }
    }

    public void setColumns(int columns)
    {
        if(columns >=1) {
            this.columns = columns;
        }
    }

    public Location getNeighbourTileLocation(Location tile, int direction)
    {
        Location location = new Location(tile.getRow(), tile.getCol(), tile.getDepth());

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

    public Location makePath(Location tileLocation, int direction, int path)
    {
        Tile tile = this.getTileAt(tileLocation);
        tile.setDirection(direction, path);
        this.setTileAt(tile, tileLocation);

        Location newTileLocation = this.getNeighbourTileLocation(tileLocation, direction);
        Tile newTile = new Tile();
        newTile.setDirection(Tile.reverseDirection(direction), path);

        this.setTileAt(newTile, newTileLocation);

        return newTileLocation;
    }

    public ArrayList<Integer> getUnvisitedNeighbours(Location tile)
    {
        ArrayList<Integer> available_directions = new ArrayList<Integer>();

        int rowLocation = tile.getRow();
        int colLocation = tile.getCol();

        if (rowLocation != 0 && this.getTileAt(this.getNeighbourTileLocation(tile, Tile.DIR_NORTH)) == null) {
            available_directions.add(Tile.DIR_NORTH);
        }

        if(rowLocation < rows - 1 && this.getTileAt(this.getNeighbourTileLocation(tile, Tile.DIR_SOUTH)) == null){
            available_directions.add(Tile.DIR_SOUTH);
        }

        if(colLocation != 0 && this.getTileAt(this.getNeighbourTileLocation(tile, Tile.DIR_WEST)) == null){
            available_directions.add(Tile.DIR_WEST);
        }

        if(colLocation < columns - 1 && this.getTileAt(this.getNeighbourTileLocation(tile, Tile.DIR_EAST)) == null){
            available_directions.add(Tile.DIR_EAST);
        }

        ArrayList<Map<String, Tile>> eastArrayAttempt = new ArrayList<Map<String, Tile>>();

        Map<String, Tile> tempEastMap = new HashMap<String, Tile>();

        if (colLocation < columns -1) {
            Location tempEast = new Location(tile.row, tile.col, tile.depth);

            while(tempEast.col < columns -1 ) {
                tempEast.col++;
                Tile newTile = new Tile();
                newTile.setDirection(Tile.DIR_WEST, Tile.PATH_DOWN);

                if (this.getTileAt(tempEast) == null){
                    tempEastMap.put(tempEast.getHashMapKey(), newTile);
                    eastArrayAttempt.add(tempEastMap);
                    break;
                } else {
                    Location underEast = new Location(tempEast.row, tempEast.col, -1);
                    newTile.setDirection(Tile.DIR_EAST, Tile.PATH_DOWN);
                    tempEastMap.put(underEast.getHashMapKey(), newTile);
                }
            }
        }

        System.out.println(eastArrayAttempt.size());

        return available_directions;
    }

    public Tile getTileAt(int row, int col, int depth)
    {
        return this.getTileAt(new Location(row, col, depth));
    }

    public Tile getTileAt(Location l)
    {
        return map.get(l.getHashMapKey());
    }

    public void setTileAt(Tile tile, int row, int col, int depth)
    {
        this.setTileAt(tile, new Location(row, col, depth));
    }

    public void setTileAt(Tile tile, Location l)
    {
        map.put(l.getHashMapKey(), tile);
    }

}
