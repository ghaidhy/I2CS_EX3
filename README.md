# I2CS Ex3 – Pac-Man Project

## Overview
This project is part of the Introduction to Computer Science (I2CS) course.
The goal of Ex3 is to design and implement a complete Pac-Man game using
object-oriented programming principles, code reuse, and integration.

This README describes the **design of the Pac-Man algorithm** as required in
Stage 2 of the assignment.

---

## Pac-Man Algorithm – Design

### Goal
The main goal of the Pac-Man is to **eat all the pink dots** on the board while
**avoiding the ghosts**.  
When Pac-Man eats a green dot, the ghosts become *eatable* for a short period
of time, which changes the strategy of the algorithm.

---

### Board and Movement
- The game board is represented as a 2D grid.
- Pac-Man can move **one step at a time**.
- Allowed movement directions are:
    - UP
    - DOWN
    - LEFT
    - RIGHT
- Pac-Man cannot move through walls.

---

### Algorithm Logic

At each step of the game, Pac-Man makes a decision based on the current state
of the board:

#### 1. Green Dot Mode (Ghosts are eatable)
- If Pac-Man has recently eaten a green dot:
    - Ghosts become eatable for a few seconds.
    - Pac-Man moves **towards the nearest ghost**.
    - The goal is to eat as many ghosts as possible during this time.

#### 2. Danger Avoidance (Ghosts are close)
- If ghosts are not eatable:
    - The distance between Pac-Man and each ghost is calculated.
    - If a ghost is closer than a predefined safety distance:
        - Pac-Man moves in a direction that **increases the distance** from the
          nearest ghost.
    - This step has higher priority than eating pink dots.

#### 3. Eating Pink Dots (Safe Situation)
- If no ghost is close and no green dot is active:
    - Pac-Man searches for the **nearest pink dot**.
    - Pac-Man moves in the direction that minimizes the distance to that dot.
    - The goal is to clear the board as efficiently as possible.

---

### Decision Priority
The algorithm follows this priority order:
1. Eat ghosts when they are eatable (after green dot).
2. Escape from nearby ghosts.
3. Move towards the nearest pink dot.

---

### Algorithm Loop
This decision process is repeated continuously:
- At every game step, Pac-Man re-evaluates the board.
- A new movement decision is made based on updated positions of ghosts and dots.
- The algorithm runs until the game ends (win or loss).

---

## Summary
This algorithm balances:
- **Safety** (avoiding ghosts),
- **Opportunity** (eating ghosts when possible),
- **Efficiency** (clearing pink dots).

The design is simple, modular, and suitable for implementation using the
Map2D algorithms developed in Ex2.

