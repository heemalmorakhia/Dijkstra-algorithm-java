/**
 * @author: Heemal Morakhia
 * @purpose: Support for my demo program
 */

public class DataEntry {

    private String destination;
    private int cost;
    private String nextHop;

    public DataEntry(){
    }

    public DataEntry(DataEntry entry){
        this.destination = entry.getDestination();
        this.cost = entry.getCost();
        this.nextHop = entry.getNextHop();
    }

    public DataEntry(String destination, int cost, String nextHop){
        this.destination = destination;
        this.cost = cost;
        this.nextHop = nextHop;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getNextHop() {
        return nextHop;
    }

    public void setNextHop(String nextHop) {
        this.nextHop = nextHop;
    }

    public String toString(){
        return this.getDestination() + "," + this.getCost() + "," + this.getNextHop();
    }
}
