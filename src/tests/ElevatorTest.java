// Standard Library Imports
import java.util.PriorityQueue;

// Elevator System imports
import ElevatorSystem.Elevator;
import ElevatorSystem.ElevatorState;
import ElevatorSystem.StopRequest;

// Attempted to set up junit, but that wasn't working so made an example to mimic what would be the junit test
public class ElevatorTest {

    //! Main function to call all of the tests
    public static void main(String[] args) throws Exception {

        if (args.length == 0) {
            // Test the case where no move is required
            test_no_move_required(false);

            // Test ascending
            test_ascending(false);

            // Test the case where the elevator ascends then descends
            test_ascending_descending(false);

            // Test the case where the elevator ascends, then adds a stop then adds a descending stop
            test_ascending_descending_with_stop_added(false);

        } else if (args.length == 1) {
            switch (args[0]) {
                case "1": {
                    test_no_move_required(true);
                    break;
                }
                case "2": {
                    test_ascending(true);
                    break;
                }
                case "3": {
                    test_ascending_descending(true);
                    break;
                }
                case "4": {
                    test_ascending_descending_with_stop_added(true);
                    break;
                }
                case "5": {
                    test_ascending_back_to_ground_floor(true);
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
    private static Elevator get_default_elevator() {
        // Make elevator
        int num_floors = 10;
        return new Elevator("Default Elevator", num_floors);
    }

    //! Test the situation where the elevator shouldn't have to move, as its already at the right floor
    public static void test_no_move_required(boolean debug) throws Exception {
        // Get default elevator
        Elevator elevator = get_default_elevator();

        // Estimate the time to get to the first floor
        StopRequest stop_req = new StopRequest(1);
        int time_to_floor = elevator.estimate_time_to_stop(stop_req);

        if (debug) {
            System.out.println("Time to floor 1: " + Integer.toString(time_to_floor));
        }

        // This would normally be a junit assert statement, but didn't have time to get that set up
        if (time_to_floor != 0.0) {
            throw new Exception("Pseudo test failed");
        }
    }

    //! Test the situation where the elevator is purely ascending
    public static void test_ascending(boolean debug) throws Exception {
        // Get default elevator
        Elevator elevator = get_default_elevator();

        // Set a stop
        StopRequest stop_req = new StopRequest(4);
        elevator.add_stop(stop_req);
        if (debug) {
            System.out.println("Adding stop on floor 4.");
        }

        int[] expected_floors = {2, 3, 4, 4, 4};
        int step = 0;

        for (int k = 0; k < expected_floors.length; k++) {
            // Step the elevator
            int cur_floor = elevator.step();
            step += 1;

            if (debug) {
                System.out.println("Step: " + Integer.toString(step) + ", Current Floor: " + Integer.toString(cur_floor));
            }

            // This would normally be a junit assert statement, but didn't have time to get that set up
            if (cur_floor != expected_floors[k]) {
                throw new Exception("Should be on floor " + Integer.toString(expected_floors[k]));
            }
        }

        // This would normally be a junit assert statement, but didn't have time to get that set up
        if (elevator.get_elevator_state() != ElevatorState.IDLE) {
            throw new Exception("The elevator should now be inactive.");
        }
    }

    //! Test the situation where the elevator is purely ascending
    public static void test_ascending_descending(boolean debug) throws Exception {
        // Get default elevator
        Elevator elevator = get_default_elevator();
        int step = 0;

        // Set a stop on the eighth floor
        StopRequest stop_req1 = new StopRequest(8);
        elevator.add_stop(stop_req1);
        if (debug) {
            System.out.println("Adding stop on floor 8.");
        }

        // Step until the 4th floor is reached
        while (elevator.get_current_floor() < 4) {
            int cur_floor = elevator.step();
            step += 1;

            if (debug) {
                System.out.println("Step: " + Integer.toString(step) + ", Current Floor: " + Integer.toString(cur_floor));
            }
        }

        // Add stop on 3rd floor
        StopRequest stop_req2 = new StopRequest(3);
        elevator.add_stop(stop_req2);
        if (debug) {
            System.out.println("Adding stop on floor 3.");
        }

        // Set the expected floors
        int[] expected_floors = {5, 6, 7, 8, 8, 8, 7, 6, 5, 4, 3, 3, 3};


        for (int k = 0; k < expected_floors.length; k++) {
            // Step the elevator
            int cur_floor = elevator.step();
            step += 1;

            if (debug) {
                System.out.println("Step: " + Integer.toString(step) + ", Current Floor: " + Integer.toString(cur_floor));
            }

            // This would normally be a junit assert statement, but didn't have time to get that set up
            if (cur_floor != expected_floors[k]) {
                throw new Exception("Should be on floor " + Integer.toString(expected_floors[k]));
            }
        }

        // This would normally be a junit assert statement, but didn't have time to get that set up
        if (elevator.get_elevator_state() != ElevatorState.IDLE) {
            throw new Exception("The elevator should now be inactive.");
        }
    }

    //! Test the situation where the elevator is purely ascending
    public static void test_ascending_descending_with_stop_added(boolean debug) throws Exception {
        // Get default elevator
        Elevator elevator = get_default_elevator();
        int step = 0;

        // Set a stop on the eighth floor
        StopRequest stop_req1 = new StopRequest(8);
        elevator.add_stop(stop_req1);
        if (debug) {
            System.out.println("Adding stop on floor 8.");
        }

        // Step until the 4th floor is reached
        while (elevator.get_current_floor() < 4) {
            int cur_floor = elevator.step();
            step += 1;

            if (debug) {
                System.out.println("Step: " + Integer.toString(step) + ", Current Floor: " + Integer.toString(cur_floor));
            }
        }

        // Add stop on 3rd floor
        StopRequest stop_req2 = new StopRequest(3);
        elevator.add_stop(stop_req2);
        if (debug) {
            System.out.println("Adding stop on floor 3.");
        }

        // Add another stop on the 6th floor
        StopRequest stop_req3 = new StopRequest(6);
        elevator.add_stop(stop_req3);
        if (debug) {
            System.out.println("Adding stop on floor 6.");
        }

        // Set the expected floors
        int[] expected_floors = {5, 6, 6, 6, 7, 8, 8, 8, 7, 6, 5, 4, 3, 3, 3};


        for (int k = 0; k < expected_floors.length; k++) {
            // Step the elevator
            int cur_floor = elevator.step();
            step += 1;

            if (debug) {
                System.out.println("Step: " + Integer.toString(step) + ", Current Floor: " + Integer.toString(cur_floor));
            }

            // This would normally be a junit assert statement, but didn't have time to get that set up
            if (cur_floor != expected_floors[k]) {
                throw new Exception("Should be on floor " + Integer.toString(expected_floors[k]));
            }
        }

        // This would normally be a junit assert statement, but didn't have time to get that set up
        if (elevator.get_elevator_state() != ElevatorState.IDLE) {
            throw new Exception("The elevator should now be inactive.");
        }
    }


    //! Test the situation where the elevator is purely ascending
    public static void test_ascending_back_to_ground_floor(boolean debug) throws Exception {
        // Get default elevator
        Elevator elevator = get_default_elevator();

        // Set a stop
        StopRequest stop_req = new StopRequest(4, 1);
        elevator.add_stop(stop_req);
        if (debug) {
            System.out.println("Adding stop on floor 4, then return to floor 1.");
        }

        int[] expected_floors = {2, 3, 4, 4, 4, 3, 2, 1, 1, 1};
        int step = 0;

        for (int k = 0; k < expected_floors.length; k++) {
            // Step the elevator
            int cur_floor = elevator.step();
            step += 1;

            if (debug) {
                System.out.println("Step: " + Integer.toString(step) + ", Current Floor: " + Integer.toString(cur_floor));
            }

            // This would normally be a junit assert statement, but didn't have time to get that set up
            if (cur_floor != expected_floors[k]) {
                throw new Exception("Should be on floor " + Integer.toString(expected_floors[k]));
            }
        }

        // This would normally be a junit assert statement, but didn't have time to get that set up
        if (elevator.get_elevator_state() != ElevatorState.IDLE) {
            throw new Exception("The elevator should now be inactive.");
        }
    }
}
