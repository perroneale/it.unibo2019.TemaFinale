%====================================================================================
% zooming2 description   
%====================================================================================
context(ctxbutler, "localhost",  "TCP", "8080").
context(ctxmaitre, "localhost",  "TCP", "8081").
context(ctxfridge, "localhost",  "TCP", "8082").
 qactor( butlerresourcemodel, ctxbutler, "it.unibo.butlerresourcemodel.Butlerresourcemodel").
  qactor( mind, ctxbutler, "it.unibo.mind.Mind").
  qactor( butlermind, ctxbutler, "it.unibo.butlermind.Butlermind").
  qactor( planningroute, ctxbutler, "it.unibo.planningroute.Planningroute").
  qactor( execroute, ctxbutler, "it.unibo.execroute.Execroute").
  qactor( butler, ctxbutler, "it.unibo.butler.Butler").
  qactor( maitre, ctxmaitre, "it.unibo.maitre.Maitre").
  qactor( fridge, ctxfridge, "it.unibo.fridge.Fridge").
  qactor( pantry, ctxbutler, "it.unibo.pantry.Pantry").
  qactor( dishwasher, ctxbutler, "it.unibo.dishwasher.Dishwasher").
  qactor( table, ctxbutler, "it.unibo.table.Table").
