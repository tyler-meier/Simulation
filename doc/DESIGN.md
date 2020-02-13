# Simulation Design Final
### Names
Erik Gregorio (eg158)
Tyler Meier (tkm22)

## Team Roles and Responsibilities

 * Team Member #1: Erik \
 Responsible for configurations of the project.

 * Team Member #2: Tyler\
 Responsible for all frontend and visualizations, helped debugging as well

 * Team Member #3


## Design goals
Overall: We split up the main parts of the code (simulations, configurations, and visualizations) 
into different packages because it made the most sense and allowed for us to work on them separately.
Then within those packages we were able to split up our code into multiple different classes that all worked
together to allow the simulation to run. 

Configurations: The main goal of the design was modularity. We tried to make it easier for my classes
to take on new additions, specifically new parameter for the simulations. For example, in ReadXML 
the default parameters are all stored in Map and can be retrieved via a string. This allows for new
parameters to easily be added to an simulation.

Visualizations: The main goal of the design was to make it easier for new sims to be visualized without
having to change much in the visualization classes. That is why there is a separate sim visualization class
compared to the start visualization class. If a new simulation were to be created, then all you would mainly
need to change is a case for if the simName is that new simulation, and add rules to be displayed for it
so it sets up the correct scene. Also, I tried to make sure that all of the variables and method names
in these classes were easy to understand and helped the user in figuring out the code

#### What Features are Easy to Add
Configurations: It is easy to add new parameters to the simulation.

## High-level Design
Configurations: We tried to replicate other java parsers for my XML parser. For this implementation,
ReadXML was an object from which you could request specific information from the same way that a 
File Reader is used in Java.

#### Core Classes
Configurations: ReadXML is vital to this program as it reads the simulation files.

## Assumptions that Affect the Design
Configurations: 
#### Features Affected by Assumptions
Configurations: In order to simplify the process, the save file feature uses default parameters as 
opposed to the original parameters. However, including the original parameters should be easy enough 
and only require more time.

## New Features HowTo
Configurations: In order to write new parameters to the simulations simply include then in the XML
file. The back/front end should be able retrieve them via this method. 
``` java
public String getParameters(String parameter)
```
Additionally, make sure to add a default value for the new parameter in the following method: 
``` java
private void setUpDefaultValues()
```

#### Easy to Add Features
Configurations: Adding new parameter for each simulation.

#### Other Features not yet Done

