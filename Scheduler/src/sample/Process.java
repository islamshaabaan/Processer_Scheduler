package sample;

public class Process {
    private String name;
    private int arrival_time;
    private int burst_time;
    private int wt;
    private int turn_around_time;
    private int priority;
    private String color;
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }



    public Process() {
        arrival_time = 0;
        burst_time = 0;
        wt = 0;
        turn_around_time = 0;
        name = "";
        priority=0;

    }
    public Process(Process another) {
        this.arrival_time = another.arrival_time;
        this.burst_time = another.burst_time;
        this.wt = another.wt;
        this.turn_around_time = another.turn_around_time;
        this.name = another.name;
        this.priority=another.priority;
        this.color=another.color;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getArrival_time() {
        return arrival_time;
    }
    public void setArrival_time(int arrival_time) {
        this.arrival_time = arrival_time;
    }
    public int getBurst_time() {
        return burst_time;
    }

    public void setBurst_time(int burst_time) {
        this.burst_time = burst_time;
    }
    public int getWt() {
        return wt;
    }

    public void setWt(int wt) {
        this.wt = wt;
    }
    public int getTurn_around_time() {
        return turn_around_time;
    }

    public void setTurn_around_time(int turn_around_time) {
        this.turn_around_time = turn_around_time;
    }
    public int getPriority()
    {
        return priority;
    }
    public void setPriority(int g)
    {
        this.priority=g;
    }



    public static void ProcessSort(Process[] prcos) {
        int i, j;
        for(i = 0;  i < prcos.length-1;  i ++) {

            for (j = i+1; j <prcos.length ; j++) {
                if (prcos[j].getArrival_time() < prcos[i].getArrival_time()) {
                    Process temp = prcos[i];
                    prcos[i] = prcos[j];
                    prcos[j] = temp;
                }
            }

        }
    }

}
