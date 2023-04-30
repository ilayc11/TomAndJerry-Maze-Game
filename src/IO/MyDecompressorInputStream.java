package IO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

public class MyDecompressorInputStream extends InputStream {
    InputStream in;

    public MyDecompressorInputStream(InputStream i)
    {
        in = i;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }

    @Override
    public int read(byte[] answer) throws IOException {

        byte[] data = null;
        int index = 0;
        int startX = 0;
        int startY = 0;
        int endX = 0;
        int endY = 0;
        int row = 0;
        int col = 0;

        // Reads all the data
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
        }
        data = out.toByteArray();

        for (int i=4; i<8; i++)
        {
            col += Byte.toUnsignedInt(data[i]);
        }

        System.arraycopy(data, 0, answer, 0, 4);

        // Col data
        System.arraycopy(data, 4, answer, 4, 4);

        // Start Position X,Y and End Position X,Y
        for (int i=0; i<16; i++)
        {
            answer[answer.length-16+i] += Byte.toUnsignedInt(data[data.length-16+i]);
        }

        LinkedList<String> afterDec = new LinkedList<String>();
        int counter = 0;

        // How much elements to read for each row
        int elementsInRow = (int) Math.ceil((double)col / 8);

        // Number of digits in the end of each row
        int endRowDigits = col % 8;

        for (int i=8; i< data.length-16; i++)
        {

            byte curNum = data[i];

            // Last element in the row
            if (counter == elementsInRow-1)
            {
                if (endRowDigits != 0)
                {
                    // How many zeros added in the compression

                    String stringCurNum = String.format("%8s", Integer.toBinaryString(data[i] & 0xFF)).replace(' ', '0');

                    String stringToAdd = stringCurNum.substring(0, endRowDigits);

                    afterDec.add(stringToAdd);

                    counter = 0;
                }
                else
                {
                    String stringToAdd = String.format("%8s", Integer.toBinaryString(data[i] & 0xFF)).replace(' ', '0');

                    afterDec.add(stringToAdd);

                    counter = 0;
                }
            }
            else
            {
                String stringToAdd = String.format("%8s", Integer.toBinaryString(data[i] & 0xFF)).replace(' ', '0');

                afterDec.add(stringToAdd);

                counter++;
            }
        }


        StringBuilder stringAns = new StringBuilder();

        for (String s : afterDec) {
            stringAns.append(s);
        }

        String ans = stringAns.toString();

        for (int i=0; i<ans.length(); i++)
        {
            answer[8+i] = (byte) ans.charAt(i);
        }


        return -1;
    }
}
