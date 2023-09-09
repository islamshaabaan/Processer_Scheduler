package sample;

import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.LinkedList;

import static sample.Controller.colors;

public class PriorityAlgorithm {
    private static double Average_waiting_time=0;
    private static double Average_turnAround_time=0;

    public static double getAverage_turnAround_time() {
        return Average_turnAround_time;
    }

    public static void setAverage_turnAround_time(double average_turnAround_time) {
        Average_turnAround_time = average_turnAround_time;
    }

    public static  double getAverage_waiting_time() {
        return Average_waiting_time;
    }

    public static void setAverage_waiting_time(double average_waiting_time) {
        Average_waiting_time = average_waiting_time;
    }


    public static XYChart.Series NonPrePriority (Process[] copy_process  )
    {
        setAverage_turnAround_time(0);
        setAverage_waiting_time(0);
        Process[]p1=new Process[copy_process.length];
        for (int i = 0; i < p1.length; i++) {
            p1[i]=new Process(copy_process[i]);
        }
        XYChart.Series series1 = new XYChart.Series();

        double resc = 0;
        ArrayList<Process>p =new ArrayList<>();
        for(int i=0;i<p1.length;i++){
            p.add(p1[i]);
        }

        String seq = new String();
        double res=0;
        int finishtime=0;
        int starttime=0;

        ArrayList <Process> maskp=new ArrayList<Process>();
        for(int i=0;i<p.size();i++){
            maskp.add(p.get(i));
        }

        int t = 0;
        int max=0;
        for(int j=0;j<p.size();j++) {
            for (int i = 1; i < p.size(); i++) {
                if (t >= p.get(i).getArrival_time()) {
                    if (p.get(i).getPriority() < p.get(max).getPriority()) {
                        max = i;
                    }
                }
            }
            if (p.get(max).getArrival_time() >= 0) {
                if((t==0&&p.get(max).getArrival_time()!=0)||p.get(max).getArrival_time() >t ) {
                    int k=t;

                    for (int i = 0; i < p.get(max).getArrival_time()-k; i++) {
                        starttime=t;
                        seq +=" " + t + " ";
                        t++;
                        finishtime=t;
                        //  series1.getData().add(new XYChart.Data(starttime,"processes",new GanttChart.ExtraData( finishtime-starttime,colors[j])));
                        for (int i1 = 1; i1 < p.size(); i1++) {
                            if (t >= p.get(i1).getArrival_time()) {
                                if (p.get(i1).getPriority() < p.get(max).getPriority()) {
                                    max = i1;
                                }
                            }
                        }
                    }
                }


                seq += t+" " +p.get(max).getName()+" ";
                starttime=t;

                t = t + p.get(max).getBurst_time();
                p.get(max).setWt(t-copy_process[maskp.indexOf(p.get(max))].getBurst_time()-p.get(max).getArrival_time());
                p.get(max).setTurn_around_time(t - p.get(max).getArrival_time());
                finishtime=t;
                series1.getData().add(new XYChart.Data(starttime,"", new GanttChart.ExtraData( finishtime-starttime, p1[(maskp.indexOf(p.get(max)))].getColor())));


            }
            p.remove(max);
            j--;
            max=0;
        }
        System.out.println("name   wtime");
        seq +=" " + t ;
        for (int i = 0; i < maskp.size(); i++) {
            System.out.println(" " + maskp.get(i).getName()
                    + "    " + maskp.get(i).getWt());
            res = res + maskp.get(i).getWt();
            resc = resc + maskp.get(i).getTurn_around_time();
        }
        System.out.println("Average waiting time is "
                + (float)res / maskp.size());
        setAverage_waiting_time((double)res/maskp.size());
        setAverage_turnAround_time(((double)resc/maskp.size()));
        System.out.println("Sequence is like that " + seq);


        return series1;
    }

