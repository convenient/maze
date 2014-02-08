import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class DepthFirstMaze extends Grid
{
    Location start = new Location();
    private Random randomGenerator = new Random();

    public DepthFirstMaze()
    {
        super(0,0);
    }

    public DepthFirstMaze(int rowCount, int colCount, Location start)
    {
        super(rowCount, colCount);
        randomGenerator.setSeed(System.currentTimeMillis() / 1000L);
        this.start = start;
        this.grid[start.getRow()][start.getCol()] = new Tile();
    }

    public void generate()
    {
        Stack<Location> stack = new Stack<Location>();
        stack.push(this.start);

        Location working;

        while(!stack.empty()){

            working = stack.peek();

            ArrayList<Integer> directions = this.getUnvisitedNeighbours(working);

            if(directions.size()==0){
                stack.pop();
            } else {
                int direction = directions.get(randomGenerator.nextInt(directions.size()));
                Location newTile = this.makePath(working, direction, Tile.PATH_FLAT);
                stack.push(newTile);
            }

        }

    }
}
