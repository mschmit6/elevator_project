/*!
 * \file StopRequest.java
 * \brief Defines the stop requested by a user
 */

// Define package
package ElevatorSystem;

// Imports
import java.util.ArrayList;
import ElevatorSystem.DirectionRequest;

public class StopRequest {

    //--------------------------------------
    // Constructors
    //--------------------------------------

    /*!
     * \brief Define a stop request given the floor and direction
     *
     * \param floor Floor of the requested stop
     * \param dir_request Direction that this sends the elevator in
     */
    public StopRequest(int floor, DirectionRequest dir_request) {
        this.floor_ = floor;
        this.dir_ = dir_request;
    }

    //--------------------------------------
    // Class Methods
    //--------------------------------------

    /*!
     * \brief Return the floor of the requested stop
     *
     * \return The floor of the requested stop
     */
    int get_floor() {
        return floor_;
    }

    /*!
     * \brief Return the direction that the user request will send the elevator in
     *
     * \return The direction that the user request will send the elevator in
     */
    DirectionRequest get_direction_request() {
        return dir_;
    }

    //--------------------------------------
    // Class Attributes
    //--------------------------------------

    private int floor_;             //!< Floor that the elevator is requested at
    private DirectionRequest dir_;  //!< Defines the direction that the user request will send the elevator in

}
