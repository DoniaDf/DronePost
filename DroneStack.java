package backup;

/*
 * Represents a Stack data structure for managing 5 drones
 * ArrayList is used to handle the drones that were popped outside the stack
 * Includes static methods 
 */

import java.util.ArrayList;
import java.util.Stack;

public class DroneStack {
	
	//Initializing a droneStack and 5 drones by using drone constructor
	private static Stack <Drone> dronesStack = new Stack<Drone>();
	private static Drone drone1 = new Drone(1001, true, 100);
	private static Drone drone2 = new Drone(1002, true, 100);
	private static Drone drone3 = new Drone(1003, true, 100);
	private static Drone drone4 = new Drone(1004, true, 100);
	private static Drone drone5 = new Drone(1005, true, 100);
		
	//Adding 5 drones to the stack
	public static void addDronestoStack() {
		addDronetoStack(drone1);
		addDronetoStack(drone2);
		addDronetoStack(drone3);
		addDronetoStack(drone4);
		addDronetoStack(drone5);
	}
	
	//Add drone to the Stack
	public static void addDronetoStack(Drone drone) {
		if(drone.isAvailable){
			dronesStack.push(drone);
		}
	}
	
	//Pop a drone from the Stack, put it in the ArayList, and via the ArrayList assign it to an order
	public static Drone assignDrone(Order order) {
		
		ArrayList <Drone> list = new ArrayList<Drone>();
		
		for(int i=0; i< dronesStack.size(); i++){
			list.add(dronesStack.pop());
			Drone drone = list.get(i);
			drone.setEstimatedTime(order.estimatedTime);
			int batteryLevelAfter = drone.getBatteryLevel()-order.estimatedTime;
			
			//if the drone battery is sufficient to the order estimated time then assign it and end the for loop
			if(batteryLevelAfter >= 0){
				drone.setOrder(order);
				while(drone.getState() != Thread.State.WAITING){
					//do nothing
				}
				System.out.println("\n!!!!!!!!!!!!!!!!!!!Drone Active now " + drone.toString());
				drone.setAvailablity(true);
				
				//Return the drone that just finished the order to the Drone Stack
				while(!list.isEmpty()) {
					System.out.println("List item " + " " + list.get(0).toString());
					addDronetoStack(list.remove(0));
				}
				//End the for loop
				i=dronesStack.size();
				return showAvailableDrone();
			} else {
				System.out.println("List:: Item with low battery: " + list.get(i).toString());
			}
		}
		
		//At the end return the drones from the ArrayList to the Drone Stack and print a message for each drone
		if(!list.isEmpty()){
			for(int i=0; i<list.size(); i++){
				System.out.println("\nDrone ID: " + list.get(i).droneID + " Added to stack!!!!!");
				addDronetoStack(list.get(i));
			}
		}
		return null;
	}
	
	//Show available drone that is ready for new order
	public static Drone showAvailableDrone() {
		// TODO Auto-generated method stub
		return dronesStack.peek();
	}
	
}