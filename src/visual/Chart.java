package visual;

import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import java.util.ResourceBundle;

public class Chart {
    public static final int HEIGHT = 250;

    private ResourceBundle myResources;
    private final NumberAxis xAxis = new NumberAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
    private final StackPane bottom = new StackPane();
    private final XYChart.Series<Number, Number> series = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> series3 = new XYChart.Series<>();

    /**
     * Creates the line chart that shows the change in cells over the time of the simulation
     * @param width sets the width of the line chart
     * @param simName
     * @param resource
     * @return
     */
    public StackPane createChart(int width, String simName, String resource){
        myResources = ResourceBundle.getBundle(resource + "allStrings");
        setAxis();
        createLineChart(width, simName);
        createStackPane();
        return bottom;
    }

    public void updateChart(int green, int red, int white, int step){
        series.getData().add(new XYChart.Data<>(step, red));
        series2.getData().add(new XYChart.Data<>(step, white));
        series3.getData().add(new XYChart.Data<>(step, green));

    }

    private void setAxis(){
        xAxis.setLabel(myResources.getString("Cycles"));
        xAxis.setAnimated(false);
        yAxis.setLabel(myResources.getString("Value"));
        yAxis.setAnimated(false);
    }

    private void createLineChart(int width, String simName){
        lineChart.setTitle(myResources.getString("cellCounts"));
        lineChart.setAnimated(false);
        createSeries(simName);
        if (!series.getName().equals("")) lineChart.getData().add(series);
        if (!series2.getName().equals("")) lineChart.getData().add(series2);
        if (!series3.getName().equals("")) lineChart.getData().add(series3);
        lineChart.setMaxSize(width, HEIGHT);
        lineChart.setCreateSymbols(false);
    }

    private void createSeries(String name){
        if (name.equals("Fire")){
            series.setName(myResources.getString("Burning"));
            series2.setName(myResources.getString("Burnt"));
            series3.setName(myResources.getString("Tree"));
        }
        else if (name.equals("GameOfLife")){
            series.setName("");
            series2.setName(myResources.getString("Dead"));
            series3.setName(myResources.getString("Alive"));
        }
        else if (name.equals("Segregation")){
            series.setName(myResources.getString("Population1"));
            series2.setName("");
            series3.setName(myResources.getString("Population2"));
        }
        else if (name.equals("Percolation")){
            series.setName("");
            series2.setName(myResources.getString("Open"));
            series3.setName(myResources.getString("Full"));
        }
        else if (name.equals("PredatorPrey")){
            series.setName(myResources.getString("Predator"));
            series2.setName(myResources.getString("Empty"));
            series3.setName(myResources.getString("Prey"));
        }
    }

    private void createStackPane(){
        bottom.setPrefHeight(400);
        bottom.setAlignment(lineChart, Pos.TOP_CENTER);
        bottom.getChildren().add(lineChart);
    }
}
