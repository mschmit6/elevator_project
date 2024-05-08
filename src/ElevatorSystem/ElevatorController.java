/*!
 * \file ElevatorController.java
 * \brief Defines the Elevator class
 */

// Define package
package ElevatorSystem;

// Imports
import java.util.ArrayList;
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
        this.num_floors_ = num_floors;
        this.num_elevators_ = num_elevators;

    }


    //--------------------------------------
    // Class Methods
    //--------------------------------------

    /*!
     * \brief Add a new stop to the Elevator
     *
     * \param floor Floor of the requested stop
     */
    public void add_stop_request(int floor) throws IllegalArgumentException  {
        // Error Checking on floor request
        if (floor < 1 || floor > num_floors_ ) {
            throw new IllegalArgumentException("Elevator.add_stop_request() - floor number must be between 1 and num_floors.");
        }

        // Determine the best Elevator to use
    }

    //--------------------------------------
    // Class Attributes
    //--------------------------------------

    private int num_floors_;                    //!< Number of floors in the building
    private int num_elevators_;                 //!< Number of elevators in the building
    private ArrayList<Elevator> elevators_;     //!< Elevators contained within the builidng
}
