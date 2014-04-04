import java.awt.*;
import java.util.*;

GridContainer container = new GridContainer();

void setup() {
        size(800, 400);

//        Helper.printInstructions();
        container.generate();
        container.draw();
}

//Draw needs to be called for keyPressed to work.
void draw(){}
void keyPressed() {
    Boolean redraw = false;
    switch(Character.toLowerCase(key)) {
        case 'p':
            container.boxWidth++;
            redraw = true;
            break;
        case 'o':
            container.boxWidth--;
            redraw = true;
            break;
        case 'k':
            container.pipeOffset++;
            redraw = true;
            break;
        case 'l':
            container.pipeOffset--;
            redraw = true;
            break;
        case 'm':
            container.lineWidth++;
            redraw = true;
            break;
        case 'n':
            container.lineWidth--;
            redraw = true;
            break;
        case 'w':
            container.rows++;
            container.generate();
            redraw = true;
            break;
        case 'q':
            container.rows--;
            container.generate();
            redraw = true;
            break;
        case 's':
            container.columns++;
            container.generate();
            redraw = true;
            break;
        case 'a':
            container.columns--;
            container.generate();
            redraw = true;
            break;
        case 'g':
            container.generate();
            redraw = true;
            break;
    }

    if (redraw) {
        container.draw();
    }
}

static class Helper {
    public static void printInstructions() {
        System.out.println("Decrease/Increase");
        System.out.println("Box Width:\t\tO/P");
        System.out.println("Pipe size:\t\tK/L");
        System.out.println("Line Width:\tN/M");
        System.out.println("Rows:\t\tQ/W");
        System.out.println("Columns:\t\tA/S");

        System.out.println();
        System.out.println("Generate Maze:\tG");

    }
}

class GridContainer {
    public DepthFirstMaze maze;
    public PathFinder pathFinder;
    public int boxWidth;
    public int margin;
    public int pipeOffset;
    public int lineWidth;

    public int rows;
    public int columns;

    public Location start;

    GridContainer() {
//        this.boxWidth = 30;
//        this.margin = 50;
//        this.pipeOffset = 6;
//        this.lineWidth = 5;
//        this.rows = 10;
//        this.columns = 25;
        this.margin = 50;

        this.boxWidth = 15;
        this.pipeOffset = 0;
        this.lineWidth = 1;

        /**
         * Coords work as follows, be aware to avoid intersections
         *
         * 0,0       -->         0,20
         *
         *  ^                      |
         *  |                      v
         *
         * 10,0      <--        10,20
         */
//        Rectangle
//        int xpoints[] = {0,0,10,10};
//        int ypoints[] = {0,20,20,0};

        start = new Location(6,10,0);

//        Sliced

        Polygon boundary = new Polygon();
        boundary.addPoint(0,10);
        boundary.addPoint(0,21);
        boundary.addPoint(10,31);
        boundary.addPoint(21,21);
        boundary.addPoint(21,10);
        boundary.addPoint(10,0);

//        boundary.addPoint(-10,0);
//        boundary.addPoint(-21,0);
//        boundary.addPoint(-21,21);
//        boundary.addPoint(-10,21);

        maze = new DepthFirstMaze(boundary, start);
        pathFinder = new PathFinder(maze);
    }

    public void generate() {
        maze.generate();
    }

    public void draw() {
        background(255);
        strokeCap(ROUND);
        strokeWeight(lineWidth);


//        Stack<Tile> longestPath = new Stack<Tile>();
//        for (Map.Entry entry : maze.map.entrySet()) {
//            Tile t = (Tile)entry.getValue();
//            if (t.getPathDirections().size() == 1 && maze.isBoundaryEdge(t)) {
//                Stack<Tile> path = this.pathFinder.get(start, t.getLocation());
//                if (path.size() > longestPath.size()) {
//                    longestPath = path;
//                }
//                break;
//            }
//        }
//
//        lolmakehighway(longestPath.peek());
//        Tile lastTile = new Tile(new Location(-1, -1, -1));
//        while (!longestPath.empty()) {
//            lastTile = longestPath.pop();
////            markTile(lastTile);
//        }
//        lolmakehighway(lastTile);

//        markTile(maze.getFromHashMap(start));
        //Iterate through the hashmap and print all tiles
        for (Map.Entry entry : maze.map.entrySet()) {
            Tile t = (Tile)entry.getValue();
            printTile(t);
        }
    }

