package IO;

import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
public class SimpleDecompressorInputStream extends InputStream {
    InputStream in;
    public SimpleDecompressorInputStream(InputStream in){
        this.in=in;
    }

    @Override
    public int read() throws IOException {
        byte[]data=null;
        int index = 0;
        int startX = 0;
        int startY = 0;
        int endX = 0;
        int endY = 0;
        int row = 0;
        int col = 0;

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
        }
        data = out.toByteArray();

        // Row data
        for (int i=0; i<4; i++)
        {
            row += data[i];
        }

        // Col data
        for (int i=4; i<8; i++)
        {
            col += data[i];
        }

        // Start Position X
        for (int i=0; i<4; i++)
        {
            startX += data[data.length-16+i];
        }

        // Start Position Y
        for (int i=0; i<4; i++)
        {
            startX += data[data.length-12+i];
        }

        // End Position X
        for (int i=0; i<4; i++)
        {
            endX += data[data.length-8+i];
        }

        // End Position Y
        for (int i=0; i<4; i++)
        {
            endY += data[data.length-4+i];
        }


        return 0;
    }
}
