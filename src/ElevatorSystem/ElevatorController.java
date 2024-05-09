/*!
 * \file ElevatorController.java
 * \brief Defines the Elevator class
 */

// Define package
package ElevatorSystem;

// Standard library imports
import java.util.ArrayList;

// Elevator System imports
import ElevatorSystem.Elevator;

public class ElevatorController {

    //--------------------------------------
    // Constructors
    //--------------------------------------

    /*!
     * \brief Construct an ElevatorController object, specifying the number of floors and elevators that are operating
     *
     * \param num_floors Number of floors that the elevator operates on
     * \param num_elevators The number of elevators in the building
     */
    public ElevatorController(int num_floors, int num_elevators) throws IllegalArgumentException {
        // Error Checking
        if (num_floors <= 1) {
            throw new IllegalArgumentException("ElevatorController() - num_floors value must be greater than or equal to 2.");
        }
        if (num_elevators < 1) {
            throw new IllegalArgumentException("ElevatorController() - num_elevators value must be greater than 0.");
        }

        // Set values
        num_floors_ = num_floors;
        elevators_ = new ArrayList<Elevator>();
        for (int k = 0; k < num_elevators; k++) {
            elevators_.add(new Elevator("Elevator " + Integer.toString(k + 1), num_floors));
        }

    }


    //--------------------------------------
    // Class Methods
    //--------------------------------------

    /*!
     * \brief Return the number of elevators in the system
     *
     * \return The number of elevators in the system
     */
    public int get_num_elevators() {
        return elevators_.size();
    }

    /*!
     * \brief Return the number of floors that the elevator system operates on
     *
     * \return The number of floors that the elevator system operates on
     */
    public int get_num_floors() {
        return num_floors_;
    }

    //! Step all elevators in the system forward by one time step
    public void step() {
        for (Elevator elevator : elevators_) {
            elevator.step();
        }
    }

    //! Display the status of each elevator within the system
    public void display_status() {
        for (Elevator elevator : elevators_) {
            System.out.println(elevator.get_name() + ":");
            System.out.println("    Status: " + elevator.get_elevator_state().toString());
            System.out.println("    Current Floor: " + Integer.toString(elevator.get_current_floor()));
        }
        System.out.println("\n");
    }

    /*!
     * \brief Returns true if any elevator in the system is active, false otherwise
     *
     * \return true if any elevator in the system is active, false otherwise
     */
    public boolean is_active() {
        boolean active = false;
        for (Elevator elevator : elevators_) {
            if (elevator.get_elevator_state() != ElevatorState.INACTIVE) {
                active = true;
                break;
            }
        }

        return active;
    }

    /*!
     * \brief Add a stop to an elevator within the system.
     *
     * \param floor Floor to stop on
     */
    public void add_stop(int floor) throws IllegalArgumentException  {
        // Error Checking on floor request
        if (floor < 1 || floor > num_floors_ ) {
            throw new IllegalArgumentException("Elevator.add_stop() - floor number must be between 1 and num_floors.");
        }

        // Determine the best Elevator to use
        Elevator best_elevator = null;
        int min_time = 0;

        for (Elevator elevator : elevators_) {
            // Get the estimated time to reach the floor
            int estimated_time = elevator.estimate_time_to_stop(floor);
            if (best_elevator == null || estimated_time < min_time) {
                best_elevator = elevator;
                min_time = estimated_time;
            }
        }

        // Add the stop
        if (best_elevator != null) {
            best_elevator.add_stop(floor);
        }
    }

    //--------------------------------------
    // Class Attributes
    //--------------------------------------

    private int num_floors_;                    //!< Number of floors in the building
    private ArrayList<Elevator> elevators_;     //!< Elevators contained within the builidng
}
