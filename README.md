# Tom and Jerry maze game 

![tom-and-jerry-tom-and-jerry-wallpaper-preview](https://github.com/ilayc11/ATP-Project-PartB/assets/128902699/cce55ce8-2597-4f48-8d42-afa739a4bcb9)

### Intoduction
This Tom and Jerry inspired maze game was build as part of big project in ATP (Advanced Topic In Programming) course by Victor Gavrilenko and Ilay Cohen.
The game based on maze generation algorithms (such as Randomized Prim's algorithm), solving algorithms (such as BSF and DFS), client and server topics and finally GUI representation for the game that gathers all of the project parts together into one game.

### Server
In addition we bulid two servers, Each one of them have different Purpose. The first server supports on genertaing the maze. The second server supports on solving the maze with algorithm chosen by user ( default Best First Search). For supporting in parallel programming so multiple clients can contact with the serevers, we also used ThreadPool. User can change the pool size and the solving algorithm simply by the configuraion file. Also we created a compression algorithm for the maze to make communication between client-Server much faster with less space needed.

### GUI
The GUI in this project was build with JavaFX as a MVVM (Model-view-viewmodel) architecture.
The model use "splash screen" to create a loader before the game starts to give the user sense of knowlegde about what the game is going to be and some information about the game creator while the game loads to the screen.
Model is dealing with the business logic, the view layer with all the visual design GUI and the ViewModel is the connection part between them. This part of the project was more for our enrichment.

### Game
The game starts with splash screen that shows the loading process of the game.
The screen gives to the user first sense about what the game is going to be. Moreover, the screen shows some information about the game creators :

![PreLoader jpg](https://github.com/ilayc11/ATP-Project-PartB/assets/128902699/da1e8928-a6a4-4e3d-b577-da8063f09032)




