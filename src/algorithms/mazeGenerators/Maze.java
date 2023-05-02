package algorithms.mazeGenerators;

import java.io.Serializable;

public class Maze implements Serializable {
    /**
     * This class represents the Maze itself.
     * The Maze build of 2D array of positions. Each
     * position can be either path or wall ( if the position
     * is wall then let it be True)
     * The Maze have starting and ending position.
     */
    private Position[][]Grid;
    private  Position startPos;
    private  Position endPos;
    int row,col;
    public Maze(int rowNum,int colNum){
        this.row=rowNum;
        this.col=colNum;
        Grid=new Position[rowNum][colNum];
        for(int i=0;i<rowNum;i++){
            for(int j=0;j<colNum;j++){
                Grid[i][j]=new Position(i,j);
            }
        }
        this.endPos=new Position(rowNum-1,colNum-1);
        this.startPos=new Position(0,0);
        this.endPos.setIsWall(false);
        this.startPos.setIsWall(false);

    }
    public Maze(byte[]byteArray){
        int startX = 0;
        int startY = 0;
        int endX = 0;
        int endY = 0;

        // Row data
        for (int i=0; i<4; i++)
        {
            row += Byte.toUnsignedInt(byteArray[i]);
        }

        // Col data
        for (int i=4; i<8; i++)
        {
            col += Byte.toUnsignedInt(byteArray[i]);
        }

        // Start Position X
        for (int i=0; i<4; i++)
        {
            startX += Byte.toUnsignedInt(byteArray[byteArray.length-16+i]);
        }

        // Start Position Y
        for (int i=0; i<4; i++)
        {
            startY += Byte.toUnsignedInt(byteArray[byteArray.length-12+i]);
        }

        // End Position X
        for (int i=0; i<4; i++)
        {
            endX += Byte.toUnsignedInt(byteArray[byteArray.length-8+i]);;
        }

        // End Position Y
        for (int i=0; i<4; i++)
        {
            endY += Byte.toUnsignedInt(byteArray[byteArray.length-4+i]);
        }


        int index = 8;

        this.Grid = new Position[row][col];
        for( int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                Grid[i][j] = new Position(i,j);

                // If byte is 0 so its Not a wall
                Grid[i][j].setIsWall(byteArray[index++] - 48 != 0);
            }
        }

        this.setStartPosition(new Position(startX, startY));
        this.setGoalPosition(new Position(endX, endY));
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
            System.out.print("[ ");
            for (int j = 0; j < Grid[0].length; j++)
                if (i == startPos.getRowIndex() && j == startPos.getColumnIndex())
                    System.out.print("S, ");
                else if(i==endPos.getRowIndex() && j== endPos.getColumnIndex())
                    System.out.print("E ");
                else
                    if(Grid[i][j].isWall())
                        if(j!=Grid[0].length-1)
                            System.out.print("1, ");
                        else
                            System.out.print("1 ");

                    else
                        if(j!=Grid[0].length-1)
                            System.out.print("0, ");
                        else
                            System.out.print("0 ");
            System.out.print("]");
            System.out.println();
        }
    }
    public byte[] toByteArray(){
        byte[] rowOut = new byte[4];
        byte[] colOut = new byte[4];
        byte[] startPositionX = new byte[4];
        byte[] startPositionY = new byte[4];
        byte[] endPositionX = new byte[4];
        byte[] endPositionY = new byte[4];

        byte[] ans = new byte[col*row + 24];

        int temp = row;
        for (int i=0; i<4; i++)
        {
            if (temp > 255)
            {
                rowOut[i] = (byte) 255;
                temp -= 255;
            }
            else
            {
                rowOut[i] = (byte) temp;
                break;
            }
        }

        temp = col;
        for (int i=0; i<4; i++)
        {
            if (temp > 255)
            {
                colOut[i] = (byte) 255;
                temp -= 255;
            }
            else
            {
                colOut[i] = (byte) temp;
                break;
            }
        }


        temp = startPos.getRowIndex();
        for (int i=0; i<4; i++)
        {
            if (temp > 255)
            {
                startPositionX[i] = (byte) 255;
                temp -= 255;
            }
            else
            {
                startPositionX[i] = (byte) temp;
                break;
            }
        }

        temp = startPos.getColumnIndex();
        for (int i=0; i<4; i++)
        {
            if (temp > 255)
            {
                startPositionY[i] = (byte) 255;
                temp -= 255;
            }
            else
            {
                startPositionY[i] = (byte) temp;
                break;
            }
        }

        temp = endPos.getRowIndex();
        for (int i=0; i<4; i++)
        {
            if (temp > 255)
            {
                endPositionX[i] = (byte) 255;
                temp -= 255;
            }
            else
            {
                endPositionX[i] = (byte) temp;
                break;
            }
        }

        temp = endPos.getColumnIndex();
        for (int i=0; i<4; i++)
        {
            if (temp > 255)
            {
                endPositionY[i] = (byte) 255;
                temp -= 255;
            }
            else
            {
                endPositionY[i] = (byte) temp;
                break;
            }
        }

        // Copy to answer row array
        System.arraycopy(rowOut, 0, ans, 0, 4);

        // Copy to answer col array
        System.arraycopy(colOut, 0, ans, 4, 4);

        int index = 8;
        for (int i=0; i<row; i++)
        {
            for (int j=0; j<col; j++)
            {
                if (Grid[i][j].isWall())
                    ans[index] = 1;
                else
                    ans[index] = 0;

                index++;
            }
        }

        // Copy startPositionX to ans
        System.arraycopy(startPositionX, 0, ans, ans.length - 16, 4);

        // Copy startPositionY to ans
        System.arraycopy(startPositionY, 0, ans, ans.length - 12, 4);

        // Copy endPositionX to ans
        System.arraycopy(endPositionX, 0, ans, ans.length - 8, 4);

        // Copy endPositionY to ans
        System.arraycopy(endPositionY, 0, ans, ans.length - 4, 4);

        return ans;
    }
    public void breakAllWalls(){
        for(int i=0;i<this.getRow();i++){
            for(int j=0;j<getCol();j++){
                Grid[i][j].setIsWall(false);
            }
        }
    }

}
