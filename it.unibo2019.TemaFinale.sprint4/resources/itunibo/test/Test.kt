package itunibo.test

import org.eclipse.californium.core.CoapClient
import org.eclipse.californium.core.coap.MediaTypeRegistry
import org.eclipse.californium.core.coap.CoAP.ResponseCode
import org.eclipse.californium.core.coap.Request
import org.eclipse.californium.core.coap.CoAP
import org.eclipse.californium.core.coap.OptionSet
import org.eclipse.californium.core.coap.Option


fun main(){
	
	val client = CoapClient("coap://localhost:5683/fridgecontent")

	//test richiesta specifica risorsa
	var request = Request(CoAP.Code.GET, CoAP.Type.NON)
	var optionSet = OptionSet()
	optionSet.addOption(Option(256, "pasta,5"))
	request.setOptions(optionSet)
	var response = client.advanced(request).getCode()
	println("ùùùùùùRESPONSE = $response")
	
	//test errore nella modifica di una risorsa che non esiste
	/*for( i in 2..3 ){
		var response = client.put("$i, 5", MediaTypeRegistry.TEXT_PLAIN)
		if(response.getCode() == ResponseCode.CHANGED)
			println("PERFECT")
		else
			println("ERROR")
		
		Thread.sleep(2000)
	}*/
	
}