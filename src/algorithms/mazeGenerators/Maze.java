package algorithms.mazeGenerators;

public class Maze {

    private Position[][]Grid;
    private Position startPos;
    private Position endPos;
    public Maze(int rowNum,int colNum){
        Grid=new Position[rowNum][colNum];
        for(int i=0;i<rowNum;i++){
            for(int j=0;j<colNum;j++){
                Grid[i][j]=new Position(i,j);
            }
        }
    }
    public int getCol(){
        return this.Grid[0].length;
    }
    public int getRow(){
        return this.Grid.length;
    }
    public Position getStartPosition(){
        return startPos;
    }
    public Position getGoalPosition(){
        return endPos;
    }

    public void setStartPosition(Position pos){
        this.startPos=new Position(pos.getRowIndex(), pos.getColumnIndex());
        this.startPos.setIsWall(false);
    }
    public void setGoalPosition(Position pos){
        this.endPos=new Position(pos.getRowIndex(), pos.getColumnIndex());
        this.endPos.setIsWall(false);
    }
    public boolean isWall(Position pos){
        return Grid[pos.getRowIndex()][pos.getColumnIndex()].isWall();
    }
    public void setWallState(Position pos,boolean state){
        Grid[pos.getRowIndex()][pos.getColumnIndex()].setIsWall(state);
    }
    public void setPositionState(Position pos){
        boolean is1=pos.isVisited();
        boolean is2=pos.isWall();
        Grid[pos.getRowIndex()][pos.getColumnIndex()].setIsWallAndIsVisited(is1,is2);
    }
    public Position[][] getGrid(){return this.Grid;}
    public void print(){
        for (int i = 0; i < Grid.length; i++) {
            System.out.print("{ ");
            for (int j = 0; j < Grid[0].length; j++)
                if (i == startPos.getRowIndex() && j == startPos.getColumnIndex())
                    System.out.print("S, ");
                else if(i==endPos.getRowIndex() && j== endPos.getColumnIndex())
                    System.out.print("E, ");
                else
                    if(Grid[i][j].isWall())
                        System.out.print("1, ");
                    else
                        System.out.print("0, ");
            System.out.print("}");
            System.out.println();
        }
    }

}
