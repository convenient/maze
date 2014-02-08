public class Location{

    public int row;
    public int col;

    public Location() {
    }

    public Location(int row, int col) {
        this.row = row;
        this.col = col;
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

    public String toString() {
        return getClass().getName() + "[row=" + row + ",col=" + col + "]";
    }

    public void set(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
