public class Task implements Comparable<Task> {
    private Integer queueID;
    private Integer clientID;
    private Integer arrivalTime;
    private Integer finishTime;
    private Integer processingPeriod;
    private Integer waitingPeriod;

    public Task(int clientID, int arrivalTime, int processingPeriod) {
        this.clientID = clientID;
        this.arrivalTime = arrivalTime;
        this.processingPeriod = processingPeriod;
    }

    public void setQueueID(Integer queueID) {
        this.queueID = queueID;
    }

    public Integer getArrivalTime() {
        return arrivalTime;
    }

    public Integer getFinishTime() {
        return finishTime;
    }

    public void setFinishTime() {
        finishTime = arrivalTime + processingPeriod + waitingPeriod;
    }

    public Integer getProcessingPeriod() {
        return processingPeriod;
    }

    public void setWaitingPeriod(Integer waitingPeriod) {
        this.waitingPeriod = waitingPeriod;
    }

    public void decrementProcessingPeriod() {
        processingPeriod = processingPeriod - 1;
    }

    public int compareTo(Task o) {
        if (arrivalTime == o.arrivalTime)
            return 0;
        else if (arrivalTime > o.arrivalTime)
            return 1;
        else return -1;
    }

    public String toString() {
        return "(" + clientID + ", " + arrivalTime + ", " + processingPeriod + ')';
    }
}