    private void lolmakehighway(Tile t)
    {
        ArrayList<Integer> pathDirections = t.getPathDirections();
        int direction = Tile.getOppositeDirection(pathDirections.get(0));

        Location attemptedHighway = t.getNeighbourLocation(direction);

        if (maze.getFromHashMap(attemptedHighway) != null) {

            //If the ideal straight line exit is blocked, brutely try and find the free path
            ArrayList<Integer> potentialDirections = new ArrayList<Integer>();
            potentialDirections.add(Tile.DIR_NORTH);
            potentialDirections.add(Tile.DIR_SOUTH);
            potentialDirections.add(Tile.DIR_WEST);
            potentialDirections.add(Tile.DIR_EAST);

            for (Integer dir : potentialDirections) {
                if (dir != direction) {
                    attemptedHighway = t.getNeighbourLocation(dir);
                    if (maze.getFromHashMap(attemptedHighway) == null) {
                        direction = dir;
                        break;
                    }
                }
            }
        }

        Tile vestibule = t.connect(direction);
        vestibule.setDirection(direction, Tile.PATH_FLAT);
        maze.addToHashMap(vestibule);
    }

    public void printTile(Tile tile)
    {
        Location tLoc = tile.getLocation();
        int row = tLoc.getRow();
        int col = tLoc.getCol();

        //Adjust the row and column indexes so that we have a margin around the screen, as well as multiplying for
        //pixel box width
        int adjustedRow = (row * (boxWidth)) + margin;
        int adjustedCol = (col * boxWidth) + margin;

        //Take note that Columns are x axis, and rows are y axis!
        int topLeftX = adjustedCol;
        int topLeftY = adjustedRow;

        int topRightX = topLeftX + boxWidth;
        int topRightY = topLeftY;

        int bottomLeftX = topLeftX;
        int bottomLeftY = topLeftY + boxWidth;

        int bottomRightX = topRightX;
        int bottomRightY = topRightY + boxWidth;

        //Pipe offset
        topLeftX += pipeOffset;
        topLeftY += pipeOffset;

        bottomLeftX += pipeOffset;
        bottomLeftY -= pipeOffset;

        topRightX -= pipeOffset;
        topRightY += pipeOffset;

        bottomRightX -= pipeOffset;
        bottomRightY -= pipeOffset;

        switch (tile.getNorth()) {
            case Tile.PATH_NONE:
                line(topLeftX, topLeftY, topRightX, topRightY);
                break;
            case Tile.PATH_FLAT:
                line(topLeftX, topLeftY, topLeftX, topLeftY - pipeOffset);
                line(topRightX, topRightY, topRightX, topRightY - pipeOffset);
                break;
        }

        switch (tile.getEast()) {
            case Tile.PATH_NONE:
                line(topRightX, topRightY, bottomRightX, bottomRightY);
                break;
            case Tile.PATH_FLAT:
                line(topRightX, topRightY, topRightX + pipeOffset, topRightY);
                line(bottomRightX, bottomRightY, bottomRightX + pipeOffset, bottomRightY);
                break;
        }

        switch (tile.getWest()) {
            case Tile.PATH_NONE:
                line(topLeftX, topLeftY, bottomLeftX, bottomLeftY);
                break;
            case Tile.PATH_FLAT:
                line(topLeftX, topLeftY, topLeftX - pipeOffset, topLeftY);
                line(bottomLeftX, bottomLeftY, bottomLeftX - pipeOffset, bottomRightY);
                break;
        }

        switch (tile.getSouth()) {
            case Tile.PATH_NONE:
                line(bottomRightX, bottomRightY, bottomLeftX, bottomLeftY);
                break;
            case Tile.PATH_FLAT:
                line(bottomLeftX, bottomLeftY, bottomLeftX, bottomLeftY + pipeOffset);
                line(bottomRightX, bottomRightY, bottomRightX, bottomRightY + pipeOffset);
                break;
        }

    }

    public void markTile(Tile tile)
    {
        Location tLoc = tile.getLocation();
        int row = tLoc.getRow();
        int col = tLoc.getCol();

        int adjustedRow = (row * (boxWidth)) + margin;
        int adjustedCol = (col * boxWidth) + margin;

        int topLeftX = adjustedCol;
        int topLeftY = adjustedRow;

        ellipse(topLeftX+boxWidth/2, topLeftY+boxWidth/2, boxWidth/7, boxWidth/7);
    }

}