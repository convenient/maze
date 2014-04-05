import java.awt.*;
import java.util.*;

Container container = new Container();
void setup() {
        size(800, 400);
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
            if (container.lineWidth <= 0) {
                container.lineWidth = 1;
            }
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

class Container {

    MazeContainer mazeContainer;
    public int boxWidth;
    public int margin;
    public int pipeOffset;
    public int lineWidth;

    Container() {
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
        Polygon boundary = new Polygon();
        boundary.addPoint(0, 0);
        boundary.addPoint(0, 30);
        boundary.addPoint(13, 35);
        boundary.addPoint(13, -5);

        boundary.translate(0, 5);

        mazeContainer = new MazeContainer(boundary);

        this.margin = 50;
        this.boxWidth = 15;
        this.pipeOffset = 0;
        this.lineWidth = 1;

//        for (int i = 0; i <941; i++) {
//            mazeContainer.generate();
//        }
//        System.out.println("\n\n\n\n\n\n\n\n\n");

    }

    public void generate()
    {
        mazeContainer.generate();
    }

    public void draw() {
        background(255);
        strokeCap(ROUND);
        strokeWeight(lineWidth);

        for (Map.Entry entry : mazeContainer.maze.map.entrySet()) {
            Tile t = (Tile)entry.getValue();
            printTile(t);
        }

//        markTile(mazeContainer.maze.getTile(mazeContainer.entrance));
//        markTile(mazeContainer.maze.getTile(mazeContainer.exit));

    }

    public void printTile(Tile tile)
    {
        Location tLoc = tile.getLocation();
        int row = tLoc.getRow();
        int col = tLoc.getCol();

        //Adjust the row and column indexes so that we have getId margin around the screen, as well as multiplying for
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

        switch (tile.getUp()) {
            case Tile.PATH_NONE:
                line(topLeftX, topLeftY, topRightX, topRightY);
                break;
            case Tile.PATH_FLAT:
                line(topLeftX, topLeftY, topLeftX, topLeftY - pipeOffset);
                line(topRightX, topRightY, topRightX, topRightY - pipeOffset);
                break;
        }

        switch (tile.getRight()) {
            case Tile.PATH_NONE:
                line(topRightX, topRightY, bottomRightX, bottomRightY);
                break;
            case Tile.PATH_FLAT:
                line(topRightX, topRightY, topRightX + pipeOffset, topRightY);
                line(bottomRightX, bottomRightY, bottomRightX + pipeOffset, bottomRightY);
                break;
        }

        switch (tile.getLeft()) {
            case Tile.PATH_NONE:
                line(topLeftX, topLeftY, bottomLeftX, bottomLeftY);
                break;
            case Tile.PATH_FLAT:
                line(topLeftX, topLeftY, topLeftX - pipeOffset, topLeftY);
                line(bottomLeftX, bottomLeftY, bottomLeftX - pipeOffset, bottomRightY);
                break;
        }

        switch (tile.getDown()) {
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