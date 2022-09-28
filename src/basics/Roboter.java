package basics;


import lejos.hardware.Sound;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.*;
import lejos.robotics.RegulatedMotor;
import lejos.hardware.lcd.*;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.utility.Delay;
import positions.Position2D;
import positions.Position3D;
import game.*;

public class Roboter {
//	public static void main(String args[]) {
//		try {
//			Roboter roboter = new Roboter();
//			Sound.beep();
//			roboter.moveToHomePosition();
//			roboter.bereitePapierVor();
//
//			Delay.msDelay(1000);
//			roboter.entfernePapier();
//			roboter.moveToHomePosition();
//			Sound.twoBeeps();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
	
/*  Übernommen aus LEGO\Test1 zwecks calibrate() */	
	static RegulatedMotor motorA = new EV3LargeRegulatedMotor(MotorPort.A);

	// Papiereinzug
	static RegulatedMotor motorB = new EV3LargeRegulatedMotor(MotorPort.B);

	// Stift
	static RegulatedMotor motorC = new EV3LargeRegulatedMotor(MotorPort.C);
	static EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S1);
	static EV3ColorSensor sensor2 = new EV3ColorSensor(SensorPort.S2);
	
	static SensorMode sensorMode = touchSensor.getTouchMode();
	static float[] sample = new float[sensorMode.sampleSize()];
/* */		
	

	private Position3D currentPosition;

	private MultiPositionAchse xAchse = new MultiPositionAchse(new TouchSensor(SensorPort.S1), MotorPort.A, Einbaurichtung.UMGEKEHRT, new Reifen(40.0), new Zahnradsatz(new Zahnrad(Zahnrad.ANZAHL_ZAEHNE_KLEIN), new Zahnrad(Zahnrad.ANZAHL_ZAEHNE_GROSS)));
	private MultiPositionAchse yAchse = new MultiPositionAchse(new LichtSensor(SensorPort.S3), MotorPort.B, Einbaurichtung.UMGEKEHRT, new Reifen(43.2), new Zahnradsatz(new Zahnrad(Zahnrad.ANZAHL_ZAEHNE_KLEIN), new Zahnrad(Zahnrad.ANZAHL_ZAEHNE_GROSS)));
	private DualPositionAchse zAchse = new DualPositionAchse(null, MotorPort.C, Einbaurichtung.REGULAER, null, null);

	// Stift ist zu Beginn oben = 0; Stift unten ist durch 1 dargestellt
		static int penState = 0;
		
// 	Konsturktor
	
	public Roboter() {

	}

	
//	Methoden
	private void bereitePapierVor() throws InterruptedException {
	}

	private void entfernePapier() throws InterruptedException {
		zAchse.deaktiviere();
		yAchse.setSpeed(Integer.MAX_VALUE);
		yAchse.backward(2000);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.exit(0);
	}

	
	
	public Position3D getCurrentPosition() {
		return this.currentPosition;
	}

	public MultiPositionAchse getXAchse() {
		return this.xAchse;
	}

	public MultiPositionAchse getYAchse() {
		return this.yAchse;
	}

	static void calibrate() {
		penup(); 
		motorA.setSpeed(50);
		motorA.setAcceleration(1000);
		while (sample[0] == 0) {
			motorA.forward();
			Delay.msDelay(1);
			sensorMode.fetchSample(sample, 0);
			if (motorA.isStalled()) {
				motorA.stop();
			}
		}
		motorA.stop();
		motorA.resetTachoCount();
		LCD.drawString("Kalibriert!", 0, 0);
		LCD.drawString("TachoA " + String.valueOf(motorA.getTachoCount()), 0, 1);
		Sound.beep();
		Delay.msDelay(2000);

	}
	
	// Methoden für Stift - Hoch und runter
		static void penup() {
			if (penState != 0) {
				myrotate(motorC, -180);
				penState = 0;
			}
		}

		static void pendown() {
			if (penState != 1) {
				myrotate(motorC, 180);
				penState = 1;
			}
		}
		
		private static void myrotate(RegulatedMotor motor, int grad) {
			motor.rotate(grad);
		}
	
	protected void moveToHomePosition() throws InterruptedException {
		zAchse.deaktiviere();
		xAchse.setSpeed(50);
		while (!xAchse.isSensorAktiv()) {
			xAchse.backward();
		}
		xAchse.stop();
		xAchse.forward();
		Delay.msDelay(200);
		xAchse.stop();
		this.currentPosition = new Position3D(0, 0, false);
		this.resetTachoCounts();
	}

	private void moveToPosition(Position2D position2D, int mmSec) throws InterruptedException {
		this.moveToPosition(new Position3D(position2D, this.zAchse.isAktiv()), mmSec);
	}

	private void moveToPosition(Position3D position, int mmSec) throws InterruptedException {
		if (position.isZ())
			this.zAchse.aktiviere();
		else
			this.zAchse.deaktiviere();

		double deltaX = currentPosition.getX() - position.getX();
		double deltaY = currentPosition.getY() - position.getY();
		double hypo = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

		double time = hypo / mmSec;

		xAchse.getMotor().synchronizeWith(yAchse.getMotor());

		xAchse.setSpeed(deltaX / time);
		yAchse.setSpeed(deltaY / time);

		xAchse.getMotor().startSynchronization();

		xAchse.rotateMm(deltaX);
		yAchse.rotateMm(deltaY);

		xAchse.getMotor().endSynchronization();

		xAchse.getMotor().waitComplete();
		yAchse.getMotor().waitComplete();

		this.currentPosition = new Position3D(xAchse.getPositionFromTachoCount(), yAchse.getPositionFromTachoCount(), zAchse.isAktiv());

	}

	private void resetTachoCounts() {
		this.xAchse.resetTachoCount();
		this.yAchse.resetTachoCount();
		if (xAchse.getTachoCount() != 0 || yAchse.getTachoCount() != 0)
			throw new RuntimeException("Konnte Tachocount nicht zurücksetzen");
	}

	public void stop() {
		xAchse.stop();
		yAchse.stop();
		zAchse.stop();
	}

}
