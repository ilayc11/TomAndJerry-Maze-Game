# Tom and Jerry maze game 

Game creators : Victor Gavrilenko & Ilay Cohen

![tom-jerry-mouse-maze](https://github.com/VictorGavrilenko97/TomAndJerry-Maze-Game/assets/140259266/61b0f5e0-e9b1-4aa6-b9c3-e8921d8fafd3)


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

![PreLoader jpg](https://github.com/VictorGavrilenko97/TomAndJerry-Maze-Game/assets/140259266/45183d6f-7a35-489a-9786-564c785839db)

When the loader is finished, the home screen of the game is shown to the user with some videos of Tom and Jerry, a simple buttons to start the game and some helping bar menu on the top part of the screen :

![HomeScreen jpg](https://github.com/VictorGavrilenko97/TomAndJerry-Maze-Game/assets/140259266/e8fe9e42-b8f3-48eb-8aca-a5a78a144133)

When the user decides to start a game, after choosing the maze size or just using the defult size, the game starts :

![GameScreen jpg](https://github.com/VictorGavrilenko97/TomAndJerry-Maze-Game/assets/140259266/020eccb7-8c30-4f8f-b78e-c284c023913c)

In case the user succeded to reach the right end corner (where the cheese is at), the win screen is shown to the user. The screen includes some information about the maze, users total score etc :

![WinScreen jpg](https://github.com/VictorGavrilenko97/TomAndJerry-Maze-Game/assets/140259266/864bc1ef-6d8b-463b-846a-8d64d7f8f062)

In case the user wants to read some additional information about the game, he can always choose the "About" button :

![AboutScreen jpg](https://github.com/VictorGavrilenko97/TomAndJerry-Maze-Game/assets/140259266/0e9b8548-c44d-48e7-9bab-097a708ad03d)

We hope you will have a greate time solving some mazes to help Jerry get his cheese ! :D





