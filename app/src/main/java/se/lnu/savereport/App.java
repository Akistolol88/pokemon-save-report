package se.lnu.savereport;

import java.util.List;
import se.lnu.rm222xi.svgcharts.BarChart;

public class App {
    public static void main(String[] args) {
        BarChart barChart = new BarChart();
        barChart.setTitle("Party levels");
        barChart.setLabels(List.of("Bulbasaur", "Charmander", "Squirtle"));
        barChart.setValues(List.of(6.0, 5.0, 4.0));
        System.out.println(barChart.toSvg());
    }
}
