/**
 * @author: Heemal Morakhia
 * @purpose: Understanding and implementing Djikstra's Algorithm
 * @Resources: Dalhousie University - CSCI 2110 - Data Structures and Algorithms
 */

import java.util.ArrayList;
import java.util.Scanner;

public class demo {
    public static void main(String [] args){
        Scanner in = new Scanner(System.in);

        System.out.println("Welcome! Start by entering nodes and write 'done' when you are done: ");
        String s = in.nextLine();
        ArrayList<String> input = new ArrayList<>();
        while(!s.toLowerCase().equals("done")){
            input.add(s);
            s = in.nextLine();
        }

        System.out.println("Okay you got the nodes down.\nIs this graph directed or not?");
        boolean directed = false;
        if(in.nextLine().toLowerCase().charAt(0) == 'y') directed = true;

        System.out.println("Is this graph weighted or not?");
        boolean weighted = false;
        if(in.nextLine().toLowerCase().charAt(0) == 'y') weighted = true;

        Graph graph = new Graph(input,directed,weighted);
        boolean loop = true;

        System.out.println("\nWhat do you wanna do? \n" +
                "1) Create an Edge \n" +
                "2) Print the table \n" +
                "3) Exit \n" +
                "4) BFS \n");

        while(loop) {
            System.out.println("\nEnter 1,2 or 3: ");
            int choice = Integer.parseInt(in.nextLine());
            String edge;

            switch (choice){
                case 1:
                    System.out.println("Okay. You can enter your input in the following form: \n" +
                            "Node1,Node2,weight(if applicable) \n" +
                            "Comma separated");
                    edge = in.nextLine();
                    graph.createEdge(edge);
                    break;
                case 2:
                    graph.fillWithZeroesORPrint(1);
                    break;
                case 3:
                    loop = false;
                    break;
                case 4:
                    graph.breadthFirstTraversal();
                    break;
                default:
                    System.out.println("Invalid Input. 1,2 or 3");
            }
        }

        System.out.println("Do you wanna try out the Djikstra Algorithm?");
        boolean answer = false;
        if(in.nextLine().toLowerCase().charAt(0) == 'y') answer = true;

        if(!answer){
            System.out.println("Alright, See ya!");
            System.exit(-1);
        }

        System.out.println("\nPlease enter the source node: ");
        String source = in.nextLine();
        System.out.println();

        String[] result = graph.shortestPath_Djikstra(source);

        System.out.println("\nThe Shortest Paths from " + source + " to every node in the graph are as follows:");

        for(int i = 0; i < result.length; i++){
            System.out.printf("%-5s",i + 1 + ".");
            System.out.printf("%-15s", result[i]);
            System.out.println();
        }

        System.out.println("\n Congratulations. Now you have learned Djisktra Algorithm!!!!!!");
    }
}
