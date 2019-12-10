%====================================================================================
% roomexplvirtual description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxroomexplvirtual, "localhost",  "MQTT", "0").
context(ctxresourcemodelv, "notcarehost",  "MQTT", "0").
 qactor( mind, ctxresourcemodelv, "external").
  qactor( butlerresourcemodel, ctxresourcemodelv, "external").
  qactor( butler, ctxresourcemodelv, "external").
  qactor( onestep, ctxroomexplvirtual, "it.unibo.onestep.Onestep").
  qactor( roomexplorationvirtual, ctxroomexplvirtual, "it.unibo.roomexplorationvirtual.Roomexplorationvirtual").
