import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.DoubleToIntFunction;

public class YJ {

    private static int v = 0;  //num of Vertices
    private static ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>(v);
    private static int totalNodes = 0;
    private static int totalEdges = 0;


    public static int getRandomInteger(int maximum, int minimum){
        return ((int) (Math.random()*(maximum - minimum))) + minimum;
    }

    public static void Readfile(String filepath){
        try{
            File myObj = new File(filepath);
            Scanner myReader = new Scanner(myObj);

            int rowNo = 1;
            int totalEdges = 0;

            while (myReader.hasNextLine() && rowNo < 100){
                String data = myReader.nextLine();
                if(rowNo == 3 || rowNo > 4) {
                    String[] intParts = data.split("\\s+");

                    if (rowNo == 3) {
                        //totalNodes = Integer.parseInt(intParts[2]);
                        //totalEdges = Integer.parseInt(intParts[4]);

                        //keys = new int[totalNodes];
                    } else if (intParts.length == 2) {
                        //System.out.println("data: " + data);

                        Integer FromNode = Integer.parseInt(intParts[0]);
                        Integer ToNode = Integer.parseInt(intParts[1]);
                        System.out.println("From [" + FromNode + "] : To [" + ToNode + "]");

                        addEdge(adj, FromNode, ToNode);
                        totalEdges++;
                    }

                }
                rowNo++;
            }


            //System.out.println("totalNodes: " + totalNodes);
            //System.out.println("totalEdges: " + totalEdges);
            System.out.println("totalEdges: " + totalEdges);
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public static void RandomGraph(int totalnodes){
        for(int i = 0; i < totalnodes;i++){
            int fromNode = getRandomInteger(totalnodes, 0);
            int toNode = getRandomInteger(totalnodes, 0);
            System.out.println("From [" + fromNode + "] : To [" + toNode + "]");
            addEdge(adj, fromNode, toNode);
        }
    }


    /*
        @param v = specify the max NodeId.
        Eg: v = 10 is equals to 0 ~ 10

        @param source
        @param hNodes
        @param setTopK

        @param filepath

    */
    public static void main(String args[])
    {
        v = 10;
        int source = 1;
        int[] hNodes = new int[]{ 9, 6,8, 2};
        int setTopK = 3;

        //Initialize the adjacency list
        for (int i = 0; i < v; i++) {
            adj.add(new ArrayList<Integer>());
        }

        //Method 1: RandomGraph
        //RandomGraph(v);

        //Method 2: Read from file
        //String filepath = "/Users/wenjun/Desktop/CZ2001/src/input/roadNet-PA.txt";
        String filepath = "/Users/wenjun/Desktop/CZ2001/src/input/test.txt";
        Readfile(filepath);


        HashMap<Integer,LinkedList<Integer>> compareList = new HashMap<Integer,LinkedList<Integer>>();
        for(int i : hNodes){
            compareList.put(i, printShortestDistance(adj, source, i, v));
        }

        //Call findTopK to find the top-k's shortest path
        findTopK(compareList,setTopK);
    }

    private static void findTopK(HashMap<Integer,LinkedList<Integer>> adj, int k){

        //HashMap<Integer,LinkedList<Integer>> maxTop = new HashMap<Integer,LinkedList<Integer>>();
//        int [] topList = new int[k];

        HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();

        System.out.println(" ");
        for(var j : adj.entrySet()) {
            hm.put(j.getKey(),j.getValue().size());
        }


        Map<Integer,Integer> hml = sortByValue(hm);

        int counter = 0;

        System.out.println("===================");
        System.out.println("Top " + k + "'s shortest path: ");
        System.out.println("===================");
        for (Map.Entry<Integer, Integer> en : hml.entrySet()) {
            if(counter >= k){
                break;
            }

            //System.out.println("Key = " + en.getKey() + ", Value = " + en.getValue());
            //System.out.println("Resevered Arr" + adj.get(en.getKey()));

            LinkedList<Integer> inversedList = adj.get(en.getKey());

            System.out.print("[");
            for(int rv = inversedList.size() - 1; rv >= 0; rv--){
                if(rv == 0){
                    System.out.print(inversedList.get(rv));
                }
                else{
                    System.out.print(inversedList.get(rv) + ",");
                }
            }
            System.out.print("]\n");
            System.out.println("===================");

            counter++;
        }
    }

    public static HashMap<Integer, Integer> sortByValue(HashMap<Integer, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<Integer, Integer> > list = new LinkedList<Map.Entry<Integer, Integer> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer> >() {
            public int compare(Map.Entry<Integer, Integer> o1,
                               Map.Entry<Integer, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<Integer, Integer> temp = new LinkedHashMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    private static void addEdge(ArrayList<ArrayList<Integer>> adj, int i, int j)
    {
        adj.get(i).add(j);
        adj.get(j).add(i);
    }


    private static LinkedList<Integer> printShortestDistance(
            ArrayList<ArrayList<Integer>> adj,
            int s, int dest, int v)
    {

        int pred[] = new int[v];
        int dist[] = new int[v];

        if (BFS(adj, s, dest, v, pred, dist) == false) {
            //System.out.println("\nGiven source and destination are not connected");
            return new LinkedList<Integer>();
            //return null;
        }


        LinkedList<Integer> path = new LinkedList<Integer>();
        int crawl = dest;
        path.add(crawl);
        while (pred[crawl] != -1) {
            path.add(pred[crawl]);
            crawl = pred[crawl];
        }


        System.out.println("\nPath length is: " + dist[dest]);
        System.out.println("Path is :");
        for (int i = path.size() - 1; i >= 0; i--) {
            System.out.print(path.get(i) + " ");
        }
        return path;
    }


    private static boolean BFS(ArrayList<ArrayList<Integer>> adj, int src,
                               int dest, int v, int pred[], int dist[])
    {

        LinkedList<Integer> queue = new LinkedList<Integer>();

        boolean visited[] = new boolean[v];

        for (int i = 0; i < v; i++) {
            visited[i] = false;
            dist[i] = Integer.MAX_VALUE;
            pred[i] = -1;
        }


        visited[src] = true;
        dist[src] = 0;
        queue.add(src);


        while (!queue.isEmpty()) {
            int u = queue.remove();
            for (int i = 0; i < adj.get(u).size(); i++) {
                if (visited[adj.get(u).get(i)] == false) {
                    visited[adj.get(u).get(i)] = true;
                    dist[adj.get(u).get(i)] = dist[u] + 1;
                    pred[adj.get(u).get(i)] = u;
                    queue.add(adj.get(u).get(i));


                    if (adj.get(u).get(i) == dest)
                        return true;
                }
            }
        }
        return false;
    }
}


