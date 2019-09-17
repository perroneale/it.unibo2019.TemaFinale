pantry(0, 3, down).
dishwasher(3, 3, right).
fridge(3, 0, up).
table(0, 0).

updateTable(X, Y) :- replaceRule(table(_ , _) , table(X, Y)).