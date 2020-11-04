import javax.management.StringValueExp;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ProgramGUI {

    /*Set defaultDirectory
    * Change the path for defaultDirectory
    * Windows path: public static String defaultDirectory = "C:\\Users\\mindy\\IdeaProjects\\ALGO_GIT\\";
    * Mac path: public static String defaultDirectory = "/Users/wenjun/Downloads/14_9/"; */

    //Windows path
    public static String defaultDirectory = "C:\\Users\\mindy\\IdeaProjects\\ALGO_GIT\\";
    public static File file;

    public static void main(String[] args) {
        //GUI Window
        JFrame frame = new JFrame("PT2 GrpB2 Graph Algorithm Program");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1250,550);

        //Graph Label
        JLabel graphLabel;
        graphLabel = new JLabel("Graph File: ");
        graphLabel.setBounds(50,50,100,30);

        //Display Graph File Name (TextBox)
        JTextField graphFileName;
        graphFileName = new JTextField();
        graphFileName.setBounds(225,50,300,30);


        //Graph File Button
        JButton graphFButton = new JButton("Import Graph File");
        graphFButton.setBounds(550,50,150,30);
        graphFButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    file=chooseFileDialog();
                }
                catch(Exception ex){
                    System.out.println(ex);
                }
                finally {
                    System.out.println(file.getName());
                }
            }
        });

        //Hospital Label
        JLabel hospitalLabel;
        hospitalLabel = new JLabel("Hospital File: ");
        hospitalLabel.setBounds(50,100,100,30);

        //Display Hospital File Name (TextBox)
        JTextField hospitalFileName;
        hospitalFileName = new JTextField();
        hospitalFileName.setBounds(225,100,300,30);

        //Hospital File Button
        JButton hospitalFButton = new JButton("Import Hospital File");
        hospitalFButton.setBounds(550,100,150,30);

        //Algorithm Label
        JLabel algoLabel;
        algoLabel = new JLabel("Algorithm: ");
        algoLabel.setBounds(50,150,100,30);

        //Algorithm Radio Button 1
        JRadioButton algo1 = new JRadioButton("1: Algorithm 1");
        algo1.setBounds(225,150,150,30);

        //Algorithm Radio Button 2
        JRadioButton algo2 = new JRadioButton("2: Algorithm 2");
        algo2.setBounds(225,180,150,30);

        //Algorithm Button Group
        ButtonGroup bg = new ButtonGroup();
        bg.add(algo1);
        bg.add(algo2);

        //Input Node Size Label
        JLabel nSizeLabel;
        nSizeLabel = new JLabel("Input # of Nodes: ");
        nSizeLabel.setBounds(50,250,150,30);

        //Input Node Size TextBox
        JTextField nSizeInput;
        nSizeInput = new JTextField();
        nSizeInput.setBounds(225,250,150,30);

        //Input Hospital Size Label
        JLabel hSizeLabel;
        hSizeLabel = new JLabel("Input # of Hospitals: ");
        hSizeLabel.setBounds(50,300,150,30);

        //Input Hospital Size TextBox
        JTextField hSizeInput;
        hSizeInput = new JTextField();
        hSizeInput.setBounds(225,300,150,30);

        //Input Top K Hospital Size Label
        JLabel kSizeLabel;
        kSizeLabel = new JLabel("Input # of Top-K Hospitals: ");
        kSizeLabel.setBounds(50,350,200,30);

        //Input Top K Hospital Size TextBox
        JTextField kSizeInput;
        kSizeInput = new JTextField();
        kSizeInput.setBounds(225,350,150,30);

        //Output File Label
        JLabel outputLabel;
        outputLabel = new JLabel("Output File Name: ");
        outputLabel.setBounds(50,400,200,30);

        //Output File Name (TextBox)
        JTextField outputFile;
        outputFile = new JTextField();
        outputFile.setBounds(225,400,300,30);

        //Generate Algorithm Button
        JButton transverseButton = new JButton("Transverse");
        transverseButton.setBounds(225,450,150,30);

        //Program Run Label
        JLabel programLabel;
        programLabel = new JLabel("Program Run: ");
        programLabel.setBounds(725,15,100,30);

        //Program Run Display (TextArea + ScrollPane)
        JTextArea programTextArea = new JTextArea();
        programTextArea.setBounds(725,45,475,450);


        //GUI Frame Components
        frame.add(graphLabel);
        frame.add(graphFileName);
        frame.add(graphFButton);
        frame.add(hospitalLabel);
        frame.add(hospitalFileName);
        frame.add(hospitalFButton);
        frame.add(algoLabel);
        frame.add(algo1);
        frame.add(algo2);
        frame.add(nSizeLabel);
        frame.add(nSizeInput);
        frame.add(hSizeLabel);
        frame.add(hSizeInput);
        frame.add(kSizeLabel);
        frame.add(kSizeInput);
        frame.add(outputLabel);
        frame.add(outputFile);
        frame.add(transverseButton);
        frame.add(programLabel);
        frame.add(programTextArea);

        frame.setLayout(null);
        frame.setVisible(true);
    }

    public static File chooseFileDialog() throws FileNotFoundException {

        final JFileChooser fc = new JFileChooser(defaultDirectory);
        Scanner scanner = new Scanner(System.in);

        int returnVal = fc.showOpenDialog(null);

        //User selected file
        if(returnVal==JFileChooser.APPROVE_OPTION)
        {
            //Retrieve selected file
            return fc.getSelectedFile();
        }
        return null;
    }

    public static int[] ReadSourceFile(File file)
    {
        String input = null;
        String regex =" ";
        int counter=0;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            long start = System.currentTimeMillis();

            System.out.println("File reading...");
            while ((input = br.readLine()) != null) {

                //TBC
                if (++counter==3)
                {
                    //String Spilt to get totalNodeSize and totalEdgeSize

                    String[] sizeOutput = input.split(regex);
                    System.out.println("Total Nodes: "+ sizeOutput[1]);
                    System.out.println("Total Edges: "+sizeOutput[3]);
                }
                if(counter>4)
               {
                    String[] arrayOutput = input.split(regex);
                    System.out.println("Start Node: "+arrayOutput[0]+" "+"End Node: "+arrayOutput[2]);
               }
            }

            long stop = System.currentTimeMillis();
            System.out.println("File Reading Time (ms): " + (stop - start));

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error processing the file!");
        }
    }
}
