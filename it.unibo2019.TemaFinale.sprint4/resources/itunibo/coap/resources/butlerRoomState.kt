package itunibo.coap.resources

import org.eclipse.californium.core.CoapResource
import it.unibo.kactor.ActorBasic
import org.eclipse.californium.core.CoapServer
import itunibo.robot.robotSupport
import org.eclipse.californium.core.coap.CoAP.Type
import org.eclipse.californium.core.server.resources.CoapExchange
import org.json.JSONObject
import org.eclipse.californium.core.coap.CoAP.ResponseCode
import org.eclipse.californium.core.coap.MediaTypeRegistry
import itunibo.robot.coapPositionModifier
import itunibo.appliance.Food
import org.json.JSONArray

class butlerRoomState(name: String) : CoapResource(name){
	var roomState = ArrayList<Pair<String, Int>>()
	var foodOnTable = ArrayList<Food>()
	
	companion object{
		lateinit var resourceCoap : butlerRoomState
		lateinit var position : currentPosition
		lateinit var server : CoapServer
		
		fun create(){
			server = CoapServer(5684)
			resourceCoap = butlerRoomState("roomstate")
			position = currentPosition("position")
			server.add(resourceCoap)
			server.add(position)
			server.start();
			println("@@@CoAP server start roomstate")
			robotSupport.setroomstate(resourceCoap)
			coapPositionModifier.setPositionresource(position)
		}
	}
	
	init{
		roomState.add(Pair("dishesPantry",20))
		roomState.add(Pair("posatePantry",20))
		roomState.add(Pair("bicchieriPantry",20))
		roomState.add(Pair("dishesTable",0))
		roomState.add(Pair("bicchieriTable",0))
		roomState.add(Pair("posateTable",0))
		roomState.add(Pair("dishesDish",0))
		roomState.add(Pair("posateDish",0))
		roomState.add(Pair("bicchieriDish",0))
		setObservable(true)
		setObserveType(Type.NON)
	}
	
	override fun handleGET(exchange: CoapExchange?) {
		var json = JSONObject()
		/*for(i in 0..roomState.size-1){
			json.put(roomState.get(i).first, roomState.get(i).second)
		}*/
		var dishesjson = JSONArray(roomState)
		var foodjson = JSONArray(foodOnTable)
		json.put("dishes", dishesjson)
		json.put("foodtable", foodjson)
		println(json)
		exchange!!.respond(ResponseCode.CONTENT, json.toString(), MediaTypeRegistry.APPLICATION_JSON)
	}
	
	//viene passato come argomento il numero di posate prelevate
	fun updatePantryadd(dishes : Int, posate : Int, bicchieri : Int){
		for(i in 0..roomState.size-1){
			when(roomState.get(i).first){
				"dishesPantry" -> {
					var current = roomState.get(i).second
					current += dishes
					roomState.set(i,Pair(roomState.get(i).first, current))}
				"posatePantry" -> {
					var current = roomState.get(i).second
					current += posate
					roomState.set(i,Pair(roomState.get(i).first, current))}
				"bicchieriPantry" -> {
					var current = roomState.get(i).second
					current += bicchieri
					roomState.set(i,Pair(roomState.get(i).first, current))}
			}
		}
		changed()
	}
	
	fun updateDishwasheradd(clutlery : ArrayList<Pair<String,Int>>){
		println("@@@@@@@@ "+ clutlery.toString())
		for(j in 0..clutlery.size-1){
			when(clutlery.get(j).first){
				"dishesTable" -> {
					updateDishwasherContentInCleanAction("dishesDish", clutlery.get(j).second)
				}
				"posateTable" -> {
					updateDishwasherContentInCleanAction("posateDish", clutlery.get(j).second)
				}
				"bicchieriTable" -> {
					updateDishwasherContentInCleanAction("bicchieriDish", clutlery.get(j).second)
				}
			}
		}
		changed()
	}
	
	fun updateDishwasherContentInCleanAction(content : String, currentValue: Int){
		var index = findIndexDishesContent(content)
		var current = roomState.get(index).second
		current += currentValue
		roomState.set(index,Pair(roomState.get(index).first, current))
	}
	
	fun findIndexDishesContent(content : String):Int{
		var index = 0
		for(i in 0..roomState.size-1){
				if(roomState.get(i).first == content){
					index = i
				}
		}
		return index
	}
	
