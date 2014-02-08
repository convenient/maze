void setup() {
        size(1200, 600);

        Location start = new Location();
        start.setRow(1);
        start.setCol(2);

        DepthFirstMaze maze = new DepthFirstMaze(10, 25, start);
        maze.generate();

        int boxWidth = 30;
        int margin = 20;
        int pipeOffset = 6;
        int lineWidth = 5;
        GridPainter p = new GridPainter(boxWidth, margin, pipeOffset, lineWidth);
        p.draw(maze);
}

void draw() {
}

void keyPressed() {
    switch(Character.toLowerCase(key)) {
        case 'm':
            println("HARRO");
            break;
    }
}

class GridPainter{

    private int boxWidth;
    private int margin;
    private int pipeOffset;

    GridPainter(int boxWidth, int margin, int pipeOffset, int lineWidth)
    {
        noFill();
        strokeCap(ROUND);
        strokeWeight(lineWidth);

        this.boxWidth = boxWidth;
        this.margin = margin;
        this.pipeOffset = pipeOffset;
    }

    public void draw(Grid g)
    {
        for (int row=0; row < g.grid.length; row++) {
            for (int col=0; col < g.grid[row].length; col++) {
                if (g.grid[row][col] != null) {
                    this.printTile(row, col, g.grid[row][col]);
                }
            }
        }
    }

    public void printTile(int row, int col, Tile tile)
    {
        int adjustedRow = (row * (boxWidth)) + margin;
        int adjustedCol = (col * boxWidth) + margin;

        //Take note that Columns are x axis, and rows are y axis!
        int topLeftX = adjustedCol;
        int topLeftY = adjustedRow;

        int topRightX = topLeftX + boxWidth;
        int topRightY = topLeftY;

        int bottomLeftX = topLeftX;
        int bottomLeftY = topLeftY+boxWidth;

        int bottomRightX = topRightX;
        int bottomRightY = topRightY + boxWidth;

        //Pipe offset
        topLeftX += pipeOffset;
        topLeftY += pipeOffset;

        bottomLeftX += pipeOffset;
        bottomLeftY -= pipeOffset;

        topRightX-=pipeOffset;
        topRightY+=pipeOffset;

        bottomRightX-=pipeOffset;
        bottomRightY-=pipeOffset;

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