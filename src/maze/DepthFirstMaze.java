import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class DepthFirstMaze extends AbstractMaze
{
    Location start = new Location();
    private Random randomGenerator = new Random();

    public DepthFirstMaze(int rowCount, int colCount, Location start)
    {
        this.rowCount = rowCount;
        this.columnCount = colCount;

        randomGenerator.setSeed(19091990);

        this.start = start;
    }

    public void generate()
    {
        this.maze = new Tile[this.rowCount][this.columnCount];
        this.maze[start.getRow()][start.getCol()] = new Tile();

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
