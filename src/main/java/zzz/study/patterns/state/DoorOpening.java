package zzz.study.patterns.state;

public class DoorOpening extends DoorState {

    public DoorOpening(Door2 door) {
        super(door);
    }

    public void touch() {
        door.setState(door.CLOSING);
    }

    public void complete() {
        door.setState(door.OPEN);
    }
}
