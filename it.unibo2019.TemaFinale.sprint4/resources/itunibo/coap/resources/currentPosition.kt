package itunibo.coap.resources

import org.eclipse.californium.core.CoapResource
import org.eclipse.californium.core.coap.CoAP.Type
import org.eclipse.californium.core.server.resources.CoapExchange
import org.json.JSONObject
import org.eclipse.californium.core.coap.CoAP.ResponseCode
import org.eclipse.californium.core.coap.MediaTypeRegistry
import it.unibo.kactor.ActorBasic

class currentPosition(name :String) : CoapResource(name){
	var x = 0;
	var y = 0;
	
	init{
		setObservable(true)
		setObserveType(Type.NON)
	}
	override fun handleGET(exchange: CoapExchange?) {
		var json = JSONObject()
		json.put("x", y)
		json.put("y", x)
		println(json)
		exchange!!.respond(ResponseCode.CONTENT, json.toString(), MediaTypeRegistry.APPLICATION_JSON)
	}
	
	fun updatePos(xC : String, yC : String){
		//actor.solve("currentPosition(robot, X,Y)")
		x = xC.toInt()
		y = yC.toInt()
		changed();
	}
}