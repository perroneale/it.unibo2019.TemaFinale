package itunibo.coap.resources

import org.eclipse.californium.core.CoapResource
import it.unibo.kactor.ActorBasic
import org.eclipse.californium.core.CoapServer
import org.eclipse.californium.core.coap.CoAP.Type
import org.eclipse.californium.core.server.resources.CoapExchange
import org.eclipse.californium.core.coap.CoAP.ResponseCode
import org.eclipse.californium.core.coap.MediaTypeRegistry
import java.util.ArrayList
import itunibo.appliance.Food
import itunibo.appliance.fridgeAppliance
import org.eclipse.californium.core.server.resources.Resource
import org.eclipse.californium.core.coap.OptionSet
import org.json.JSONArray
import org.json.JSONObject

class fridgeContent(name : String) : CoapResource(name){

	var fridgeContent = ArrayList<Food>()
	
	companion object {
		lateinit var resourceCoap : fridgeContent
		//var content = ""
		
		
		//avvio il server
		fun create(actor : ActorBasic){
			val server = CoapServer()
			resourceCoap = fridgeContent("fridgecontent")
			server.add(resourceCoap)
			
			server.start();
			println("###CoAP server FRIDGE started")
			fridgeAppliance.setFridgeCoap( resourceCoap)
		}
		
		
	}
	
	init{
		setObservable(true)
		setObserveType(Type.CON)
		getAttributes().setObservable()
	}
	/*la richiesta di disponibilità di un cibo viene effettuata seguendo questo sintassi:
	 cibo,quantity*/
	override fun handleGET(exchange: CoapExchange?) {
		println("food content send")
		var option = false
	    lateinit var  payload : String
		//invia tutto il contenuto del frigo
		//exchange!!.respond(ResponseCode.CONTENT, getContent(), MediaTypeRegistry.TEXT_PLAIN)
		var optionSet = exchange!!.advanced().getRequest().getOptions()
		var options = optionSet.asSortedList()
		options.stream()
			.filter { o -> o.getNumber() == 256 }
			.findFirst()
			.ifPresent { o -> println("${o.getNumber()} + ${o.getStringValue()}");option = true; payload = o.getStringValue() }
		if(option){
			println("[[[[payload $payload")
			payload = payload.replace(" ","")
			var list = payload.split(",")
			var foodName = list.get(0)
			var quantity = list.get(1)
			var find = foodAvailability(foodName, quantity)
			if(find.first){
				exchange.respond(ResponseCode.VALID)
			}else{
				exchange.respond(ResponseCode.NOT_ACCEPTABLE, find.second.toString())
			}
		}else{
			var json = JSONArray(fridgeContent)
			println(json)
			var js = JSONObject()
			js.put("content",json)
			exchange!!.respond(ResponseCode.CONTENT, js.toString(), MediaTypeRegistry.APPLICATION_JSON)
		}
	}
	
	override fun handlePUT(exchange: CoapExchange?) {
		var request = exchange!!.getRequestText().replace(" ","")
		var splitted = request.split(',')
		var name = splitted.get(0)
		var quantity = Integer.parseInt(splitted.get(1))
		println("########$name and $quantity ")
		var b = updateFood(name, quantity)
		if(b.first){
			exchange.respond(ResponseCode.CHANGED, b.second.getFoodCode().toString())
		}else{
			exchange.respond(ResponseCode.BAD_REQUEST, "-1")
		}
	}

	fun updateFood(name : String, quantity : Int): Pair<Boolean, Food>{
		var b = false
		var currentFood = Food()
		for(i in 0..fridgeContent.size-1){
			if(fridgeContent.get(i).getName() == name){
				if(fridgeContent.get(i).getQuantity() >= quantity){
					b = true
					currentFood = fridgeContent.get(i)
					var currentQuantity = fridgeContent.get(i).getQuantity()
					var newQuantity = currentQuantity - quantity
					fridgeContent.get(i).setQuantity(newQuantity)
				}
				break
			}
		}
		changed()
		return Pair<Boolean,Food>(b,currentFood)
	}
	
	fun getContent() : String{
		var result = ""
		for( i in 0..(fridgeContent.size - 1)){
			result = result + fridgeContent.get(i).toString()+"@"
		}
		//println("##### " + result)
		return result	
	}
	
	fun setContent(content : String){
		var elab = content.replace("[","").replace("]","").replace(" ","").replace("food(","").replace(")","")
		println("@@@@ "+ elab)
		var splitted = elab.split(',') //return list string
		var cont = 0
		while(cont < splitted.size){
			var id = Integer.parseInt(splitted.get(cont))
			var name = splitted.get(cont + 1)
			var quant = Integer.parseInt(splitted.get(cont + 2))
			println("@@@@@ $cont food $id $name $quant")
			fridgeContent.add(Food(id,name,quant))
			cont = cont + 3
		}
		
		changed() //notifica i cambiamenti
	}
	
	override fun getChild(name: String?): Resource? {
		return super.getChild(name)
	}
	
	fun foodAvailability(foodName : String, foodQuantity : String) : Pair<Boolean, Int>{
		var b = false
		var q = foodQuantity.toInt()
		var av = 0
		for(i in 0..fridgeContent.size-1){
			if(fridgeContent.get(i).getName() == foodName){
				if(fridgeContent.get(i).getQuantity() >= q){
					av = fridgeContent.get(i).getQuantity()
					b = true
					//fridgeContent.get(i).setQuantity(fridgeContent.get(i).getQuantity() - q)
					//changed()
				}
				break
			}
		}
		return Pair(b,av)
	}
	
	/*
	 *Riceve la lista del cibo che era presente sul tavolo ed aggiorna la lista del cibo presente nel frigo
	 */
	fun updateFoodClean(foodClean : ArrayList<Food>){
		println("########UPDATEFOODCLEAN")
		for(i in 0..foodClean.size-1){
			for(j in 0..fridgeContent.size-1){
				if(fridgeContent.get(j).getName() == foodClean.get(i).getName()){
					var currentQuant = fridgeContent.get(j).getQuantity() 
					fridgeContent.get(j).setQuantity(currentQuant + foodClean.get(i).getQuantity())
				}
			}
		}
		changed()
	}
}
