/*
===============================================================
resourceModelButler.pl
===============================================================
*/
model( actuator, robot,      state(stopped) ).   %% initial state
model( sensor,   sonarRobot, state(unknown) ).   %% initial state

%%TASK CHE IL ROBOT STA COMPIENDO TRA PREPARE, CLEAR, ADDFOOD,	 INRH

currentTask(robot, task(waiting)). %%initial task

%%SAVE CURRENT POSITION
currentPosition(robot, 0, 0).

%%CIBO E QUANTITA' RICHIESTE NELL'ADD FOOD, DI DEFAULT SARANNO A 0 SE VIENE RICEVUTO UN ALTRO TASK
food(0,0).

action(robot, move(w)) :- changeModel( actuator, robot, movingForward  ).
action(robot, move(s)) :- changeModel( actuator, robot, movingBackward ).
action(robot, move(a)) :- changeModel( actuator, robot, rotateLeft     ).
action(robot, move(d)) :- changeModel( actuator, robot, rotateRight    ).
action(robot, move(h)) :- changeModel( actuator, robot, stopped        ).
action(robot, move(l)) :- changeModel( actuator, robot, rotateLeft90   ).
action(robot, move(r)) :- changeModel( actuator, robot, rotateRight90  ).

action(sonarRobot, V)  :- changeModel( sensor, sonarRobot, V  ).

cmd(robot, task(preparing)) :- changeTask(robot, preparing).
cmd(robot, task(cleaning)) :- changeTask(robot, cleaning).
cmd(robot, task(adding)) :- changeTask(robot, adding).
cmd(robot, task(waiting)) :- changeTask(robot, waiting).

updatePosition(robot, X, Y) :- replaceRule( currentPosition(NAME, _, _ ), currentPosition(NAME, X, Y)). 

changeTask(NAME, VALUE) :- replaceRule( currentTask(NAME, task(_) ), currentTask(NAME, task(VALUE))).

updateFood(ID, QUANTITY) :- replaceRule( food( _ , _ ), food(ID, QUANTITY)).

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
		
		
		