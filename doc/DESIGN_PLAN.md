# Simulation Design Plan
### Team Number 20 
### Names  Farzeen Najam, Erik Gregorio, Tyler Meier.

## Introduction
We are trying to make a simulation program that consists of 5 different simulations. The primary goal of the design
of our simulation will be to it be flexible enough to add more simulations. We will try to make most of the methods
private with getter methods to access the variables.

## Overview
We plan on orignally having three classes simulation, Visualization and Main class. simulation class has 5 subclasses
which are the 5 different simulations. We have a separate class for front end and then a main class that 
helps switching scene. An alternative design which we thought about is having a cell class and let the cells become
the main controlling force in the program, the cells and simulation class run side by side and get updated
based on xml file, however we thought that the second design might be a bit clumsy and trickier to implement.
We plan on making a 2d array which will be our basic structure. The simulator class will have abstract methods like
update,type etc which will be inherited by sub classes and implemented based on the xml file.
On the other hand our Alternative design makes use of lists of lists  and cells keep the track of rules.
Here is the picture of our basic implementation design in the doc folder.
Generally they will collaborate with each other by main function running the simulation which triggers simulation class to
read in the xml file and decide the current state of the cells and set up the environment and visualization class displays the 
front end of the program.

## User Interface
The user interface will be simple: There will be a main page that will have buttons to link to each simulation
each simulation will have a name and a back option to go back to main menu:
There will be several user popups in case something goes down in the simulation.
Heres a picture in the doc folder.

## Design Details

Design Details:
	Simulation Super Class:
		This class takes care of updating the cells and it’s an abstract class. Each simulation will have a subclass that is part of this superclass. Within these subclasses, there will be data structures to maintain each individual cell within the simulation. Additionally, there will be update methods to keep the next state of each cell current. This update method will make sure to distinguish between middle and edge cells. This class provides the current state of each cell to the visualization class and also reads in the XML file.
	Visualization Class:
		This class takes care of displaying the information going on in the simulation. It will acquire a cell’s state using getter methods located in the simulation class. Furthermore, it will have a global variable to be able to modify the FPS of the animation. This helps implement the slowdown, speed up features in the specification. It will also include a button to upload a new file and begin a new simulation.
	Main Class:
		This class sets up the main menu and creates a new visualization object to run. 

To apply the rules of Game of Life, the GameOfLife subclass would call its update method. Then, using indices, the update method can distinguish between the middle and edge cells and apply rules accordingly. 
To move on to the next generation, the subclass will call a method nextgen() which copies the next state data structure into the current state data structure.
To set a global parameter, the simulation subclass takes in a file name as its constructor and calls a method to read the XML file and properly assign values.
To load a new simulation, the user will click a button within the visualization screen which gives the user the option to input a new file from which to build the new simulation.

We choose to divide our project into these pieces because it keeps things simple. We have one a simulation class that takes care of all back end while visualization keeps track of displaying it. Using the setup, we can also add new simulation relatively easily since we would just create a new subclass for the simulation subclass.


## Design Considerations

We had a long discussion about creating a Cell class. In order to make this cell class much more active, we would allow it to update itself based on the state of its neighbors. This implementation would require abstraction in the Cell class so different simulations can have different cell objects. However, we decided against doing this because this would cause a more inactive Simulation class. Instead of being part of the project, it would serve more as a way to store cells.
An assumption our team is making is that the user will not want to load a different type of simulations from the one currently running.



#### Components

#### Use Cases


## Team Responsibilities

Erik: Visualization
Farzeen: Simulation Subclasses
Tyler: Simulation subclasses



