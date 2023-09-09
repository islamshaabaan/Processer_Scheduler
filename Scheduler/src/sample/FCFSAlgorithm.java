package sample;

import javafx.scene.chart.XYChart;

import java.util.ArrayList;

import static sample.PriorityAlgorithm.NonPrePriority;


    public class FCFSAlgorithm {

        public static  XYChart.Series FCFS(Process p[])
        {
            for(int i=0; i<p.length; i++) {
                p[i].setPriority(p[i].getArrival_time());
            }

            Process.ProcessSort(p);
            ArrayList<Process> processes = new ArrayList<Process>();
            for (int i=0;i<p.length;i++) {
                processes.add(p[i]);
            }
            return  NonPrePriority(p);
        }
    }


