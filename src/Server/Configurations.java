package Server;
import algorithms.mazeGenerators.*;
import algorithms.mazeGenerators.SimpleMazeGenerator;
import algorithms.search.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Configurations implements Serializable {
    private static Configurations instance;
    private String searchAlgorithmName, numberOfThreads, mazeGeneratorName;
    private Properties prop;

    private Configurations(){
        try (InputStream in = Files.newInputStream(Paths.get("resources/config.properties"))){
            prop = new Properties();
            // load a properties file
            prop.load(in);
            this.searchAlgorithmName = prop.getProperty("mazeSearchingAlgorithm");
            this.numberOfThreads = prop.getProperty("threadPoolSize");
            this.mazeGeneratorName = prop.getProperty("mazeGeneratingAlgorithm");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * static method to implement Singleton design pattern that retrieves
     * instance of Configurations
     * @return Configurations instance
     */
    public static Configurations getInstance(){
        if(instance == null) {
            instance = new Configurations();
        }
        return instance;
    }

    // getters :

    public ISearchingAlgorithm getSearchingAlgorithm(){
        if(this.searchAlgorithmName.equals("BestFirstSearch"))
            return new BestFirstSearch();
        else if (this.searchAlgorithmName.equals("BreadthFirstSearch"))
            return new BreadthFirstSearch();
        else if (this.searchAlgorithmName.equals("DepthFirstSearch"))
            return new DepthFirstSearch();
        else
            return null;
    }

    public IMazeGenerator getMazeGenerator(){
        if(this.mazeGeneratorName.equals("MyMazeGenerator"))
            return new MyMazeGenerator();
        else if(this.mazeGeneratorName.equals("SimpleMazeGenerator"))
            return new SimpleMazeGenerator();
        else
            return new EmptyMazeGenerator();
    }

    public int getNumberOfThreads(){return Integer.parseInt(numberOfThreads);}

    // setters :
    public void setSearchAlgorithm(String searchName){
        if( searchName != null) {
            prop.setProperty("mazeSearchingAlgorithm", searchName);
            searchAlgorithmName = searchName;
        }
    }

    public void setNumberOfThreads( int num){
        if( num >= 0){
            String numStr = Integer.toString(num);
            prop.setProperty("threadPoolSize", numStr);
            numberOfThreads = numStr;
        }
    }

    public void setGeneratorAlgorithm(String genName){
        if(genName != null){
            prop.setProperty("mazeGeneratingAlgorithm",genName);
            mazeGeneratorName = genName;
        }
    }
}
