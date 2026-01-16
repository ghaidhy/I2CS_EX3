# Ex3 – Pac-Man Algorithm (Client Side)

## General Description
This project implements an automatic Pac-Man algorithm as part of Ex3 in the course
"Introduction to Computer Science (I2CS)".

The goal of the algorithm is to control Pac-Man automatically in order to eat all the
pink dots on the board while avoiding ghosts and taking advantage of green (power) dots.

---

## Movement Rules
At every step, Pac-Man can move in one of the four directions:
UP, DOWN, LEFT, or RIGHT.

The algorithm decides the next move according to the current game state.

---

## Algorithm Strategy

The decision-making process of Pac-Man is based on the following priorities:

### 1. Eatable Ghosts
If one or more ghosts are in an *eatable* state (after Pac-Man eats a green dot),
Pac-Man moves toward the **closest eatable ghost** in order to gain extra points.

### 2. Avoiding Ghosts
If the ghosts are not eatable, Pac-Man avoids dangerous areas around them.
Cells close to ghosts are considered unsafe and are avoided when possible.

### 3. Green Dots (Power Pills)
When ghosts are not eatable, Pac-Man prefers moving toward a **green dot**
if it is reachable safely.
Eating a green dot allows Pac-Man to eat ghosts for a limited time.

### 4. Pink Dots
If no green dot is nearby or safe, Pac-Man moves toward the **nearest pink dot**
using a shortest-path (BFS) search.

### 5. Fallback Move
If no safe path is found, Pac-Man chooses a legal move that maximizes the distance
from the nearest ghost.

---

## Path Finding
The algorithm uses a **Breadth-First Search (BFS)** approach on the game board in order to:
- Find the shortest path to a target (ghost, green dot, or pink dot)
- Avoid walls and dangerous cells
- Decide the first direction to move in each step

---

## Summary
This algorithm enables Pac-Man to:
- Move automatically without user input
- Eat all pink dots efficiently
- Avoid ghosts when they are dangerous
- Chase ghosts when they are eatable
- Successfully complete advanced game levels (including Level 4)

---

## Author
Student ID: 214756645
