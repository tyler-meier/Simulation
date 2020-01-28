# Design Exercise

##Names
  * Tyler Meier, tkm22 
  * Erik Gregorio, eg158 
  * Farzeen Najam, fn26

##Inheritance Review

* Reviewing Erik's code:
    * Currently Erik only has the power up class that determines the type 
    of power up, but doesn't actually add any mechanics to the game, main 
    is where the power ups are actually implemented.
    * Design we are proposing is making the superclass power up, and then
    creating different subclasses that inherit the superclass power up
    and that actually code the mechanics of each one, and then these
    would be called instead having the methods in main.
    * the method signature in each subclass would be: public void 
    trigger(PowerUp myPowerup)
    
##Simulation High Level Design
* How does cell know about neighbors? How can it update without affecting?
    * We would implement a 2D array. This allows each cell to know about
     its neighbors by indexing into the cells that surround it 
     (knowing the rows and columns). It wouldn't affect its neighbors 
     because you can index to a certain cell, which shouldn;t have an 
     affect on those surrounding enough
* What relationship exists between cell and simulation rules?
    * The simulations will determine the status of the cell at any 
    given moment, and how its status will affect its neighboring cell.
* What is grid, behaviors, who knows?
    * 2D array, array of arrays, behaviors is you can index into 
    each specific spot, the method which triggers a change in each 
    cell should know about the grid so it can update its status
* What info about a simulation needs to be the configuration file?
    * The rules of the simulation (what triggers a change in status) 
    and also the initial state of the grid and what it wants to run.
* How is graphical view of simulation updated after ll the cells have 
been updated?
    * Have a for loop going through the grid and checking what the 
    status is after each cell has changed. If cell has been updated 
    it will change the graphical view.
    
## CRC's and Use Cases

![Some CRCs](IMG_2772.png)
![Use cases](IMG_2773.png)
    
