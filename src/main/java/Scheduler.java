import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Scheduler {
    private List<Server> servers;
    private int maxNoOfServers;
    private int maxNoOfTasksPerServer;

    public Scheduler(int maxNoOfServers, int maxNoOfTasksPerServer) {
        this.maxNoOfServers = maxNoOfServers;
        this.maxNoOfTasksPerServer = maxNoOfTasksPerServer;
        servers = Collections.synchronizedList(new ArrayList<>());
        for(int i=0;i<maxNoOfServers;i++){
            servers.add(new Server(maxNoOfTasksPerServer,i));
            Thread thread=new Thread(servers.get(i));
            thread.start();
        }
    }

    public void dispatchTask(Task client) throws InterruptedException {
        int currentServer = 0;
        int minWaitingTime = Integer.MAX_VALUE;
        for (int i = 0; i < servers.size(); i++) {
            if (servers.get(i).getWaitingPeriod().get() < minWaitingTime) {
                minWaitingTime = servers.get(i).getWaitingPeriod().get();
                currentServer = i;
            }
        }
        servers.get(currentServer).addTask(client);
    }


    public List<Server> getServers() {
        return servers;
    }
}
