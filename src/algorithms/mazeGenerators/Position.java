package algorithms.mazeGenerators;

public class Position {
    private int currRow, currCol;
    private boolean isWall,isVisited;

    public Position(int currRow, int currCol) {
        this.currRow = currRow;
        this.currCol = currCol;
        this.isWall=true;
        this.isVisited=false;
    }
    public void setPosition(int currRow, int currCol) {
        this.currRow = currRow;
        this.currCol = currCol;
    }
    public int getRowIndex(){
        return this.currRow;
    }
    public int getColumnIndex(){
        return this.currCol;
    }
    public boolean isWall(){
        return this.isWall;
    }
    public boolean isVisited(){
        return this.isVisited;
    }
    public void setIsVisited(boolean is){
        this.isVisited=is;
    }
    public void setIsWall(boolean is){
        this.isWall=is;
    }
    public void setIsWallAndIsVisited(boolean wall,boolean visited){
        this.isVisited=visited;
        this.isWall=wall;
    }
    @Override
    public String toString() {
        return "{" + currRow + "," + currCol + "}";
    }
}
