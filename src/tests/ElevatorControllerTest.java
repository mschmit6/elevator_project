// Standard Library Imports
import java.util.HashSet;

// Elevator System imports
import ElevatorSystem.Elevator;
import ElevatorSystem.ElevatorController;
import ElevatorSystem.ElevatorState;
import ElevatorSystem.StopRequest;

// Attempted to set up junit, but that wasn't working so made an example to mimic what would be the junit test
public class ElevatorControllerTest {

    //! Main function to call all of the tests
    public static void main(String[] args) throws Exception {

        if (args.length == 0) {
            // Test the case where no move is required
            test_initialization(false);

        } else if (args.length == 1) {
            switch (args[0]) {
                case "1": {
                    test_initialization(true);
                    break;
                }
                case "2": {
                    test_two_stops(true);
                    break;
                }
                case "3": {
                    test_complex_scenario(true);
                    break;
                }
                case "4": {
                    break;
                }
                default: {
                    throw new Exception("Test case must be a number from 1 to 4.");
                }
            }
        }
    }

    /*!
     * \brief Return a default elevator object that operates on 10 floors
     *
     * \return A default elevator object that operates on 10 floors
     */
    private static ElevatorController get_default_elevator_controller() {
        // Make elevator
        int num_floors = 10;
        int num_elevators = 4;
        return new ElevatorController(num_floors, num_elevators);
    }

    //! Test the situation where the elevator shouldn't have to move, as its already at the right floor
    public static void test_initialization(boolean debug) throws Exception {
        // Get default elevator
        ElevatorController elevator_controller = get_default_elevator_controller();

        if (debug) {
            System.out.println("Number of floors: " + Integer.toString(elevator_controller.get_num_floors()));
            System.out.println("Number of elevators: " + Integer.toString(elevator_controller.get_num_elevators()));
        }

        // This would normally be a junit assert statement, but didn't have time to get that set up
        if (elevator_controller.get_num_floors() != 10) {
            throw new Exception("Number of floors was not correct.");
        } else if (elevator_controller.get_num_elevators() != 4) {
            throw new Exception("Number of elevators was not correct.");
        }
    }

    //! Test the situation where multiple stops are requested at the same time
    public static void test_two_stops(boolean debug) throws Exception {
        // Get default elevator
        ElevatorController elevator_controller = get_default_elevator_controller();

        if (debug) {
            System.out.println("Number of floors: " + Integer.toString(elevator_controller.get_num_floors()));
            System.out.println("Number of elevators: " + Integer.toString(elevator_controller.get_num_elevators()));
        }

        // Add a stop on the 6th and 7th floors
        StopRequest stop_req1 = new StopRequest(6);
        elevator_controller.add_stop(stop_req1);

        StopRequest stop_req2 = new StopRequest(7);
        elevator_controller.add_stop(stop_req2);

        if (debug) {
            elevator_controller.display_status();
        }

        // Step through until the whole system is inactive
        while (elevator_controller.is_active()) {
            elevator_controller.step();

            if(debug) {
                elevator_controller.display_status();
            }
        }

        // Now add a stop at the 5th floor, which should trigger elevator 1 to move down
        StopRequest stop_req3 = new StopRequest(5);
        elevator_controller.add_stop(stop_req3);

        // Step through until the whole system is inactive
        while (elevator_controller.is_active()) {
            elevator_controller.step();

            if(debug) {
                elevator_controller.display_status();
            }
        }
    }

    //! Test the situation where multiple stops are requested with different origin/destination floors
    public static void test_complex_scenario(boolean debug) throws Exception {
        // Get default elevator
        ElevatorController elevator_controller = get_default_elevator_controller();

        if (debug) {
            System.out.println("Number of floors: " + Integer.toString(elevator_controller.get_num_floors()));
            System.out.println("Number of elevators: " + Integer.toString(elevator_controller.get_num_elevators()));
        }

        // Add a stop on the 6th and 7th floors
        HashSet<Integer> dest_floors = new HashSet<Integer>() {{
            add(6);
            add(7);
        }};
        StopRequest stop_req1 = new StopRequest(1, dest_floors);
        elevator_controller.add_stop(stop_req1);

        // Simultaneously add another stop requesting pickup from floor 7 and drop off at floor 2
        StopRequest stop_req2 = new StopRequest(7, 2);
        elevator_controller.add_stop(stop_req2);

        if (debug) {
            elevator_controller.display_status();
        }

        // Step through until the whole system is inactive
        while (elevator_controller.is_active()) {
            elevator_controller.step();

            if(debug) {
                elevator_controller.display_status();
            }
        }

        // Now add a stop at the 5th floor, which should trigger elevator 1 to move down
        StopRequest stop_req3 = new StopRequest(5, 1);
        elevator_controller.add_stop(stop_req3);

        // Step through until the whole system is inactive
        while (elevator_controller.is_active()) {
            elevator_controller.step();

            if(debug) {
                elevator_controller.display_status();
            }
        }
    }
}
