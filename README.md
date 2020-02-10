simulation
====

This project implements a cellular automata simulator.

Names: \
    Erik Gregorio (eg158)

### Timeline

Start Date: January 23rd

Finish Date: February 9th

Hours Spent: \
Erik: 15 hrs

### Primary Roles
Erik: Configurations and debugging 

### Resources Used
Erik: TutorialsPoint, Stackoverflow

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



### Notes/Assumptions

Assumptions or Simplifications:

Interesting data files:

Known Bugs:

Extra credit:


### Impressions
Erik: I took sometime to get used to working with an XML file. However, once
I got used to it, I realized why this file type is used in the industry. It is
very straightforward to work with and create.
