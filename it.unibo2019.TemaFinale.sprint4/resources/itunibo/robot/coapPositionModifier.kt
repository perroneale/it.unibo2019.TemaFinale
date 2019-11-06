package itunibo.robot

import itunibo.coap.resources.currentPosition
import it.unibo.kactor.ActorBasic

object coapPositionModifier{
	lateinit var positionResource : currentPosition
	
	fun setPositionresource(resource : currentPosition){
		positionResource = resource
	}
	
	fun updatePosition(x : String, y:String){
		positionResource.updatePos(x,y)
	}
}