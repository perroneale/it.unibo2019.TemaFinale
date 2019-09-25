package itunibo.coap.client

import org.eclipse.californium.core.CoapHandler
import org.eclipse.californium.core.CoapResponse
import org.eclipse.californium.core.CoapClient
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.IOException


object maitreClient : CoapHandler{
	val fridgeAddress = "coap://localhost:5683/fridgecontent"
	
	override fun onLoad(response: CoapResponse?) {
		val content = response!!.getResponseText()
		println("###MAITRE COAP OBSERVER"+content)
	}

	override fun onError() {
		println("ERROR FROM OBSERVER")
	}
	
	fun createClient(newfridgeAddress : String = fridgeAddress){
		val maitre = CoapClient(newfridgeAddress)
		maitre.observe( maitreClient)
	}
}

	fun main( ) {
			maitreClient.createClient( "${maitreClient.fridgeAddress}" )
			println("CoapLedObserverClient.java: OBSERVE (press enter to exit)")
	 
			 
	// After you have setup your observe relation you need to make sure your program is still doing something
			// wait for user
			val br = BufferedReader(InputStreamReader(System.`in`))
			try {
				br.readLine()
				println("CoapLedObserverClient.java: CANCELLATION")
				//relation!!.proactiveCancel()
			} catch (e: IOException) {
			}
	}