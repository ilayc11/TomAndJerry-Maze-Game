package algorithms.maze3D;

public class Position3D {
    /**
     * This class represents position in the Maze.
     * Each position has its row number and column number (indexes).
     * Each position can be either a wall or path, and every
     * position will be marked after visiting it during the solvation
     * of the Maze.
     */
    private int currRow, currCol,currDepth;
    private boolean isWall,isVisited;

    public Position3D(int currDepth,int currRow, int currCol) {
        /**
         * init a new position as a Wall for easier faster maze creation and not visited for later use.
         */
        this.currRow = currRow;
        this.currCol = currCol;
        this.currDepth=currDepth;
        this.isWall=true;
        this.isVisited=false;
    }
    public void setPosition(int currDepth,int currRow, int currCol) {
        this.currRow = currRow;
        this.currCol = currCol;
        this.currDepth=currDepth;
    }
    public int getRowIndex(){
        return this.currRow;
    }
    public int getColumnIndex(){
        return this.currCol;
    }
    public int getDepthIndex(){return this.currDepth;}
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
        return "{" +this.currDepth +","+ currRow + "," + currCol + "}";
    }
}
