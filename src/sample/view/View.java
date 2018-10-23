package sample.view;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.model.Model;
import sample.model.algorithm.*;
import sample.util.Point;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by Mina on 2017. 11. 09..
 */
public class View extends Application {

    private Model model;
    private GridPane root = new GridPane();
    private Scene scene = new Scene(root);
    private Stage window;
    private boolean hasChanges;
    private TableView<myPairs> table = new TableView<>();
    private TableView<String> singleTable = new TableView<>();
    private final ObservableList<myPairs> data = FXCollections.observableArrayList(
            new myPairs("0", "none")
    );
    private Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();


    public static void main(String[] args){
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        hasChanges = false;
        window = primaryStage;


        window.setTitle("Graph Educator");
        window.setScene(initHomePage(window));
        window.setMaximized(true);
        //window.setFullScreen(true);


        //hülye padding nem akarja az igazságot... se a hülye font color


        //root.add(editGraph, 0, 1);
        //root.add(learn, 1, 1);
        //root.add(supervision, 2, 1);

        window.show();
    }

    private Scene initHomePage(Stage window) {

        GridPane root = new GridPane();

        Scene scene = new Scene(root);


        window.setWidth(visualBounds.getWidth());
        window.setHeight(visualBounds.getHeight());
        window.setMaximized(true);

        Button editGraph = new Button("Edit");
        editGraph.setOnAction(e -> {
            window.setScene(initEditGraph(window));
        });
        Button learn = new Button("Learn");
        learn.setOnAction(e -> {
            window.setScene(initLearn(window));
        });
        Button supervision = new Button("Supervise");
        supervision.setOnAction((e -> {
            window.setScene(initSupervise(window));
        }));

        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));


        root.setHgap(200);
        //root.setVgap(3);
        //root.setPadding(new Insets(100, 100, 100, 100));
        root.setAlignment(Pos.CENTER);

        editGraph.setStyle("-fx-font: 40 arial; -fx-font-color: FFFFFF;  -fx-base: FF7CDB; ");
        learn.setStyle("-fx-font: 40 arial; -fx-font-color: FFFFFF;  -fx-base: FF7CDB;");
        supervision.setStyle("-fx-font: 40 arial; -fx-font-color: FFFFFF;  -fx-base: FF7CDB;");

        editGraph.setMaxWidth(Double.MAX_VALUE);
        learn.setMaxWidth(Double.MAX_VALUE);
        supervision.setMaxWidth(Double.MAX_VALUE);

        VBox vbButtons = new VBox();

        vbButtons.setPadding(new Insets(10, 20, 10, 20));
        vbButtons.getChildren().addAll(editGraph, learn, supervision);

        root.add(vbButtons, 0, 0);

        return scene;
    }

    private Scene initEditGraph(Stage window) {
        HBox root = new HBox();

        Canvas canvas = new Canvas(visualBounds.getWidth()/2, visualBounds.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Observer graphChangedHandler = new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println("Graph changed...");

                if (!(arg instanceof List)){
                    return;
                }

                gc.clearRect(0, 0, 300, 300);

                for (Object d : (List)arg){
                    if(d instanceof Drawable) {
                        ((Drawable)d).Draw(gc);
                    }
                }
            }
        };

        newGraphMethod(window, graphChangedHandler, gc);



        Button newVertexButton = new Button("New vertex");
        newVertexButton.setMaxWidth(Double.MAX_VALUE);
        newVertexButton.setOnAction(e -> {

            String label = null;
            int x = 0;
            int y = 0;

            try {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Add new vertex");
                //dialog.setHeaderText("Look, a Text Input Dialog");
                dialog.setContentText("Vertex name:");
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()){
                   label = result.get();
                }
                dialog.setTitle("Add new vertex");
                dialog.setContentText("Vertex X position:");
                result = dialog.showAndWait();
                if (result.isPresent()){
                    x = Integer.parseInt(result.get());
                }
                dialog.setTitle("Add new vertex");
                dialog.setContentText("Vertex Y position:");
                result = dialog.showAndWait();
                if (result.isPresent()){
                    y = Integer.parseInt(result.get());
                }

                model.addNewVertex(label, new Point(x, y));
                hasChanges = true;

            } catch(IllegalArgumentException ex){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error happened.");
                alert.setHeaderText(ex.getMessage());

                alert.showAndWait();
            }


        });

        Button newEdgeButton = new Button("New edge");
        newEdgeButton.setMaxWidth(Double.MAX_VALUE);
        newEdgeButton.setOnAction(e -> {
            try {

                String in = null;
                String out = null;

                List<String> choices = model.listOfLabels();
                ChoiceDialog<String> dialog = new ChoiceDialog<String>(choices.get(0), choices);
                dialog.setTitle("Add new edge");
                dialog.setHeaderText("Chooose Out Vertex");
                dialog.setContentText("Chooose Out Vertex");

                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    out = result.get();
                }

                dialog.setTitle("Add new edge");
                dialog.setHeaderText("Chooose in Vertex");
                dialog.setContentText("Chooose in Vertex");
                result = dialog.showAndWait();
                if (result.isPresent()) {
                    in = result.get();
                }
                System.out.println(in + " " + out);

                if(model.isWeighted()){
                    int weight = 0;
                    TextInputDialog dialogT = new TextInputDialog();
                    dialogT.setTitle("Add new edge");
                    dialogT.setContentText("Edge weight");
                    result = dialogT.showAndWait();
                    if (result.isPresent()){
                        weight = Integer.parseInt(result.get());
                    }

                    model.addNewEdge(out, in, weight);

                }else{

                    model.addNewEdge(out, in);
                }


                hasChanges = true;

            } catch (IllegalArgumentException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error happened.");
                alert.setHeaderText(ex.getMessage());

                alert.showAndWait();
            }
        });

        Button loadFromFile = new Button("Load");
        loadFromFile.setMaxWidth(Double.MAX_VALUE);
        loadFromFile.setOnAction(e -> {
            if (!hasChanges) {
                loadMethod(window, gc);
            } else {
                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(window);
                VBox dialogVbox = new VBox(20);
                HBox dialogHbox = new HBox(20);

                dialogVbox.getChildren().add(new Text("Wanna save the unsaved changes?\n"));

                Button yesButton = new Button("Yes");
                yesButton.setOnAction(yes -> {
                    dialog.close();
                    saveMethod(window);
                });

                Button noButton = new Button("No");
                noButton.setOnAction(no -> {
                    dialog.close();
                    loadMethod(window, gc);
                });

                Button cancelButton = new Button("Cancel");
                cancelButton.setOnAction(f -> dialog.close());

                dialogHbox.getChildren().add(yesButton);
                dialogHbox.getChildren().add(noButton);
                dialogHbox.getChildren().add(cancelButton);

                dialogVbox.getChildren().add(dialogHbox);

                Scene dialogScene = new Scene(dialogVbox, 300, 100);
                dialog.setScene(dialogScene);
                dialog.show();
            }
        });

        Button saveToFile = new Button("Save");
        saveToFile.setMaxWidth(Double.MAX_VALUE);
        saveToFile.setOnAction(e -> {
            saveMethod(window);
        });
        //saveToFile.setOnAction(clickedOnSaveButton);

        Button newGraph = new Button("New Graph");
        newGraph.setMaxWidth(Double.MAX_VALUE);
        newGraph.setOnAction(e -> {

            if (!hasChanges) {
                newGraphMethod(window,graphChangedHandler, gc);
            } else {

                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(window);
                VBox dialogVbox = new VBox(20);
                HBox dialogHbox = new HBox(20);

                dialogVbox.getChildren().add(new Text("Wanna save the unsaved changes?\n"));

                Button yesButton = new Button("Yes");
                yesButton.setOnAction(yes -> {
                    dialog.close();
                    saveMethod(window);
                });

                Button noButton = new Button("No");
                noButton.setOnAction(no -> newGraphMethod(window, graphChangedHandler, gc));

                Button cancelButton = new Button("Cancel");
                cancelButton.setOnAction(f -> dialog.close());

                dialogHbox.getChildren().add(yesButton);
                dialogHbox.getChildren().add(noButton);
                dialogHbox.getChildren().add(cancelButton);

                dialogVbox.getChildren().add(dialogHbox);

                Scene dialogScene = new Scene(dialogVbox, 300, 100);
                dialog.setScene(dialogScene);
                dialog.show();

            }
        });

        Button back = new Button("Back");
        back.setMaxWidth(Double.MAX_VALUE);
        back.setOnAction(e -> {
            window.setScene(initHomePage(window));
        });

        root.getChildren().add(canvas);

        VBox menu = new VBox();

        menu.getChildren().add(newGraph);
        menu.getChildren().add(newVertexButton);
        menu.getChildren().add(newEdgeButton);
        menu.getChildren().add(loadFromFile);
        menu.getChildren().add(saveToFile);
        menu.getChildren().add(back);

        root.getChildren().add(menu);


        Scene scene = new Scene(root);

        window.setTitle("Graph Educator");
        window.setScene(scene);
        window.setMaximized(true);
        //window.setFullScreen(true);
        window.show();

        return scene;

    }

    private Scene initLearn(Stage stage){
        window = stage;
        window.setTitle("Graph Educator - Learn");

        HBox root = new HBox();

        Canvas canvas = new Canvas(visualBounds.getWidth()/2,visualBounds.getHeight());
        VBox rightSide = new VBox();

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Observer graphChangedHandler = new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println("Graph changed...");

                if (!(arg instanceof List)){
                    return;
                }

                gc.clearRect(0, 0, 300, 300);

                for (Object d : (List)arg){
                    if(d instanceof Drawable) {
                        ((Drawable)d).Draw(gc);
                    }
                }
            }
        };

        HBox buttons = new HBox();
        //JTable myTableView = model.getTable().toTableView();


        Button back = new Button("Back");
        back.setMaxHeight(Double.MAX_VALUE);
        back.setOnAction(e -> {
            model.stepBackward();
        });
        Button next = new Button("Next");
        next.setMaxHeight(Double.MAX_VALUE);
        next.setOnAction(e -> {
            model.stepForward();
        });
        Button home = new Button("Home");
        home.setMaxHeight(Double.MAX_VALUE);
        home.setOnAction(e -> {
            window.setScene(initHomePage(window));
        });

        ImageView stuctogram = new ImageView();

        loadAlgorithmMethod(stage, graphChangedHandler, gc, stuctogram);



        buttons.getChildren().addAll(back, next, home);
        rightSide.getChildren().addAll(buttons, stuctogram);
        root.getChildren().addAll(canvas, rightSide);

        Scene scene = new Scene(root);

        window.setScene(scene);
        window.show();

        return scene;
    }


    private Scene initSupervise(Stage stage){

        window = stage;
        window.setTitle("Graph Educator - Supervise");
        window.setMaximized(true);
        //window.setFullScreen(true);

        ;

        HBox root = new HBox();

        System.out.println("WIDTH: " + visualBounds.getWidth());
        System.out.println("HEIGHT: " + visualBounds.getHeight());
        Canvas canvas = new Canvas(visualBounds.getWidth()/2, visualBounds.getHeight());

        VBox rightSide = new VBox();
        VBox leftSide = new VBox();
        HBox leftDown = new HBox();

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Observer graphChangedHandler = new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println("Graph changed...");

                if (!(arg instanceof List)){
                    return;
                }

                gc.clearRect(0, 0, 300, 300);

                for (Object d : (List)arg){
                    if(d instanceof Drawable) {
                        ((Drawable)d).Draw(gc);
                    }
                }
            }
        };

        table.setEditable(true);
        table.setMinWidth(visualBounds.getWidth()/2);
        table.setMinHeight((visualBounds.getHeight()-70));

        TableColumn d = new TableColumn("d");
        /**/
        Callback<TableColumn<myPairs, String>, TableCell<myPairs, String>> cellFactory =
                new Callback<TableColumn<myPairs, String>, TableCell<myPairs, String>>() {
                    public TableCell call(TableColumn dd) {
                        TableCell cell = new TableCell<myPairs, String>() {
                            @Override
                            public void updateItem(String item, boolean empty) {
                                System.out.println("MEGHÍVÓDIK EZ A SZAR A CELLA ALATT");
                                super.updateItem(item, empty);
                                setText(empty ? null : getString());
                                System.out.println("GETSTRING: " + getString());
                                if(!"".equals(getString()) && !"-fx-background-color:#55FF33".equals(getStyle()) && !"-fx-background-color:#FF3333".equals(getStyle())) {
                                    setStyle(model.isItCorrect(getString()) ? "-fx-background-color:#55FF33" : "-fx-background-color:#FF3333");
                                    System.out.println("CALLBACK SZERINT ISCORRECT: " + model.isItCorrect(getString()));
                                    model.updateIfTrue(getString());
                                }

                            }

                            private String getString() {
                                return getItem() == null ? "" : getItem().toString();
                            }
                        };


                        return cell;
                    }
                };
        /**/
        d.setMinWidth(visualBounds.getWidth()/4);
        d.setCellValueFactory(new PropertyValueFactory<myPairs, String>("d"));
        d.setCellFactory(TextFieldTableCell.forTableColumn());
        //d.setCellFactory(cellFactory);
        d.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<myPairs, String>>(){
                    @Override
                    public void handle(TableColumn.CellEditEvent<myPairs, String> event) {
                        ((myPairs) event.getTableView().getItems().get(
                                event.getTablePosition().getRow())
                        ).setD(event.getNewValue());
                    }
                }
        );

        TableColumn parent = new TableColumn("parent");
        parent.setMinWidth(visualBounds.getWidth()/4);
        parent.setCellValueFactory(new PropertyValueFactory<myPairs, String>("parent"));
        parent.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<myPairs, String>>(){
                    @Override
                    public void handle(TableColumn.CellEditEvent<myPairs, String> event) {
                        ((myPairs) event.getTableView().getItems().get(
                                event.getTablePosition().getRow())
                        ).setParent(event.getNewValue());
                    }
                }
        );

        table.setItems(data);
       table.getColumns().addAll(d, parent);;

        HBox rightDownSide = new HBox();
        rightDownSide.setMinWidth(visualBounds.getWidth()/2);
        rightDownSide.setSpacing(5);
        rightDownSide.setPadding(new Insets(10, 0, 0, 10));

        TextArea writeTheGuessD = new TextArea();
        writeTheGuessD.setMaxWidth(visualBounds.getWidth()/6);
        writeTheGuessD.setMaxHeight(10);
        TextArea writeTheGuessParent = new TextArea();
        writeTheGuessParent.setMaxWidth(visualBounds.getWidth()/6);
        writeTheGuessParent.setMaxHeight(10);

        Button sendTheGuess = new Button("Ok");
        sendTheGuess.setMinWidth(visualBounds.getWidth()/12);
        sendTheGuess.setOnAction(e -> {
            data.add(new myPairs(
                    writeTheGuessD.getText(),
                    writeTheGuessParent.getText()
            ));
            model.isItCorrect(writeTheGuessD.getText());
            System.out.println("JÓ AMIT BEÍRTAM" + model.isItCorrect(writeTheGuessD.getText()));
            d.setCellFactory(cellFactory);
            //model.updateIfTrue(writeTheGuessD.getText());
            writeTheGuessD.clear();
            writeTheGuessParent.clear();

            /** /
            Callback<TableColumn<myPairs, String>, TableCell<myPairs, String>> cellFactory1 =
                    new Callback<TableColumn<myPairs, String>, TableCell<myPairs, String>>() {
                        public TableCell call(TableColumn d) {
                            TableCell cell = new TableCell<myPairs, String>() {
                                @Override
                                public void updateItem(String item, boolean empty) {
                                    System.out.println("MEGHÍVÓDIK EZ A CAR A GOMBBAL");
                                    super.updateItem(item, empty);
                                    setText(empty ? null : getString());
                                    setStyle(model.isItCorrect(getString()) ? "-fx-background-color:#FF3333" : "-fx-background-color:#55FF33");
                                }

                                private String getString() {
                                    return getItem() == null ? "" : getItem().toString();
                                }
                            };


                            return cell;
                        }
                    };
            /**/
        });

        Button back = new Button("Back");
        back.setMinWidth(visualBounds.getWidth()/12);
        back.setOnAction(e -> {
            window.setScene(initHomePage(window));
        });

        ImageView stuctogram = new ImageView();

        loadAlgorithmMethod(stage, graphChangedHandler, gc, stuctogram);


        rightDownSide.getChildren().addAll(writeTheGuessD, writeTheGuessParent, sendTheGuess, back);
        rightSide.getChildren().addAll(table, rightDownSide);
        root.getChildren().addAll(canvas, rightSide);

        Scene scene = new Scene(root);

        window.setScene(scene);
        window.show();

        return scene;

    }

    public static class myPairs{
        private final SimpleStringProperty d;
        private final SimpleStringProperty parent;

        private myPairs(String d, String parent){
            this.d = new SimpleStringProperty(d);
            this.parent = new SimpleStringProperty(parent);
        }

        public String getD(){
            return d.get();
        }

        public String getParent(){
            return parent.get();
        }

        public void setD(String d){
            this.d.set(d);
        }

        public void setParent(String parent){
            this.parent.set(parent);
        }
    }

    public static class myVertexTableData{
        private final SimpleStringProperty columnLabel;
        private final SimpleStringProperty posX;
        private final SimpleStringProperty posY;

        private myVertexTableData(String columnLabel, String posX, String posY){
            this.columnLabel = new SimpleStringProperty(columnLabel);
            this.posX = new SimpleStringProperty(posX);
            this.posY = new SimpleStringProperty(posY);
        }

        public String getColumnLabel(){
            return columnLabel.get();
        }

        public String getPosX(){
            return posX.get();
        }

        public String getPosY(){
            return posY.get();
        }

        public void setColumnLabel(String columnLabel){
            this.columnLabel.set(columnLabel);
        }

        public void setPosX(String posX){
            this.posX.set(posX);
        }

        public void setPosY(String posY){
            this.posY.set(posY);
        }
    }

    private void saveMethod(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML", "*.XML"),
                //new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                //new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showSaveDialog(stage);
        if (selectedFile != null) {
            try {
                model.saveToFile(selectedFile.getPath());
            }catch (FileNotFoundException ex){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error happened.");
                alert.setHeaderText(ex.getMessage());

                alert.showAndWait();
            }
            hasChanges = false;
        }
    }

    private void newGraphMethod(Stage stage, Observer graphChangedHandler, GraphicsContext gc){
        /** /
         TextInputDialog dialog = new TextInputDialog();
         dialog.setTitle("Text Input Dialog");
         dialog.setHeaderText("Look, a Text Input Dialog");
         dialog.setContentText("Please enter your name:");
         /**/
            /**/


        gc.clearRect(0, 0, visualBounds.getWidth()/2, visualBounds.getHeight());
            final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
        dialog.setOnCloseRequest( f -> {
            model = new Model(true, true, true);
            //model.graphChangedEvent.addObserver(graphChangedHandler);
            model.addObserver(graphChangedHandler);
            hasChanges = false;
        });
        VBox dialogVbox = new VBox(20);

        dialogVbox.getChildren().add(new Text("Set the graph's parameters."));

        CheckBox directionCB = new CheckBox("Direction");
        CheckBox weightCB = new CheckBox("Weight");
        CheckBox listedCB = new CheckBox("List");
        dialogVbox.getChildren().addAll(directionCB, weightCB, listedCB);

        Button okButton = new Button("Ok");
        okButton.setOnAction(f -> {
            boolean isDirected, isWeighted, isListed;
            isDirected = directionCB.isSelected();
            isListed = listedCB.isSelected();
            isWeighted = weightCB.isSelected();
            model = new Model(isDirected, isWeighted, isListed);
            //model.graphChangedEvent.addObserver(graphChangedHandler);
            model.addObserver(graphChangedHandler);
            hasChanges = false;
            dialog.close();

        });
        dialogVbox.getChildren().add(okButton);

        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();

        /** /
         ToggleGroup group = new ToggleGroup();
         RadioButton directed = new RadioButton("Directed");
         directed.setToggleGroup(group);
         directed.setSelected(true);
         RadioButton undirected = new RadioButton("Undirected");
         undirected.setToggleGroup(group);
         /**/
        //Optional<String> result = dialog.showAndWait();

        //model = new Model(true);
        //model.graphChangedEvent.addObserver(graphChangedHandler);
    }

    private void loadAlgorithmMethod(Stage stage, Observer graphChangedHandler, GraphicsContext gc, ImageView iv){

        gc.clearRect(0, 0, visualBounds.getWidth()/2, visualBounds.getHeight());
        final Stage dialog = new Stage();
        dialog.setTitle("Chose Algorithm");
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);

        ObservableList<String> algorihtms =
                FXCollections.observableArrayList(
                        "Bellman Ford",
                        "Breadth First Search",
                        "Depth First Search",
                        "Dijkstra",
                        "Kruskal",
                        "Prim"
                );
        final ComboBox comboBox = new ComboBox(algorihtms);

        ObservableList<String> exampleGraphs =
                FXCollections.observableArrayList(
                        "1",
                        "2",
                        "3"
                );
        final ComboBox comboBox2 = new ComboBox(exampleGraphs);

        final ToggleGroup group = new ToggleGroup();

        RadioButton basicGraphs = new RadioButton("");
        basicGraphs.setToggleGroup(group);
        basicGraphs.setSelected(true);

        RadioButton customGraph = new RadioButton("Custom Graph");
        customGraph.setToggleGroup(group);

        HBox basicGraphLayout = new HBox();
        basicGraphLayout.getChildren().addAll(basicGraphs, comboBox2);

        Button ok = new Button("ok");
        Model m = new Model(true, true, true);
        model = m;
        model.addObserver(graphChangedHandler);
        ok.setOnAction(e -> {
            if(customGraph.isSelected()){
                dialog.close();
                loadMethod(stage, gc);
            }else{
                System.out.println("Pathot kell írni a tesztgráf fájlokhoz, sé algoritmustól függően betölteni");
            }

            String algorithm = (String)comboBox.getValue();
            try{

                switch (algorithm){
                    case "Bellman Ford":
                        model.loadAlgorithm(new BellmanFord(model.getGraph()), model.getGraph().getVertexByLabel(model.listOfLabels().get(0)));
                        break;
                    case "Breadth First Search":
                        iv.setImage(new Image("res/BreadthFirstSearch.JPG"));
                        model.loadAlgorithm(new BreadthFirstSearch(model.getGraph()), model.getGraph().getVertexByLabel(model.listOfLabels().get(0)));
                        break;
                    case "DepthFirstSearch":
                        model.loadAlgorithm(new DepthFirstSearch(model.getGraph()), model.getGraph().getVertexByLabel(model.listOfLabels().get(0)));
                        break;
                    case "Dijkstra":
                        System.out.println("NULLT KULDOK: " + (model.getGraph() == null));
                        model.loadAlgorithm(new Dijkstra(model.getGraph()), model.getGraph().getVertexByLabel(model.listOfLabels().get(0)));
                        break;
                    case "Kruskal":
                        model.loadAlgorithm(new Kruskal(model.getGraph()), model.getGraph().getVertexByLabel(model.listOfLabels().get(0)));
                        break;
                    case "Prim":
                        model.loadAlgorithm(new Prim(model.getGraph()), model.getGraph().getVertexByLabel(model.listOfLabels().get(0)));
                        break;
                }

            }catch (IllegalArgumentException ex){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error happened.");
                alert.setHeaderText(ex.getMessage());

                alert.showAndWait();
                dialog.show();
            }

           // try{
                //model.loadAlgorithm();
            //}
        });

        VBox layout = new VBox();
        layout.getChildren().addAll(comboBox, basicGraphLayout, customGraph, ok);

        Scene dialogScene = new Scene(layout, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();


    }

    private void loadMethod(Stage stage, GraphicsContext gc){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML", "*.xml"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            try{
                gc.clearRect(0, 0, visualBounds.getWidth()/2, visualBounds.getHeight());
                model.loadFromFile(selectedFile.getPath());
                hasChanges = false;
            }catch(Exception ex){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error happened.");
                alert.setHeaderText(ex.getMessage());

                alert.showAndWait();
            }
        }
    }

}
