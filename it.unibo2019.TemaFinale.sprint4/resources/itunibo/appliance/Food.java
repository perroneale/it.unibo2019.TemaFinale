package itunibo.appliance;

public class Food {
	private int foodCode = 0;
	private String name = "";
	private int quantity = 0;
	
	public Food() {
	}
	
	public Food(int foodCode, String name, int quantity) {
		this.foodCode = foodCode;
		this.name = name;
		this.quantity = quantity;
	}

	public int getFoodCode() {
		return foodCode;
	}

	public void setFoodCode(int foodCode) {
		this.foodCode = foodCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "foodCode=" + foodCode + ", name=" + name +", quantity="+quantity;
	}
	
	
}
