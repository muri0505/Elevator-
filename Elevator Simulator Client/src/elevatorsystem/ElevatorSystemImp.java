/*
 * File name: ElevatorApplication.java
 * Author: Yuxin Zhang, 040905767
 * Course: CST8288
 * Assignment: 1
 * Date:Feb.16,2018
 * Professor:
 * Purpose: ElevatorSystemImp 
 */

package elevatorsystem;
import elevator.Elevator;
import elevator.MovingState;

public class ElevatorSystemImp implements ElevatorSystem , ElevatorPanel{
	private final int MAX_FLOOR;
	private final int MIN_FLOOR;
	private Elevator elevator;

	/*
	 * default constructor
	 * @param MIN_FLOOR: min floor, MAX_FLOOR: max floor
	 */
	public ElevatorSystemImp(int MIN_FLOOR, int MAX_FLOOR) {
		this.MIN_FLOOR = MIN_FLOOR;
		this.MAX_FLOOR = MAX_FLOOR; 
	}

	/*
	 * check if elevator is off or invalid target floor, then goto requestStop floor 
	 * @param floor:targetFloor, elevator: this elevator
	 */
	public void requestStop(int floor, Elevator elevator) {
		if(checkForElevator() && floorCheck(floor)) {
			elevator = call(floor,MovingState.Idle);
		}
	}

	/*
	 * elevator goes up
	 * @param floor: targetFloor
	 */
	public Elevator callUp(int floor) {
		elevator.moveTo(floor);
		return elevator;
	}

	/*
	 * elevator goes down
	 * @param floor: targetFLoor
	 */
	public Elevator callDown(int floor) {
		elevator.moveTo(floor);
		return elevator;
	}

	/*
	 * check targetFloor is valid
	 * @param: floor: targetFloor
	 */
	private boolean floorCheck(int floor) {
		if ( floor > MAX_FLOOR || floor < MIN_FLOOR) {
			return false;
		}
		return true;
	}

	/*
	 * elevator goes up/down depend on targetFloor
	 * @param floor: targetFloor, direction: movingstate
	 */
	private Elevator call(int floor, MovingState direction)  {
		if(floor > getCurrentFloor()) {
			return callUp(floor);
		}else {
			return callDown(floor);
		}
	}

	/*
	 *check if elevator is off or no one on elevator
	 *return boolean 
	 */
	private boolean checkForElevator() {
		return (!(elevator.getState()).equals(MovingState.Off) && (elevator.getCapacity()!=0));
	}

	/*
	 * get elevator's powerConsumed 
	 * return double 
	 */
	public double getPowerConsumed() {
		return elevator.getPowerConsumed();
	}

	/*
	 * get currentFloor
	 * return int
	 */
	public int getCurrentFloor() {
		return elevator.getFloor();
	}

	/*
	 * get max floor
	 * return int
	 */
	public int getMaxFloor() {
		return MAX_FLOOR;
	}

	/*
	 * get min floor
	 * return int
	 */
	public int getMinFloor() {
		return MIN_FLOOR;
	}
	
	/*
	 * get total floor count 
	 * return int
	 */
	public int getFloorCount() {
		return MAX_FLOOR-MIN_FLOOR+1;
	}

	/*
	 * add elevator to elevatorSystem
	 * @param elevator: new elevator 
	 */
	public void addElevator(Elevator elevator) {
		this.elevator = elevator;
	}
}
