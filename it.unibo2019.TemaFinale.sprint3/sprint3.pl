%====================================================================================
% sprint3 description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxbutler, "localhost",  "MQTT", "0" ).
context(ctxmaitre, "localhost",  "MQTT", "0" ).
context(ctxfridge, "localhost",  "MQTT", "0" ).
context(ctxexploration, "192.168.43.102",  "MQTT", "0" ).
 qactor( roomexploration, ctxexploration, "external").
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
