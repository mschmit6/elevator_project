// Standard Library Imports
import java.util.PriorityQueue;

// Elevator System imports
import ElevatorSystem.StopRequest;

// Attempted to set up junit, but that wasn't working so made an example to mimic what would be the junit test
public class StopRequestTest {

    //! Main function to call all of the tests
    public static void main(String[] args) throws Exception {
        test_stop_request_comparison();
        test_stop_request_queue();
    }

    //! Test comparisons between different stop requests
    public static void test_stop_request_comparison() throws Exception {

        StopRequest request1 = new StopRequest(2, 5);
        StopRequest request2 = new StopRequest(3, 7);

        if (request1.compareTo(request2) > 0) {
            throw new Exception("First comparison between request1 and request2 failed.");
        }

        if (!(request2.compareTo(request1) > 0)) {
            throw new Exception("Second comparison between request1 and request2 failed.");
        }
    }

    //! Test adding stop requests to a queue
    public static void test_stop_request_queue() throws Exception {

        StopRequest request1 = new StopRequest(2, 5);
        StopRequest request2 = new StopRequest(3, 7);

        PriorityQueue<StopRequest> queue = new PriorityQueue<StopRequest>();
        queue.add(request2);
        queue.add(request1);

        StopRequest request = queue.peek();

        // Throw exception, as request should equal request1
        if (!request.equals(request1)) {
            throw new Exception("First equality between request1 and request2 failed.");
        }

        // Throw exception, as request should NOT equal request2
        if (request.equals(request2)) {
            throw new Exception("Second equality between request1 and request2 failed.");
        }
    }
}
