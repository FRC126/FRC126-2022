package frc.robot.subsystems;

public class PixyPacket {
	public int Signature;
	public int X;
	public int Y;
	public int Width;
	public int Height;
	public int trackingIndex;
	public int age;
	public boolean isValid;
	public int checksumError;
	
	/************************************************************************
	 ************************************************************************/

	public PixyPacket() {
		initPacket();
	}
	
	/************************************************************************
	 ************************************************************************/

	public String toString() {
		return "" +
	" S:" + Signature +
	" X:" + X + 
	" Y:" + Y +
	" W:" + Width + 
	" H:" + Height +
	" TI:" + trackingIndex +
	" AGE:" + age +
	" Valid: " + isValid;
	}

	/************************************************************************
	 ************************************************************************/

	public void initPacket() {
		Signature=0;
		X=0;
		Y=0;
		Width=0;
		Height=0;
		trackingIndex=0;
		age=0;
		isValid=false;
	}
}