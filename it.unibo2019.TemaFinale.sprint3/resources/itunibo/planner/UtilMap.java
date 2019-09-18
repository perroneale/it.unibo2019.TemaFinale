package itunibo.planner;

import aima.core.environment.wumpusworld.Room;
import itunibo.planner.model.Box;
import itunibo.planner.model.RoomMap;

public class UtilMap {

	private static UtilMap singletonUtilMap;
	public static UtilMap getUtilMap() {
		if( singletonUtilMap == null)
			singletonUtilMap = new UtilMap();
		return singletonUtilMap;
	}
	
	private UtilMap() {
		
	}
	
	public void updateMap(String mappa) {
		System.out.println(mappa);
		int x = 0;
		int y = 0;
		String map = mappa.replace(",", "");
		System.out.println(map);
		for(int i = 0; i< map.length(); i++) {
			System.out.println("char at "+i+" "+map.charAt(i));
			switch(map.charAt(i)) {
			case '1' :
				RoomMap.getRoomMap().put(x, y, new Box(false, false, false));
				x++;
				break;
			case '0' :
				RoomMap.getRoomMap().put(x, y, new Box(false, true, false));
				x++;
				break;
			case 'r' : 
				RoomMap.getRoomMap().put(x, y, new Box(false, false, true));
				x++;
				break;
			case 'X' :
				RoomMap.getRoomMap().put(x, y, new Box(true, false, false));
				x++;
				break;
			case '@' :
				x = 0;
				y++;
				break;
			}
		}
		
		System.out.println(RoomMap.getRoomMap().toString());
		
	}
}
