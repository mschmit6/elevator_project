// Standard Library Imports
import java.util.PriorityQueue;

// Elevator Sytem imports
import ElevatorSystem.Elevator;
import ElevatorSystem.ElevatorController;
import ElevatorSystem.ElevatorState;

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


    //! Test the situation where the elevator shouldn't have to move, as its already at the right floor
    public static void test_two_stops(boolean debug) throws Exception {
        // Get default elevator
        ElevatorController elevator_controller = get_default_elevator_controller();

        if (debug) {
            System.out.println("Number of floors: " + Integer.toString(elevator_controller.get_num_floors()));
            System.out.println("Number of elevators: " + Integer.toString(elevator_controller.get_num_elevators()));
        }

        // Add a stop on the 6th and 7th floors
        elevator_controller.add_stop(6);
        elevator_controller.add_stop(7);

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
        elevator_controller.add_stop(5);

        // Step through until the whole system is inactive
        while (elevator_controller.is_active()) {
            elevator_controller.step();

            if(debug) {
                elevator_controller.display_status();
            }
        }
    }
}
