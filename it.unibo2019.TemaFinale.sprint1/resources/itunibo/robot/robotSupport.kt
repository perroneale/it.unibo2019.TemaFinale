package itunibo.robot
import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object robotSupport{
	lateinit var robotKind : String
	var time = 0L
	
	fun create( actor: ActorBasic, robot : String = "no", port: String ){
		robotKind = robot
		when( robotKind ){
			"virtual"    ->  { itunibo.robotVirtual.clientWenvObjTcp.initClientConn( actor, "localhost" ) }
			//"realmbot"   ->  { itunibo.robotMbot.mbotSupport.create( actor, port ) }  //port="/dev/ttyUSB0"   "COM6"
			"realnano" ->    { itunibo.robotRaspOnly.nanoSupport.create(actor, true ) } //false=NO SONAR SUPPORT!!!
			else -> println( "robot unknown" )
		}
	}
	
	fun move( cmd : String ){ //cmd = msg(M) M=w | a | s | d | h
		println("robotSupport move cmd=$cmd robotKind=$robotKind" )
		when( robotKind ){
			"virtual"  -> { itunibo.robotVirtual.clientWenvObjTcp.sendMsg(  cmd ) }	
			//"realmbot" -> { itunibo.robotMbot.mbotSupport.move( cmd ) }
			"realnano" -> { itunibo.robotRaspOnly.nanoSupport.move( cmd ) }
			else       -> println( "robot unknown" )
		}
		
	}
	
	fun calibrate(actor: ActorBasic){
		
		time += 50
		Thread.sleep(time)
		GlobalScope.launch{
			actor.forward("robotAction","robotAction(h)", "butler")
		}
	}
	
}