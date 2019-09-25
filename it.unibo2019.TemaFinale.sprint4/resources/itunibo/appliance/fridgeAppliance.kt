package itunibo.appliance

import itunibo.coap.resources.fridgeContent

object fridgeAppliance{
	
	lateinit var fridgecont : fridgeContent
	
	fun setFridgeCoap(fridgecoap : fridgeContent){
		fridgecont = fridgecoap
	}
	
	fun updateContent(content : String){
		fridgecont.setContent(content)
	}
}