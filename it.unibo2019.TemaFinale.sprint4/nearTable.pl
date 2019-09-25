addTable(X,Y) :- X1 is X - 1, Y1 is Y - 1, table(X1, Y), table(X, Y1), !,
								assert(table(X,Y)), retractall(nearTable(X,Y,_)), X2 is X + 1, Y2 is Y + 1, assert(nearTable(X, Y2, upDir)), assert(nearTable(X2, Y, leftDir)).

addTable(X, Y) :- X1 is X - 1, table(X1, Y), !, assert(table(X,Y)), retract(nearTable(X,Y,_)), X2 is X + 1, Y1 is Y - 1, Y2 is Y + 1,
								assert(nearTable(X, Y2, upDir)), assert(nearTable(X, Y1, downDir)), assert(nearTable(X2, Y, leftDir)).

addTable(X, Y) :- Y1 is Y - 1, table(X, Y1), !, assert(table(X,Y)), retract(nearTable(X,Y,_)), X2 is X + 1, X1 is X - 1, Y2 is Y + 1,
								assert(nearTable(X1, Y, rightDir)), assert(nearTable(X2, Y, leftDir)), assert(nearTable(X, Y2, upDir)).

addTable(X, Y) :- assert(table(X,Y)), X1 is X + 1, Y1 is Y + 1, X2 is X - 1, Y2 is Y - 1, assert(nearTable(X, Y2, downDir)), assert(nearTable(X, Y1, upDir)), assert(nearTable(X2, Y, rightDir)), assert(nearTable(X1, Y, leftDir)).


getNearTable :- findall(nearTable(X,Y,Dir), nearTable(X,Y,Dir), L), stamp(L).

stamp([]) :- stdout <- println('finish'), !.

stamp([H|T]) :- stdout <- println(H), stamp(T).

distance(A,B, Dir) :- findall([X,Y], nearTable(X,Y,_), L), manhattan(L,A,B,Ris), nearTable(A,B,Dir).
  
manhattan([X],A,B,Ris) :- calc(X, A,B,Ris),!.
manhattan([H|T],A,B,R) :- calc(H,A,B,R), manhattan(T, C,D,R2), R < R2.
manhattan([H|T],C,D,R2) :- calc(H,A,B,R), manhattan(T, C,D,R2), R >= R2.

calc([H,T], H,T, Ris) :- curPos(X,Y), X1 is X - H, Y1 is Y - T, X2 is abs(X1), Y2 is abs(Y1), Ris is X2 + Y2.