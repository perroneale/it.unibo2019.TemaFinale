package itunibo.appliance

import itunibo.coap.resources.butlerRoomState

object applianceCoAPResourcesManagment{
	lateinit var roomState : butlerRoomState
	
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
