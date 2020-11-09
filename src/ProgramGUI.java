import javax.management.StringValueExp;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.io.IOException.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ProgramGUI {

    /*Set defaultDirectory
     * Change the path for defaultDirectory
     * Windows path: public static String defaultDirectory = "C:\\Users\\mindy\\IdeaProjects\\ALGO_GIT\\src";
     * Mac path: public static String defaultDirectory = "/Users/wenjun/Downloads/14_9/"; */

    //Windows path
    public static String defaultDirectory = "C:\\Users\\YIJIA\\Desktop\\Algorithm 2\\src\\input";
    public static String outputDirectory = "C:\\Users\\YIJIA\\Desktop\\Algorithm 2\\out";
    public static File graphFile, hospitalFile;
    public static JTextArea programTextArea;
    public static String N, K,O;
    public static ArrayList<Integer> HospitalNodes;

    public static void main(String[] args) {
        //GUI Window
        JFrame frame = new JFrame("PT2 GrpB2 Graph Algorithm Program");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 550);

        //Graph Label
        JLabel graphLabel;
        graphLabel = new JLabel("Graph File: ");
        graphLabel.setBounds(50, 50, 100, 30);

        //Display Graph File Name (Label)
        JLabel graphFileLabel;
        graphFileLabel = new JLabel();
        graphFileLabel.setBounds(225, 50, 300, 30);

        //Graph File Button
        JButton graphFButton = new JButton("Import Graph File");
        graphFButton.setBounds(425, 50, 150, 30);
        graphFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    graphFile = chooseFileDialog();
                } catch (Exception ex) {
                    System.out.println(ex);
                } finally {
                    graphFileLabel.setText(graphFile.getName());
                    readGraphFile(graphFile);
                }
            }
        });

        //Hospital Label
        JLabel hospitalLabel;
        hospitalLabel = new JLabel("Hospital File: ");
        hospitalLabel.setBounds(50, 100, 100, 30);

        //Display Hospital File Name (Label)
        JLabel hospitalFileLabel;
        hospitalFileLabel = new JLabel();
        hospitalFileLabel.setBounds(225, 100, 300, 30);

        //Hospital File Button
        JButton hospitalFButton = new JButton("Import Hospital File");
        hospitalFButton.setBounds(425, 100, 150, 30);
        hospitalFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    hospitalFile = chooseFileDialog();
                } catch (Exception ex) {
                    System.out.println(ex);
                } finally {
                    hospitalFileLabel.setText(hospitalFile.getName());
                    HospitalNodes = readHospitalFile(hospitalFile);
                }
            }
        });

        //Algorithm Label
        JLabel algoLabel;
        algoLabel = new JLabel("Algorithm: ");
        algoLabel.setBounds(50, 150, 100, 30);

        //Algorithm Radio Button 1
        JRadioButton algo1 = new JRadioButton("1: Random Graph");
        algo1.setBounds(225, 150, 200, 30);

        //Do not use!!
        //Algorithm Radio Button 2
        JRadioButton algo2 = new JRadioButton("2: Real Network Graph");
        algo2.setBounds(225, 180, 200, 30);

        //Algorithm Button Group
        ButtonGroup bg = new ButtonGroup();
        bg.add(algo1);
        bg.add(algo2);

        //Input Node Size Label
        JLabel nSizeLabel;
        nSizeLabel = new JLabel("Input # of Nodes: ");
        nSizeLabel.setBounds(50, 225, 150, 30);

        //Input Node Size TextBox
        JTextField nSizeInput;
        nSizeInput = new JTextField();
        nSizeInput.setBounds(225, 225, 150, 30);

