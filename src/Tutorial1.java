import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.algorithm.generator.*;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.stream.Source;
import org.graphstream.algorithm.*;
import org.graphstream.algorithm.generator.BaseGenerator;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Tutorial1 {
    protected static String styleSheet =
            "node {" +
                    "   size: 10px; " +
                    "  text-size: 30px; text-alignment: at-right; text-padding: 3px, 2px; text-background-mode: rounded-box; text-background-color: yellow;" +
                    "}" +
            "edge { " +
                    "text-size: 20px; text-color: blue; text-padding: 5px;"
                    + "}" +
            "node.marked {" +
                    "	fill-color: red;" +
                    "}";

    public static void main(String args[]) {

        System.setProperty("org.graphstream.ui", "swing");

        Graph graph = new SingleGraph("Random");
        graph.setAttribute("ui.stylesheet", styleSheet);
        //Generator gen = new RandomGenerator(2,false,false,null,"length");
        Generator gen = new RandomGenerator(2);
        gen.addSink(graph);
        gen.begin();
        for(int i=0; i<5; i++)
            gen.nextEvents();
        gen.end();

        System.out.println("====== Display Node Ids ======");
        for(Node n:graph) {

            Stream<Edge> edges = n.edges();
            long edgesLength = edges.count();
            System.out.println("NodeId: " + n.getId() + " with edges length of " + edgesLength);
            int mm = 0;
        }

        //Explicity set the weight for each edges from 1
        graph.edges().forEach(e -> e.setAttribute("length",Math.floor(Math.random() * 10) + 1));

        System.out.println(" ");
        System.out.println("====== Display Edges Ids ======");
        graph.edges().forEach(e -> {
            System.out.println("EdgeId: " + e.getId() + " and it's weight: " + e.getAttribute("length"));
        });

        graph.nodes().forEach(n -> n.setAttribute("label", "Node " + n.getId()));
        graph.edges().forEach(e -> e.setAttribute("label", "Weight: " + (int) e.getNumber("length")));
        graph.display();

        /*
        try{
            int rowNo = 1;
            int totalNodes = 0;
            int totalEdges = 0;
            int arrayIndex = 0;
            int[] keys = new int[0];
            Boolean hasNode0 = false;
            int test = 0;

            File myObj = new File("./Data/roadNet-PA.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine() && rowNo < 100) { //
                String data = myReader.nextLine();


                if(rowNo == 3 || rowNo > 4){
                    String[] intParts = data.split("\\s+");

                    if(rowNo == 3){
                        //int k = 0;
                        totalNodes = Integer.parseInt(intParts[2]);
                        totalEdges = Integer.parseInt(intParts[4]);

                        keys = new int[totalNodes];
                    }
                    else if(intParts.length == 2){
                        System.out.println(data);
                        String FromNode = intParts[0];
                        String ToNode = intParts[1];

                        boolean ContainsFromKey = IntStream.of(keys).anyMatch(x -> x == Integer.parseInt(FromNode));
                        boolean ContainsToKey = IntStream.of(keys).anyMatch(x -> x == Integer.parseInt(ToNode));

                        if((FromNode.equals("0") || ToNode.equals("0")) && !hasNode0){
                            graph.addNode("0");
                            hasNode0 = true;
                        }
                        if(!ContainsFromKey){
                            graph.addNode(FromNode);
                            keys[arrayIndex] = Integer.parseInt(FromNode); //Store the keys
                            arrayIndex++;
                        }
                        if(!ContainsToKey){
                            graph.addNode(ToNode);
                            keys[arrayIndex] = Integer.parseInt(ToNode); //Store the keys
                            arrayIndex++;
                        }

                        if(!ContainsFromKey && !ContainsToKey){
                            graph.addEdge(String.valueOf(test), FromNode, ToNode,false);
                            test++;
                        }


                    }
                }
                rowNo++;
            }
            graph.edges().forEach(n -> n.setAttribute("label", n.getId()));
            graph.display();
            System.out.println("KV pairs length: " + arrayIndex);

            System.out.println("totalNodes: " + totalNodes);
            System.out.println("totalEdges: " + totalEdges);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        */
    }
}