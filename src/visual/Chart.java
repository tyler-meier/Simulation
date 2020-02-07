package visual;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Chart {
    public static final int HEIGHT = 250;

    private final NumberAxis xAxis = new NumberAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
    private final StackPane bottom = new StackPane();
    private final XYChart.Series<Number, Number> series = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> series3 = new XYChart.Series<>();
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    public StackPane createChart(int width, Stage currentStage){
        setAxis();
        createLineChart(width);
        createStackPane();
        return bottom;
    }
    private void setAxis(){
        xAxis.setLabel("Cycles"); //found code to do this online
        xAxis.setAnimated(false);
        yAxis.setLabel("Value");
        yAxis.setAnimated(false);
    }

    private void createLineChart(int width){
        lineChart.setTitle("Cell counts");
        lineChart.setAnimated(false);
        series.setName("Orange Red Cell");
        series2.setName("Light Green Cell");
        series3.setName("White Cell");
        lineChart.getData().add(series);
        lineChart.getData().add(series2);
        lineChart.getData().add(series3);
        lineChart.setMaxSize(width, HEIGHT);
    }

    public void updateChart(int green, int red, int white){
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            Platform.runLater(() -> {

                // put random number with current time
                //series.getData().add(new XYChart.Data<>(1, red));
                series2.getData().add(new XYChart.Data<>(1, green));
                series3.getData().add(new XYChart.Data<>(1, white));
            });
        }, 0, 1, TimeUnit.SECONDS);

    }

    public void createStackPane(){
        bottom.setPrefHeight(400);
        bottom.setAlignment(lineChart, Pos.TOP_CENTER);
        bottom.getChildren().add(lineChart);
    }
}
