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
together to allow the simulation to run. This is a good example of design because it allows for cleaner 
code that is easier to follow and organized. 

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

Visualizations: It is easy to add new rules and names and such for newly created simulations, you just have to 
make sure to write them out in the properties file. It is also easy to add other features to the 
scene, such as more buttons, you just have to look at how they are currently being created and follow
how to make them and set their action. I also thought adding the clickable to change cell status wasn't
hard either, I just had to add a method to the abstract simulation class and implement it in all of the 
sim subclasses and it started working

## High-level Design
Configurations: We tried to replicate other java parsers for my XML parser. For this implementation,
ReadXML was an object from which you could request specific information from the same way that a 
File Reader is used in Java.

Visualizations: We split up the startVisual and the simVisual but they still work together a lot. 
The startVisual calls the step method of whatever sim is playing so it can step through that one.
Also the created chart class is able to be its own object in simVisual so that it can use its specific
methods in its class. The visualization classes also work with information from the xml classes to use
for the title and the rules in visualization, but without knowing what the specific info is. Same with the
sims, it takes in information from xml and uses that to show the simulation, but updating for whatever
specific simulation it is.

#### Core Classes
Configurations: ReadXML is vital to this program as it reads the simulation files.

Visualizations: Core classes are StartVisual and SimVisual which set up the different scenes.
Chart class is important only if you want the chart to be seen on each scene

## Assumptions that Affect the Design
Configurations: 

Visualizations: Assumptions made were that other sims were going to be added , so the design is set up
so that this is able to be done easily without having to change a lot of the code (such as just 
adding things to properties files and changing some name stuff)

#### Features Affected by Assumptions
Configurations: In order to simplify the process, the save file feature uses default parameters as 
opposed to the original parameters. However, including the original parameters should be easy enough 
and only require more time.

Visualizations: Some conditional statements had to be added to take into account multiple simulations and 
for some to be added, just because it couldn't be coded in a way that was basic for just one 
sim set up. When I had to add the other features, such as chart, all i really had to do was just add
a new chart class that dealt with all of the setup and everything with that and then just create a 
chart object to be called in sim visual. Again, the click to change cell status also wasn't bad, just had 
to add an event handler for when the mouse is clicked on one of the rectangles and add a method in 
the abstract simulation file that changed the cell when a rectangle was clicked. 

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

Visualizations: To add new features to visualization, just know what the new sim is going to be called and take 
into account the new rules and name for that, and then if any buttons need to be added just
make sure to initialize it then set size and then action (all in separate methods). So if a new button
was added, just call methods :
``` java
private void createAllButtons()
```
``` java
private void buttonSizes()
```
``` java
private void buttonActions()
```
In the code masterpiece I submitted I also separated the createAllButtons method into two to initialize
the buttons in a separate method. To add more text just make sure you put into properties
file and then call from myResources.

#### Easy to Add Features
Configurations: Adding new parameter for each simulation.

Visualizations: Buttons on screen, chart, labels and text (rules/title)

#### Other Features not yet Done

Visualizations: I was not able to complete the creation of a new window to compare the two simulations.
I was very close, had the new window popping up and everything with the new simulation, but the animation was also
going onto the next window, even when i tried to create a new timeline for the new window. I couldn't figure
out how to keep both animations running at the same time and i ran out of time at the end of the week to finish it
and since it wasn't working completely I just took that part of the code out since I had completed two other
full features and it would have made the design worse. To do this though, I just added a new button 
that when clicked opened up a new stage with the desired simulation (using 
the same set up simulation scene method in simVisual). But again, I was working on trying to get two
animations running but ran out of time. I also wasn't able to get to the feature where a user is able 
to dynamically change the value of the parameters because I was focusing on the other three new features. 
However, to add this new feature I would have had to create a separate panel or pane in the window that
had either a text field that the user typed in or a slider bar that changed the params. I would have had
to change some stuff in the setup of the scene itself and also in the update method to change whatever the new parameters
would be set to.  