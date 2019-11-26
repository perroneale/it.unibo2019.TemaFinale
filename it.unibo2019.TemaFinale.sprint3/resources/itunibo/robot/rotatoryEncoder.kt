package itunibo.robot

import itunibo.test.SerialPortConnSupport
import kotlinx.coroutines.launch
import it.unibo.kactor.MsgUtil
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ActorBasicFsm
import it.unibo.kactor.ApplMessage
import alice.tuprolog.Struct
import alice.tuprolog.Term

class rotatoryEncoder(name : String, val owner : ActorBasicFsm, val conn : SerialPortConnSupport) : ActorBasic(name, owner.scope){
	
	init{
		scope.launch{
			autoMsg("start","start(1)")}
	}
		
	suspend override fun actorBody(msg: ApplMessage) {
 		while(true){
			var curData = conn.receiveALine()
			if(curData.isEmpty()){
				
			}else{
				
				var d = curData.replace("\n","")
				println("current data = $d")
				val event = MsgUtil.buildEvent(name,"rotatoryCounter","rotatoryCounter($d)")
				owner.emit(event)
			}
		}
	}
	
	/*suspend fun elabData(data : String ){
		while(true){
			var curData = conn.receiveALine()
			println(curData)
			var v = curData.toDouble() 
			//handle too fast change ?? NOT HERE
			var data = v.toInt();
			val event = MsgUtil.buildEvent(name,"rotatoryCounter","rotatoryCounter($data)")
			owner.emit(event)
		}
	}*/
}

/*class rotatoryEncoder(name : String, val owner : ActorBasicFsm, val conn : SerialPortConnSupport) : ApplActorDataStream(name, owner.scope){
	
	init{
		scope.launch{  autoMsg("start","start(1)") }
	}
	
	override suspend fun elabData(data : String ){
		while(true){
			var curData = conn.receiveALine()
			println(curData)
			var v = curData.toDouble() 
			//handle too fast change ?? NOT HERE
			var data = v.toInt();
			val event = MsgUtil.buildEvent(name,"rotatoryCounter","rotatoryCounter($data)")
			owner.emit(event)
		}
	}
}*/