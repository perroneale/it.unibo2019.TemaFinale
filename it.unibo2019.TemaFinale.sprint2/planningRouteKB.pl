pantry(0, 3, downDir).
dishwasher(3, 3, rightDir).
fridge(3, 0, upDir).
rh(0 ,0, downDir).
curPos(0,0).

getPosition(X, Y, Pos) :- pantry(0, 3, downDir).
getPosition(X, Y, Pos) :- dishwasher(3, 3, rightDir).
getPosition(X, Y, Pos) :- fridge(3, 0, upDir).
getPosition(X, Y, Pos) :- rh(0 ,0, downDir).