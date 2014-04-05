import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class MazeContainer
{
    public DepthFirstMaze maze;
    private PathFinder pathFinder;

    public Location entrance;
    public Location exit;

    public int counter = 0;

    public MazeContainer(Polygon boundary)
    {
        maze = new DepthFirstMaze(boundary);
        pathFinder = new PathFinder(maze);
    }

    public void generate()
    {
        counter++;
        System.out.println("Generating:\t" + counter);
        maze.generate();
        generateEntranceAndExit();
    }

    private ArrayList<Location> getDeadEndEdgeTiles()
    {
        ArrayList<Location> deadEnds = new ArrayList<Location>();

        for (Map.Entry entry : maze.map.entrySet()) {
            Tile t = (Tile)entry.getValue();

            if (t.getPathDirections().size() == 1 && maze.isBoundaryEdge(t)) {
                deadEnds.add(t.getLocation());
            }
        }

        return deadEnds;
    }

    public void generateEntranceAndExit()
    {
        ArrayList<Tile> longestPath = new ArrayList<Tile>();
        ArrayList<Location> deadEndCandidates = getDeadEndEdgeTiles();

        //Finds the longest path between all edge facing dead ends in the maze
        for (int startPtr = 0; startPtr < deadEndCandidates.size() -1; startPtr++) {
            Location startLocation = deadEndCandidates.get(startPtr);
            for (int endPtr = startPtr+1; endPtr < deadEndCandidates.size(); endPtr++) {
                Location endLocation = deadEndCandidates.get(endPtr);

                if (counter == 76) {
//                    System.out.println(startLocation);
//                    System.out.println(endLocation);
//                    System.exit(0);
                }

                ArrayList<Tile> path = pathFinder.get(startLocation, endLocation);
                if (path.size() > longestPath.size()) {
                    longestPath = path;
                }
            }
        }

        if (longestPath.size() >= 2) {
            Tile lastTile = longestPath.get(0);
            Tile firstTile = longestPath.get(longestPath.size() -1);

            lolmakehighway(firstTile);
            lolmakehighway(lastTile);
            entrance = firstTile.getLocation();
            exit = lastTile.getLocation();
        } else {
            System.out.println("No viable path exists, regenerating");
            generate();
        }
    }

    private void lolmakehighway(Tile t)
    {
        ArrayList<Integer> pathDirections = t.getPathDirections();
        int direction = Direction.getOpposite(pathDirections.get(0));

        Location attemptedHighway = t.getNeighbourLocation(direction);

        if (maze.getTile(attemptedHighway) != null) {

            //If the ideal straight line exit is blocked, brutely try and find the free path
            ArrayList<Integer> potentialDirections = new ArrayList<Integer>();
            potentialDirections.add(Direction.UP);
            potentialDirections.add(Direction.DOWN);
            potentialDirections.add(Direction.LEFT);
            potentialDirections.add(Direction.RIGHT);

            for (Integer dir : potentialDirections) {
                if (dir != direction) {
                    attemptedHighway = t.getNeighbourLocation(dir);
                    if (maze.getTile(attemptedHighway) == null) {
                        direction = dir;
                        break;
                    }
                }
            }
        }

        Tile vestibule = t.connect(direction);
        vestibule.setDirection(direction, Tile.PATH_FLAT);
        maze.addTile(vestibule);
    }
}
