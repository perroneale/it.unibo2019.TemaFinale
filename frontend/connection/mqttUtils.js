//msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM )
const mqtt = require('mqtt');
const mqttBroker = 'mqtt://192.168.43.102';
const topicM = 'unibo/qak/maitre';

//connect client to broker
var client = mqtt.connect(mqttBroker);
var taskCompleted = "none";


client.on('connect', function(){
  client.subscribe(topicM, function(err){
    if(!err){
      console.log('Client connected successfully with ' + mqttBroker);
    }
  })
});
//msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM )
//msg(completedTask, dispatch, butlerMind, maitre, completedTask(TASK), seqnum)
client.on('message', function(topicM, message){
  let msgStr = message.toString();
  let sp1 = msgStr.lastIndexOf('(');
  let sp2 = msgStr.indexOf(')');
  let task = msgStr.substring(sp1 +1,sp2);
  completedTask = task;
});


exports.publish = function(topic, msg){
  console.log(topic +"----"+ msg);
  client.publish(topic, msg);
}

exports.getCompletedTask = function(){
  return completedTask;
}
