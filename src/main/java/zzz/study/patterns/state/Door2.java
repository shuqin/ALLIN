package zzz.study.patterns.state;

import java.util.Observable;

public class Door2 extends Observable {
	
	public final DoorState CLOSED = new DoorClosed(this);
	public final DoorState OPENING = new DoorOpening(this);
	public final DoorState OPEN = new DoorOpen(this);
	public final DoorState CLOSING = new DoorClosing(this);
	public final DoorState STAYOPEN = new DoorStayOpen(this);
	
	private DoorState state = CLOSED;
	
	public void complete() {
		state.complete();
	}
	public void status() {
		state.status();
	}
	public void timeout() {
		state.timeout();
	}
	public void touch() {
		state.touch();
	}
	
	public void setState(DoorState state) {
		this.state = state;
		setChanged();
		notifyObservers();
	}

}
