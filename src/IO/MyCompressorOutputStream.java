package IO;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.LinkedList;

public class MyCompressorOutputStream extends OutputStream {
    public OutputStream out;
    public MyCompressorOutputStream(OutputStream out){
        this.out=out;
    }
    @Override
    public void write(int b) throws IOException {

    }
    @Override
    public void write(byte[]b)throws IOException{
        byte[] row = new byte[4];
        byte[] col = new byte[4];
        byte[] startPositionX = new byte[4];
        byte[] startPositionY = new byte[4];
        byte[] endPositionX = new byte[4];
        byte[] endPositionY = new byte[4];


        System.arraycopy(b, 0, row, 0, 4);

        System.arraycopy(b, 4, col, 0, 4);

        for (int i=0; i<4; i++)
        {
            startPositionX[3-i] = b[b.length-16+i];
        }

        for (int i=0; i<4; i++)
        {
            startPositionY[3-i] = b[b.length-12+i];
        }

        for (int i=0; i<4; i++)
        {
            endPositionX[3-i] = b[b.length-8+i];
        }

        for (int i=0; i<4; i++)
        {
            endPositionY[3-i] = b[b.length-4+i];
        }


        int rowNum = 0;
        int colNum = 0;

        for (int i=0; i<4; i++)
        {
            rowNum += Byte.toUnsignedInt(row[i]);
        }

        // Col data
        for (int i=0; i<4; i++)
        {
            colNum += Byte.toUnsignedInt(col[i]);
        }

        LinkedList<Byte> byteArr = new LinkedList<Byte>();

        int index = 8;
        byte[] tempArr = new byte[8];

        for (int i=0; i<rowNum; i++)
        {
            for (int j=0; j<colNum; j=j+8)
            {
                if (index >= b.length - 16)
                    break;
                Arrays.fill(tempArr, (byte)0);
                if (j + 7 >= colNum || index + 7 >= rowNum*colNum+8)
                {

                    int numOfBits;

                    if (colNum%8 == 0)
                        numOfBits = 8;
                    else {
                        numOfBits = colNum % 8;
                    }
                    // Read the leftovers bits and then add untill its 8 bits
                    System.arraycopy(b, index, tempArr, 0, numOfBits);
                    StringBuilder bString = new StringBuilder();
                    for (byte value : tempArr) {
                        bString.append(value);
                    }
                    String cur = bString.toString();
                    byteArr.add((byte) Byte.toUnsignedInt((byte) Integer.parseInt(cur, 2)));
                    index += numOfBits;
                    break;
                }
                else
                {
                    System.arraycopy(b, index, tempArr, 0, 8);
                    StringBuilder bString = new StringBuilder();
                    for (byte value : tempArr) {
                        bString.append(value);
                    }
                    String cur = bString.toString();
                    byteArr.add((byte) Byte.toUnsignedInt((byte) Integer.parseInt(cur, 2)));
                    index += 8;
                }
            }
        }

        LinkedList<Byte> writeResArray = new LinkedList<Byte>(byteArr);


        byte[] xx = new byte[writeResArray.size() + 24];

        int j=8;
        for(Byte by: writeResArray)
            xx[j++] = by;



        System.arraycopy(row, 0, xx, 0, 4);

        System.arraycopy(col, 0, xx, 4, 4);

        System.arraycopy(startPositionX, 0, xx, xx.length - 16, 4);

        System.arraycopy(startPositionY, 0, xx, xx.length - 12, 4);

        System.arraycopy(endPositionX, 0, xx, xx.length - 8, 4);

        System.arraycopy(endPositionY, 0, xx, xx.length - 4, 4);

        out.write(xx);

    }

}
