package zzz.study.patterns.state;

public class DoorClosed extends DoorState {

    public DoorClosed(Door2 door) {
        super(door);
    }

    public void touch() {
        door.setState(door.OPENING);
    }
}
