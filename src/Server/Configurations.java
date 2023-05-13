package Server;

import algorithms.mazeGenerators.EmptyMazeGenerator;
import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;
import algorithms.search.BestFirstSearch;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.DepthFirstSearch;
import algorithms.search.ISearchingAlgorithm;

import java.io.*;
import java.util.Properties;

public class Configurations {
    private static Configurations instance;
    private  String searchAlgorithm;
    private  String numberOfThreads;
    private  String generatorAlgorithm;
    private  Properties prop;

    /**
     *  This configuration class initially will load the config file data to it fields and then will set
     *  values if requested to the config file while updating it fields accordingly.
     */
    private Configurations(){
        try {
            InputStream file = new FileInputStream(new File("resources/config.properties"));
            Properties props = new Properties();
            props.load(file);
            searchAlgorithm = props.getProperty("mazeSearchingAlgorithm");
            numberOfThreads = props.getProperty("threadPoolSize");
            generatorAlgorithm = props.getProperty("mazeGeneratingAlgorithm");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Configurations getInstance(){
        if(instance == null) instance = new Configurations();
        return instance;
    }

    public ISearchingAlgorithm getSearchAlgorithm(){
        if(searchAlgorithm.equals("BestFirstSearch")) return new BestFirstSearch();

        else if(searchAlgorithm.equals("BreadthFirstSearch")) return new BreadthFirstSearch();

        else return new DepthFirstSearch();


    }

    public int getNumberOfThreads(){
        return Integer.parseInt(numberOfThreads);
    }

    public IMazeGenerator getGeneratorAlgorithm(){
        if(generatorAlgorithm.equals("MyMazeGenerator")) return new MyMazeGenerator();

        else if(generatorAlgorithm.equals("SimpleMazeGenerator")) return new SimpleMazeGenerator();

        else return new EmptyMazeGenerator();
    }

    public void setSearchAlgorithm(String searchName){
        if( searchName != null) {
            prop.setProperty("mazeSearchingAlgorithm", searchName);
            searchAlgorithm = searchName;
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
            generatorAlgorithm = genName;
        }

    }
}