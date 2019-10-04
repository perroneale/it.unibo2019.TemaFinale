dishes(15).
posate(15).
bicchieri(10).

%%tutti i food  food(CODICE, Quantity).
food(1,pasta,10).
food(2,carne,10).
food(3,pizzette,20).
food(4,tramezzini,15).
food(5,frutta,30).
food(6,dolci,20).

getFood(L) :- findall(food(CODE, NAME, QUANTITY) , food(CODE, NAME, QUANTITY), L).