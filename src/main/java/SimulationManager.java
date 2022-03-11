import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.io.FileWriter;
import java.io.IOException;

public class SimulationManager implements Runnable {
    private static Integer simulationTime;
    private static Integer clientNr;
    private static Integer queueNr;
    private static Integer minArrivalTime;
    private static Integer maxArrivalTime;
    private static Integer minProcessingTime;
    private static Integer maxProcessingTime;
    private static Scheduler scheduler;
    private static View GUI;
    private static List<Task> waitingQ;
    private boolean canStart;
    private static FileWriter fileLog;
    private static Integer peakTime;
    private static Integer maxClients;
    private static int currentTime;

    public SimulationManager() throws InterruptedException {
        GUI = new View();
        GUI.addStartListener(new StartListener());
        while (canStart == false) {
            Thread.sleep(100);
        }
        waitingQ = Collections.synchronizedList(new ArrayList<>());
        Generator.generateRandomTasks(waitingQ, clientNr, minArrivalTime, maxArrivalTime, minProcessingTime, maxProcessingTime);
        scheduler = new Scheduler(queueNr, clientNr);
    }

    public void getData() {
        peakTime = 0;
        maxClients = 0;
        simulationTime = Integer.parseInt(GUI.getsimulationTime());
        clientNr = Integer.parseInt(GUI.getNrclientst());
        queueNr = Integer.parseInt(GUI.getNrqueuest());
        minArrivalTime = Integer.parseInt(GUI.getMinArrivalTimet());
        maxArrivalTime = Integer.parseInt(GUI.getMaxArrivalTimet());
        minProcessingTime = Integer.parseInt(GUI.getMinProcessTimet());
        maxProcessingTime = Integer.parseInt(GUI.getMaxProcessTimet());
    }
    
    private void openFile() {
        try {
            fileLog = new FileWriter("fileLog.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeFile() {
        try {
            fileLog.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeResults(int currentTime) {
        String message = "Time " + currentTime + "\n";
        message = message + "Waiting clients :";
        for (Task t : waitingQ) {
            message = message + t.toString();
            message = message + ";";
        }
        message = message + "\n";
        for (Server s : scheduler.getServers()) {
            message = message + s.toString();
            message = message + "\n";
        }
        message = message + "\n";
        try {
            this.fileLog.append(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.GUI.setlogst(message);
    }

    private double averageWaitingTime() {
        int totalWaitingTime = 0;
        for (Server current : scheduler.getServers()) {
            totalWaitingTime += current.getTotalWaitingPeriod();
        }
        double averageWaitingTime = ((double) totalWaitingTime / (double) (clientNr - waitingQ.size()));
        return Math.round(averageWaitingTime * 100.0) / 100.0;
    }

    private double averageProcessingTime() {
        int totalProcessingTime = 0;
        for (Server current : scheduler.getServers()) {
            totalProcessingTime += current.getTotalProcessingPeriod();
        }
        double averageProcessingTime = ((double) totalProcessingTime / (double) (clientNr - waitingQ.size()));
        return Math.round(averageProcessingTime * 100.0) / 100.0;
    }

    private void peak(int currentTime) {
        int totalnrclients = 0;
        for (Server current : scheduler.getServers()) {
            //totalnrclients += current.getWaitingPeriod().intValue();
            totalnrclients += current.getClients().size();

        }
        if (totalnrclients >= maxClients) {
            peakTime = currentTime;
            maxClients = totalnrclients;
        }
        if (totalnrclients == 0 && waitingQ.size() == 0) {
            canStart = false;
        }
    }

    private void writeResults() {
        //writeResults(currentTime);
        try {

            fileLog.append("\nAverage waiting time: " + averageWaitingTime() + "\nAverage processing time: " + averageProcessingTime() + "\nPeak time: " + peakTime + " with " + maxClients + " clients.\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.GUI.appendlogst("\nAverage waiting time: " + averageWaitingTime() + "\nAverage processing time: " + averageProcessingTime() + "\nPeak time: " + peakTime + " with " + maxClients + " clients.\n");
    }

    class StartListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (!canStart) {
                if (GUI.getsimulationTime().isEmpty() || GUI.getNrclientst().isEmpty() || GUI.getNrqueuest().isEmpty() || GUI.getMinArrivalTimet().isEmpty()
                        || GUI.getMaxArrivalTimet().isEmpty() || GUI.getMinProcessTimet().isEmpty() || GUI.getMaxProcessTimet().isEmpty())
                    JOptionPane.showMessageDialog(null, "Please insert values!!");
                else {
                    canStart = true;
                    getData();
                }
            } else
                canStart = false;
        }
    }

    public void run() {
        currentTime = 0;
        openFile();
        while (currentTime < simulationTime && canStart) {
            for (Iterator<Task> t = waitingQ.iterator(); t.hasNext(); ) {
                Task current = t.next();
                if (current.getArrivalTime() == currentTime) {
                    try {
                        scheduler.dispatchTask(current);
                        t.remove();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            writeResults(currentTime);
            peak(currentTime);
            currentTime++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        writeResults();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        closeFile();
    }

}
