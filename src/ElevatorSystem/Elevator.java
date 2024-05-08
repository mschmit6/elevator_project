/*!
 * \file Elevator.java
 * \brief Defines the Elevator class
 */

// Define package
package ElevatorSystem;

// Imports
import java.lang.Math;
import java.util.PriorityQueue;
import ElevatorSystem.DirectionRequest;

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
    public Elevator(int num_floors, double drop_off_time, double time_between_floors) throws IllegalArgumentException {
        // Error Checking
        if (num_floors <= 1) {
            throw new IllegalArgumentException("Elevator() - num_floors value must be greater than or equal to 2.");
        }
        if (drop_off_time <= 0.) {
            throw new IllegalArgumentException("Elevator() - drop_off_time value must be greater than 0.");
        }
        if (time_between_floors <= 0.) {
            throw new IllegalArgumentException("Elevator() - time_between_floors value must be greater than 0.");
        }

        // Set values
        this.num_floors_ = num_floors;
        this.cur_floor_ = 1;
        this.drop_off_time_ = drop_off_time;
        this.time_between_floors_ = time_between_floors;
        this.elevator_state_ = ElevatorState.NEUTRAL;
        this.asc_queue_ = new PriorityQueue<Integer>();
        this.des_queue_ = new PriorityQueue<Integer>((a, b) -> b - a);
    }

    /*!
     * \brief Construct an Elevator object, specifying the number of floors that it operates on
     *
     * \note The drop_off_time defaults to 60 seconds, and time_between_floors defaults to 30 seconds
     *
     * \param num_floors Number of floors that the elevator operates on
     */
    public Elevator(int num_floors) {
        // Error Checking
        if (num_floors <= 1) {
            throw new IllegalArgumentException("Elevator() - num_floors value must be greater than or equal to 2.");
        }

        // Set values
        this.num_floors_ = num_floors;
        this.cur_floor_ = 1;
        this.drop_off_time_ = 60.0;
        this.time_between_floors_ = 30.0;
        this.elevator_state_ = ElevatorState.NEUTRAL;
        this.asc_queue_ = new PriorityQueue<Integer>((a, b) -> b - a);
        this.des_queue_ = new PriorityQueue<Integer>((a, b) -> b - a);
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
     * \brief Set the current floor that the elevator is at
     *
     * \param cur_floor The current floor that the elevator is at
     */
    public void set_current_floor(int cur_floor) {
        cur_floor_ = cur_floor;
    }

    /*!
     * \brief Get the load currently on the Elevator, which is the sum of the number of stops that it has to support
     *
     * \return The load currently on the Elevator
     */
    public int get_load() {
        return asc_queue_.size() + des_queue_.size();
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
     * \brief Provides an estimate for how long it would take this Elevator to get to the requested floor
     */
    public double get_time_to_stop(int floor) throws IllegalArgumentException {


        return 0;
    }

    /*!
     * \brief Return the queue of stops in the ascending direction
     *
     * \return The queue of stops in the ascending direction
     */
    public PriorityQueue<Integer> get_ascending_queue() {
        return asc_queue_;
    }

    /*!
     * \brief Return the queue of stops in the descending direction
     *
     * \return The queue of stops in the descending direction
     */
    public PriorityQueue<Integer> get_descending_queue() {
        return des_queue_;
    }

    /*!
     * \brief Add a new stop to the Elevator
     *
     * \param floor Floor of the requested stop
     */
    public void add_stop_request(int floor) throws IllegalArgumentException  {
        // Error Checking on floor request
        if (floor < 1 || floor > num_floors_ ) {
            throw new IllegalArgumentException("Elevator.add_stop_request() - floor number must be between 1 and " + Integer.toString(num_floors_) + ".");
        }

        // Add stop to the appropriate queue based on if it requires the elevator to ascend or descend
        // Note: If you are at the current floor nothing will happen
        if (floor > cur_floor_ && !asc_queue_.contains(floor)) {
            asc_queue_.add(floor);
        } else if (floor < cur_floor_ && !des_queue_.contains(floor)) {
            des_queue_.add(floor);
        }
    }

    /*!
     * \brief Estimate the time it will take for the current elevator to reach the target floor, fitting the tgt_floor
     * in where it makes sense
     *
     * \param tgt_floor Target floor for the time request
     *
     * \return Estimated time to reach the target floor
     */
    public double estimate_time_to_floor(int tgt_floor) {
        // If you are already at the specified floor, return 0
        if (tgt_floor == cur_floor_) {
            return 0.0;
        }

        // Determine which direction you would have to go to get to the target floor
        DirectionRequest direction = tgt_floor > cur_floor_ ? DirectionRequest.ASCENDING : DirectionRequest.DESCENDING;

        // Compute the expected amount of time it would take to get to the tgt_floor
        double elapsed_time = 0.;
        switch (elevator_state_) {
            case ElevatorState.NEUTRAL: {
                // No other stops, so time to stop is just the time to travel to the target floor
                elapsed_time = Math.abs(tgt_floor - cur_floor_) * time_between_floors_;
            }
            case ElevatorState.ASCENDING: {
                int start_floor = cur_floor_;
                if (direction == DirectionRequest.DESCENDING) {
                    // Finish out the ascending queue first before descending back to the target floor to the target floor
                    QueueEvaluation queue_eval = evaluate_queue(asc_queue_, cur_floor_, num_floors_ + 1);
                    elapsed_time = queue_eval.elapsed_time;
                    start_floor = queue_eval.cur_floor;
                }

                // Compute time required to now descend to target floor
                QueueEvaluation queue_eval = evaluate_queue(des_queue_, start_floor, tgt_floor);
                elapsed_time += queue_eval.elapsed_time;
                break;
            }
            case ElevatorState.DESCENDING: {
                int start_floor = cur_floor_;
                if (direction == DirectionRequest.ASCENDING) {
                    // Finish out the descending queue first before descending back to the target floor to the target floor
                    QueueEvaluation queue_eval = evaluate_queue(des_queue_, cur_floor_, 0);
                    elapsed_time = queue_eval.elapsed_time;
                    start_floor = queue_eval.cur_floor;
                }

                // Compute time required to now ascend to target floor
                QueueEvaluation queue_eval = evaluate_queue(asc_queue_, start_floor, tgt_floor);
                elapsed_time += queue_eval.elapsed_time;
                break;
            }
        }

        return elapsed_time;
    }

    /*!
     * \brief Evaluate the amount of time it would take to get through the current queue of stops
     *
     * \param stop_queue Queue of stops to evaluate
     *
     * \return A pair containign the Amount of time it would take to get through the current queue of stops,
     * and the last floor that was stopped at
     */
    public QueueEvaluation evaluate_queue(PriorityQueue<Integer> stop_queue, int start_floor, int stop_floor) {
        // If you are starting at the requested stop floor, return 0 for no time elapsed
        if (start_floor == stop_floor) {
            return new QueueEvaluation(0, start_floor);
        }

        // Initialize elapsed time and current floor
        double elapsed_time = 0.;
        int cur_floor = start_floor;

        // Sum the time it would take to get through the queue
        for (int floor : stop_queue) {

            // Break early if you've reached the stop floor
            if (floor == stop_floor) {
                // Update time required to get to the new floor only, but don't add in stop time
                elapsed_time += Math.abs(floor - cur_floor) * time_between_floors_;
                break;
            }

            // Compute elapsed time. Assume no elapsed time if you are already at the floor from the queue
            if (floor != cur_floor) {
                elapsed_time += drop_off_time_ + Math.abs(floor - cur_floor) * time_between_floors_;
            }
            cur_floor = floor;
        }

        return new QueueEvaluation(elapsed_time, cur_floor);
    }

    private class QueueEvaluation {

        QueueEvaluation(double elapsed_time, int cur_floor) {
            this.elapsed_time = elapsed_time;
            this.cur_floor = cur_floor;
        }
        public double elapsed_time;
        public int cur_floor;
    }
    //--------------------------------------
    // Class Attributes
    //--------------------------------------

    private int num_floors_;                //!< Number of floors that the elevator operates on
    private int cur_floor_;                 //!< The current floor that the elevator is at
    private double drop_off_time_;          //!< Amount of time for which the doors stay open when picking up/dropping off [sec]
    private double time_between_floors_;    //!< Time it takes the elevator to move between floors [sec]
    private ElevatorState elevator_state_;  //!< Defines the state of the current elevator
    private PriorityQueue<Integer> asc_queue_;  //!< Queue defining stop requests in the ascending direction
    private PriorityQueue<Integer> des_queue_;   //!< Queue defining stop requests in the ascending direction
}
