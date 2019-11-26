var events = require('events')
var emitter = new events.EventEmitter();

emitter.on('update', function update(arg1){
  console.log('From listener update content '+ arg1);
});
