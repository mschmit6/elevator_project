import java.util.PriorityQueue;

import ElevatorSystem.Elevator;

public class ElevatorExample {

    public static Elevator get_default_elevator() {
        // Make elevator
        int num_floors = 10;
        double drop_off_time = 60.0;
        double time_between_floors = 30.0;
        return new Elevator(num_floors, drop_off_time, time_between_floors);
    }

    public static void test_no_move_required() {
        // Get default elevator
        Elevator elevator = get_default_elevator();
        double time_to_floor = elevator.estimate_time_to_floor(1);
        System.out.println(time_to_floor);
    }

    public static void main(String[] args) {
        // Test the case where no move is required
        test_no_move_required();

        // Get default elevator
        Elevator elevator = get_default_elevator();

        // Add stop to elevator
        elevator.add_stop_request(5);
        elevator.add_stop_request(3);
        elevator.add_stop_request(4);

        System.out.println(elevator.get_ascending_queue());

        double time_to_floor = elevator.estimate_time_to_floor(1);
        System.out.println(time_to_floor);

        PriorityQueue<Integer> ascendingStops = new PriorityQueue<>();
        ascendingStops.add(5);
        ascendingStops.add(2);
        ascendingStops.add(4);


        System.out.println(ascendingStops.peek());

    }
}
