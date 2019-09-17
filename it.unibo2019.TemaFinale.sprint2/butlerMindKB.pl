near(0, 3, pantry).
near(3, 3, dishwasher).
near(3, 0, fridge).
near(0, 0, rh).
%%near table da aggiungere


getPosition(X, Y, Pos) :- near(X, Y, Pos).