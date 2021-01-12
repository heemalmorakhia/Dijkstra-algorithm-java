/**
 * @author: Heemal Morakhia
 * @purpose: Implementing Graphs
 * @Resources: Dalhousie University - CSCI 2110 - Data Structures and Algorithms
 */

import java.util.*;

public class Graph {

    private ArrayList<String> nodes;
    private boolean directed, weighted;
    private int [][] graphMatrix;

    public Graph(ArrayList<String> nodes, boolean directed, boolean weighted){
        this.nodes = nodes;

        graphMatrix = new int[this.nodes.size()][this.nodes.size()];
        fillWithZeroesORPrint(0);

        this.directed = directed;
        this.weighted = weighted;
    }

    public void createEdge(String input){
        String[] inp = input.split(",");

        String node1 = inp[0];
        String node2 = inp[1];
        int weight = 1;
        if(weighted) weight = Integer.parseInt(inp[2]);

        int idx_node1 = nodes.indexOf(node1);
        int idx_node2 = nodes.indexOf(node2);

        graphMatrix[idx_node1][idx_node2] = weight;
        if(!directed) graphMatrix[idx_node2][idx_node1] = weight;
    }

    public String[] shortestPath_Djikstra(String source){
        int[][] matrix = getMatrix();
        ArrayList<String> allNodes = this.getNodes();
        HashMap<String, HashMap<String, Integer>> adjList = adjList(matrix,allNodes);

        ArrayList<DataEntry> confirmed = new ArrayList<>();
        ArrayList<DataEntry> tentative = new ArrayList<>();

        DataEntry entry = new DataEntry(source, 0,"-");
        confirmed.add(entry);

        String base = source;
        int k = 0;
        String destination,next = "",nextHop;
        int cost, baseCost = 0;
        DataEntry minEntry = new DataEntry();

        while(true) {
            HashMap<String, Integer> neighbourList = adjList.get(base);

            for(Map.Entry mapElem : neighbourList.entrySet()){

                destination = (String) mapElem.getKey();
                cost = (int)mapElem.getValue() + baseCost;

                if( k == 0) next = destination;
                nextHop = next;
                entry = new DataEntry(destination,cost,nextHop);
                int pos;

                if(this.contains(confirmed,entry) == -1){
                    pos = this.contains(tentative,entry);
                    if(pos != -1){
                        int oldCost = tentative.get(pos).getCost();
                        int newCost = entry.getCost();
                        if(newCost < oldCost) {
                            tentative.remove(pos);
                            tentative.add(entry);
                        }
                    }
                    else{
                        tentative.add(entry);
                    }
                }
            }

            minEntry = new DataEntry(this.findMinEntry(tentative));
            int remPos = this.contains(tentative,minEntry);
            confirmed.add(tentative.remove(remPos));
            if(tentative.size() == 0) break;

            base = minEntry.getDestination();
            baseCost = minEntry.getCost();
            next = minEntry.getNextHop();

            k++;
        }

        return this.convertList(confirmed);
    }

    private HashMap<String, HashMap<String, Integer>> adjList(int[][] matrix, ArrayList<String> allNodes){
        HashMap<String, HashMap<String, Integer>> adjList = new HashMap<>();

        for(int i = 0; i < allNodes.size(); i++){
            String mainKey = allNodes.get(i);
            HashMap<String,Integer> hm2 = new HashMap<>();
            for(int j = 0; j < matrix[1].length; j++){
                if(matrix[i][j] > 0) {
                    hm2.put(nodes.get(j), matrix[i][j]);
                }
            }
            adjList.put(mainKey, hm2);
        }

        System.out.println("Printing Adjacency List:");
        printADJLIST(adjList);

        return adjList;
    }

    public void printADJLIST(HashMap<String, HashMap<String, Integer>> list){
        ArrayList<String> allNodes = this.getNodes();
        for(int i = 0; i < allNodes.size(); i++){
            HashMap<String,Integer> map = list.get(allNodes.get(i));
            System.out.print(allNodes.get(i) + " : ");
            int k = 0;
            for(Map.Entry mapElem : map.entrySet()){
                if(k != 0) System.out.print(",");
                System.out.print("(" + mapElem.getKey() + "," + mapElem.getValue() + ") ");
                k++;
            }
            System.out.println();
        }
    }

    public DataEntry findMinEntry(ArrayList<DataEntry> list){
        DataEntry min = new DataEntry();
        min.setCost(Integer.MAX_VALUE);

        for(DataEntry dataEntry: list){
            if(dataEntry.getCost() < min.getCost()){
                min = new DataEntry(dataEntry);
            }
        }

        return min;
    }
    public int contains(ArrayList<DataEntry> list, DataEntry entry){
        DataEntry temp;
        int location = -1, k = 0;
        for (DataEntry dataEntry : list) {
            temp = dataEntry;
            if (temp.getDestination().equals(entry.getDestination())){
                location = k;
                break;
            }
            k++;
        }
        return location;
    }

    public String[] convertList(ArrayList<DataEntry> list){
        String temp = "", destination = "", nextHop = "";
        int cost, i = 0;
        String[] result = new String[list.size()];

        for(DataEntry dataEntry: list){
            destination = dataEntry.getDestination();
            cost = dataEntry.getCost();
            nextHop = dataEntry.getNextHop();

            temp = destination + "," + cost + "," + nextHop;

            result[i] = temp;
            i++;
        }

        return result;
    }

    public int[][] getMatrix(){
        return this.graphMatrix;
    }

    public ArrayList<String> getNodes(){
        return this.nodes;
    }

    public void fillWithZeroesORPrint(int flag){
        int[][] matrix = this.getMatrix();
        ArrayList<String> allNodes = this.getNodes();

        if(flag != 0){
            System.out.printf("%-5s","");
            for(int i = 0; i < allNodes.size(); i++){
                System.out.printf("%-5s",allNodes.get(i));
            }
            System.out.println();
        }

        for(int i = 0; i < matrix[0].length; i++){
            for(int j = 0; j < matrix[1].length; j++){
                if(flag == 0) matrix[i][j] = 0;
                else {
                    if(j == 0){
                        System.out.printf("%-5s",allNodes.get(i));
                    }
                    System.out.printf("%-5s" , ("" + matrix[i][j]));
                }
            }
            if(flag != 0) System.out.println();
        }
    }

    public void breadthFirstTraversal(){
        int[][] matrix = getMatrix();
        ArrayList<String> allNodes = getNodes();

        ArrayList<String> bfs = new ArrayList<>();
        ArrayList<String> temp = new ArrayList<>();
        int i;

        String node = allNodes.get(0);
        String tempStr, tempStr2;
        temp.add(node);

        while(temp.size() != 0) {
            tempStr = temp.remove(0);
            bfs.add(tempStr);
            i = allNodes.indexOf(tempStr);

            for(int j = 0; j < matrix[1].length; j++){
                if(matrix[i][j] != 0){
                    tempStr2 = allNodes.get(j);
                    if(!bfs.contains(tempStr2) && !temp.contains(tempStr2)){
                        temp.add(tempStr2);
                    }
                }
            }
        }

        String result = "";

        for(String s : bfs){
            result += s + " ";
        }

        System.out.println("\nThe Breadth First Traversal results to : "  + result);
    }



}
