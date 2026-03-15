# TDDD78 – Object-Oriented Programming

My lab solutions for the TDDD78 course (Object-Oriented Programming) at Linköping University. The course introduces Java and OOP principles through a series of programming exercises, culminating in a complete Tetris game.

## Labs
The course consists of five labs, progressing from basic Java to a complete Tetris game:

- **Lab 1** – Introductory exercises to get comfortable with Java syntax, loops, and reading user input.
- **Lab 2** – Starting the Tetris project: enums, classes, and the first building blocks of the game.
- **Lab 3** – Working with inheritance and interfaces through a shape hierarchy, plus simple Swing graphics.
- **Lab 4** – Making Tetris actually work: timers, keyboard controls, and the Observer pattern.
- **Lab 5** – Extra features for higher grades: menus, resource handling, and a highscore system using Gson.

## Repository Structure
```
├── src/se/liu/simjo878/          # Main source code
│   ├── lab1/                      # Introductory exercises
│   ├── shapes/                     # Shape hierarchy (lab 3)
│   ├── calendar/                    # Calendar classes
│   └── tetris/                       # Complete Tetris implementation
│       ├── gui/                        # Graphical components
│       ├── highscore/                   # Highscore handling with Gson
│       ├── interaction/                  # Keyboard controls
│       └── fallhandlers/                 # Different falling behaviors
├── resources/                               # Images and other resources
└── libs/                                       # External libraries (Gson, etc.)
```

## How to Run
1. Open the project in IntelliJ IDEA.
2. Ensure the libraries in `libs/` are added as dependencies.
3. Run `TetrisLauncher.java` to start the game.

## About the Course
TDDD78 is an introductory object-oriented programming course for Computer Science students at LiU. The course covers Java, OOP principles, and GUI programming, with a focus on writing well-structured, maintainable code.

[Course page](https://www.ida.liu.se/~TDDD78/index.sv.shtml)  
[Lab instructions](https://www.ida.liu.se/~TDDD78/labs/2026/lab1/)
