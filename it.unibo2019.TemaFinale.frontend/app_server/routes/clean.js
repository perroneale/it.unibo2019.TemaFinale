const express = require('express');
const router = express.Router();
const mqtt = require('../../connection/mqttUtils');

let topic = "unibo/qak/butlerresourcemodel"

router.post('/', function(req, res, next){
  var msg = "msg(modelChangeTask,dispatch,maitre,butlerresourcemodel,modelChangeTask(robot, cleaning, 0 ,0),1)";
  mqtt.publish(topic, msg);
  res.redirect('localhost:3000/');
});

module.exports = router;
