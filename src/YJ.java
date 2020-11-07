 import java.util.*;
 import java.util.Scanner;


        public class YJ {
        public static void main(String args[])
        {

        int v = 10;


        ArrayList<ArrayList<Integer>> adj =
        new ArrayList<ArrayList<Integer>>(v);
        for (int i = 0; i < v; i++) {
        adj.add(new ArrayList<Integer>());
        }

        addEdge(adj, 0, 1);
        addEdge(adj, 0, 3);
        addEdge(adj, 1, 2);
        addEdge(adj, 3, 4);
        addEdge(adj, 3, 7);
        addEdge(adj, 4, 5);
        addEdge(adj, 4, 6);
        addEdge(adj, 4, 7);
        addEdge(adj, 5, 6);
        addEdge(adj, 6, 7);
        addEdge(adj, 6, 8);
        addEdge(adj, 2, 9);
        addEdge(adj, 9, 4);

        int source = 2;
        int[] hNodes = new int[]{ 9, 6,8};
        HashMap<Integer,LinkedList<Integer>> compareList = new HashMap<Integer,LinkedList<Integer>>();

        for(int i : hNodes){
        compareList.put(i, printShortestDistance(adj, source, i, v));
        }
        findTopK(compareList,2);
        System.out.println("\nManuall");
//        printShortestDistance(adj, source, 9, v);
//        printShortestDistance(adj, source, 6, v);
        }

        private static void findTopK(HashMap<Integer,LinkedList<Integer>> adj, int k){

        HashMap<Integer,LinkedList<Integer>> maxTop = new HashMap<Integer,LinkedList<Integer>>();

        int [] topList = new int[k];

        HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();

        System.out.println(" ");
        for(var j : adj.entrySet()) {
        hm.put(j.getKey(),j.getValue().size());
        }


        Map<Integer,Integer> hml = sortByValue(hm);

        int counter = 0;
        for (Map.Entry<Integer, Integer> en : hml.entrySet()) {
        if(counter >= k){
        break;
        }

        System.out.println("Key = " + en.getKey() + ", Value = " + en.getValue());

       // System.out.println("Resevered Arr" + adj.get(en.getKey()));

        LinkedList<Integer> inversedList = adj.get(en.getKey());


        System.out.print("Correct order of Arr: [");
        for(int rv = inversedList.size() - 1; rv >= 0; rv--){
        if(rv == 0){
        System.out.print(inversedList.get(rv));
        }
        else{
        System.out.print(inversedList.get(rv) + ",");
        }
        }
        System.out.print("]\n");


//            ListIterator<Integer> it = inversedList.listIterator(inversedList.size());
//            while (it.hasPrevious())
//                System.out.print(it.previous() + ",");

        counter++;
        }
        int mf = 0;
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
        System.out.println("Given source and destination" +
        "are not connected");
//            return;
        return null;
        }


        LinkedList<Integer> path = new LinkedList<Integer>();
        int crawl = dest;
        path.add(crawl);
        while (pred[crawl] != -1) {
        path.add(pred[crawl]);
        crawl = pred[crawl];
        }


        System.out.println("\nShortest path length is: " + dist[dest]);


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


