public class Main {
    public static void main(String[] args) throws InterruptedException {

        SimulationManager simulate = new SimulationManager();
        Thread t = new Thread(simulate);
        t.start();
        t.join();

    }
}
