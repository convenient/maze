import java.util.*;

public class DepthFirstMaze extends AbstractMaze
{
    private Random randomGenerator = new Random();

    public DepthFirstMaze(int rowCount, int colCount, Location start)
    {
        super(rowCount, colCount, start);
        randomGenerator.setSeed(19091990);
    }

    public void generate()
    {
        start = new Location(5,5,0);

        map.clear();

        Location l = new Location(7, 5, 0);

        this.setTileAt(new Tile(), l);

        l = this.makePath(l, Tile.DIR_NORTH, Tile.PATH_FLAT);
        l = this.makePath(l, Tile.DIR_NORTH, Tile.PATH_FLAT);
        l = this.makePath(l, Tile.DIR_NORTH, Tile.PATH_FLAT);
        l = this.makePath(l, Tile.DIR_NORTH, Tile.PATH_FLAT);
        l = this.makePath(l, Tile.DIR_NORTH, Tile.PATH_FLAT);
        l = this.makePath(l, Tile.DIR_WEST, Tile.PATH_FLAT);
        l = this.makePath(l, Tile.DIR_SOUTH, Tile.PATH_FLAT);
        l = this.makePath(l, Tile.DIR_SOUTH, Tile.PATH_FLAT);
        l = this.makePath(l, Tile.DIR_SOUTH, Tile.PATH_FLAT);
        l = this.makePath(l, Tile.DIR_SOUTH, Tile.PATH_FLAT);
        l = this.makePath(l, Tile.DIR_WEST, Tile.PATH_FLAT);
        l = this.makePath(l, Tile.DIR_NORTH, Tile.PATH_FLAT);
        l = this.makePath(l, Tile.DIR_NORTH, Tile.PATH_FLAT);
        l = this.makePath(l, Tile.DIR_NORTH, Tile.PATH_FLAT);
        l = this.makePath(l, Tile.DIR_NORTH, Tile.PATH_FLAT);
        l = this.makePath(l, Tile.DIR_WEST, Tile.PATH_FLAT);
        l = this.makePath(l, Tile.DIR_WEST, Tile.PATH_FLAT);
        l = this.makePath(l, Tile.DIR_SOUTH, Tile.PATH_FLAT);
        l = this.makePath(l, Tile.DIR_SOUTH, Tile.PATH_FLAT);
        l = this.makePath(l, Tile.DIR_EAST, Tile.PATH_FLAT);


        ArrayList<Map<String, Tile>> directions = this.getUnvisitedNeighboursA(l);
        Map<String, Tile> path = directions.get(randomGenerator.nextInt(directions.size()));

        for (Map.Entry entry : path.entrySet()) {
            this.map.put((String)entry.getKey(), (Tile)entry.getValue());
        }

    }

    public ArrayList<Map<String, Tile>> getUnvisitedNeighboursA(Location tile)
    {
        int rowLocation = tile.getRow();
        int colLocation = tile.getCol();

        ArrayList<Map<String, Tile>> available_directions = new ArrayList<Map<String, Tile>>();

        int DIRECTION = Tile.DIR_EAST;

        if (colLocation < columns -1) {
            Map<String, Tile> tempMap = new HashMap<String, Tile>();
            Location tempLoc = new Location(tile.row, tile.col, tile.depth);

            while(tempLoc.col < columns -1 ) {
                tempLoc.col++;

                Tile newTile = new Tile();
                newTile.setDirection(Tile.reverseDirection(DIRECTION), Tile.PATH_DOWN);

                if (this.getTileAt(tempLoc) == null){
                    tempMap.put(tempLoc.getHashMapKey(), newTile);
                    available_directions.add(tempMap);
                    break;
                } else {
                    newTile.setDirection(DIRECTION, Tile.PATH_DOWN);
                    tempMap.put(new Location(tempLoc.row, tempLoc.col, -1).getHashMapKey(), newTile);
                }
            }

        }

        return available_directions;
    }


}
