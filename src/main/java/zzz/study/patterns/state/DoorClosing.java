package zzz.study.patterns.state;

public class DoorClosing extends DoorState {
	
	public DoorClosing(Door2 door) {
		super(door);
	}
	
	public void touch() {
		door.setState(door.OPENING);
	}
	
	public void complete() {
		door.setState(door.CLOSED);
	}

}