    public static XYChart.Series PrePriorityFC (Process[] copy_process)
    {
        setAverage_turnAround_time(0);
        setAverage_waiting_time(0);
        double resc = 0;
        Process[]p1=new Process[copy_process.length];
        for (int i = 0; i < p1.length; i++) {
            p1[i]=new Process(copy_process[i]);
        }
        XYChart.Series series1 = new XYChart.Series();

        int starttime=0;
        int finishtime=0;
        ArrayList<Process> p=new ArrayList<>();
        for(int i=0;i<p1.length;i++){
            p.add(p1[i]);
        }
        String seq = new String();
        double res=0;
        ArrayList <Process> maskp=new ArrayList<Process>();
        for(int i=0;i<p.size();i++){
            maskp.add(p.get(i));
        }
        LinkedList<Process> queue=new LinkedList<Process>();
        for (int i = 0; i < p.size(); i++) {
            queue.add(p.get(i));
        }
        int t = 0;
        int max=0;
        for(int j=0;j<queue.size();j++) {
            for (int i = 1; i < queue.size(); i++) {
                if (t >= queue.get(i).getArrival_time()) {
                    if (queue.get(i).getPriority() <= queue.get(max).getPriority()) {
                        if (queue.get(i).getName().compareTo(queue.get(max).getName()) < 0||(queue.get(i).getArrival_time() < queue.get(max).getArrival_time()&&queue.get(i).getPriority() < queue.get(max).getPriority())||queue.get(i).getPriority() < queue.get(max).getPriority()) {
                            max = i;
                        }
                    }
                }
            }
            if (queue.get(max).getArrival_time() >= 0) {
                if((t==0&&queue.get(max).getArrival_time()!=0)||queue.get(max).getArrival_time() >t ) {
                    int k=t;
                    for (int i = 0; i < queue.get(max).getArrival_time()-k; i++) {
                        starttime=t;
                        seq +=" " + t + " ";
                        t++;
                        finishtime=t;
                        for (int i1 = 1; i1 < queue.size(); i1++) {
                            if (t >= queue.get(i1).getArrival_time()) {
                                if (queue.get(i1).getPriority() <= queue.get(max).getPriority()) {
                                    if (queue.get(i1).getName().compareTo(queue.get(max).getName()) < 0||(queue.get(i1).getArrival_time() < queue.get(max).getArrival_time()&&queue.get(i1).getPriority() < queue.get(max).getPriority())||queue.get(i1).getPriority() < queue.get(max).getPriority()) {
                                        max = i1;
                                    }
                                }
                            }
                        }
                    }
                }
                starttime=t;



                queue.get(max).setBurst_time(queue.get(max).getBurst_time()-1);
                seq += t+" " +queue.get(max).getName()+" ";
                t = t + 1;
                queue.get(max).setWt(t-copy_process[maskp.indexOf(queue.get(max))].getBurst_time()-queue.get(max).getArrival_time());
                queue.get(max).setTurn_around_time(t-queue.get(max).getArrival_time());
                finishtime=t;
                series1.getData().add(new XYChart.Data(starttime,"", new GanttChart.ExtraData( finishtime-starttime, p1[(maskp.indexOf(queue.get(max)))].getColor())));

            }
            if (queue.get(max).getBurst_time() != 0) {
                queue.add( queue.get(max));
            }
            queue.remove(max);
            j--;
            max=0;
        }
        System.out.println("name   wtime");
        seq +=" " + t ;
        for (int i = 0; i < maskp.size(); i++) {
            System.out.println(" " + maskp.get(i).getName()
                    + "    " + maskp.get(i).getWt());
            res = res + maskp.get(i).getWt();
            resc = resc + maskp.get(i).getTurn_around_time();

        }
        System.out.println("Average waiting time is "
                + (float)res / maskp.size());
        setAverage_waiting_time((double)res/maskp.size());
        setAverage_turnAround_time((double)resc/maskp.size());
        System.out.println("Sequence is like that " + seq);
        return series1;
    }

    public static XYChart.Series PrePriorityRR (Process[] copy_process)

    {
        setAverage_turnAround_time(0);
        setAverage_waiting_time(0);
        Process[]p1=new Process[copy_process.length];
        for (int i = 0; i < p1.length; i++) {
            p1[i]=new Process(copy_process[i]);
        }
        XYChart.Series series1 = new XYChart.Series();

        int starttime=0;
        int finishtime=0;
        ArrayList <Process> p=new ArrayList<Process>();
        for(int i=0;i<p1.length;i++){
            p.add(p1[i]);
        }
        String seq = new String();
        double res=0;
        double resc=0;
        ArrayList <Process> maskp=new ArrayList<Process>();
        for(int i=0;i<p.size();i++){
            maskp.add(p.get(i));
        }
        LinkedList<Process> queue=new LinkedList<Process>();
        for (int i = 0; i < p.size(); i++) {
            queue.add(p.get(i));
        }
        int t = 0;
        int max=0;
        for(int j=0;j<queue.size();j++) {
            for (int i = 1; i < queue.size(); i++) {
                if (t >= queue.get(i).getArrival_time()) {
                    if (queue.get(i).getPriority() < queue.get(max).getPriority()) {
                        max = i;
                    }
                }
            }
            if (queue.get(max).getArrival_time() >= 0) {
                if((t==0&&queue.get(max).getArrival_time()!=0)||queue.get(max).getArrival_time() >t ) {
                    int k=t;
                    for (int i = 0; i < queue.get(max).getArrival_time()-k; i++) {
                        starttime=t;
                        seq +=" " + t + " ";
                        t++;
                        finishtime=t;
                        for (int i1 = 1; i1 < queue.size(); i1++) {
                            if (t >= queue.get(i1).getArrival_time()) {
                                if (queue.get(i1).getPriority() < queue.get(max).getPriority()) {
                                    max = i1;
                                }
                            }
                        }
                    }
                }
                starttime=t;


                queue.get(max).setBurst_time(queue.get(max).getBurst_time()-1);
                seq += t+" " +queue.get(max).getName()+" ";
                t = t + 1;

                queue.get(max).setWt(t-copy_process[maskp.indexOf(queue.get(max))].getBurst_time()-queue.get(max).getArrival_time());
                queue.get(max).setTurn_around_time( t - queue.get(max).getArrival_time());
                finishtime=t;
                series1.getData().add(new XYChart.Data(starttime,"", new GanttChart.ExtraData( finishtime-starttime, p1[(maskp.indexOf(queue.get(max)))].getColor())));

            }
            if (queue.get(max).getBurst_time() != 0) {
                queue.add( queue.get(max));
            }
            queue.remove(max);
            j--;
            max=0;
        }
        System.out.println("name   wtime");
        seq +=" " + t ;
        for (int i = 0; i < maskp.size(); i++) {
            System.out.println(" " + maskp.get(i).getName()
                    + "    " + maskp.get(i).getWt());
            res = res + maskp.get(i).getWt();
            resc=resc+maskp.get(i).getTurn_around_time();

        }
        System.out.println("Average waiting time is "
                + (float)res / maskp.size());
        System.out.println("Sequence is like that " + seq);
        setAverage_waiting_time((double)res/maskp.size());
        setAverage_turnAround_time((double)resc/maskp.size());
        return series1;
    }


}