/*!
 * \file StopRequest.java
 * \brief Defines the stop requested by a user
 */

// Define package
package ElevatorSystem;

// Standard libray imports
import java.util.Queue;

public class StopRequest implements Comparable<StopRequest>{

    //--------------------------------------
    // Constructors
    //--------------------------------------

    /*!
     * \brief Define a stop request given the current floor (or the floor the elevator was on when the button was pressed)
     * and the destination floor
     *
     * \param origin_floor The floor on which the elevator button was pressed
     * \param dest_floor The destination floor once the user enters the elevator
     */
    public StopRequest(int origin_floor, int dest_floor) {
        floors.add(dest_floor);
        floors.add(dest_floor);
    }

    /*!
     * \brief Define a stop request given only the destination floor
     *
     * \param dest_floor The destination floor once the user enters the elevator
     */
    public StopRequest(int dest_floor) {
        floors.add(dest_floor);
    }

    /*!
     * \brief Override comparison operator allowing StopRequest objects to be sorted
     */
    @Override
    public int compareTo(StopRequest other) {
        return Integer.compare(this.floors.peek(), other.floors.peek());
    }

    //--------------------------------------
    // Class Attributes
    //--------------------------------------

    public Queue<Integer> floors;    //!< The floor on which the elevator button was pressed

}
