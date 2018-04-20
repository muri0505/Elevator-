package Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import elevator.Elevator;
import elevator.ElevatorImp;
import elevatorsystem.ElevatorSystem;
import elevatorsystem.ElevatorSystemImp;
import elevatorsystem.ElevatorPanel;

class ElevatorSystemTest {
	private static ElevatorSystem elevatorSystem;
	private static Elevator elevator;
	
	@BeforeAll
	static void init() throws Exception {
		System.out.println("Elevator System test start");
	}
	
	@BeforeEach
	void setup() {
		elevatorSystem = new ElevatorSystemImp(0,10);
		elevator = new ElevatorImp( 10, (ElevatorPanel) elevatorSystem);
		elevatorSystem.addElevator(elevator);
		elevatorSystem.callUp(10);
	}
	
	@Test
	void testgetFloorCount() {
		assertTrue(11==elevatorSystem.getFloorCount());
	}
	
	@Test
	void testgetMaxFloor() {
		assertTrue(10==elevatorSystem.getMaxFloor());
	}
	
	@Test
	void testgetMinFloor() {
		assertTrue(0==elevatorSystem.getMinFloor());
	}
	
	@Test
	void testcallup() {
		assertTrue(elevator==elevatorSystem.callUp(5));
	}
	
	@Test
	void testcalldown() {
		assertTrue(elevator==elevatorSystem.callDown(5));
	}
	
	@Test
	void testgetCurrentFloor() {
		assertTrue(10 == elevatorSystem.getCurrentFloor());
	}
	
	@Test
	void testgetPowerConsumed() {
		assertTrue(12 == elevatorSystem.getPowerConsumed());
	}
	
	@Test
	void testaddElevator() {
		elevatorSystem.addElevator(elevator);
		assertTrue( elevator == elevatorSystem.callUp(10));
	}
	
	@AfterAll
	static void reset() throws Exception {
		elevatorSystem = null;
		elevator = null;
		System.out.println("Elevator System test end");
	}
}
