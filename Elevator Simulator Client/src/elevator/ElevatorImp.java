/*
 * File name: ElevatorApplication.java
 * Author: Yuxin Zhang, 040905767
 * Course: CST8288
 * Assignment: 1
 * Date:Feb.16,2018
 * Professor:
 * Purpose: elevatorImp class
 */

package elevator;
import java.util.Observable;

import elevatorsystem.ElevatorPanel;

public class ElevatorImp extends Observable implements Elevator {
	private static final int POWER_START_STOP = 2;
	private static final int POWER_CONTINOUS = 1;
	private static final long SLEEP_START_STOP = 50;
	private static final long SLEEP_CONTINUS = 25;

	private static final long SECOND = 1000000000l;
	private static final long SLOW = SECOND/3;
	private static final long NOR = SECOND/7;

	private final double MAX_CAPACITY_PERSON;

	private double powerUsed=0;
	private int currentFloor = 0;
	private int capacity=0;
	private ElevatorPanel panel;
	private MovingState state = MovingState.Off;

	private long timer;
	
	/*
	 * defualt constructor
	 * @param capacity_persons: MAX_CAPACITY_PERSON, panel:ElevatorPanel
	 */
	public ElevatorImp(double capacity_persons, ElevatorPanel panel) {
		MAX_CAPACITY_PERSON = capacity_persons;
		this.panel = panel;
	}
	
	/*
	 * elevator moving UP/DOWN/SLOW from currentFloor to targetFloor depend to movingState
	 * notifies obervers update currentFloor, targetFloor
	 * @param floor: target floor
	 */
	public void moveTo(int floor) {
		state = MovingState.Idle;
		int step = floor - currentFloor<0? -1:1;
		
		while(currentFloor != floor) {
			currentFloor+=step;

			switch(state) {
			case Up:
			case Down:
			case SlowUp:
			case SlowDown:
				processMovingState(floor);
				break;
			case Idle:
				if(step>0) {
					state=MovingState.SlowUp;
					timer = SLOW;
					powerUsed+=POWER_START_STOP;
					processMovingState(floor);
				}
				else {
					state = MovingState.SlowDown;
					timer = SLOW;
					powerUsed+=POWER_START_STOP;
					processMovingState(floor);
				}
				break;
			case Off:
				break;
			}
			
			double[] pair = {currentFloor, floor, powerUsed};
			setChanged();
			notifyObservers(pair);
		}
	}

	/*
	 * change movingState depend on difference between currentFloor, targetFloor
	 * @param floor: targetFloor
	 */
	private void processMovingState(int floor) {
		int step = Math.abs(floor-currentFloor);

		if (step >1) {
			state.normal();
			timer = NOR;
			powerUsed+=POWER_CONTINOUS;
		}
		
		if (step==1){
			state.slow();
			timer = SLOW;
			powerUsed+=POWER_START_STOP;
		}
		
		if(step == 0) {
			state = MovingState.Idle;
		}
	}

	/*
	 * get currentFloor
	 * return int
	 */
	public int getFloor() {
		return currentFloor;
	}

	/*
	 * check if elevator's capacity is full
	 * return boolean
	 */
	public boolean isFull() {
		if(getCapacity() < MAX_CAPACITY_PERSON)  return false;
		return true;
	}

	/*
	 * check if elevator's capacity is empty
	 * return boolean
	 */
	public boolean isEmpty() {
		if(getCapacity() !=0) return false;
		return true;
	}

	/*
	 * get elevator powerconsumed from currentFloor to targetFloor
	 * return double 
	 */
	public double getPowerConsumed() {
		return powerUsed;
	}

	/*
	 * add person if elevator has capacity, else some/non people add
	 * @para person: person wants to take elevator
	 */
	public void addPersons(int persons) {
		if(isEmpty() && persons < MAX_CAPACITY_PERSON) {
			capacity = persons;
		}

		else if(isFull()) {
			
		}else {
			if( (persons + capacity) < MAX_CAPACITY_PERSON) {
				capacity+=persons;
			}else {
				capacity = (int) MAX_CAPACITY_PERSON;
			}	
		}
	}

	/*
	 * call panel interface
	 * @param floor: targetFloor
	 */
	public void requestStop(int floor) {
		panel.requestStop(floor, this);
	}

	/*
	 * return movingstate 
	 */
	public MovingState getState() {
		return state;
	}

	/*
	 * return capacity
	 */
	public int getCapacity() {
		return capacity;
	}
	
}

