import java.awt.*;
import java.util.*;

public class DepthFirstMaze
{
    public Map<String, Tile> map = new HashMap<String, Tile>();

    private Random randomGenerator = new Random();
    private Polygon boundary;

    public DepthFirstMaze(Polygon boundary)
    {
        randomGenerator.setSeed(19091990);

        this.boundary = boundary;
    }

    public void addTile(Tile tile)
    {
        map.put(tile.getId(), tile);
    }

    public Tile getTile(Location l)
    {
        return map.get(l.getHashMapKey());
    }

    private Location getStartLocation()
    {
        Rectangle innerRectangle = boundary.getBounds();
        while (true) {
            int randomRow = randomGenerator.nextInt((int)innerRectangle.getWidth()) + (int)innerRectangle.getX();
            int randomCol = randomGenerator.nextInt((int)innerRectangle.getHeight()) + (int)innerRectangle.getY();
            if (boundary.contains(randomRow, randomCol)) {
                return new Location(randomRow, randomCol);
            }
        }
    }

    public void generate()
    {
        Stack<Tile> stack = new Stack<Tile>();
        map.clear();

        Tile beginning = new Tile(getStartLocation());
        addTile(beginning);
        stack.push(beginning);

        while (!stack.empty()) {
            Tile working = stack.peek();

            ArrayList<Integer> directions = getPotentialNeighbours(working);
            if (directions.isEmpty()) {
                stack.pop();
            } else {
                Integer chosenDirection = directions.get(randomGenerator.nextInt(directions.size()));
                Tile newTile = working.connect(chosenDirection);
                addTile(newTile);
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
        if (boundary.contains(row, col) && getTile(loc) == null) {
            available_directions.add(direction);
        }
    }

    public ArrayList<Tile> getNeighbouringTiles(Tile t)
    {
        ArrayList<Tile> neighbours = new ArrayList<Tile>();

        Tile north = getTile(t.getNeighbourLocation(Direction.UP));
        if (north != null) {
            neighbours.add(north);
        }
        Tile south = getTile(t.getNeighbourLocation(Direction.DOWN));
        if (south != null) {
            neighbours.add(south);
        }
        Tile east = getTile(t.getNeighbourLocation(Direction.RIGHT));
        if (east != null) {
            neighbours.add(east);
        }
        Tile west = getTile(t.getNeighbourLocation(Direction.LEFT));
        if (west != null) {
            neighbours.add(west);
        }

        return neighbours;
    }

}
