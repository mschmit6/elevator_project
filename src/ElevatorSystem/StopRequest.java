/*!
 * \file StopRequest.java
 * \brief Defines the stop requested by a user, specifying the destination floor and potentially the origin floor
 */

// Define package
package ElevatorSystem;

// Standard libray imports
import java.util.HashSet;
import java.util.Set;

public class StopRequest implements Comparable<StopRequest>{

    //--------------------------------------
    // Constructors
    //--------------------------------------

    /*!
     * \brief Define a stop request with a single stop
     *
     * \note This assumes that the users are already on the Elevator, and selecting their final destination
     *
     * \param single_stop Destination floor for the stop request
     */
    public StopRequest(int single_stop) {
        pick_up_floor_ = single_stop;
        drop_off_floors_ = new HashSet<Integer>();
    }

    /*!
     * \brief Define a stop request given the current floor (or the floor the elevator was on when the button was pressed)
     * and the destination floor
     *
     * \param pick_up_floor The floor on which the elevator button was pressed
     * \param dest_floor The destination floor once the user enters the elevator
     */
    public StopRequest(int pick_up_floor, int dest_floor) {
        pick_up_floor_ = pick_up_floor;
        drop_off_floors_ = new HashSet<Integer>();
        drop_off_floors_.add(dest_floor);
    }


    /*!
     * \brief Define a stop request given the current floor (or the floor the elevator was on when the button was pressed)
     * and an array of destination floors
     *
     * \note This is intended to simulate multiple people getting on at the same stop
     *
     * \param pick_up_floor The floor on which the elevator button was pressed
     * \param drop_off_floors Array of destination floors once the users enters the elevator
     */
    public StopRequest(int pick_up_floor, Set<Integer> drop_off_floors) {
        pick_up_floor_ = pick_up_floor;
        drop_off_floors_ = new HashSet<Integer>(drop_off_floors);
    }

    //--------------------------------------
    // Operator Overloads
    //--------------------------------------

    /*!
     * \brief Override comparison operator allowing StopRequest objects to be sorted
     *
     * \return Comparison between two integers
     */
    @Override
    public int compareTo(StopRequest other) throws IllegalArgumentException {
        return Integer.compare(this.get_pick_up_floor(), other.get_pick_up_floor());
    }

    /*!
     * \brief Override comparison operator allowing StopRequest objects to be sorted
     *
     * \return Comparison between two integers
     */
    @Override
    public boolean equals(Object other) {
        // If the object is compared with itself then return true
        if (other == this) {
            return true;
        }
        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(other instanceof StopRequest)) {
            return false;
        }

        StopRequest oth = (StopRequest) other;
        return this.get_pick_up_floor() == oth.get_pick_up_floor();
    }

    //--------------------------------------
    // Class Methods
    //--------------------------------------

    /*!
     * \brief Return the number of stops remaining in the StopRequest after the origin floor has been reached
     *
     * \return The number of stops remaining in the StopRequest after the origin floor has been reached
     */
    public int size() {
        return drop_off_floors_.size();
    }

    /*!
     * \brief Peek at the next floor in the StopRequest, without removing it from the queue
     *
     * \return Peek at the next floor in the StopRequest, without removing it from the queue
     */
    public int get_pick_up_floor() {
        return pick_up_floor_;
    }

    /*!
     * \brief Return the destination floors
     *
     * \return The destination floors
     */
    public Set<Integer> get_drop_off_floors() {
        return drop_off_floors_;
    }

    /*!
     * \brief Combine the destination floors of two StopRequests with the same origin floor
     *
     * \param other StopRequest whose destination floors are being merged into the current object
     */
    public void combine_destination_floors(StopRequest other) {
        // Only combine the destination floors if the origins are equal
        if (get_pick_up_floor() == other.get_pick_up_floor()) {
            drop_off_floors_.addAll(other.get_drop_off_floors());
        }
    }

    //--------------------------------------
    // Class Attributes
    //--------------------------------------

    private int pick_up_floor_;            //!< The floor at which members are getting picked up
    private Set<Integer> drop_off_floors_;    //!< The floors where those picked up wish to be dropped off
}
