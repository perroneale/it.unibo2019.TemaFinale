%====================================================================================
% roomexploration description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxroomexpl, "localhost",  "MQTT", "0").
context(ctxresourcemodel, "localhost",  "MQTT", "0").
 qactor( mind, ctxresourcemodel, "external").
  qactor( butlerresourcemodel, ctxresourcemodel, "external").
  qactor( roomexploration, ctxroomexpl, "it.unibo.roomexploration.Roomexploration").
