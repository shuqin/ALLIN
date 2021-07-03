package zzz.study.patterns.state;

public class DoorStayOpen extends DoorState {

    public DoorStayOpen(Door2 door) {
        super(door);
    }

    public void touch() {
        door.setState(door.CLOSING);
    }

}
