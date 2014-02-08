GridContainer container = new GridContainer();

void setup() {
        size(1200, 600);

        Helper.printInstructions();

        Location start = new Location();
        start.setRow(1);
        start.setCol(2);

        container.setMaze(new DepthFirstMaze(10, 25, start));

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
        case 'l':
            container.pipeOffset++;
            redraw = true;
            break;
        case 'k':
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
        System.out.println("Pipe Offset:\tK/L");
        System.out.println("Line Width:\tN/M");

        System.out.println();
        System.out.println("Generate Maze:\tG");

    }
}

class GridContainer {
    public DepthFirstMaze maze;
    public int boxWidth;
    public int margin;
    public int pipeOffset;
    public int lineWidth;

    GridContainer() {
        this.boxWidth = 30;
        this.margin = 20;
        this.pipeOffset = 6;
        this.lineWidth = 5;
    }

    public void setMaze(DepthFirstMaze maze) {
        this.maze = maze;
    }

    public void generate() {
        maze.generate();
    }

    public void draw() {
        background(255);
        noFill();
        strokeCap(ROUND);
        strokeWeight(lineWidth);

        for (int row = 0; row < maze.maze.length; row++) {
            for (int col = 0; col < maze.maze[row].length; col++) {
                if (maze.maze[row][col] != null) {
                    this.printTile(row, col, maze.maze[row][col]);
                }
            }
        }
    }

    public void printTile(int row, int col, Tile tile) {
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

}