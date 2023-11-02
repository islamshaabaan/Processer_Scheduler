package sample;

import javafx.scene.chart.XYChart;

import static sample.PriorityAlgorithm.*;

public class SJFAlgorithm {

    public static XYChart.Series NonPreSJF(Process p[])
    {
        for(int i=0; i<p.length; i++)
        {
            p[i].setPriority(p[i].getBurst_time());
        }

        Process.ProcessSort(p);

        return NonPrePriority( p);
    }

    public static XYChart.Series  PreSJFFC(Process p[])
    {
        for(int i=0; i<p.length; i++) {
            p[i].setPriority(p[i].getBurst_time());
        }

        Process.ProcessSort(p);

        return PrePriorityFC( p);
    }
    public static XYChart.Series  PreSJFRR(Process p[])
    {
        for(int i=0; i<p.length; i++) {
            p[i].setPriority(p[i].getBurst_time());
        }

        Process.ProcessSort(p);

        return PrePriorityRR( p);
    }

}

