package itunibo.appliance;

import java.util.ArrayList;

public class Table {
	private static Table singletonTable;
	private ArrayList<Food> foodOnTable = new ArrayList();
	private int dishes = 0;
	private int bicchieri = 0;
	private int posate = 0;
	
	
	public static Table getTable() {
		if(singletonTable == null)
			singletonTable = new Table();
		return singletonTable;
	}
	
	private Table() {
	}
	
	public void addFood(int code, String name, int quantity) {
		Food food = new Food(code, name, quantity);
		foodOnTable.add(food);
	}
	
	public void updateFoodQuantity(int code, int quantity) {
		for(int i = 0; i< foodOnTable.size(); i++) {
			if(foodOnTable.get(i).getFoodCode() == code) {
				foodOnTable.get(i).setQuantity(quantity);
			}
		}
	}

	public ArrayList<Food> getFoodOnTable() {
		return foodOnTable;
	}

	public int getDishes() {
		return dishes;
	}

	public void setDishes(int dishes) {
		this.dishes = dishes;
	}

	public int getBicchieri() {
		return bicchieri;
	}

	public void setBicchieri(int bicchieri) {
		this.bicchieri = bicchieri;
	}

	public int getPosate() {
		return posate;
	}

	public void setPosate(int posate) {
		this.posate = posate;
	}
	
	public void add(int dishes, int bicchieri, int posate) {
		this.dishes += dishes;
		this.bicchieri += bicchieri;
		this.posate += posate;
	}
	
	public void take(int dishes, int bicchieri, int posate) {
		this.dishes -= dishes;
		this.bicchieri -= bicchieri;
		this.posate -= posate;
	}
}
