package backup;


/*
 * Represents a drone in the DronePost service.
 * Contains drone details
 * Every drone can be assigned to order by the method setOrder(Order order)
 * Every drone has a timer that count the working time in seconds
 */

public class Drone extends Thread {
		
	public int secondsCounter;
	public boolean stop = false; //stop=true means: drone is disabled
	public boolean isCharged = false; // false means, drone has not been charged
	public int orderEstimatedTime;
	public int internalCounter = 0;
	protected int batteryPercentage = 10;
	
	protected long droneID;
	protected boolean isAvailable;
	
	Order order = new Order();
	
	//Default constructor for Drone class
	public Drone(){}
	
	//Drone Class constructor
	public Drone(long droneID, boolean isAvailable, int batteryPercentage){
		this.droneID = droneID;
		this.isAvailable = isAvailable;
		this.batteryPercentage = batteryPercentage;
	}

	//Set and Get methods
	public long getDroneID(){
		return droneID;
	}
	
	public void setDroneID(long droneID){
		this.droneID = droneID;
	}
	
	public boolean getAvailablity(){
		return isAvailable;
	}
	
	public void setAvailablity(boolean isAvailable){
		this.isAvailable = isAvailable;
	}
	
	public int getBatteryLevel(){
		return batteryPercentage;
	}
	
	public int getTimer(){
		return secondsCounter;
	}
	
	//Assign the Drone for an order.
	//start(): start new thread, seconds counter (timer) will start from zero.
	//resumeThread(): thread (timer) resumes from the last point that it was ended.
	public void setOrder(Order order){
		this.order = order;

		if(getAvailablity()){
			this.isAvailable=false;
			if(secondsCounter==0 && !isCharged){
				start();
			} else if (isCharged || secondsCounter != 0){
				resumeThread();
			}
		} 
	}
	
	public Order getOrder(){
		return order;
	}

	//Convert all the parameters to String for printing out
	public String toString(){
		return "Drone:: Drone ID: " + droneID + ", isAvailable: " + isAvailable + ", Battery: " + getBatteryLevel();
	}
	
	//Set estimated time for order
	public void setEstimatedTime(int orderEstimatedTime){
		this.orderEstimatedTime = orderEstimatedTime;
	}
	
	//Timer Thread
	@Override
	public void run() {
		try{
			while(stop==false){ // while drone is working
				
				internalCounter++; // Will count from zero and ended when thread is stopped
				secondsCounter++; // Will count from the last value of the counter when the thread was stopped
				batteryPercentage = 10-secondsCounter;//Full charged battery can work 10 seconds (for short time testing)
				
				//if battery is empty, stop the thread and charge the battery
				if(batteryPercentage == 0){
					System.out.println("Battery low");
					batteryCharge();
				} else {
					sleep(1000);

					System.out.println("Seconds passed: " + secondsCounter + ", Battery: " + batteryPercentage);// + "%");
				}
				
				//if the seconds counter reach the estimated time then the shipment was succeeded -> stop thread
				if(internalCounter == orderEstimatedTime){
					System.out.println("Shipment succeeeded!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					internalCounter = 0;
					stopThread();
				}
				
				//if stop is true, the thread will be sat to WAIT mode
				synchronized (this) {
					while(stop == true){
						wait();
					}
				}
			}
		}
		catch(InterruptedException e){}
	}
	
	public synchronized void stopThread() {
		stop = true;
	}
	
	public synchronized void resumeThread() {
		stop = false;
		notify();
	}
	
	//Charge the battery and change the relevant parameters and print a message to the Console
	public synchronized void batteryCharge(){
		stop = true;
		try {
			sleep(2000);
		} catch (InterruptedException e) {}
		
		secondsCounter = 0;
		batteryPercentage = 10;
		isCharged = true;
		System.out.println("Battery has been charged: " + batteryPercentage);
	}
}
