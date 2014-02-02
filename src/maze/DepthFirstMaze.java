import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.awt.Point;

public class DepthFirstMaze extends Grid
{
    int startHeight = 0;
    int startWidth = 0;
    Point start = new Point();
    private Random randomGenerator = new Random();

    public DepthFirstMaze(int width, int height, int start_x, int start_y)
    {
        super(width, height);
        start.setLocation(start_x, start_y);
        this.grid[start_x][start_y] = new Tile();
    }

    public void generate()
    {
        Stack stack = new Stack();
        stack.push(start);

        Point working;

        while(!stack.empty()){

            working = (Point)stack.peek();

            int hPos = (int)working.getX();
            int wPos = (int)working.getY();

            ArrayList<Integer> directions = this.getUnvisitedNeighbours(hPos, wPos);

            if(directions.size()==0){
                stack.pop();
            } else {
                int direction = directions.get(randomGenerator.nextInt(directions.size()));
                Point neighbourLocation = this.doTileThing(hPos, wPos, direction, Tile.PATH_FLAT);
                stack.push(neighbourLocation);
            }

        }

    }
}
