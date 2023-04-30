package IO;

import java.io.IOException;
import java.io.OutputStream;

public class SimpleCompressorOutputStream extends OutputStream {
    OutputStream out;
    public SimpleCompressorOutputStream(OutputStream out){
        this.out=out;
    }
    @Override
    public void write(int b) throws IOException {}

    @Override
    public void write(byte[]b)throws IOException{
        byte[]row = new byte[4];
        byte[]col = new byte[4];
        byte[]startPositionX = new byte[4];
        byte[]startPositionY = new byte[4];
        byte[]endPositionX = new byte[4];
        byte[]endPositionY = new byte[4];
        for (int i=0;i<4;i++)
            row[i]=b[i];
        out.write(row);
        for (int i=0;i<4;i++)
            col[i]=b[i+5];
        out.write(col);
        for (int i=0; i<4; i++)
            startPositionX[3-i] = b[b.length-16+i];
        out.write(startPositionX);
        for (int i=0; i<4; i++)
            startPositionY[3-i] = b[b.length-12+i];
        out.write(startPositionY);
        for (int i=0; i<4; i++)
            endPositionX[3-i] = b[b.length-8+i];
        out.write(endPositionX);
        for (int i=0; i<4; i++)
            endPositionY[3-i] = b[b.length-4+i];
        out.write(endPositionY);
        byte counter = 0;
        int index = 0;
        byte[] byteArray = new byte[2];
        for (int i=8; i<b.length-16; i++)
        {
            while (b[i] == b[index])
            {
                // Max Size of Byte
                if (Byte.toUnsignedInt(counter) == 255)
                {
                    byteArray[0] = b[i];
                    byteArray[1] = (byte) Byte.toUnsignedInt(counter);

                    // Send 2 Bytes --> The first is which number 1/0 and the second is how many times
                    out.write(byteArray);

                    if (index != b.length - 17)
                    {
                        if (b[index] == b[index+1])
                        {
                            byteArray[0] = (byte) Math.abs(b[index] - 1);       // if 0 we need to send 1 0-1=|-1|=1 and if 1 we need to send 0 1-1=|0|=0
                            byteArray[1] = 0;
                            out.write(byteArray);
                        }
                    }

                    counter = 0;
                    i = index;
                    break;
                }
                counter++;
                index++;
            }

            byteArray[0] = b[i];
            byteArray[1] = (byte) Byte.toUnsignedInt(counter);

            // Send 2 Bytes --> The first is which number 1/0 and the second is how many times
            out.write(byteArray);

            counter = 0;
            i = index;
        }

    }
}
