
import ElevatorSystem.Elevator;

public class ElevatorExample {
    public static void main(String[] args) {
        // Make elevator
        int num_floors = 10;
        double drop_off_time = 60.0;
        double time_between_floors = 30.0;
        Elevator elevator = new Elevator(num_floors, drop_off_time, time_between_floors);

        System.out.println("In Elevator Example");
        System.out.println(elevator.get_current_floor());
    }
}
