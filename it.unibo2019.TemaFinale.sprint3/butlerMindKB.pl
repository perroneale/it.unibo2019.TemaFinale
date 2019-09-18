near(0, 3, pantry).
near(3, 3, dishwasher).
near(3, 0, fridge).
near(0, 0, rh).

getPosition(X, Y, Pos) :- near(X, Y, Pos),!.
getPosition(X, Y, Pos) :- nearTable(X, Y, Pos).

addTable([X]) :- assert(X),!.
addTable([H|T]) :- assert(H), addTable(T).

%%updateDishes(ND, NB, NP) :- replaceRule(dishes(_), dishes(ND)), replaceRule(bicchieri(_), bicchieri(NB)), replaceRule(posate(_), posate(NP)).