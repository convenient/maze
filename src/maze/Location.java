public class Location{

    public int row;
    public int col;
    public int depth;

    public Location() {
    }

    public Location(int row, int col, int depth) {
        this.row = row;
        this.col = col;
        this.depth = depth;
    }

    public int getRow()
    {
        return this.row;
    }

    public int getCol()
    {
        return this.col;
    }

    public void setRow(int row)
    {
        this.row = row;
    }

    public void setCol(int col)
    {
        this.col = col;
    }

    public void setDepth(int depth)
    {
        this.depth = depth;
    }

    public int getDepth()
    {
        return this.depth;
    }

    public String toString() {
        return getClass().getName() + "[row=" + row + ",col=" + col + ",depth=" + depth + "]";
    }

    public String getHashMapKey() {
        return getClass().getName() + "[row=" + row + ",col=" + col + ",depth=" + depth + "]";
    }

    public void set(int row, int col, int depth) {
        this.row = row;
        this.col = col;
        this.depth = depth;
    }
}
