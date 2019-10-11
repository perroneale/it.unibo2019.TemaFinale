package itunibo.coap.client

import org.eclipse.californium.core.CoapHandler
import org.eclipse.californium.core.CoapClient
import itunibo.appliance.Food
import org.eclipse.californium.core.coap.Request
import org.eclipse.californium.core.coap.CoAP
import org.eclipse.californium.core.coap.OptionSet
import org.eclipse.californium.core.coap.Option
import org.eclipse.californium.core.coap.CoAP.ResponseCode
import it.unibo.kactor.ActorBasic
import org.eclipse.californium.core.coap.MediaTypeRegistry
import itunibo.robot.foodRequire

object butlerMindClient{
	
	val butlerClient = CoapClient("coap://localhost:5683/fridgecontent")
	
	fun requireAllFood(foodList : ArrayList<Food>){
		for(i in 0..foodList.size -1){
			var food = foodList.get(i)
			var response = butlerClient.put("${food.getFoodCode()},${food.getQuantity()}", MediaTypeRegistry.TEXT_PLAIN)
			if(response.getCode() == ResponseCode.CHANGED)
				println("${food.getName()} prese ${food.getQuantity()} unità dal frigo")
			else
				println("${food.getName()} ERROR")
		}
	}
	
	fun takeOneFood(name : String, quantity : String){
		var id = foodRequire.getFoodId(name)
		butlerClient.put("$id, $quantity", MediaTypeRegistry.TEXT_PLAIN)
	}
	
	suspend fun foodAvailability( actor : ActorBasic,name : String, quantity : String){
			var request = Request(CoAP.Code.GET, CoAP.Type.NON)
			var optionSet = OptionSet()
			optionSet.addOption(Option(256, "$name,$quantity"))
			request.setOptions(optionSet)
			var response = butlerClient.advanced(request)
			if(response.getCode() == ResponseCode.VALID)
				actor.autoMsg("positiveResponse","positiveResponse")
			else
				actor.autoMsg("negativeResponse","negativeResponse($name,$quantity,${response.getResponseText()})")
	}
}