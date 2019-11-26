//msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM )
const mqtt = require('mqtt');
const iosocketemitter = require('../public/javascripts/iosocketemitter');
const mqttBroker = 'mqtt://localhost';
const topicM = 'unibo/qak/maitre';
const topicB = "unibo/qak/butlerresourcemodel";

//connect client to broker
var client = mqtt.connect(mqttBroker);
var taskCompleted = "none";

client.on('connect', function(){
  client.subscribe(topicM, function(err){
    if(!err){
      console.log('Client connected successfully with ' + mqttBroker + 'topic ' + topicM);
    }else{
      console.log("error topic "+topicM);
    }
  });
  client.subscribe(topicB, function(err){
    if(!err){
      console.log('Client connected successfully with ' + mqttBroker + 'topic ' + topicB);
    }else{
      console.log("error topic "+topicB);
    }
  });
});

//msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM )
//msg(completedTask, dispatch, butlerMind, maitre, completedTask(TASK), seqnum)
//warning(C,Q,AQ)
client.on('message', function(topicM, message){
  let msgStr = message.toString();
  var indexId1 = msgStr.indexOf('(');
  var indexId2 = msgStr.indexOf(',');
  var id = msgStr.substring(indexId1+1, indexId2);
  switch(id){
    case "completedTask":
      let sp1 = msgStr.lastIndexOf('(');
      let sp2 = msgStr.indexOf(')');
      let task = msgStr.substring(sp1 +1,sp2);
      taskCompleted = task;
      console.log("node: "+taskCompleted);
      iosocketemitter.completedTask(taskCompleted);
      break;
    case "warning":
      let sp11 = msgStr.lastIndexOf('(');
      let sp22 = msgStr.indexOf(')');
      var indices = [];
      for(var i = 0; i< msgStr.length; i++){
        if(msgStr[i] === ",")
            indices.push(i);
      }
      var foodCode = msgStr.substring(sp11 + 1, indices[4])
      var quantity = msgStr.substring(indices[4] +1,indices[5])
      var avQuant = msgStr.substring(indices[5]+1, sp22)
      iosocketemitter.foodNotAvailable(foodCode, quantity, avQuant)
      break;
  }
  
});

client.on('message', function(topicB, message){
  console.log("mqtt topic butlerresourcemodel received " + message.toString());
});


exports.publish = function(topic, msg){
  console.log(topic +"----"+ msg);
  client.publish(topic, msg);
}

exports.getCompletedTask = function(){
  return completedTask;
}
