/*!
 * \file Elevator.java
 * \brief Defines the Elevator class
 */

// Define package
package ElevatorSystem;

// Imports
import java.util.ArrayList;
import ElevatorSystem.ElevatorState;
import ElevatorSystem.StopRequest;

public class Elevator {

    //--------------------------------------
    // Constructors
    //--------------------------------------

    /*!
     * \brief Construct an Elevator object, specifying the number of floors that it operates on, the drop off time,
     * and time it takes to move between floors
     *
     * \param num_floors Number of floors that the elevator operates on
     * \param drop_off_time Amount of time for which the doors stay open when picking up/dropping off [sec]
     * \param time_between_floors Time it takes the elevator to move between floors [sec]
     */
    public Elevator(int num_floors, double drop_off_time, double time_between_floors) {
        // Error Checking
        if (num_floors <= 1) {
            throw new Exception("Elevator() - num_floors value must be greater than or equal to 2.");
        }
        if (drop_off_time <= 0.) {
            throw new Exception("Elevator() - drop_off_time value must be greater than 0.");
        }
        if (time_between_floors <= 0.) {
            throw new Exception("Elevator() - time_between_floors value must be greater than 0.");
        }

        // Set values
        this.num_floors_ = num_floors;
        this.cur_floor_ = 1;
        this.drop_off_time_ = drop_off_time;
        this.time_between_floors_ = time_between_floors;
        this.elevator_state_ = ElevatorState.NEUTRAL;
        this.queue_ = new ArrayList<Integer>();
    }


    //--------------------------------------
    // Class Methods
    //--------------------------------------

    /*!
     * \brief Return the current floor that the elevator is at
     *
     * \return The current floor that the elevator is at
     */
    public int get_current_floor() {
        return cur_floor_;
    }

    /*!
     * \brief Return the number of floors that the elevator operates on
     *
     * \return The number of floors that the elevator operates on
     */
    public int get_num_floors() {
        return num_floors_;
    }

    /*!
     * \brief Return the amount of time for which the doors stay open when picking up/dropping off
     *
     * \return The amount of time for which the doors stay open when picking up/dropping off [sec]
     */
    public double get_drop_off_time() {
        return drop_off_time_;
    }

    /*!
     * \brief Return time it takes the elevator to move between floors
     *
     * \return The time it takes the elevator to move between floors [sec]
     */
    public double get_time_between_floors() {
        return time_between_floors_;
    }

    /*!
     * \brief Return the state of the current elevator
     *
     * \return The state of the current elevator
     */
    public ElevatorState get_elevator_state() {
        return elevator_state_;
    }

    /*!
     * \brief Add a new stop to the Elevator
     *
     * \param floor Floor of the requested stop
     */
    public void add_stop_request(int floor) {
        // Error Checking on floor request
        if (floor < 1 || floor > num_floors_ ) {
            throw new Exception("Elevator.add_stop_request() - floor number must be between 1 and num_floors.");
        }

        // Add stop to the appropriate queue based on if it requires the elevator to ascend or descend
        // Note: If you are at the current floor nothing will happen
        if (floor > cur_floor_) {
            add_stop_to_queue(floor, asc_queue_);
        } else if (floor < cur_floor_) {
            add_stop_to_queue(floor, des_queue_);
        }
    }

    /*!
     * \brief Add a new stop to the specified queue
     *
     * \param floor Floor of the requested stop
     * \param queue Queue to add the stop to
     */
    private void add_stop_to_queue(int floor, ArrayList<int> queue) {
        // Check to make sure the listed floor isn't already in the queue
        boolean duplicate = false;
        for (int floor_req in asc_queue_) {
            if (floor_req == floor) {
                duplicate = true;
                break;
            }
        }

        if (!duplicate) {
            // TODO: Sort queue after adding or use container that naturally sorts
            queue.add(floor);
        }
    }

    //--------------------------------------
    // Class Attributes
    //--------------------------------------

    private int num_floors_;                //!< Number of floors that the elevator operates on
    private int cur_floor_;                 //!< The current floor that the elevator is at
    private double drop_off_time_;          //!< Amount of time for which the doors stay open when picking up/dropping off [sec]
    private double time_between_floors_;    //!< Time it takes the elevator to move between floors [sec]
    private ElevatorState elevator_state_;  //!< Defines the state of the current elevator
    private ArrayList<int> asc_queue_;      //!< Queue defining stop requests in the ascending direction
    private ArrayList<int> des_queue_;      //!< Queue defining stop requests in the ascending direction
}
