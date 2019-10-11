package itunibo.coap.resources

import org.eclipse.californium.core.CoapResource
import it.unibo.kactor.ActorBasic
import org.eclipse.californium.core.CoapServer

class butlerRoomState(name: String) : CoapResource(name){
	var dishesPantry = 0
	var posatePantry = 0
	var bicchieriPantry = 0
	var dishesTable = 0
	var posateTable = 0
	var bicchieriTable = 0
	var dishesDish = 0
	var posateDish = 0
	var bicchieriDish = 0
	
	companion object{
		lateinit var resourceCoap : butlerRoomState
		
		fun create(actor: ActorBasic){
			val server = CoapServer()
			resourceCoap = butlerRoomState("roomstate")
			server.add(resourceCoap)
			
			server.start();
			println("@@@CoAP server startd roomstate")
		}
		
	}
	
	
}