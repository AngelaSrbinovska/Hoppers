package puzzles.hoppers.gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import puzzles.common.Observer;
import puzzles.hoppers.model.HoppersConfig;
import puzzles.hoppers.model.HoppersModel;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;

import static javafx.geometry.Pos.*;

/**
 * The class for the HoppersGUI puzzle.
 *
 * @author Angela Srbinovska (as2179@rit.edu)
 */
public class HoppersGUI extends Application implements Observer<HoppersModel, String> {
    /**
     * The resources' directory is located directly underneath the gui package
     */
    private final static String RESOURCES_DIR = "resources/";
    /**
     * the height of an image
     */
    private final static int FIT_HEIGHT = 50;
    /**
     * the width of an image
     */
    private final static int FIT_WIDTH = 50;
    /**
     * the HoppersModel
     */
    private HoppersModel model;
    /**
     * the border pane
     */
    private BorderPane borderPane;
    /**
     * the top label, where messages are displayed
     */
    private Label label = new Label();
    /**
     * the current file to load
     */
    private String currentFile;
    /**
     * the native array of buttons that makes the moves of the GUI.
     */
    private Button[][] buttons;
    /**
     * the grid pane
     */
    private GridPane gridPane;
    /**
     * the image for the red frog
     */
    private final Image redFrog = new Image(getClass().getResourceAsStream(RESOURCES_DIR + "red_frog.png"));
    /**
     * the image for the green frog
     */
    private final Image greenFrog = new Image(getClass().getResourceAsStream(RESOURCES_DIR + "green_frog.png"));
    /**
     * the image for the lily pad
     */
    private final Image lilyPad = new Image(getClass().getResourceAsStream(RESOURCES_DIR + "lily_pad.png"));
    /**
     * the image for the water where the lily pads are on, on which the frogs are
     */
    private final Image water = new Image(getClass().getResourceAsStream(RESOURCES_DIR + "water.png"));

    /**
     * Method that initializes the model, and sets the current filename to the
     * filename.
     */
    public void init() {
        String filename = getParameters().getRaw().get(0);
        this.currentFile = filename;
        this.model = new HoppersModel(filename);
        this.model.addObserver(this);
        buttons = new Button[model.getCurrentConfig().getRows()][model.getCurrentConfig().getCols()];
    }

    /**
     * Method that creates a border pane and sets the center, bottom, and top.
     *
     * @param stage the stage
     * @return the border pane
     */
    private BorderPane borderPane(Stage stage) {
        this.borderPane = new BorderPane();

        // CENTER - play the game
        this.borderPane.setCenter(this.centerGridPane());

        // BOTTOM - 3 buttons (LOAD, RESET, HINT)
        this.borderPane.setBottom(this.hBox(stage));

        // TOP - display messages
        this.borderPane.setTop(this.topLabel(currentFile));

        return this.borderPane;
    }

