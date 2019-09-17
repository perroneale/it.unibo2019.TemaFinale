
%%FRIDGE CONTENT

%%cibi disponibili
%%food(CODE, QUANTITY).


%%regole per modificare la base di conoscenza
updateQuantity(Code, Quantity) :- replaceRule( food(Code, _ ) , food(Code, Quantity)).
removeFood(Code) :- removeRule(food(Code, _ )).
addFood(Code, Quantity) :-  addRule( food(Code, Quantity)).