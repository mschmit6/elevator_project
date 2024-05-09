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
     * \brief Construct an Elevator object, specifying the number of floors that it operates on
     *
     * \note The drop_off_time defaults to 60 seconds, and time_between_floors defaults to 30 seconds
     *
     * \param name Name of the elevator
     * \param num_floors Number of floors that the elevator operates on
     */
    public Elevator(String name, int num_floors) {
        // Error Checking
        if (num_floors <= 1) {
            throw new IllegalArgumentException("Elevator() - num_floors value must be greater than or equal to 2.");
        }

        // Set values
        name_ = name;
        time_ = 0;
        num_floors_ = num_floors;
        cur_floor_ = 1;                                 // Elevator starts on the first floor
        drop_off_time_unit_ = 2;                        // An elevator will stay at a stop for 2 time steps
        move_time_unit_ = 1;                            // It takes the elevator one time step to move one floor
        elevator_state_ = ElevatorState.INACTIVE;       // Elevator starts out inactive
        at_stop_count_ = 0;
        asc_queue_ = new PriorityQueue<Integer>();
        des_queue_ = new PriorityQueue<Integer>((a, b) -> b - a);
    }


    //--------------------------------------
    // Public Class Methods
    //--------------------------------------

    /*!
     * \brief Return the name of the elevator
     *
     * \return The name of the elevator
     */
    public String get_name() {
        return name_;
    }
    /*!
     * \brief Return the current floor that the elevator is at
     *
     * \return The current floor that the elevator is at
     */
    public int get_current_floor() {
        return cur_floor_;
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
     * \brief Return the state of the current elevator
     *
     * \return The state of the current elevator
     */
    public ElevatorState get_elevator_state() {
        return elevator_state_;
    }


    /*!
     * \brief Step one time step into the future, returning the floor that the elevator ends up at
     *
     * \return The floor that the elevator is at after the time step
     */
    public int step() {
        // Increment the current time by one time unit
        time_ += 1;

        switch (elevator_state_) {
            case ElevatorState.INACTIVE: {
                break;
            }
            case ElevatorState.ASCENDING: {
                if (cur_floor_ == asc_queue_.peek()) {
                    // Elevator is stopping at a drop off point
                    at_stop_count_ += 1;

                    // If we have reached the max stop time, so remove this stip from the queue
                    if (at_stop_count_ == drop_off_time_unit_) {
                        // Remove the stop and reset the at_stop_count
                        asc_queue_.poll();
                        at_stop_count_ = 0;

                        // Update the elevator state, if necessary
                        if (asc_queue_.size() == 0) {
                            elevator_state_ = des_queue_.size() > 0 ? ElevatorState.DESCENDING : ElevatorState.INACTIVE;
                        }
                    }
                } else {
                    // Moves one floor in a given time step
                    cur_floor_ += 1;
                }
                break;
            }
            case ElevatorState.DESCENDING: {
                if (cur_floor_ == des_queue_.peek()) {
                    // Elevator is stopping at a drop off point
                    at_stop_count_ += 1;

                    // If we have reached the max stop time, so remove this stip from the queue
                    if (at_stop_count_ == drop_off_time_unit_) {
                        // Remove the stop and reset the at_stop_count
                        des_queue_.poll();
                        at_stop_count_ = 0;

                        // Update the elevator state, if necessary
                        if (des_queue_.size() == 0) {
                            elevator_state_ = asc_queue_.size() > 0 ? ElevatorState.ASCENDING : ElevatorState.INACTIVE;
                        }
                    }
                } else {
                    // Moves one floor in a given time step
                    cur_floor_ -= 1;
                }
                break;
            }
        }

        return cur_floor_;
    }

    /*!
     * \brief Add a new stop to the Elevator
     *
     * \param floor Floor of the requested stop
     */
    public void add_stop(int floor) throws IllegalArgumentException  {
        // Error Checking on floor request
        if (floor < 1 || floor > num_floors_ ) {
            throw new IllegalArgumentException("Elevator.add_stop() - floor number must be between 1 and " + Integer.toString(num_floors_) + ".");
        }

        // Add stop to the appropriate queue based on if it requires the elevator to ascend or descend
        // Note: If you are at the current floor nothing will happen
        if (floor > cur_floor_ && !asc_queue_.contains(floor)) {
            asc_queue_.add(floor);

            // Update the elevator state if it is inactive to set the direction for the next step
            if (elevator_state_ == ElevatorState.INACTIVE) {
                elevator_state_ = ElevatorState.ASCENDING;
            }

        } else if (floor < cur_floor_ && !des_queue_.contains(floor)) {
            des_queue_.add(floor);

            // Update the elevator state if it is inactive to set the direction for the next step
            if (elevator_state_ == ElevatorState.INACTIVE) {
                elevator_state_ = ElevatorState.DESCENDING;
            }
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
    public int estimate_time_to_stop(int tgt_floor) {
        // If you are already at the specified floor, return 0
        if (tgt_floor == cur_floor_) {
            return 0;
        }

        // Determine which direction you would have to go to get to the target floor
        DirectionRequest direction = tgt_floor > cur_floor_ ? DirectionRequest.ASCENDING : DirectionRequest.DESCENDING;

        // Compute the expected amount of time it would take to get to the tgt_floor
        int elapsed_time = 0;
        switch (elevator_state_) {
            case ElevatorState.INACTIVE: {
                // No other stops, so time to stop is just the time to travel to the target floor
                elapsed_time = Math.abs(tgt_floor - cur_floor_) * move_time_unit_;
                break;
            }
            case ElevatorState.ASCENDING: {
                int start_floor = cur_floor_;
                if (direction == DirectionRequest.DESCENDING) {
                    // Finish out the ascending queue first before descending back to the target floor to the target floor
                    QueueEvaluation queue_eval = evaluate_queue(asc_queue_, cur_floor_, num_floors_ + 1);
                    elapsed_time = queue_eval.elapsed_time;
                    start_floor = queue_eval.cur_floor;

                    // Compute time required to now descend to target floor
                    queue_eval = evaluate_queue(des_queue_, start_floor, tgt_floor);
                    elapsed_time += queue_eval.elapsed_time;
                } else {
                    // Compute time required to now ascend to the destination floor to target floor
                    QueueEvaluation queue_eval = evaluate_queue(asc_queue_, start_floor, tgt_floor);
                    elapsed_time += queue_eval.elapsed_time;
                }

                break;
            }
            case ElevatorState.DESCENDING: {
                int start_floor = cur_floor_;
                if (direction == DirectionRequest.ASCENDING) {
                    // Finish out the descending queue first before descending back to the target floor to the target floor
                    QueueEvaluation queue_eval = evaluate_queue(des_queue_, cur_floor_, 0);
                    elapsed_time = queue_eval.elapsed_time;
                    start_floor = queue_eval.cur_floor;

                    // Compute time required to now ascend to target floor
                    queue_eval = evaluate_queue(asc_queue_, start_floor, tgt_floor);
                    elapsed_time += queue_eval.elapsed_time;
                } else {
                    // Compute time required to now descend to target floor
                    QueueEvaluation queue_eval = evaluate_queue(des_queue_, start_floor, tgt_floor);
                    elapsed_time += queue_eval.elapsed_time;
                }

                break;
            }
        }

        return elapsed_time;
    }

    //--------------------------------------
    // Private Class Methods
    //--------------------------------------

    /*!
     * \brief Evaluate the amount of time it would take to get through the current queue of stops
     *
     * \param stop_queue Queue of stops to evaluate
     *
     * \return A pair containign the Amount of time it would take to get through the current queue of stops,
     * and the last floor that was stopped at
     */
    private QueueEvaluation evaluate_queue(PriorityQueue<Integer> stop_queue, int start_floor, int stop_floor) {
        // If you are starting at the requested stop floor, return 0 for no time elapsed
        if (start_floor == stop_floor) {
            return new QueueEvaluation(0, start_floor);
        }

        // Initialize elapsed time and current floor
        int elapsed_time = 0;
        int cur_floor = start_floor;

        // Sum the time it would take to get through the queue
        for (int floor : stop_queue) {

            // Break early if you've reached the stop floor
            if (floor == stop_floor) {
                // Update time required to get to the new floor only, but don't add in stop time
                elapsed_time += Math.abs(floor - cur_floor) * move_time_unit_;
                break;
            }

            // Compute elapsed time. Assume no elapsed time if you are already at the floor from the queue
            if (floor != cur_floor) {
                elapsed_time += drop_off_time_unit_ + Math.abs(floor - cur_floor) * move_time_unit_;
            }
            cur_floor = floor;
        }

        return new QueueEvaluation(elapsed_time, cur_floor);
    }

    private class QueueEvaluation {

        QueueEvaluation(int elapsed_time, int cur_floor) {
            this.elapsed_time = elapsed_time;
            this.cur_floor = cur_floor;
        }
        public int elapsed_time;
        public int cur_floor;
    }
    //--------------------------------------
    // Class Attributes
    //--------------------------------------

    private String name_;                       //!< Name of the elevator
    private int time_;                          //!< Time, stored as integer number of "time units"
    private int num_floors_;                    //!< Number of floors that the elevator operates on
    private int cur_floor_;                     //!< The current floor that the elevator is at
    private int drop_off_time_unit_;            //!< Amount of time for which the doors stay open when picking up/dropping off [sec]
    private int move_time_unit_;                //!< Time it takes the elevator to move between floors [sec]
    private ElevatorState elevator_state_;      //!< Defines the state of the current elevator
    private int at_stop_count_;                 //!< Integer indicating number of time counts that the elevator has been at a stop for
    private PriorityQueue<Integer> asc_queue_;  //!< Queue defining stop requests in the ascending direction
    private PriorityQueue<Integer> des_queue_;  //!< Queue defining stop requests in the ascending direction
}
