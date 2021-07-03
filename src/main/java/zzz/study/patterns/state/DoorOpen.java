package zzz.study.patterns.state;

public class DoorOpen extends DoorState {

    public DoorOpen(Door2 door) {
        super(door);
    }

    public void touch() {
        door.setState(door.STAYOPEN);
    }

    public void timeout() {
        door.setState(door.CLOSING);
    }

}
