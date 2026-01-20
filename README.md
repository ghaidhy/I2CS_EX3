# Ex3 – Pac-Man

---

## Project Description
This project implements a complete solution for Exercise 3 – Pac-Man. The purpose of this assignment is to apply object-oriented programming principles, code reuse, and integration by designing and implementing an automatic Pac-Man algorithm together with a game engine.

The solution is divided into two main parts: a client side that controls the behavior of Pac-Man automatically, and a server side that manages the game logic, board, ghosts, collisions, scoring, and graphical display.

---

## Game Rules
Pac-Man moves on a grid-based board and at each step can move in one of four directions: UP, DOWN, LEFT, RIGHT.  
The objective of the game is to eat all the pink dots on the board.

Ghosts move on the board and may kill Pac-Man upon collision.  
When Pac-Man eats a green dot, the ghosts become eatable for a limited amount of time, allowing Pac-Man to collide with them safely.

---

## Pac-Man Algorithm (Ex3Algo)
The Pac-Man algorithm makes a decision at every step based on the current game state.

First, the algorithm reads the current position of Pac-Man, the positions of the ghosts, the structure of the board, and the current green-mode status.  
Next, it generates all legal moves from the current position, considering only moves that do not lead into walls.

For each legal move, the algorithm evaluates safety by calculating the distance to nearby ghosts. If the ghosts are not eatable, moves that bring Pac-Man too close to a ghost are considered dangerous and are avoided whenever possible. If all available moves are dangerous, the algorithm selects the move that maximizes the distance from the nearest ghost.

After the safety check, the algorithm selects a target. When the ghosts are eatable, the target is the nearest ghost. Otherwise, the target is the nearest pink dot on the board.  
A shortest path to the selected target is calculated using a grid-based shortest path search (BFS), and Pac-Man moves one step along this path.

To prevent Pac-Man from getting stuck in small loops or repeating the same positions, the algorithm detects repeated movement patterns and switches to a different target when necessary. This ensures full coverage of the board and steady progress toward completing the level.

---

## Correctness and Properties
The algorithm guarantees that Pac-Man always performs a legal move, avoids dangerous situations whenever possible, and eventually eats all pink dots on the board.  
The behavior adapts dynamically to changes in ghost positions and game state.

---

## Complexity Analysis
Let the board size be N x M.

Each BFS computation runs in O(N·M).  
A constant number of BFS operations are performed per step, making the solution efficient for the given game sizes.

---

## Testing and Results
The solution was tested using JUnit tests for the client-side algorithm.  
The algorithm was tested on Level 4, where Pac-Man successfully completes the level and wins the game.

---

## Running the Project
The project can be run in manual mode using keyboard controls or in automatic mode using the Ex3Algo implementation.  
Runnable JAR files are included in the repository as required.

