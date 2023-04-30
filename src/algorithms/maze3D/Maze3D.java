package algorithms.maze3D;

import java.util.ArrayList;

public class Maze3D {
    /**
     * This class represents the Maze itself.
     * The Maze build of 3d array of positions. Each
     * position can be either path or wall ( if the position
     * is wall then let it be True)
     * The Maze have starting and ending position.
     */
    private Position3D[][][]Grid;
    private Position3D[][][]Grid2;
    private  Position3D startPos;
    private  Position3D endPos;

    /**
     * @param depthnum depth num
     * @param rowNum row num
     * @param colNum col num
     */
    public Maze3D(int depthnum,int rowNum,int colNum){
        Grid=new Position3D[depthnum][rowNum][colNum];
        for(int i=0;i<depthnum;i++){
            for(int j=0;j<rowNum;j++){
                for(int k=0;k<colNum;k++) {
                    Grid[i][j][k] = new Position3D(i, j, k);

                }
            }
        }

        this.endPos=new Position3D(0,rowNum-1,colNum-1);
        this.startPos=new Position3D(0,0,0);
        this.endPos.setIsWall(false);
        this.startPos.setIsWall(false);

    }

    /**
     * @return return 3d array of walls and passages as int[][][]
     */
    public int[][][] getMap(){
        int tmpDepth=getDepth(),tmpRow=this.getRow(),tmpCol=this.getCol();
        int[][][] tmp =new int[tmpDepth][tmpRow][tmpCol];
        for(int k=0;k<tmpDepth;k++)
             for(int i=0;i<tmpRow;i++)
                 for(int j=0;j<tmpCol;j++) {
                     if (!Grid[k][i][j].isWall())
                         tmp[k][i][j] = 0;
                     else
                         tmp[k][i][j] = 1;
                 }
        return tmp;

    }
    public int getCol(){
        return this.Grid[0][0].length;
    }
    public int getRow(){
        return this.Grid[0].length;
    }
    public int getDepth(){
        return this.Grid.length;
    }
    public Position3D getStartPosition(){
        return startPos;
    }
    public Position3D getGoalPosition(){
        return endPos;
    }

    public void setStartPosition(Position3D pos){
        this.startPos=new Position3D(pos.getDepthIndex(),pos.getRowIndex(), pos.getColumnIndex());
        this.startPos.setIsWall(false);
    }
    public void setGoalPosition(Position3D pos){
        this.endPos=new Position3D(pos.getDepthIndex(),pos.getRowIndex(), pos.getColumnIndex());
        this.endPos.setIsWall(false);
    }
    public boolean isWall(Position3D pos){
        return Grid[pos.getDepthIndex()][pos.getRowIndex()][pos.getColumnIndex()].isWall();
    }
    public void setWallState(Position3D pos,boolean state){
        Grid[pos.getDepthIndex()][pos.getRowIndex()][pos.getColumnIndex()].setIsWall(state);
    }
    public void setPositionState(Position3D pos){
        boolean is1=pos.isVisited();
        boolean is2=pos.isWall();
        Grid[pos.getDepthIndex()][pos.getRowIndex()][pos.getColumnIndex()].setIsWallAndIsVisited(is1,is2);
    }
    public Position3D[][][] getGrid(){return this.Grid;}

    /**
     * prints the maze
     */
    public void print() {
        for (int k = 0; k < Grid.length; k++) {
            System.out.print("[ ");
            System.out.println();
            for (int i = 0; i < Grid.length; i++) {
                System.out.print("[ ");
                for (int j = 0; j < Grid[0].length; j++)
                    if (k==startPos.getDepthIndex()&&i == startPos.getRowIndex() && j == startPos.getColumnIndex())
                        System.out.print("S, ");
                    else if (k==endPos.getDepthIndex()&&i == endPos.getRowIndex() && j == endPos.getColumnIndex())
                        System.out.print("E ");
                    else if (Grid[k][i][j].isWall())
                        if (j != Grid[0].length - 1)
                            System.out.print("1, ");
                        else
                            System.out.print("1 ");

                    else if (j != Grid[0].length - 1)
                        System.out.print("0, ");
                    else
                        System.out.print("0 ");
                System.out.print("]");
                System.out.println();
            }
            System.out.print(" ]");
            System.out.println();
        }
    }

    /**
     * our maze starts with walls only, we added a function for breaking them all
     */
    public void breakAllWalls(){
        for(int k=0;k<this.getDepth();k++)
         for(int i=0;i<this.getRow();i++)
            for(int j=0;j<getCol();j++)
                Grid[k][i][j].setIsWall(false);
    }

}