/*      //Do not use!!
        //Input Hospital Size Label
        JLabel hSizeLabel;
        hSizeLabel = new JLabel("Input # of Hospitals: ");
        hSizeLabel.setBounds(50, 300, 150, 30);
        //Input Hospital Size TextBox
        JTextField hSizeInput;
        hSizeInput = new JTextField();
        hSizeInput.setBounds(225, 300, 150, 30);*/

        //Input Top K Hospital Size Label
        JLabel kSizeLabel;
        kSizeLabel = new JLabel("Input # of Top-K Hospitals: ");
        kSizeLabel.setBounds(50, 275, 200, 30);

        //Input Top K Hospital Size TextBox
        JTextField kSizeInput;
        kSizeInput = new JTextField();
        kSizeInput.setBounds(225, 275, 150, 30);
        //Output File Label
        JLabel outputLabel;
        outputLabel = new JLabel("Output File Name: ");
        outputLabel.setBounds(50, 325, 200, 30);

        //Output File Name (TextBox)
        JTextField outputFile;
        outputFile = new JTextField();
        outputFile.setBounds(225, 325, 150, 30);

        //Generate Algorithm with user input
        JButton transverseButton = new JButton("Transverse");
        transverseButton.setBounds(425,375,150,30);
        transverseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    N= nSizeInput.getText();
                    K= kSizeInput.getText();
                    O =outputFile.getText();

                    //Add file name inside File()
                    File outputFile = new File(O+".txt");
                    FileWriter fileWriter = new FileWriter(outputDirectory+outputFile);
                    //fileWriter.write(programTextArea.getText());

                    programTextArea.append("Node #: "+N+" "+"Top-K Hospital #: "+ K+"\n\n");
                    //To add the link to the BFS.java when completed

                    int selectedAlgoOption = -1;
                    if(algo1.isSelected())
                    {
                        //To call out the algo1.java class
                        programTextArea.append("Running Algorithm - "+algo1.getText()+"\n\n");

                        //Check in path if file exists
                        if(outputFile.createNewFile()){
                            programTextArea.append("File created: "+O+".txt");
                        }
                        else{
                            programTextArea.append("File already exists.");
                        }

                        selectedAlgoOption = 0;
                        System.out.println("Selected Algo 1");
                    }
                    else if (algo2.isSelected()){
                        programTextArea.append("Running Algorithm - "+algo1.getText()+"\n\n");

                        //Check in path if file exists
                        if(outputFile.createNewFile()){
                            programTextArea.append("File created: "+O+".txt");
                        }
                        else{
                            programTextArea.append("File already exists.");
                        }
                        selectedAlgoOption = 1;
                        System.out.println("Selected Algo 2");
                    }
                    else
                    {
                        programTextArea.append("Kindly select the algorithm."+"\n\n");
                    }


                    System.out.println("Num of Nodes: " + N);
                    YJ BFS = new YJ(Integer.parseInt(N));
                    BFS.setSource(0);  //Set the source NodeId
                    BFS.setTopK(Integer.parseInt(K));    //Set the top-k's shortest path
                    BFS.setAlgoOption(selectedAlgoOption);
                    BFS.sethNodes(HospitalNodes);

                    ArrayList<String> StpList = BFS.executeBFS();
                    programTextArea.append("\n");

                    //Print out all the shortest paths
                    for(String output : StpList){
                        programTextArea.append(output);
                        fileWriter.write(output);
                    }
                    fileWriter.close();
                }
                catch(IOException e1){
                    programTextArea.append("An error occured. File cannot be created");
                    e1.printStackTrace();
                }
            }
        });

