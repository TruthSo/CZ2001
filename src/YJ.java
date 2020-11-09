import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.DoubleToIntFunction;

public class YJ {

    private int v = 0;  //num of Vertices
    private ArrayList<ArrayList<Integer>> adj;
    private int algoOption = 0;
    private int source = 0;
    private int topK = 0;
    private ArrayList<Integer> hNodes = new ArrayList<Integer>();
    private String filepath;
    private ArrayList<String> outputFileStr = new ArrayList<String>();
    private HashMap<Integer, ArrayList<Integer>> RoadNodes;
    //getRandomInteger: to get a Random Num
    public static int getRandomInteger(int maximum, int minimum){
        return ((int) (Math.random()*(maximum - minimum))) + minimum;
    }
    public ArrayList<ArrayList<Integer>> getAdj() {
        return adj;
    }

    public void setAlgoOption(int algoOption) {
        this.algoOption = algoOption;
    }

    public ArrayList<Integer> gethNodes() {
        return hNodes;
    }

    public void setV(int v) {
        this.v = v;
    }

    public int getV() {
        return v;
    }

    public void sethNodes(ArrayList<Integer> hNodes) {
        this.hNodes = hNodes;
    }

    public HashMap<Integer, ArrayList<Integer>> getRoadNodes(){
        return this.RoadNodes;
    }
    public void setRoadNodes(HashMap<Integer, ArrayList<Integer>> roadNotes) {
        this.RoadNodes = roadNotes;
    }



    public void RandomGraph(int totalnodes){
        for(int i = 0; i < totalnodes;i++){
            int fromNode = getRandomInteger(totalnodes, 0);
            int toNode = getRandomInteger(totalnodes, 0);
            System.out.println("From [" + fromNode + "] : To [" + toNode + "]");
            addEdge(getAdj(), fromNode, toNode);
        }
    }
    public void ReadRoadNodes(HashMap<Integer, ArrayList<Integer>>  roadNodes){
        for(var i = 0; i < roadNodes.size();i++){
            var fromNode = i;
            var toNodeList =roadNodes.get(i);
            System.out.println("From Node:" + i);
            for(int q : toNodeList){
                var toNode = q;

                System.out.println("ToNoode :" + q);
                addEdge(getAdj(), fromNode, toNode);
            }
            int k = 0;
            //var fromNode = getRoadNodes(roadNodes, 0);
            //var toNode = getRoadNodes(roadNodes, 0);
            //System.out.println("From [" + fromNode + "] : To [" + toNode + "]");
            //addEdge(ArrayList(), fromNode, toNode);
        }

    }


    /*
        @param v = specify the max NodeId.
        Eg: v = 10 is equals to 0 ~ 10
        @param source: start Node
        @param hNodes: list of Hospital nodes
        @param setTopK: Set for top-k shortest path
        @param filepath: file path for reading input
    */

    private void init(){

        adj =  new ArrayList<ArrayList<Integer>>(getV());
        //Initialize the adjacency list
        for (int i = 0; i < getV(); i++) {
            adj.add(new ArrayList<Integer>());
        }
        int k = 0;
    }


    public void setSource(int source) {
        this.source = source;
        System.out.println("Start NodeId: " + source);
    }

    public int getTopK() {
        return topK;
    }

    public void setTopK(int topK) {
        this.topK = topK;
    }

    public ArrayList<String> getOutputFileStr() {
        return outputFileStr;
    }

    public void setOutputFileStr(ArrayList<String> outputFileStr) {
        this.outputFileStr = outputFileStr;
    }

    public YJ(int vertices) {
        setV(vertices);
        init();
    }

    public ArrayList<String> executeBFS(){
        //Method 1: RandomGraph
        if(this.algoOption == 0){
            RandomGraph(v);
            System.out.println("YJ.java run RandomGraph()");
        }

        //Method 2: ReadFile
        else if(this.algoOption == 1){
            var ss = getRoadNodes();
            System.out.println("YJ.java run ReadFile()");
            ReadRoadNodes(ss);
        }

        HashMap<Integer,LinkedList<Integer>> compareList = new HashMap<Integer,LinkedList<Integer>>();

        for(int i : gethNodes()){
            System.out.println("Finding h NodeId: " + i);
            compareList.put(i, printShortestDistance(adj, source, i, v));
        }

        //Call findTopK to find the top-k's shortest path
        findTopK(compareList,getTopK());

        return getOutputFileStr();
    }

    private void findTopK(HashMap<Integer,LinkedList<Integer>> adj, int k)
    {
        //ArrayList<String> stpList = new ArrayList<String>();
        HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();

        System.out.println(" ");
        for(var j : adj.entrySet()) {
            hm.put(j.getKey(),j.getValue().size());
        }

        Map<Integer,Integer> hml = sortByValue(hm);

        int counter = 0;

        String pathOutput = "";
        String LabelTopK = "Top " + k + "'s shortest path for ";

        pathOutput += LabelTopK;
        System.out.println(LabelTopK);


        for (Map.Entry<Integer, Integer> en : hml.entrySet()) {
            if(counter >= k){
                break;
            }

            LinkedList<Integer> inversedList = adj.get(en.getKey());

            boolean checkEmptyPath = en.getValue() != 0;

            if(checkEmptyPath){
                String HospitalLabel = "hospital NodeId [" + en.getKey() + "]";
                pathOutput += HospitalLabel + "\n[";

                System.out.println(HospitalLabel);
                System.out.print("[");

                for(int rv = inversedList.size() - 1; rv >= 0; rv--){
                    if(rv == 0){
                        System.out.print(inversedList.get(rv));
                        pathOutput+= inversedList.get(rv);
                    }
                    else{
                        System.out.print(inversedList.get(rv) + ",");
                        pathOutput+= inversedList.get(rv) + ",";
                    }
                }

                String label_end = "]\n";
                String label_endB = "===================";

                pathOutput+= label_end;
                pathOutput+= label_endB;

                System.out.print(label_end);
                System.out.println(label_endB);

                counter++;
                outputFileStr.add(pathOutput);
            }

        }

        if(outputFileStr.size() == 0){
            outputFileStr.add("empty path ! Please tranverse the program again.");
        }
    }



    private static void addEdge(ArrayList<ArrayList<Integer>> adj, int i, int j)
    {
        adj.get(i).add(j);
        adj.get(j).add(i);
    }


    private static LinkedList<Integer> printShortestDistance(ArrayList<ArrayList<Integer>> adj, int s, int dest, int v)
    {

        int pred[] = new int[v];
        int dist[] = new int[v];

        if (BFS(adj, s, dest, v, pred, dist) == false) {
            return new LinkedList<Integer>();
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
        System.out.println("\n---------------");
        return path;
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
    public void Readfile(String filepath){
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

//                        var aa = getAdj();
//                        addEdge(aa, FromNode, ToNode);
                        totalEdges++;
                    }

                }
                rowNo++;
            }
            //System.out.println("totalNodes: " + totalNodes);
            System.out.println("totalEdges: " + totalEdges);
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

}
