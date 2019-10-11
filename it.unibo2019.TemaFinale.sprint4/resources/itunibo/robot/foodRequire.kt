package itunibo.robot

import itunibo.appliance.Food

object foodRequire{
	var foodReq = ArrayList<Food>()
	
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
			foodReq.add(Food(id,name,quant))
			cont = cont + 3
		}
	}
	
	fun getFood() : ArrayList<Food>{
		return foodReq
	}
	
	fun getFoodId(name:String) : Int{
		for(i in 0..foodReq.size -1){
			if(foodReq.get(i).getName() == name){
				return foodReq.get(i).getFoodCode()
			}
		}
		return -1
	}
	
}
