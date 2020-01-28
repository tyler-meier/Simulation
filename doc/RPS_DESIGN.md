# Design Lab

##Names

* Tyler Meier, tkm22 
* Erik Gregorio, eg158 
* Farzeen Najam, fn26

##Program Design

* There would be 2 basic classes: Main and Weapon
    * Weapon class would have one boolean public method, result, that 
    returns true or false on if it wins or loses based off the parameter, 
    which would most likely be a list of strings that the current weapon 
    could beat. It would have another public method that reads in the 
    given file that has information like weapon name, what it can defeat, 
    and what defeats it, and then creates an array of what it can defeat 
    which will then be used in the previous method. This way, we can 
    create multiple various weapons/choices with only having two classes 
    needed. 
    * Main class would have public methods start, scene, stage, and step, 
    which allow the game to be visualized and updated and played. Main 
    would also have handleUSerInputs, for any number of choices the game 
    supports, this would support the digital choice by the user. 
    * One trade-off of this design is that you would need multiple text 
    files for each weapon, whereas if you had multiple classes, you may 
    only need one text file.