	fun updateTableadd(dishes : Int, posate : Int, bicchieri : Int){
		for(i in 0..roomState.size-1){
			when(roomState.get(i).first){
				"dishesTable" -> {
					var current = roomState.get(i).second
					current += dishes
					roomState.set(i,Pair(roomState.get(i).first, current))}
				"posateTable" -> {
					var current = roomState.get(i).second
					current += posate
					roomState.set(i,Pair(roomState.get(i).first, current))}
				"bicchieriTable" -> {
					var current = roomState.get(i).second
					current += bicchieri
					roomState.set(i,Pair(roomState.get(i).first, current))}
			}
		}
		changed()
	}
	
	fun updatePantrytake(dishes : Int, posate : Int, bicchieri : Int){
		for(i in 0..roomState.size-1){
			when(roomState.get(i).first){
				"dishesPantry" -> {
					var current = roomState.get(i).second
					current -= dishes
					roomState.set(i,Pair(roomState.get(i).first, current))}
				"posatePantry" -> {
					var current = roomState.get(i).second
					current -= posate
					roomState.set(i,Pair(roomState.get(i).first, current))}
				"bicchieriPantry" -> {
					var current = roomState.get(i).second
					current -= bicchieri
					roomState.set(i,Pair(roomState.get(i).first, current))}
			}
		}
		changed()
	}
	
	
	fun updateDishwashertake(dishes : Int, posate : Int, bicchieri : Int){
		for(i in 0..roomState.size-1){
			when(roomState.get(i).first){
				"dishesDish" -> {
					var current = roomState.get(i).second
					current -= dishes
					roomState.set(i,Pair(roomState.get(i).first, current))}
				"posateDish" -> {
					var current = roomState.get(i).second
					current -= posate
					roomState.set(i,Pair(roomState.get(i).first, current))}
				"bicchieriDish" -> {
					var current = roomState.get(i).second
					current -= bicchieri
					roomState.set(i,Pair(roomState.get(i).first, current))}
			}
		}
		changed()
	}
	
	
	/*fun updateTabletake(dishes : Int, posate : Int, bicchieri : Int){
		for(i in 0..roomState.size-1){
			when(roomState.get(i).first){
				"dishesTable" -> {
					var current = roomState.get(i).second
					current -= dishes
					roomState.set(i,Pair(roomState.get(i).first, current))}
				"posateTable" -> {
					var current = roomState.get(i).second
					current -= posate
					roomState.set(i,Pair(roomState.get(i).first, current))}
				"bicchieriTable" -> {
					var current = roomState.get(i).second
					current -= bicchieri
					roomState.set(i,Pair(roomState.get(i).first, current))}
			}
		}
		changed()
	}*/
	
	fun updateTabletake():ArrayList<Pair<String,Int>>{
		var currentContent = ArrayList<Pair<String,Int>>()
		for(i in 0..roomState.size-1){
			when(roomState.get(i).first){
				"dishesTable" -> {
					currentContent.add(roomState.get(i))
					roomState.set(i,Pair(roomState.get(i).first, 0))}
				"posateTable" -> {
					currentContent.add(roomState.get(i))
					roomState.set(i,Pair(roomState.get(i).first, 0))}
				"bicchieriTable" -> {
					currentContent.add(roomState.get(i))
					roomState.set(i,Pair(roomState.get(i).first, 0))}
			}
		}
		changed()
		return currentContent
	}
	
	fun updateFoodOnTable(foodOnT : ArrayList<Food>){
		foodOnTable = foodOnT
		println("food added on table" + foodOnT.toString())
		println("food added on table2" + foodOnTable.toString())
		changed()
	}
	
	/*
	 *Aggiungo un nuovo cibo sul frigo:
	  1. Controllo se il cibo è già presente, eventualmente ne aggiorno la quantità
	  2. Se non è presente aggiungo il nuovo cibo*/
	fun addFoodOnTable(foodCode : Int, name : String, quantity : String) : Boolean{
		println("°°°°AGGIUNGO CIBO SUL TAVOLO $name")
		for(i in 0..foodOnTable.size - 1){
			if(foodOnTable.get(i).getName() == name){
				var currentQuant = foodOnTable.get(i).getQuantity()
				var newQuant = currentQuant + quantity.toInt()
				foodOnTable.get(i).setQuantity(newQuant)
				changed()
				return true
			}
		}
		
		foodOnTable.add(Food(foodCode, name, quantity.toInt()))
		changed()
		return true
	}
	
	/*prendo tutti i cibi presenti sul tavolo*/
	
	fun getAllFoodFromTable() : ArrayList<Food>{
		println("°°°°Get all food from table")
		var currentFood = ArrayList<Food>()
		currentFood.addAll(foodOnTable)
		foodOnTable.clear()
		changed()
		return currentFood
	}
	
}