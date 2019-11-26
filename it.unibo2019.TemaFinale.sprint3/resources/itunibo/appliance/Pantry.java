package itunibo.appliance;

public class Pantry {
		private static Pantry singletonPantry;
		private int dishes = 20;
		private int posate = 20;
		private int bicchieri = 20;
		private boolean state = true; //true = pieno false = vuoto
		
		public static Pantry getPantry() {
			if(singletonPantry == null)
				singletonPantry = new Pantry();
			return singletonPantry;
		}
		
		private Pantry() {
			
		}
		
		public int getDishes(){
			return dishes;
		}
		
		public void putDishes(int numDishes) {
			dishes += numDishes;
		}
		
		public void getDishes(int dishes, int bicchieri,int posate) {
			this.dishes -= dishes;
			this.posate -= posate;
			this.bicchieri -= bicchieri;
		}
		
		public void putDishes(int dishes, int bicchieri,int posate) {
			this.dishes += dishes;
			this.posate += posate;
			this.bicchieri += bicchieri;
		}
		public int getPosate() {
			return posate;
		}

		public void setPosate(int posate) {
			this.posate += posate;
		}

		public int getBicchieri() {
			return bicchieri;
		}

		public void setBicchieri(int bicchieri) {
			this.bicchieri += bicchieri;
		}

		public boolean getState() {
			if(dishes == 0 && posate == 0 && bicchieri == 0)
				return false;
			else
				return true;
		}

		public void setState(boolean state) {
			this.state = state;
		}

		@Override
		public String toString() {
			return "Pantry Content : dishes=" + dishes + ", posate=" + posate + ", bicchieri=" + bicchieri + "";
		}
}
