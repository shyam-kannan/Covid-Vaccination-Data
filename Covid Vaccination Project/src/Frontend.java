import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Frontend {
    private JButton Load;
    private JButton Add;
    private JButton Save;
    private JButton Visualize;
    private JButton About;
    private JPanel fullPanel;
    private JTabbedPane display;
    private JPanel addDisplay;
    private JPanel aboutDisplay;
    private JPanel loadDisplay;
    private JPanel saveDisplay;
    private JTextField ID;
    private JTextField lastName;
    private JTextField firstName;
    private JTextField vaccine;
    private JTextField date;
    private JTextField location;
    private JButton confirmAddButton;
    private JTextPane finishedAddingTextPane;
    private JTextField savePath;
    private JTextField loadPath;
    private JButton confirmLoadButton;
    private JButton confirmSaveButton;
    private JTextArea finishedSaving;
    private JTable dataTable;
    private JScrollPane tableDisplay;
    private JFormattedTextField team40FormattedTextField;
    private JTabbedPane visualizeDisplay;
    private JPanel pieDisplay;
    private JPanel barDisplay;
    private JLabel barLabel;
    private JLabel pieLabel;
    private JLabel finishAdd;
    private JLabel finishSave;
    private JLabel finishLoad;
    private DefaultTableModel model;



    public Frontend() {
        confirmAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean duplicate;
                boolean working;
                try
                {
                    Integer.parseInt(ID.getText());
                    working = true;
                } catch (NumberFormatException ex)
                {
                    working = false;
                    finishAdd.setText("Try Again, Invalid Input");
                }


                if(working == true){

                    duplicate = Backend.add(Integer.parseInt(ID.getText()), lastName.getText(), firstName.getText(), vaccine.getText(), date.getText(), location.getText());

                    model.setRowCount(0);
                    Object [][] arr = Backend.convert2dArray();
                    for (int i = 0;i<arr.length;i++){
                        model.addRow(arr[i]);
                    }

                    if (!duplicate){
                        ID.setText("");
                        lastName.setText("");
                        firstName.setText("");
                        vaccine.setText("");
                        date.setText("");
                        location.setText("");

                        finishAdd.setText("Finished Adding!");

                        java.awt.image.BufferedImage pieImage = null;
                        java.awt.image.BufferedImage barImage = null;
                        try {
                            pieImage = Backend.pie_visualize().createBufferedImage(640,450);
                            barImage = Backend.bar_visualize().createBufferedImage(640,450);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        pieLabel.setIcon(new ImageIcon(pieImage));
                        barLabel.setIcon(new ImageIcon(barImage));

                    }else{
                        finishAdd.setText("Duplicate Value");
                    }
                }
            }
        });
        confirmLoadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean empty = false;
                try {
                    empty = Backend.load(loadPath.getText());

                    if(empty){
                        finishLoad.setText("File is empty");
                    }else{
                        loadPath.setText("");
                        model.setRowCount(0);
                        Object [][] arr = Backend.convert2dArray();
                        for (int i = 0;i<arr.length;i++){
                            model.addRow(arr[i]);
                        }
                        display.setSelectedIndex(4);

                        java.awt.image.BufferedImage pieImage = null;
                        java.awt.image.BufferedImage barImage = null;
                        try {
                            pieImage = Backend.pie_visualize().createBufferedImage(640,450);
                            barImage = Backend.bar_visualize().createBufferedImage(640,450);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        pieLabel.setIcon(new ImageIcon(pieImage));
                        barLabel.setIcon(new ImageIcon(barImage));

                        finishLoad.setText("Successfully Loaded");
                    }
                }
                catch (FileNotFoundException notFoundException) {
                    finishLoad.setText("File not found");
                }
            }
        });
        confirmSaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Backend.save(savePath.getText());
                    savePath.setText("");
                    finishSave.setText("Successfully Saved");
                } catch (IOException ioException) {
                    finishSave.setText("Enter Proper File Name");
                }
            }
        });
    }
    private void createUIComponents(){
        // TODO: place custom component creation code here

        String[] columnNames = {"ID",
                "Last Name",
                "First Name",
                "Vaccine",
                "Date",
                "Location",};

        Object[][] data = Backend.convert2dArray();

        model = new DefaultTableModel(data,columnNames);

        dataTable = new javax.swing.JTable(model);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Data Visualizer");
        frame.setContentPane(new Frontend().fullPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}