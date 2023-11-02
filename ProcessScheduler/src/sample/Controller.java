package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    public static String[] colors = {"status-darkRed","status-green","status-blue","status-yellow","status-black",
            "status-brown","status-foshia","status-bate5y","status-smawy","status-nescafe","status-orange",
            "status-red","status-lamony","status-holoOrange","status-purple","status-move","status-white"};

    public TextField processInput;
    public TextField burstInput;
    public TextField priorityInput;
    public TextField arrivalInput;
    public TableView<Process> table;
    public TableColumn processColumn;
    public TableColumn burstColumn;
    public TableColumn priorityColumn;
    public TableColumn arrivalColumn;
    public ComboBox comboBox;
    public VBox chartVBox;
    public Label comboErrorLabel;
    public Label nameLabel;
    public Label burstLabel;
    public Label priorityLabel;
    public Label arrivalLabel;
    public TextField quantumInput;
    public Label quantumError;
    public HBox buttonsBox;
    public BorderPane root;
    public VBox legendVBox;
    public Label numberError;
    public TextField numberInput;
    public MenuItem play;
    private boolean prioritySet = true;
    public TextField average_waiting_time;
    public TextField Average_Turnaround_Time;
    GanttChart<Number,String> chart;

    public void addButtonClicked() {
        Process process = new Process();
        String name = processInput.getText();
        String burstTime = burstInput.getText();
        String arrivalTime = arrivalInput.getText();
        String priority = priorityInput.getText();
        if(prioritySet) {
            if (!validateName(name) | !validateBurst(burstTime) | !validateArrival(arrivalTime) | !validatePriority(priority))
                return;
        }
        else{
            if (!validateName(name) | !validateBurst(burstTime) | !validateArrival(arrivalTime))
                return;
        }
        for (Process p : table.getItems())
        {
            if(("P"+name).equals(p.getName())) {
                nameLabel.setText("*Enter unique No.");
                return;
            }
        }
        // nameLabel.setText("");

        process.setName("P"+name);
        process.setBurst_time(Integer.parseInt(burstTime));
        process.setArrival_time(Integer.parseInt(arrivalTime));
        if(prioritySet)
            process.setPriority(Integer.parseInt(priority));
        table.getItems().add(process);
        processInput.clear();
        burstInput.clear();
        priorityInput.clear();
        arrivalInput.clear();

    }
    boolean validateName(String name)
    {
        try {
            int number = Integer.parseInt(name);
            nameLabel.setText("");
            if(number<0)
            {
                nameLabel.setText("*Enter +ve No.");
                return false;
            }
            return true;
        }catch (NumberFormatException e)
        {
            nameLabel.setText("*Enter No. of Process");
            return false;
        }
    }
    boolean validateBurst(String burst)
    {
        try {
            int number = Integer.parseInt(burst);
            if(number<0)
            {
                burstLabel.setText("*Enter +ve No.");
                return false;
            }
            burstLabel.setText("");
            return true;
        }catch (NumberFormatException e)
        {
            burstLabel.setText("*Enter integer Time");
            return false;
        }
    }
    boolean validateArrival(String arrival)
    {
        try {
            int number = Integer.parseInt(arrival);
            if(number<0)
            {
                arrivalLabel.setText("*Enter +ve No.");
                return false;
            }
            arrivalLabel.setText("");
            return true;
        }catch (NumberFormatException e)
        {
            arrivalLabel.setText("*Enter integer Time");
            return false;
        }
    }
    boolean validatePriority(String priority)
    {
        try {
            int number = Integer.parseInt(priority);
            if(number<0)
            {
                priorityLabel.setText("*Enter +ve No.");
                return false;
            }
            priorityLabel.setText("");
            return true;
        }catch (NumberFormatException e)
        {
            priorityLabel.setText("*Enter integer No.");
            return false;
        }
    }
    boolean validateQuantum(String quantum)
    {
        try {
            int number = Integer.parseInt(quantum);
            if (number<=0)
            {
                quantumError.setText("*Enter +ve No.");
                return false;
            }
            quantumError.setText("");
            return true;
        }catch (NumberFormatException e)
        {
            quantumError.setText("*Enter Correct Quantum");
            return false;
        }
    }
    boolean validateNumber(String no)
    {
        try {
            int number = Integer.parseInt(no);
            if(number<0)
            {
                numberError.setText("*Enter +ve No.");
                return false;
            }
            numberError.setText("");
            return true;
        }catch (NumberFormatException e)
        {
            numberError.setText("*Enter integer No.");
            return false;
        }
    }

    public void deleteButtonClicked() {
        ObservableList<Process> productSelected, allProducts;
        allProducts = table.getItems();
        productSelected = table.getSelectionModel().getSelectedItems();
        productSelected.forEach(allProducts::remove);

    }

    //called as soon as the layout loads
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        root.getStylesheets().add(getClass().getResource("Viper.css").toExternalForm());

        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        burstColumn.setCellValueFactory(new PropertyValueFactory<>("burst_time"));
        arrivalColumn.setCellValueFactory(new PropertyValueFactory<>("arrival_time"));
        processColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        comboBox.getItems().add("Priority(Non-Preemptive)");
        comboBox.getItems().add("Priority(Preemptive-FCFS)");
        comboBox.getItems().add("Priority(Preemptive-Round Robin)");
        comboBox.getItems().add("FCFS");
        comboBox.getItems().add("SJF(Non-Preemptive)");
        comboBox.getItems().add("SJF(PreemptiveFC)");
        comboBox.getItems().add("SJF(PreemptiveRR)");
        comboBox.getItems().add("Round Robin");

        Image cameraIcon = new Image(getClass().getResourceAsStream("/sample/index.png"));
        ImageView camera = new ImageView(cameraIcon);
        camera.setFitWidth(20);
        camera.setFitHeight(20);
        play.setGraphic(camera);
        play.setOnAction(event -> {
            startButtonClicked();

        });

        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();

        chart = new GanttChart<>(xAxis,yAxis);
        xAxis.setLabel("");
        xAxis.setTickLabelFill(Color.CHOCOLATE);
        xAxis.setMinorTickCount(4);

        yAxis.setLabel("");
        yAxis.setTickLabelFill(Color.CHOCOLATE);
        yAxis.setTickLabelGap(10);
        yAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(new String[]{""})));
        chart.setTitle("Gantt Chart");
        chart.setLegendVisible(false);
        chart.setBlockHeight(50);
        chart.getStylesheets().add(getClass().getResource("ganttchart.css").toExternalForm());
        chartVBox.getChildren().add(chart);
        comboBox.setOnAction(event -> {
            comboErrorLabel.setText("");
            if (comboBox.getValue().equals("Round Robin"))
            {
                showQuantumStuff(true);
                if(prioritySet)
                    setTableForNoPriority();
            }
            else if (comboBox.getValue().equals("FCFS")||
                    comboBox.getValue().equals("SJF(Non-Preemptive)")||
                    comboBox.getValue().equals("SJF(PreemptiveFC)")||
                    comboBox.getValue().equals("SJF(PreemptiveRR)"))
            {
                showQuantumStuff(false);
                if(prioritySet)

                    setTableForNoPriority();
            }
            else
            {
                showQuantumStuff(false);
                if(!prioritySet)
                    setTableForPriority();
            }
        });

        processInput.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(processInput.getText()==null || processInput.getText().isEmpty()) {
                nameLabel.setText("");
                return;
            }
            validateName(processInput.getText());

        }));
        burstInput.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(burstInput.getText()==null || burstInput.getText().isEmpty()) {
                burstLabel.setText("");
                return;
            }
            validateBurst(burstInput.getText());
        }));
        arrivalInput.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(arrivalInput.getText()==null || arrivalInput.getText().isEmpty()) {
                arrivalLabel.setText("");
                return;
            }
            validateArrival(arrivalInput.getText());
        }));
        priorityInput.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(priorityInput.getText()==null || priorityInput.getText().isEmpty()) {
                priorityLabel.setText("");
                return;
            }
            validatePriority(priorityInput.getText());
        }));

        quantumInput.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(quantumInput.getText()==null || quantumInput.getText().isEmpty()){
                quantumError.setText("");
                return;
            }
            validateQuantum(quantumInput.getText());
        }));
        numberInput.textProperty().addListener((observable -> {
            if(validateNumber(numberInput.getText()))
            {
                int no = Integer.parseInt(numberInput.getText());


                if(table.getItems().size()!=no)
                    numberError.setText("*Number doesn't match table, No of Processes in table will be considered");
                else
                    numberError.setText("");
            }
            else if(numberInput.getText().equals(""))
                numberError.setText("*Number wasn't entered, No of Processes in table will be considered");
        }));
    }



    public void resetButtonClicked() {
        table.getItems().clear();
        chart.getData().clear();
        legendVBox.getChildren().clear();
    }

    public void startButtonClicked() {

        if(  comboBox.getSelectionModel().getSelectedItem() == null)
            comboErrorLabel.setText("*Please Choose Algorithm First ");
        else if(table.getItems().size()==0)
            comboErrorLabel.setText("*Please Enter at least one Process ");
        else {
            chart.getData().clear();
            legendVBox.getChildren().clear();
            ObservableList<Process> processes =  table.getItems();
            Process[] pro = new Process[processes.size()];
            for (int i=0;i<pro.length;i++)
            {
                pro[i] = processes.get(i);
                pro[i].setColor(colors[i%colors.length]);

            }
            legend_Process_Sort(pro);
            setLegend(pro);
            Process.ProcessSort(pro);

            comboErrorLabel.setText("");
            if(numberInput.getText().equals(""))
                numberError.setText("*Number wasn't entered, No of Processes in table will be considered");

            switch ((String) comboBox.getValue()) {
                case "Priority(Non-Preemptive)": // Priority(Non-Preemptive)
                {
                    XYChart.Series series = PriorityAlgorithm.NonPrePriority(pro);
                    average_waiting_time.setText(Double.toString(PriorityAlgorithm.getAverage_waiting_time()));
                    Average_Turnaround_Time.setText(Double.toString(PriorityAlgorithm.getAverage_turnAround_time()));
                    chart.getData().addAll(series);
                    break;

                }
                case "Priority(Preemptive-FCFS)":
                {
                    XYChart.Series series = PriorityAlgorithm.PrePriorityFC(pro);
                    average_waiting_time.setText(Double.toString(PriorityAlgorithm.getAverage_waiting_time()));
                    Average_Turnaround_Time.setText(Double.toString(PriorityAlgorithm.getAverage_turnAround_time()));
                    chart.getData().addAll(series);
                    break;
                }
                case "Priority(Preemptive-Round Robin)": // Priority(Non-Preemptive)
                {
                    XYChart.Series series = PriorityAlgorithm.PrePriorityRR(pro);
                    average_waiting_time.setText(Double.toString(PriorityAlgorithm.getAverage_waiting_time()));
                    Average_Turnaround_Time.setText(Double.toString(PriorityAlgorithm.getAverage_turnAround_time()));
                    chart.getData().addAll(series);
                    break;
                }
                case "FCFS": // Priority(Non-Preemptive)
                {
                    XYChart.Series series = FCFSAlgorithm.FCFS(pro);
                    average_waiting_time.setText(Double.toString(PriorityAlgorithm.getAverage_waiting_time()));
                    Average_Turnaround_Time.setText(Double.toString(PriorityAlgorithm.getAverage_turnAround_time()));
                    chart.getData().addAll(series);
                    break;
                }
                case "SJF(Non-Preemptive)": // Priority(Non-Preemptive)
                {
                    XYChart.Series series = SJFAlgorithm.NonPreSJF(pro);
                    average_waiting_time.setText(Double.toString(PriorityAlgorithm.getAverage_waiting_time()));
                    Average_Turnaround_Time.setText(Double.toString(PriorityAlgorithm.getAverage_turnAround_time()));
                    chart.getData().addAll(series);
                    break;
                }
                case "SJF(PreemptiveFC)": // Priority(Non-Preemptive)
                {
                    XYChart.Series series = SJFAlgorithm.PreSJFFC(pro);
                    average_waiting_time.setText(Double.toString(PriorityAlgorithm.getAverage_waiting_time()));
                    Average_Turnaround_Time.setText(Double.toString(PriorityAlgorithm.getAverage_turnAround_time()));

                    chart.getData().addAll(series);
                    break;
                }
                case "SJF(PreemptiveRR)": // Priority(Non-Preemptive)
                {
                    XYChart.Series series = SJFAlgorithm.PreSJFRR(pro);
                    average_waiting_time.setText(Double.toString(PriorityAlgorithm.getAverage_waiting_time()));
                    Average_Turnaround_Time.setText(Double.toString(PriorityAlgorithm.getAverage_turnAround_time()));

                    chart.getData().addAll(series);
                    break;
                }
                case "Round Robin": // Priority(Non-Preemptive)
                {
                    if(validateQuantum(quantumInput.getText())) {
                        XYChart.Series series = RoundRobinAlgorithm.RoundRobin(pro, Integer.parseInt(quantumInput.getText()));
                        average_waiting_time.setText(Double.toString(RoundRobinAlgorithm.getAverage_waiting_time()));
                        Average_Turnaround_Time.setText(Double.toString(RoundRobinAlgorithm.getAverage_turnAround_time()));

                        chart.getData().addAll(series);
                    }
                    break;

                }
            }
        }

    }

    private void setTableForNoPriority()
    {
        table.getColumns().remove(2);
        buttonsBox.getChildren().remove(2);
        burstColumn.setPrefWidth(165.0);
        processColumn.setPrefWidth(150.0);
        arrivalColumn.setPrefWidth(190.0);
        prioritySet = false;
    }
    private void setTableForPriority()
    {
        buttonsBox.autosize();
        table.autosize();
        table.getColumns().add(2,priorityColumn);
        buttonsBox.getChildren().add(2,priorityInput);
        burstColumn.setPrefWidth(154.0);
        processColumn.setPrefWidth(130.0);
        arrivalColumn.setPrefWidth(182.0);
        prioritySet = true;

    }
    private void showQuantumStuff(boolean show)
    {
        quantumInput.setVisible(show);
        quantumError.setVisible(show);
    }

    private void setLegend( Process[] processes)
    {
        HBox[] hBoxes = new HBox[3];
        hBoxes[0] = new HBox(10);
        legendVBox.getChildren().add(hBoxes[0]);
        int i=0;
        for(Process process:processes) {

            Label label = new Label(process.getName());
            label.setTextFill(Color.web("#FFF"));
            label.setStyle("-fx-font-size:15pt; -fx-font-weight: bold;");
            TextField textField = new TextField();
            textField.setEditable(false);

            textField.getStyleClass().add(process.getColor());
            textField.setPrefWidth(20);
            hBoxes[i].getChildren().addAll(textField, label,new Label(""));
            if(hBoxes[i].getChildren().size()==18) {
                i++;
                hBoxes[i] = new HBox(10);
                legendVBox.getChildren().add(hBoxes[i]);
            }
        }
    }

    public static void legend_Process_Sort(Process[] prcos) {
        int i, j;
        for (i = 0; i<prcos.length-1; i++) {
            for (j = i+1; j < prcos.length; j++) {
                if (prcos[j].getName().compareTo(prcos[i].getName()) < 0) {
                    Process temp = prcos[i];
                    prcos[i] = prcos[j];
                    prcos[j] = temp;
                }
            }
        }
    }

}
