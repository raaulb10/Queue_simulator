
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private Integer totalWaitingPeriod;
    private Integer totalProcessingPeriod;
    private int serverID;
    private BlockingQueue<Task> clients;
    private AtomicInteger waitingPeriod;


    public Server(int Servernum, int Serverid) {
        totalProcessingPeriod = 0;
        totalWaitingPeriod = 0;
        serverID = Serverid;
        clients = new ArrayBlockingQueue<>(Servernum);
        waitingPeriod = new AtomicInteger(0);

    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public Integer getTotalWaitingPeriod() {
        return totalWaitingPeriod;
    }

    public Integer getTotalProcessingPeriod() {
        return totalProcessingPeriod;
    }

    public BlockingQueue<Task> getClients() {
        return clients;
    }

    public void setClients(BlockingQueue<Task> clients) {
        this.clients = clients;
    }


    public void addTask(Task newTask) throws InterruptedException {
        newTask.setQueueID(serverID);
        newTask.setWaitingPeriod(waitingPeriod.get());
        clients.put(newTask);
        waitingPeriod.getAndAdd(newTask.getProcessingPeriod());
    }

    public void run() {
        for (; ; ) {
            try {
                if (!clients.isEmpty()) {
                    Task client = clients.peek();
                    client.setFinishTime();
                    waitingPeriod.set(waitingPeriod.get() - client.getProcessingPeriod());
                    totalWaitingPeriod += client.getFinishTime();
                    totalProcessingPeriod += client.getProcessingPeriod();
                    while (client.getProcessingPeriod() > 1) {
                        client.decrementProcessingPeriod();
                        Thread.sleep(1000);
                    }
                    clients.take();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String toString() {
        String string = "Queue " + (serverID + 1) + ": ";
        if (!clients.isEmpty())
            for (Task t : clients)
                string = string + t.toString() + ";";

        else {
            string = string + "Queue is closed! ";
        }
        return string;
    }
}
