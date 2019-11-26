/*
===============================================================
resourceModelButler.pl
===============================================================
*/
move(w).
move(a).
move(w).
move(w).
move(h). 


model( actuator, robot,      state(stopped) ).   %% initial state
model( sensor,   sonarRobot, state(unknown) ).   %% initial state

%%TASK CHE IL ROBOT STA COMPIENDO TRA PREPARE, CLEAR, ADDFOOD,	 INRH

currentTask(robot, task(preparing)). %%initial task

%%SAVE CURRENT POSITION
currentPosition(robot, position(rh)).

action(robot, move(w)) :- changeModel( actuator, robot, movingForward  ).
action(robot, move(s)) :- changeModel( actuator, robot, movingBackward ).
action(robot, move(a)) :- changeModel( actuator, robot, rotateLeft     ).
action(robot, move(d)) :- changeModel( actuator, robot, rotateRight    ).
action(robot, move(h)) :- changeModel( actuator, robot, stopped        ).

action(sonarRobot, V)  :- changeModel( sensor, sonarRobot, V  ).

cmd(robot, task(preparing)) :- changeTask(robot, preparing).
cmd(robot, task(cleaning)) :- changeTask(robot, cleaning).
cmd(robot, task(adding)) :- changeTask(robot, adding).
cmd(robot, task(waiting)) :- changeTask(robot, waiting).

updatePosition(fridge) :- updateCurrentPosition(robot, fridge).
updatePosition(pantry) :- updateCurrentPosition(robot, pantry).
updatePosition(dishwasher) :- updateCurrentPosition(robot, dishwasher).
updatePosition(table) :- updateCurrentPosition(robot, table).
updatePosition(rh) :- updateCurrentPosition(robot, rh).

changeTask(NAME, VALUE) :- replaceRule( currentTask(NAME, _), currentTask(NAME, task(VALUE))).

updateCurrentPosition(NAME, VALUE) :- replaceRule( currentPosition(NAME, _), currentPosition(NAME, position(VALUE))). 

changeModel( CATEG, NAME, VALUE ) :-
   replaceRule( model(CATEG,NAME,_),  model(CATEG,NAME,state(VALUE)) ).
   %% showResourceModel.	%% at each change, show the model

showResourceModel :- 
	output("RESOURCE MODEL ---------- "),
	showResources,
	output("--------------------------").
		
showResources :- 
 	model( CATEG, NAME, STATE ),
 	output( model( CATEG, NAME, STATE ) ),
	fail.
showResources.			

output( M ) :- stdout <- println( M ).

initResourceTheory :- output("resourceModelButler loaded").
:- initialization(initResourceTheory).
		
		
		