    /**
     * Method that creates a grid pane, and changes the images accordingly,
     * whether it is red frog, a green frog, a lilypad or water.
     *
     * @return the grid pane
     */
    private GridPane centerGridPane() {
        GridPane gridPane = new GridPane();
        for (int i = 0; i < model.getCurrentConfig().getRows(); i++) {
            for (int j = 0; j < model.getCurrentConfig().getCols(); j++) {
                Button button = new Button();
                char c = model.getCurrentConfig().getBoard()[i][j];
                if (c == HoppersConfig.LILYPAD) {
                    ImageView imageView = new ImageView(lilyPad);
                    imageView.setFitHeight(FIT_HEIGHT);
                    imageView.setFitWidth(FIT_WIDTH);
                    button.setGraphic(imageView);
                }
                if (c == HoppersConfig.INVALID) {
                    ImageView imageView = new ImageView(water);
                    imageView.setFitHeight(FIT_HEIGHT);
                    imageView.setFitWidth(FIT_WIDTH);
                    button.setGraphic(imageView);
                }
                if (c == HoppersConfig.RED) {
                    ImageView imageView = new ImageView(redFrog);
                    imageView.setFitHeight(FIT_HEIGHT);
                    imageView.setFitWidth(FIT_WIDTH);
                    button.setGraphic(imageView);
                }
                if (c == HoppersConfig.GREEN) {
                    ImageView imageView = new ImageView(greenFrog);
                    imageView.setFitHeight(FIT_HEIGHT);
                    imageView.setFitWidth(FIT_WIDTH);
                    button.setGraphic(imageView);
                }
                button.setBackground(new Background(new BackgroundFill(Color.web("#1291e3"), null, null)));
                int finalJ = j;
                int finalI = i;
                button.setOnAction(actionEvent -> model.select(finalI, finalJ));
                button.setMaxWidth(Double.MAX_VALUE);
                button.setMaxHeight(Double.MAX_VALUE);
                gridPane.add(button, j, i);
            }
        }

        gridPane.setStyle("-fx-font: 18px Menlo");
        gridPane.setAlignment(TOP_CENTER);
        return gridPane;
    }

    /**
     * Method that sets the text of the label to the loaded filename.
     *
     * @param filename the filename
     * @return the label
     */
    private Label topLabel(String filename) {
        this.label.setText("Loaded: " + filename);
        this.label.setAlignment(CENTER);
        return label;
    }

    /**
     * Method that creates hBox with 3 buttons (LOAD, RESET, HINT), and calls set
     * on action on each button accordingly.
     *
     * @param stage the stage
     * @return the hBox
     */
    private HBox hBox(Stage stage) {
        HBox hBox = new HBox();

        Button load = new Button("LOAD");
        load.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                model.load(selectedFile.toPath().toString());
            }
        });

        Button reset = new Button("RESET");
        reset.setOnAction(actionEvent -> model.reset());

        Button hint = new Button("HINT");
        hint.setOnAction(actionEvent -> {
            if (this.model.getCurrentConfig().isSolution()) {
                update(model, "Already solved!");
            } else if (model.noSolution(this.model.getCurrentConfig())) {
                update(model, "No solution!");
            } else {
                this.model.hint();
                update(model, "Next step!");
            }
        });

        hBox.setAlignment(BOTTOM_CENTER);
        hBox.getChildren().addAll(load, reset, hint);
        hBox.setStyle("-fx-font: 18px Menlo");
        return hBox;
    }

    /**
     * Method that sets the scene, and shows the stage.
     *
     * @param stage the stage
     * @throws Exception throws any Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(this.borderPane(stage));
        stage.setScene(scene);
        stage.setTitle("Hoppers GUI");
        stage.show();
    }

    /**
     * Method that updates the view.
     *
     * @param hoppersModel the HoppersModel
     * @param msg          the message displayed
     */
    @Override
    public void update(HoppersModel hoppersModel, String msg) {
        this.label.setText(msg);
        gridPane = centerGridPane();
        borderPane.setCenter(gridPane);
        for (int r = 0; r < model.getCurrentConfig().getRows(); r++) {
            for (int c = 0; c < model.getCurrentConfig().getCols(); c++) {
                Button buttons = new Button();
                char buttonOnBoard = model.getCurrentConfig().getBoard()[r][c];
                if (buttonOnBoard == HoppersConfig.LILYPAD) {
                    buttons.setGraphic(new ImageView(lilyPad));
                }
                if (buttonOnBoard == HoppersConfig.RED) {
                    buttons.setGraphic(new ImageView(redFrog));
                }
                if (buttonOnBoard == HoppersConfig.GREEN) {
                    buttons.setGraphic(new ImageView(greenFrog));
                }
            }

        }
    }

    /**
     * The main method launches the GUI.
     *
     * @param args the filename
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java HoppersPTUI filename");
        } else {
            Application.launch(args);
        }
    }
}
