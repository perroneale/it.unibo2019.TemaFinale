package itunibo.coap.resources

import org.eclipse.californium.core.CoapResource
import org.eclipse.californium.core.server.resources.CoapExchange
import org.json.JSONObject
import itunibo.planner.moveUtils
import itunibo.planner.plannerUtil
import org.json.JSONArray
import org.eclipse.californium.core.coap.CoAP.ResponseCode
import org.eclipse.californium.core.coap.MediaTypeRegistry

class obstacle(val x : Int, val y :Int){
	
}

class mapResource(name : String) : CoapResource(name){
	var dimX = 0
	var dimY = 0
	var obstacleList = ArrayList<obstacle>()
	
	init{
		dimX = moveUtils.getMapDimX()
		dimY = moveUtils.getMapDimY()
		var map = plannerUtil.getMap();
		println(map)
		var newMap = map.replace("\n","").replace(" ","").replace("|","")
		var list = newMap.split(",")
		println("list.size = ${list.size}")
		var cont = 0
		var col = 0
		var raw = 0
		for(i in 0..list.size -2){
			println("col = $col  raw = $raw")
			if(list.get(i) == "X"){
				obstacleList.add(obstacle(raw, col))
			}
			col++
			if(col == dimY){
				col = 0
				raw++
			}
		}
		println("$dimX" + "\n" + "$dimY" + obstacleList.toString())
		
	}
	
	override fun handleGET(exchange : CoapExchange?){
		println("Request received")
		var json = JSONObject()
		json.put("dimX", dimX)
		json.put("dimY", dimY)
		var jsonArray = JSONArray(obstacleList!!)
		json.put("obstacle",jsonArray)
		exchange!!.respond(ResponseCode.CONTENT, json.toString(), MediaTypeRegistry.APPLICATION_JSON)
		
		
		
	}
}
