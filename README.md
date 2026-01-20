 Ex3 – Pac-Man Game

This project is part of the I2CS course.  
In this assignment, I implemented a full Pac-Man game step by step, including the algorithm, server side, and GUI.



 Stage 3 – Pac-Man Algorithm

In this stage, I focused on building the logic that controls Pac-Man.

- I implemented the algorithm in the `Ex3Algo` class.
- The algorithm decides where Pac-Man should move in every step.
- Pac-Man tries to collect food while staying away from ghosts.
- I used distance calculations (BFS) to understand which paths are safe.
- When a ghost gets too close, Pac-Man switches to an escape behavior.

This stage was mainly about thinking algorithmically and making smart movement decisions.



Stage 4 – Server Implementation

In this stage, I implemented the server side of the game.

- The server is responsible for managing the game state.
- It keeps track of Pac-Man, ghosts, and the game board.
- The server connects the game logic with the Pac-Man algorithm.
- During the game loop, the server repeatedly calls the algorithm to get the next move.

The main goal of this stage was to separate the logic from the algorithm and manage the game flow correctly.

Main classes:
- `ServerMain.java`
- `MyPacmanGame.java`



 Stage 5 – GUI and Full Integration

In this stage, I completed the project by adding a graphical user interface.

- I implemented a GUI that shows the game board visually.
- The GUI displays Pac-Man, ghosts, and food in real time.
- The GUI is connected to the server and updates according to the game state.
- At this point, all parts of the project (algorithm, server, and GUI) work together as one system.

This stage focused on integration and making sure everything runs smoothly.



 How to Run

 The project can be run using the runnable JAR files  
  or
- By running the main classes directly from IntelliJ:
  - `Ex3Main`
  - `ServerMain`



Summary

This project helped me understand how to:
- Design algorithms
- Work with client-server architecture
- Use BFS for decision making
- Integrate logic, server, and GUI into a complete program
