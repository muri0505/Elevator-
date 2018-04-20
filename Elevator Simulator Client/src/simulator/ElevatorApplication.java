/*
 * File name: ElevatorApplication.java
 * Author: Yuxin Zhang, 040905767
 * Course: CST8288
 * Assignment: 1
 * Date:Feb.16,2018
 * Professor:
 * Purpose: Main class, run application 
 */

package simulator;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import simulator.simulator;

public  class ElevatorApplication extends Application implements Observer{
	private static final int FLOOR_COUNT = 21;
	private Label[] floors;
	private simulator simulator;
	private ElevatorAnimator elevatorAnimine;
	private Label power;

	/*
	 *initial floor labels before add to floorGrid GridPane
	 */
	public void init() throws Exception{
		super.init();
		floors = new Label[FLOOR_COUNT];
		for(int i = 0; i < FLOOR_COUNT; i++) {
			floors[i] = new Label("" + i);
			floors[i].setId("empty");
			floors[0].setId("elevator");
		}
	}

	/*
	 * setup floorGrid GrindPane, scene to primaryStage. 
	 * add new simulator and elevatorAnimie
	 */
	public void start(Stage primaryStage) throws Exception { 
		GridPane floorGrid = new GridPane();
		
		power = new Label("powerConsume: 0");
		floorGrid.add(power,0,23);

		for(int i = FLOOR_COUNT-1; i >= 0; i--) {
			floorGrid.add(floors[i], 0, (FLOOR_COUNT-1)-i);

			primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
				if(event.getCode() == KeyCode.ESCAPE) {
					primaryStage.hide();
					elevatorAnimine.stop();
				}
			});
		}

		elevatorAnimine = new ElevatorAnimator();
		simulator = new simulator(this);

		elevatorAnimine.start();
		simulator.start();

		Scene scene = new Scene(floorGrid, 150,620);
		scene.getStylesheets().add(ElevatorApplication.class.getResource("elevator.css").toExternalForm());

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/*
	 * update currentFloor, targetFloor from Observable
	 * @param observable: elevator, object: currentFloor, targetFloor 
	 */
	public void update(Observable o, Object arg) {
		double[] pair = (double[] ) arg;
		elevatorAnimine.addData(pair);
	}

	/*
	 * runs elevator depend on simulator's targetFloor, prints powerUsed
	 */
	public class ElevatorAnimator extends AnimationTimer{
		private static final long SECOND = 1000000000l;
		private static final long NOR = SECOND/10;

		private int targetFloor;
		private int currentFloor=0;
		private long lastUpdate=0;
		private double powerUsed = 0;
		private Queue<double[]> queue = new LinkedList<>();

		public void handle(long now) {
			if(queue.isEmpty() || now - lastUpdate < NOR) {
				return;
			}
			lastUpdate = now;

			double[] pair = queue.poll();
			currentFloor = (int) pair[0];
			targetFloor = (int)pair[1];
			//targetFloor = FLOOR_COUNT+4;
			powerUsed = pair[2];

			try {
				if(targetFloor >= FLOOR_COUNT) {
					throw new InterruptedException("targetFloor is greater than FLOOR_COUNT");
				}else {
					for(int i = 0; i < FLOOR_COUNT; i++) {
						floors[i].setId("empty");

						floors[targetFloor].setId("target");
						floors[currentFloor].setId("elevator");

						power.setText("powerConsume: " + powerUsed);
					}
				}
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		public void addData(double[] pair) {
			queue.add(pair);
		}
	}

	/*
	public class ElevatorAnimator extends AnimationTimer{
		private static final long SECOND = 1000000000l;
		private static final long SLOW = SECOND/3;
		private static final long NOR = SECOND/7;

		private int targetFloor;
		private int currentFloor=0;
		private int step;
		private boolean newTurn = true;
		private long lastUpdate=0;
		private long checktime = SLOW; 

		public void handle (long now) {
			if(now - lastUpdate < NOR) {
				return;
			}
			lastUpdate = now;

			if(newTurn){
				while(true) {
					targetFloor = random(0, FLOOR_COUNT);
					if(targetFloor != currentFloor) {
						break;
					}
				}
				step = targetFloor - currentFloor<0? -1:1;
				newTurn = false;
				floors[targetFloor].setId("target");
			}else if(targetFloor == currentFloor) {
				newTurn = true;
				floors[targetFloor].setId("elevator");
				checktime = SLOW;
			}else {
				floors[currentFloor].setId("empty");
				currentFloor +=step;
				floors[currentFloor].setId("elevator");
				if(Math.abs(targetFloor-currentFloor)==1) {
					checktime = SLOW;
				}else {
					checktime = NOR;
				}
			}
		}
	}

	int random(int min, int max){
		return (int)(Math.random() *max + min);
	}
	 */

	public void stop() throws Exception{
		super.stop();
	}

	public static void main(String[] args) {
		launch(args);
	}
}