/*        //Save output Button
        JButton saveOutputButton = new JButton("Save as File");
        saveOutputButton.setBounds(425, 450, 150, 30);
        saveOutputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    O = outputFile.getText();
                    //Add file name inside File()
                    File outputFile = new File(O+".txt");

                    FileWriter fileWriter = new FileWriter("C:/Users/monai/OneDrive/Documents/Github/src/"+outputFile);
                    fileWriter.write(programTextArea.getText());
                    fileWriter.close();

                    //To store to the path....TBC
                    if (outputFile.createNewFile()) {
                        System.out.println("File created: " + O);
                    } else {
                        System.out.println("File already exists.");
                    }
                } catch (IOException e1) {
                    System.out.println("An error occured. File cannot be created");
                    e1.printStackTrace();
                }
            }
        });*/
        //Program Run Label
        JLabel programLabel;
        programLabel = new JLabel("Program Run: ");
        programLabel.setBounds(650, 15, 100, 30);

        //Program Run Display (TextArea + ScrollPane)
        programTextArea = new JTextArea();
        programTextArea.setBounds(650, 45, 475, 450);
        //programTextArea.setWrapStyleWord(true);
        //programTextArea.setLineWrap(true);
        JScrollPane scrollablePTextArea = new JScrollPane(programTextArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollablePTextArea.setBounds(650,45,475,460);

        //GUI Frame Components
        frame.add(graphLabel);
        frame.add(graphFileLabel);
        frame.add(graphFButton);
        frame.add(hospitalLabel);
        frame.add(hospitalFileLabel);
        frame.add(hospitalFButton);
        frame.add(algoLabel);
        frame.add(algo1);
        frame.add(nSizeLabel);
        frame.add(nSizeInput);
        frame.add(kSizeLabel);
        frame.add(kSizeInput);
        frame.add(transverseButton);
        frame.add(outputLabel);
        frame.add(outputFile);
        frame.add(programLabel);
        frame.add(algo2);
        frame.add(scrollablePTextArea);
/*        //frame.add(hSizeLabel);
        //frame.add(hSizeInput);
        //frame.add(programTextArea);
        //frame.getContentPane().add(scrollablePTextArea);
        // frame.add(saveOutputButton);*/
        frame.setLayout(null);
        frame.setVisible(true);

    }

    public static File chooseFileDialog() throws FileNotFoundException {

        final JFileChooser fc = new JFileChooser(defaultDirectory);
        Scanner scanner = new Scanner(System.in);

        int returnVal = fc.showOpenDialog(null);

        //User selected file
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            //Retrieve selected file
            return fc.getSelectedFile();
        }
        return null;
    }

    public static HashMap<Integer, ArrayList<Integer>> readGraphFile(File file) {
        String input = null;
        int counter = 0;

        //Hashmap to store ArrayList
        HashMap<Integer, ArrayList<Integer>> graph = new HashMap<Integer, ArrayList<Integer>>();
        ArrayList<Integer> nodeList;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            //Start time calculation
            long start = System.currentTimeMillis();

            programTextArea.append("File reading..."+"\n\n");
            //System.out.println("File reading...");

            while ((input = br.readLine()) != null) {
                if ((input.charAt(0) - '#') != 0) {
                    String[] stringNode = input.split("\t");
                    int startNode = Integer.parseInt(stringNode[0]);
                    int endNode = Integer.parseInt(stringNode[1]);

                    nodeList = graph.get(startNode);
                    if (nodeList == null) {
                        nodeList = new ArrayList<Integer>();
                        nodeList.add(endNode);
                    } else {
                        if (!nodeList.contains(endNode))
                            nodeList.add(endNode);
                    }
                    //System.out.println(startNode +" "+ endNode);
                    programTextArea.append(startNode +" "+ endNode+ "\n");
                    graph.put(startNode, nodeList);
                }

            }

            //Stop time calculation
            long stop = System.currentTimeMillis();
            //System.out.println("File Reading Time (ms): " + (stop - start));
            programTextArea.append("\n"+"File Reading Time (ms): " + (stop - start)+ "\n\n\n");
            //System.out.println(graph);
            return graph;

        } catch (IOException | NumberFormatException e) {
            //System.out.println("Error processing the file!");
            programTextArea.append("Error processing the file!"+"\n\n\n");
            return null;
        }

    }

    public static ArrayList<Integer> readHospitalFile(File file)
    {
        String input = null;
        int counter=0;

        // ArrayList to store hospital node
        ArrayList<Integer> hospital = new ArrayList<Integer>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            //Start time calculation
            long start = System.currentTimeMillis();

            //System.out.println("File reading...");
            programTextArea.append("File reading..."+"\n\n");
            while ((input = br.readLine()) != null) {
                if((input.charAt(0)-'#')!=0)
                {
                    hospital.add(Integer.parseInt(input));
                    programTextArea.append(input+ "\n");
                }
            }

            //Stop time calculation
            long stop = System.currentTimeMillis();
            programTextArea.append("\n"+ "File Reading Time (ms): " + (stop - start)+"\n\n");
            //System.out.println("File Reading Time (ms): " + (stop - start));

            //System.out.println(hospital);
            return hospital;

        } catch (IOException | NumberFormatException e) {
            //System.out.println("Error processing the file!");
            programTextArea.append("Error processing the file!"+"\n\n");
            return null;
        }

    }
}