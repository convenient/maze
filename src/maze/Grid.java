import java.util.ArrayList;
import java.awt.Point;

public class Grid {

    public Tile[][] grid;

    private int maze_width;
    private int maze_height;

    public Grid(int width, int height)
    {
        this.maze_height = height;
        this.maze_width = width;

        this.grid = new Tile[height][width];
    }

    private Point getNeighbourTileLocation(int heightLocation, int widthLocation, int direction)
    {
        Point point = new Point();

        switch (direction) {
            case Tile.DIR_NORTH:
                heightLocation--;
                break;
            case Tile.DIR_SOUTH:
                heightLocation++;
                break;
            case Tile.DIR_EAST:
                widthLocation++;
                break;
            case Tile.DIR_WEST:
                widthLocation--;
                break;
        }
        point.setLocation(heightLocation, widthLocation);
        return point;
    }

    public Point doTileThing(int heightLocation, int widthLocation, int direction, int path)
    {
        Point p = this.getNeighbourTileLocation(heightLocation, widthLocation, direction);
        int endHeight = (int)p.getX();
        int endWidth = (int)p.getY();

        int reversedDirection = Tile.reverseDirection(direction);

        this.grid[endHeight][endWidth] = new Tile();

        this.grid[heightLocation][widthLocation].setDirection(direction, path);
        this.grid[endHeight][endWidth].setDirection(reversedDirection, path);

        return p;
    }

    public ArrayList<Integer> getUnvisitedNeighbours(int heightLocation, int widthLocation)
    {
        ArrayList<Integer> available_directions = new ArrayList<Integer>();

        if (heightLocation != 0 && grid[heightLocation-1][widthLocation] == null) {
            available_directions.add(Tile.DIR_NORTH);
        }

        if(heightLocation < maze_height-1 && grid[heightLocation+1][widthLocation] == null){
            available_directions.add(Tile.DIR_SOUTH);
        }

        if(widthLocation != 0 && grid[heightLocation][widthLocation-1] == null){
            available_directions.add(Tile.DIR_WEST);
        }

        if(widthLocation < maze_width-1 && grid[heightLocation][widthLocation+1] == null){
            available_directions.add(Tile.DIR_EAST);
        }

        return available_directions;
    }

}
