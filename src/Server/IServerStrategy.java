package Server;
import java.io.InputStream;
import java.io.OutputStream;

public interface IServerStrategy {
    public void applyStrategy(InputStream inputStream, OutputStream outputStream);
}
