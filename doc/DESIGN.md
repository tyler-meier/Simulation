# Simulation Design Final
### Names
Erik Gregorio (eg158)
Tyler Meier (tkm22)

## Team Roles and Responsibilities

 * Team Member #1: Erik \
 Responsible for configurations of the project.

 * Team Member #2: Tyler\
 Responsible for all frontend and visualizations

 * Team Member #3


## Design goals
Configurations: The main goal of the design was modularity. We tried to make it easier for my classes
to take on new additions, specifically new parameter for the simulations. For example, in ReadXML 
the default parameters are all stored in Map and can be retrieved via a string. This allows for new
parameters to easily be added to an simulation.

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

