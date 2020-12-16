package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class webServerGUI
{
    private JFrame frame = new JFrame("Web Server");

    private JLabel textLabel32 = new JLabel();
    private JLabel textLabel34 = new JLabel();
    private JLabel textLabel36 = new JLabel();

    private JTextField field21 = new JTextField(4);
    private JTextField field22 = new JTextField();
    private JTextField field23 = new JTextField();

    private JButton button4 = new JButton();

    private int stare=3;
    private boolean checkBoxChecked = false;

    public int getStare() {
        return stare;
    }

    public void setStare(int stare) {
        this.stare = stare;
    }

    public void startGUIRunning()
    {
        textLabel32.setText("Running ... ");
        textLabel34.setText("127.0.0.1");
        textLabel36.setText("10007");

        field21.setEditable(false);
        field22.setEditable(false);

        button4.setText("Stop Server");

        createFrame();
    }

    public void startGUIMaintenance()
    {
        textLabel32.setText("Maintenance ... ");
        textLabel34.setText("127.0.0.1");
        textLabel36.setText("10007");

        field21.setEditable(false);
        field23.setEditable(false);

        button4.setText("Stop Server");

        createFrame();
    }

    public void startGUIStopped()
    {
        textLabel32.setText("Not running");
        textLabel34.setText("Not running");
        textLabel36.setText("Not running");

        button4.setText("Start Server");

        createFrame();
    }

    public void createFrame()
    {
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel textLabel31 = new JLabel("Server status : ") ;
       // JLabel textLabel32 = new JLabel("Not running ") ;
        JLabel textLabel33 = new JLabel("Server adreess : ") ;
        //JLabel textLabel34 = new JLabel("Not running ") ;
        JLabel textLabel35 = new JLabel("Server listening port : ") ;
        //JLabel textLabel36 = new JLabel("Not running ") ;


        JCheckBox checkBox4 = new JCheckBox("Switch to maintenance mode");

        JLabel label21 = new JLabel("Server listening on port");
        JLabel label22 = new JLabel("Web root directory");
        JLabel label23 = new JLabel("Maintenance directory");

        JPanel panel21 = new JPanel();

        JButton button21 = new JButton("...");
        JButton button22 = new JButton("...");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
        JPanel panel1 = new JPanel();

        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(3,3,20,20));


        panel2.add(label21);
        panel2.add(field21);
        panel2.add(panel21);

        panel2.add(label22);
        panel2.add(field22);
        panel2.add(button21);

        panel2.add(label23);
        panel2.add(field23);
        panel2.add(button22);

        JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayout(3,2));

        panel3.add(textLabel31);
        panel3.add(textLabel32);
        panel3.add(textLabel33);
        panel3.add(textLabel34);
        panel3.add(textLabel35);
        panel3.add(textLabel36);

        JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayout(2,1));

        checkBox4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBoxChecked = !checkBoxChecked;
            }
        });

        button4.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(stare==3 && !checkBoxChecked)
                {
                    stare=1;
                }

                else if(stare==1 && !checkBoxChecked)
                {
                    stare=3;
                }
                else if((stare==1 || stare==3) && checkBoxChecked)
                {
                    stare=2;
                }
            }
        });
        panel4.add(button4);
        panel4.add(checkBox4);

        panel3.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "WebServer Info"));
        panel4.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "WebServer control"));

        panel1.setLayout(new GridLayout(1,2));
        panel1.add(panel3,BorderLayout.WEST);
        panel1.add(panel4, BorderLayout.EAST);
        panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "WebServer configuration"));

        panel.add(panel1, BorderLayout.NORTH);
        panel.add(panel2, BorderLayout.SOUTH);
        frame.add(panel,BorderLayout.CENTER);
        frame.setVisible(true);

    }
}
