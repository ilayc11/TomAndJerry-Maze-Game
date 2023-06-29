# Tom and Jerry maze game 

Game creators : Victor Gavrilenko & Ilay Cohen

![tom-and-jerry-tom-and-jerry-wallpaper-preview](https://github.com/ilayc11/ATP-Project-PartB/assets/128902699/cce55ce8-2597-4f48-8d42-afa739a4bcb9)

### Intoduction
This Tom and Jerry inspired maze game was our idea for the ATP (Advanced Topic In Programming) Course.
The game is based on maze generation algorithms (such as Randomized Prim's algorithm), maze solving algorithms (such as BSF and DFS), client and server models and GUI representation for the game that combines all of the project parts together into one game.

### Server
We bulit two servers, Each one of them has different Purpose. The first server supports maze generation. The second server solvs the maze with an algorithm that is chosen by the user ( the default is Best First Search). To support parallel client connections we used Threads and Thread Pool. The User can change the pool size and the solving algorithm simply by changing the configuraion file. We also use a compression algorithm for the maze to make communication between Client-Server much faster with less space needed.

### GUI
The GUI in this project was built with JavaFX as a MVVM (Model-view-viewmodel) architecture.
The model use "splash screen" to create a loader before the game starts to give the user sense of knowlegde about what the game is going to be and some information about the game creators while the game loads to the screen.
The model is dealing with the business logic, the view layer with all the visual design GUI and the ViewModel is the connection part between them. This part of the project was more for our enrichment.

### Game
The game starts with splash screen that shows the loading process of the game.
The screen gives the user a sense about what the game is going to be. Moreover, the screen shows some information about the game creators :

![PreLoader jpg](https://github.com/ilayc11/ATP-Project-PartB/assets/128902699/da1e8928-a6a4-4e3d-b577-da8063f09032)

When the loader is finished, the home screen of the game is shown to the user with some videos of Tom and Jerry, a simple buttons to start the game and some helping bar menu on the top part of the screen :

![HomeScreen jpg](https://github.com/ilayc11/ATP-Project-PartB/assets/128902699/ca0d388c-1f79-428f-8666-b6814618f55f)

When the user decides to start a game, after choosing the maze size or just using the defult size, the game starts :

![GameScreen jpg](https://github.com/ilayc11/ATP-Project-PartB/assets/128902699/bc5e34bc-dda7-435f-9969-eb44019dfd68)

In case the user succeded to reach the right end corner (where the cheese is at), the win screen is shown to the user. The screen includes some information about the maze, users total score etc :

![WinScreen jpg](https://github.com/ilayc11/ATP-Project-PartB/assets/128902699/2f9cd2ad-ddea-423e-8299-2da30553a387)

In case the user wants to read some additional information about the game, he can always choose the "About" button :

![AboutScreen jpg](https://github.com/ilayc11/ATP-Project-PartB/assets/128902699/add672aa-74a2-43ec-8d6b-c34a5d77e212)

We hope you will have a greate time solving some mazes to help Jerry get his cheese ! :D





