import java.util.*;
import java.awt.Polygon;

public class DepthFirstMaze
{
    public Map<String, Tile> map = new HashMap<String, Tile>();

    private Random randomGenerator = new Random();
    private Polygon boundary;

    private Location startLoc;

    public DepthFirstMaze(Polygon boundary, Location start)
    {
        this.startLoc = start;
        this.boundary = boundary;

        randomGenerator.setSeed(19091990);
    }

    public void addToHashMap(Tile tile)
    {
        map.put(tile.getKey(), tile);
    }

    public Tile getFromHashMap(Location l)
    {
        return map.get(l.getHashMapKey());
    }

    public void generate()
    {
        Stack<Tile> stack = new Stack<Tile>();
        map.clear();
        stack.clear();

        Tile beginning = new Tile(startLoc);
        addToHashMap(beginning);
        stack.push(beginning);

        while (!stack.empty()) {
            Tile working = (Tile)stack.peek();

            ArrayList<Integer> directions = getPotentialNeighbours(working);
            if (directions.isEmpty()) {
                stack.pop();
            } else {
                Integer chosenDirection = directions.get(randomGenerator.nextInt(directions.size()));
                Tile newTile = working.connect(chosenDirection);
                addToHashMap(newTile);
                stack.push(newTile);
            }
        }
    }

    public Boolean isBoundaryEdge(Tile t)
    {
        Location loc = t.getNeighbourLocation(Direction.UP);
        int row = loc.getRow();
        int col = loc.getCol();
        if (!boundary.contains(row, col)) {
            return true;
        }

        loc = t.getNeighbourLocation(Direction.DOWN);
        row = loc.getRow();
        col = loc.getCol();
        if (!boundary.contains(row, col)) {
            return true;
        }

        loc = t.getNeighbourLocation(Direction.RIGHT);
        row = loc.getRow();
        col = loc.getCol();
        if (!boundary.contains(row, col)) {
            return true;
        }

        loc = t.getNeighbourLocation(Direction.LEFT);
        row = loc.getRow();
        col = loc.getCol();
        if (!boundary.contains(row, col)) {
            return true;
        }

        return false;
    }

    public ArrayList<Integer> getPotentialNeighbours(Tile tile)
    {
        ArrayList<Integer> available_directions = new ArrayList<Integer>();

        this.addDirectionIfInBoundaryAndUnused(tile, Direction.UP, available_directions);
        this.addDirectionIfInBoundaryAndUnused(tile, Direction.DOWN, available_directions);
        this.addDirectionIfInBoundaryAndUnused(tile, Direction.RIGHT, available_directions);
        this.addDirectionIfInBoundaryAndUnused(tile, Direction.LEFT, available_directions);

        return available_directions;
    }

    private void addDirectionIfInBoundaryAndUnused(Tile t, int direction,  ArrayList<Integer> available_directions)
    {
        Location loc = t.getNeighbourLocation(direction);
        int row = loc.getRow();
        int col = loc.getCol();
        if (boundary.contains(row, col) && getFromHashMap(loc) == null) {
            available_directions.add(direction);
        }
    }

    public ArrayList<Tile> getNeighbouringTiles(Tile t)
    {
        ArrayList<Tile> neighbours = new ArrayList<Tile>();

        Tile north = getFromHashMap(t.getNeighbourLocation(Direction.UP));
        if (north != null) {
            neighbours.add(north);
        }
        Tile south = getFromHashMap(t.getNeighbourLocation(Direction.DOWN));
        if (south != null) {
            neighbours.add(south);
        }
        Tile east = getFromHashMap(t.getNeighbourLocation(Direction.RIGHT));
        if (east != null) {
            neighbours.add(east);
        }
        Tile west = getFromHashMap(t.getNeighbourLocation(Direction.LEFT));
        if (west != null) {
            neighbours.add(west);
        }

        return neighbours;
    }

//    public ArrayList<Integer> getUnvisitedNeighboursB(Location tile)
//    {
//        ArrayList<Integer> available_directions = new ArrayList<Integer>();
//
//        int rowLocation = tile.getRow();
//        int colLocation = tile.getCol();
//
//        if (rowLocation != 0 && this.getTileAt(this.getNeighbourTileLocation(tile, Direction.UP)) == null) {
//            available_directions.add(Direction.UP);
//        }
//
//        if(rowLocation < rows - 1 && this.getTileAt(this.getNeighbourTileLocation(tile, Direction.DOWN)) == null){
//            available_directions.add(Direction.DOWN);
//        }
//
//        if(colLocation != 0 && this.getTileAt(this.getNeighbourTileLocation(tile, Direction.LEFT)) == null){
//            available_directions.add(Direction.LEFT);
//        }
//
//        if(colLocation < columns - 1 && this.getTileAt(this.getNeighbourTileLocation(tile, Direction.RIGHT)) == null){
//            available_directions.add(Direction.RIGHT);
//        }
//
//        ArrayList<Map<String, Tile>> eastArrayAttempt = new ArrayList<Map<String, Tile>>();
//
//        Map<String, Tile> tempEastMap = new HashMap<String, Tile>();
//
//        if (colLocation < columns -1) {
//            Location tempEast = new Location(tile.row, tile.col, tile.depth);
//
//            while(tempEast.col < columns -1 ) {
//                tempEast.col++;
//                Tile newTile = new Tile();
//                newTile.setDirection(Direction.LEFT, Tile.PATH_DOWN);
//
//                if (this.getTileAt(tempEast) == null){
//                    tempEastMap.put(tempEast.getHashMapKey(), newTile);
//                    eastArrayAttempt.add(tempEastMap);
//                    break;
//                } else {
//                    Location underEast = new Location(tempEast.row, tempEast.col, -1);
//                    newTile.setDirection(Direction.RIGHT, Tile.PATH_DOWN);
//                    tempEastMap.put(underEast.getHashMapKey(), newTile);
//                }
//            }
//        }
//
//        System.out.println(eastArrayAttempt.size());
//
//        return available_directions;
//    }
//
//    public ArrayList<Map<String, Tile>> getUnvisitedNeighboursA(Location tile)
//    {
//        int rowLocation = tile.getRow();
//        int colLocation = tile.getCol();
//
//        ArrayList<Map<String, Tile>> available_directions = new ArrayList<Map<String, Tile>>();
//
//        int DIRECTION = Direction.RIGHT;
//
//        if (colLocation < columns -1) {
//            Map<String, Tile> tempMap = new HashMap<String, Tile>();
//            Location tempLoc = new Location(tile.row, tile.col, tile.depth);
//
//            while(tempLoc.col < columns -1 ) {
//                tempLoc.col++;
//
//                Tile newTile = new Tile();
//                newTile.setDirection(Tile.getOppositeDirection(DIRECTION), Tile.PATH_DOWN);
//
//                if (this.getTileAt(tempLoc) == null){
//                    tempMap.put(tempLoc.getHashMapKey(), newTile);
//                    available_directions.add(tempMap);
//                    break;
//                } else {
//                    newTile.setDirection(DIRECTION, Tile.PATH_DOWN);
//                    tempMap.put(new Location(tempLoc.row, tempLoc.col, -1).getHashMapKey(), newTile);
//                }
//            }
//
//        }
//
//        return available_directions;
//    }


}
