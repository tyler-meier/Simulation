simulation
====

This project implements a cellular automata simulator.

Names: \
    Erik Gregorio (eg158)
    Farzeen Najam fn26

### Timeline

Start Date: January 23rd

Finish Date: February 9th

Hours Spent: \
Erik: 15 hrs

Hours Spent: 42+ hours 

### Primary Roles
Erik: Configurations and debugging 
Farzeen: Simulations Backup.

### Resources Used
Erik: TutorialsPoint, Stackoverflow
No resources used apart from TA help

### Running the Program

Main class: Main

Data files needed: There is no files necessary, but the data folder holds some
sample files to run. It also hold two random files (segregation and percolation
random) to show that our simulation can create a random grid. We also included
a Segration file with invalid states and no rows/column values to show our
error catching. We used default values to make sure our files do not crash. 

Features implemented:
Configurations: The XML parser is able to take in XML file and read it to 
set up a simulation. If the XML file is empty or not the right type, the user
is notified via an Error message. The parser also handles no values/invalid values
given by having default parameters. Additionally, the user can specify a random
generated grid inside the XML file to have the parser create a random grid. 
There is also a save feature which takes in a currently running simulation
and saves it into a file and stores it where the user wants.

Simulation classes: 6 different simulations that are in different classes.
Each simulation has grids of the cells from cell class. There is an abstract class simulation
which has some methods implemented in every class. There are hexagonal neighbourhood
4 neighbourhood and 8 neighbourhood. There is also a toroidal grid which has 4 neighbourhood
and 8 neighbourhood. 

### Notes/Assumptions

Assumptions or Simplifications:

Interesting data files:

Known Bugs: 

Extra credit:


### Impressions
Erik: I took sometime to get used to working with an XML file. However, once
I got used to it, I realized why this file type is used in the industry. It is
very straightforward to work with and create.

Farzeen: I really enjoyed making different simulations, it seemed like creating
stories, I learnt a lot during the process as well.
