package itunibo.robot
import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import itunibo.coap.resources.butlerRoomState
import itunibo.appliance.Food

object robotSupport{
	lateinit var roomState : butlerRoomState
	lateinit var robotKind : String
	var time = 0L
	fun create( actor: ActorBasic, robot : String, port: String ){
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
	
	fun setroomstate(brs : butlerRoomState){
		roomState = brs
	}
	
	fun  updatePantryAdd(dishes : String, posate : String, bicchieri : String){
		roomState.updatePantryadd(dishes.toInt(), posate.toInt(), bicchieri.toInt())
	}
	
	fun  updateDishwasherAdd(clutlery : ArrayList<Pair<String,Int>>){
		roomState.updateDishwasheradd(clutlery)
	}
	
	fun  updateTableAdd(dishes : String, posate : String, bicchieri : String){
		roomState.updateTableadd(dishes.toInt(), posate.toInt(), bicchieri.toInt())
	}
	
	fun  updatePantryTake(dishes : String, posate : String, bicchieri : String){
		roomState.updatePantrytake(dishes.toInt(), posate.toInt(), bicchieri.toInt())
	}
	
	fun  updateDishwasherTake(dishes : String, posate : String, bicchieri : String){
		roomState.updateDishwashertake(dishes.toInt(), posate.toInt(), bicchieri.toInt())
	}
	
	fun  updateTableTake() : ArrayList<Pair<String,Int>>{
		return roomState.updateTabletake()
	}
	
}