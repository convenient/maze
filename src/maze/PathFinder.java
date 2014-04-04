import java.util.*;

public class PathFinder
{
    private DepthFirstMaze maze;
    private String beginning = "BEGINNING";

    public PathFinder(DepthFirstMaze maze)
    {
        this.maze = maze;
    }

    public Stack<Tile> get(Location startLoc, Location endLoc)
    {
        int itemsInMaze = maze.map.size();

        Map<String, String> trail = new HashMap<String, String>();

        Tile temp;
        Iterator iterator;
        Queue<Location> queue = new LinkedList<Location>();
        trail.put(startLoc.getHashMapKey(), beginning);
        queue.add(startLoc);

        /**
         * Generate all paths problem
         */
        Boolean complete = false;
        while (trail.size() < maze.map.size() && !complete) {
            temp = maze.getFromHashMap(queue.poll());
            for (Location loc : temp.getNeighbourTileLocations()) {
                if (complete) {
                    break;
                }
                if (trail.get(loc.getHashMapKey()) == null) {
                    if (loc.getHashMapKey().equals(endLoc.getHashMapKey())) {
                        complete = true;
                    }
                    trail.put(loc.getHashMapKey(), temp.getLocation().getHashMapKey());
                    queue.add(loc);
                }
            }
        }

        /**
         * Return a stack of tiles in order
         */
        Stack<Tile> stack = new Stack<Tile>();
        String pathBack = endLoc.getHashMapKey();
        while (!pathBack.equals(beginning)) {
            stack.push(maze.map.get(pathBack));
            pathBack = trail.get(pathBack);
        }
        return stack;
    }
}
