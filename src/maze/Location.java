public class Location{

    private int row;
    private int col;

    public Location(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow()
    {
        return row;
    }

    public int getCol()
    {
        return col;
    }

    public void setRow(int row)
    {
        this.row = row;
    }

    public void setCol(int col)
    {
        this.col = col;
    }

    public Location clone()
    {
        return new Location(getRow(), getCol());
    }

    public String toString() {
        return getClass().getName() + "[row=" + row + ",col=" + col + "]";
    }

    public String getHashMapKey() {
        return getClass().getName() + "[row=" + row + ",col=" + col + "]";
    }
}
