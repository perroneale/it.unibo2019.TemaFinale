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

class fridgeContent(name : String) : CoapResource(name){

	var fridgeContent = ArrayList<Food>()
	
	companion object {
		lateinit var resourceCoap : fridgeContent
		var content = ""
		
		
		//avvio il server
		fun create(actor : ActorBasic){
			val server = CoapServer()
			resourceCoap = fridgeContent("fridgecontent")
			server.add(resourceCoap)
			
			server.start();
			println("###CoAP server started")
			fridgeAppliance.setFridgeCoap( resourceCoap)
		}
		
		
	}
	
	init{
		setObservable(true)
		setObserveType(Type.CON)
	}
	/*la richiesta di disponibilità di un cibo viene effettuata seguendo questo sintassi:
	 cibo,quantity*/
	override fun handleGET(exchange: CoapExchange?) {
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
			exchange!!.respond(ResponseCode.CONTENT, getContent(), MediaTypeRegistry.TEXT_PLAIN)
		}
	}
	
	override fun handlePUT(exchange: CoapExchange?) {
		var request = exchange!!.getRequestText().replace(" ","")
		var splitted = request.split(',')
		var id = Integer.parseInt(splitted.get(0))
		var quantity = Integer.parseInt(splitted.get(1))
		var b = updateFood(id, quantity)
		if(b){
			exchange.respond(ResponseCode.CHANGED)
		}else{
			exchange.respond(ResponseCode.BAD_REQUEST)
		}
	}

	fun updateFood(id : Int, quantity : Int): Boolean{
		var b = false
		for(i in 0..fridgeContent.size-1){
			if(fridgeContent.get(i).getFoodCode() == id){
				if(fridgeContent.get(i).getQuantity() >= quantity){
					b = true
					fridgeContent.get(i).setQuantity(fridgeContent.get(i).getQuantity() - quantity)
				}
				break
			}
		}
		changed()
		return b
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
					fridgeContent.get(i).setQuantity(fridgeContent.get(i).getQuantity() - q)
					changed()
				}
				break
			}
		}
		return Pair(b,av)
	}
}
