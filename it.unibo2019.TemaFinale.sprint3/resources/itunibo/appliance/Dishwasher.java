package itunibo.appliance;

import java.util.Arrays;

public class Dishwasher {
	private static Dishwasher singletonDishwasher;
	private boolean state = true; //true libera, false occupata
	private int capacita = 60;
	private int dishes = 0;
	private int bicchieri = 0;
	private int posate = 0;
	
	public static Dishwasher getDishwasher() {
		if(singletonDishwasher == null)
			singletonDishwasher = new Dishwasher();
		return singletonDishwasher;
	}
	
	private Dishwasher() {
	}
	
	public void putDishes(int dishes, int bicchieri, int posate) {
		capacita -= (dishes+bicchieri+posate);
		this.dishes += dishes;
		this.bicchieri += bicchieri;
		this.posate += posate;
	}
	
	public void getDishes() {
		capacita += (dishes+bicchieri+posate);
		this.dishes = 0;
		this.posate = 0;
		this.bicchieri = 0;
		
	}

	@Override
	public String toString() {
		return "Dishwasher :capacita=" + capacita + ", dishes=" + dishes + ", bicchieri=" + bicchieri + ", posate="
				+ posate + "";
	}
	
	
	
}
