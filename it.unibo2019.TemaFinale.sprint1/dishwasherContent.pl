%% DISHWASHER CONTENT/ STATE

state( idle ). %% or busy

updateState :- state( STATE ), STATE == idle, !, replaceRule( state( _ ), state ( busy )).
updateState :- replaceRule( state( _ ), state ( idle )).