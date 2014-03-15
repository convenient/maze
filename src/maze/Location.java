public class Location{

    private int row;
    private int col;
    private int depth;

    public Location(int row, int col, int depth) {
        this.row = row;
        this.col = col;
        this.depth = depth;
    }

    public int getRow()
    {
        return row;
    }

    public int getCol()
    {
        return col;
    }

    public int getDepth()
    {
        return depth;
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

    public String toString() {
        return getClass().getName() + "[row=" + row + ",col=" + col + ",depth=" + depth + "]";
    }

    public String getHashMapKey() {
        return getClass().getName() + "[row=" + row + ",col=" + col + ",depth=" + depth + "]";
    }
}
