package cellsociety;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Chart {
    public static final int HEIGHT = 250;

    private final CategoryAxis xAxis = new CategoryAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
    private final StackPane bottom = new StackPane();
    private final XYChart.Series<String, Number> series = new XYChart.Series<>();
    private final XYChart.Series<String, Number> series2 = new XYChart.Series<>();
    private final XYChart.Series<String, Number> series3 = new XYChart.Series<>();
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

    public StackPane createChart(int width, Stage currentStage){
        xAxis.setLabel("Time/s"); //found code to do this online
        xAxis.setAnimated(false);
        yAxis.setLabel("Value");
        yAxis.setAnimated(false);
        lineChart.setTitle("Cell counts");
        lineChart.setAnimated(false);
        series.setName("Orange Red Cell");
        lineChart.getData().add(series);
        lineChart.setMaxSize(width, HEIGHT);

        scheduledExecutorService.scheduleAtFixedRate(() -> {
                    Integer random = ThreadLocalRandom.current().nextInt(10);


                    Platform.runLater(() -> {

                        Date now = new Date();
                        // put random number with current time
                        series.getData().add(new XYChart.Data<>(simpleDateFormat.format(now), random));
                        if (series.getData().size() > 10) {series.getData().remove(0);}
                    });
                }, 0, 1, TimeUnit.SECONDS);
        bottom.setPrefHeight(400);
        bottom.setAlignment(lineChart, Pos.TOP_CENTER);
        bottom.getChildren().add(lineChart);
        currentStage.setOnCloseRequest(event -> {
            scheduledExecutorService.shutdownNow();
        });
        return bottom;
    }
}
