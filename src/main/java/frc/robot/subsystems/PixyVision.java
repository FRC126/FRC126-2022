package frc.robot.subsystems;

import frc.robot.commands.*;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.PixyPacket;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PixyVision extends SubsystemBase {
  public PixyI2C Pixy;
	public PixyPacket[] packetData;
	String print;

	boolean upperLightOn = false;
	boolean lowerLightOn = false;

	int centeredCount=0;
	boolean grabNow=false;

	int servoX, servoY;

	/************************************************************************
	 ************************************************************************/

	public PixyVision() {
		Pixy = new PixyI2C("pixy", new I2C(Port.kOnboard, 0x54));
		packetData = new PixyPacket[24];
		for (int x=0; x<24; x++) {	
			packetData[x] = new PixyPacket();
		}
		centerServo();
		setServo();
  }

	/************************************************************************
	 ************************************************************************/

	public int getServoX() {
       return servoX;
	}

	/************************************************************************
	 ************************************************************************/

	 public int getServoY() {
		return servoY;
	}

	/************************************************************************
	 ************************************************************************/

	 public void setServoX(int value) {
		servoX=value;
		if (servoX < 15) { servoX = 15; }
		if (servoX > 500 ) { servoX = 500; }
	}

	/************************************************************************
	 ************************************************************************/

	public void setServoY(int value) {
		servoY=value;
		if (servoY < 15) { servoY = 15; }
		if (servoY > 500 ) { servoY = 500; }
	}

	/************************************************************************
	 ************************************************************************/

	public void incrServoX(int value) {
		int tmp = servoX + value;
		setServoX(tmp);
	}

	/************************************************************************
	 ************************************************************************/

	public void incrServoY(int value) {
		int tmp = servoY + value;
		setServoY(tmp);
	}

	/************************************************************************
	 ************************************************************************/

	public void centerServo() {
		setServoX(250);
		setServoY(425);
	}

	/************************************************************************
	 ************************************************************************/

	 public void initDefaultCommand() {
		setDefaultCommand(new PixyCameraData(this));
	}

	/************************************************************************
	 ************************************************************************/

	public void setLED(int red, int green, int blue) {
		try {
			Pixy.setLED(red, green, blue);
		} catch (PixyException e) {
			SmartDashboard.putString("Pixy Error: ", "exception");
		}
	}

	/************************************************************************
	 ************************************************************************/

	public void setLamp(boolean upperOn, boolean lowerOn) {
		try {
			if (upperOn != upperLightOn || lowerOn != lowerLightOn ) {
				Pixy.setLamp(upperOn, lowerOn);
				upperLightOn = upperOn;
				lowerLightOn = lowerOn;
			}	   
		} catch (PixyException e) {
			SmartDashboard.putString("Pixy Error: ", "exception");
		}
	}

	/************************************************************************
	 ************************************************************************/

	public void setServo() {
		try {
			Pixy.setServo(servoX, servoY);
		} catch (PixyException e) {
			SmartDashboard.putString("Pixy Error: ", "exception");
		}
	}

	/************************************************************************
	 ************************************************************************/

	public void getItems(int objectId, int maxBlocks) {
		try {
			Pixy.getBlocks(objectId, maxBlocks, packetData);
		} catch (PixyException e) {
			SmartDashboard.putString("Pixy Error: ", "exception");
		}	
	}

	/************************************************************************
	 ************************************************************************/

	public double trackTargetPosition(int objectID) {
		double targetPosition=0;
		double servoRatio = 1.7;

		SmartDashboard.putBoolean("grabNow:", grabNow);
		
		//if (Robot.trackTarget != Robot.targetTypes.ballTarget) {
		//	// We are not tracking the ball, just return
		//	centeredCount=0;
		//	grabNow=false;
		//	return 0;
		//}
		
		if ( !Robot.pixyVision.packetData[objectID].isValid ) {
			centeredCount = 0;
			//Robot.robotTurn = 0;
			//Robot.robotDrive = 0;
			grabNow=false;
			return 0;
		}
		
		int y = Robot.pixyVision.packetData[objectID].Y;
		int x = Robot.pixyVision.packetData[objectID].X;
		int h = Robot.pixyVision.packetData[objectID].Height;
		int w = Robot.pixyVision.packetData[objectID].Width;
		int sx = Robot.pixyVision.getServoX();
		int sy = Robot.pixyVision.getServoY();

		servoRatio += (h * w) / 4000.0;

		if (sx < 200) {
			targetPosition = ( (sx - 255) * servoRatio *-1);	
			if ( x < 80 ) {
				targetPosition -= ( (80 - x) * servoRatio); 
			}
			if ( x > 120 ) {
				targetPosition += ( (x-120) * servoRatio); 
			}
		} else if (sx > 300) {
			targetPosition = ( (sx - 255) * servoRatio *-1);
			if ( x < 80 ) {
				targetPosition -= ( (80 - x) * servoRatio); 
			}
			if ( x > 120 ) {
				targetPosition += ( (x-120) * servoRatio); 
			}
		} else if (x < 80 ) {
			targetPosition = (x * -1 * servoRatio);
		} else if (x > 120) {
			targetPosition = ((x-110) * servoRatio);
		}

		double area = h * w;
		if ( area < 2500 ) {
			Robot.robotDrive=.25;
		} else {
			Robot.robotDrive=0;
		}

		System.out.println("vision valid, x:" + x + " tp:" + targetPosition 
				 + " sx" + sx + " h:" + h + " w: " + w + " a:" + area);

		double turnFactor = .25;
	    if (Robot.robotDrive != 0) {
		    // Slow down the turn if we are moving forward
			turnFactor = .15;
		}

		if ( targetPosition < -200) {
			System.out.println("Move Left");
			//Robot.robotTurn= turnFactor * -1;
			centeredCount=0;
			grabNow=false;
		} else if ( targetPosition > 200) {
			System.out.println("Move Right");
			//Robot.robotTurn=turnFactor;  
			centeredCount=0;
			grabNow=false;
		} else {		 
			 if (Robot.robotDrive == 0) {
				 centeredCount++;
			 } else {
				centeredCount=0;
			 }		 
			 if (centeredCount > 10) {
				System.out.println("Grab Ball!");
				grabNow=true;
			 } else {
				System.out.println("Move Center");
			 }
			 Robot.robotTurn=0;
		}  		 

		return targetPosition;
	}
}
