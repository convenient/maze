import java.util.*;

public class PathFinder
{
    private DepthFirstMaze maze;
    private String beginning = "BEGINNING";

    Map<String, Map<String, String>> allPathSolutions;

    public PathFinder(DepthFirstMaze maze)
    {
        this.allPathSolutions = new HashMap<String, Map<String, String>>();
        this.maze = maze;
    }

    public Stack<Tile> get(Location startLoc, Location endLoc)
    {
        Map<String, String> trail = getAllPathsSolution(startLoc);

        /**
         * Return getId stack of tiles in order
         */
        Stack<Tile> stack = new Stack<Tile>();
        String pathBack = endLoc.getHashMapKey();
        while (!pathBack.equals(beginning)) {
            stack.push(maze.map.get(pathBack));
            pathBack = trail.get(pathBack);
        }

        return stack;
    }

    private Map<String, String> getAllPathsSolution(Location location)
    {
        Map<String, String> existing = allPathSolutions.get(location.getHashMapKey());
        if (existing != null) {
            return existing;
        }

        Map<String, String> trail = new HashMap<String, String>();
        Queue<Location> queue = new LinkedList<Location>();

        Tile temp;
        trail.put(location.getHashMapKey(), beginning);
        queue.add(location);

        while (trail.size() < maze.map.size()) {
            temp = maze.getTile(queue.poll());
            for (Location loc : temp.getNeighbourTileLocations()) {
                if (trail.get(loc.getHashMapKey()) == null) {
                    trail.put(loc.getHashMapKey(), temp.getLocation().getHashMapKey());
                    queue.add(loc);
                }
            }
        }

        allPathSolutions.put(location.getHashMapKey(), trail);

        return trail;
    }
}
