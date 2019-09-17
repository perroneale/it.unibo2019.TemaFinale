
%%PANTRY CONTENT


dishes( 0 ).
glasses( 0 ).
cutlery( 0 ).

%%regole per modificare la base di conoscenza
takeDishes :- replaceRule( dishes( _ ), dishes(0)).
putDishes( N ) :- replaceRule( dishes( _ ), dishes( N )).

takeGlasses :- replaceRule( glasses( _ ), glasses(0)).
putGlasses( N ) :- replaceRule( glasses( _ ), glasses( N )).

takeCutlery :- replaceRule( cutlery( _ ), cutlery(0)).
putCutlery( N ) :- replaceRule( cutlery( _ ), cutlery( N )).