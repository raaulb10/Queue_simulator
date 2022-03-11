import java.util.Collections;
import java.util.List;

public class Generator {
    public static void generateRandomTasks(List<Task> waitingQ, int clientNr, int minArrivalTime, int maxArrivalTime, int minProcessingTime, int maxProcessingTime) {

        for (int i = 0; i < clientNr; i++) {
            int arrivalTime = (int) (Math.random() * (maxArrivalTime - minArrivalTime) + minArrivalTime);
            int processingTime = (int) (Math.random() * (maxProcessingTime - minProcessingTime) + minProcessingTime);
            Task client = new Task(i, arrivalTime, processingTime);
            waitingQ.add(client);
        }
        Collections.sort(waitingQ);
    }
}
