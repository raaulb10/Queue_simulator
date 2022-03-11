import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {


    private static JTextField simulationTimet;
    private static JTextField Nrclientst;
    private static JTextField Nrqueuest;
    private static JTextField MinArrivalTimet;
    private static JTextField MaxArrivalTimet;
    private static JTextField MinProcessTimet;
    private static JTextField MaxProcessTimet;
    private static JButton Startbtn;
    private static JTextArea logst;
    private static JPanel panel1;
    private static JPanel panel2;


    public View() {
        JFrame frame = new JFrame();


        frame.setBounds(500, 100, 650, 950);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        panel1 = new JPanel();
        panel1.setBackground(new Color(193, 160, 35));
        panel1.setLayout(null);
        panel1.setBounds(0, 0, 2500, 250);


        Startbtn = new JButton("Start");
        Startbtn.setBounds(400, 25, 200, 80);
        panel1.add(Startbtn);

        JLabel lblSimulation = new JLabel("Simulation Time:");
        lblSimulation.setBounds(10, 20, 150, 25);
        panel1.add(lblSimulation);

        simulationTimet = new JTextField();
        simulationTimet.setBounds(150, 20, 150, 25);
        panel1.add(simulationTimet);
        simulationTimet.setColumns(10);

        JLabel lblClients = new JLabel("Nr. of clients:");
        lblClients.setBounds(10, 50, 150, 25);
        panel1.add(lblClients);

        Nrclientst = new JTextField();
        Nrclientst.setBounds(150, 50, 150, 25);
        panel1.add(Nrclientst);
        Nrclientst.setColumns(10);

        JLabel lblQueues = new JLabel("Nr. of queues:");
        lblQueues.setBounds(10, 80, 150, 25);
        panel1.add(lblQueues);

        Nrqueuest = new JTextField();
        Nrqueuest.setBounds(150, 80, 150, 25);
        panel1.add(Nrqueuest);
        Nrqueuest.setColumns(10);

        JLabel lblMinArrivalTime = new JLabel("Min arrival time:");
        lblMinArrivalTime.setBounds(10, 140, 150, 25);
        panel1.add(lblMinArrivalTime);

        MinArrivalTimet = new JTextField();
        MinArrivalTimet.setBounds(150, 140, 150, 25);
        panel1.add(MinArrivalTimet);
        MinArrivalTimet.setColumns(10);

        JLabel lblMaxArrivalTime = new JLabel("Max arrival time:");
        lblMaxArrivalTime.setBounds(320, 140, 150, 25);
        panel1.add(lblMaxArrivalTime);

        MaxArrivalTimet = new JTextField();
        MaxArrivalTimet.setBounds(450, 140, 150, 25);
        panel1.add(MaxArrivalTimet);
        MaxArrivalTimet.setColumns(10);

        JLabel lblMinProcessTime = new JLabel("Min processing time:");
        lblMinProcessTime.setBounds(10, 170, 150, 25);
        panel1.add(lblMinProcessTime);

        MinProcessTimet = new JTextField();
        MinProcessTimet.setBounds(150, 170, 150, 25);
        panel1.add(MinProcessTimet);
        MinProcessTimet.setColumns(10);

        JLabel lblMaxProcessTime = new JLabel("Max processing time:");
        lblMaxProcessTime.setBounds(320, 170, 150, 25);
        panel1.add(lblMaxProcessTime);

        MaxProcessTimet = new JTextField();
        MaxProcessTimet.setBounds(450, 170, 150, 25);
        panel1.add(MaxProcessTimet);
        MaxProcessTimet.setColumns(10);

        panel2 = new JPanel();
        panel2.setBackground(new Color(25, 132, 97));
        panel2.setLayout(null);
        panel2.setBounds(0, 250, 2500, 1000);

        logst = new JTextArea();
        logst.setBackground(new Color(255, 250, 250));
        logst.setEditable(false);
        logst.setLineWrap(true);
        logst.setWrapStyleWord(true);
        logst.setBounds(5, 5, 625, 650);
        panel2.add(logst);

        frame.add(panel1);
        frame.add(panel2);
        frame.setVisible(true);
    }

    public String getsimulationTime() {
        return simulationTimet.getText();
    }

    public String getNrclientst() {
        return Nrclientst.getText();
    }

    public String getNrqueuest() {
        return Nrqueuest.getText();
    }

    public String getMinArrivalTimet() {
        return MinArrivalTimet.getText();
    }

    public String getMaxArrivalTimet() {
        return MaxArrivalTimet.getText();
    }

    public String getMinProcessTimet() {
        return MinProcessTimet.getText();
    }

    public String getMaxProcessTimet() {
        return MaxProcessTimet.getText();
    }

    public void setlogst(String logst) {
        this.logst.setText(logst);
    }

    public void appendlogst(String logst) {
        this.logst.append(logst);
    }

    void addStartListener(ActionListener a) {
        this.Startbtn.addActionListener(a);
    }
}
