%====================================================================================
% roomexplvirtual description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxroomexplvirtual, "localhost",  "MQTT", "0" ).
context(ctxresourcemodel, "192.168.43.100",  "MQTT", "0" ).
 qactor( mind, ctxresourcemodel, "external").
  qactor( butlerresourcemodel, ctxresourcemodel, "external").
  qactor( onestep, ctxroomexplvirtual, "it.unibo.onestep.Onestep").
  qactor( roomexplorationvirtual, ctxroomexplvirtual, "it.unibo.roomexplorationvirtual.Roomexplorationvirtual").
