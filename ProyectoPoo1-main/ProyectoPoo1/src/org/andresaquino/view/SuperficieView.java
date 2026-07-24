package org.andresaquino.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.andresaquino.model.Superficie;
import org.andresaquino.model.Circular;
import org.andresaquino.model.Rectangular;
import org.andresaquino.model.Triangular;
import java.util.ArrayList;

/**
 * Vista con diseño moderno para la gestión y cálculo de superficies.
 * @author Andres Aquino
 */
public class SuperficieView extends Application {

    private ArrayList<Superficie> listaSuperficies = new ArrayList<>();

    private ListView<Superficie> listViewResumen = new ListView<>();
    private Label lblTotalCosto = new Label("Total Consulta: Q.0.00");

    private static final double PRECIO_METRO_CUADRADO = 30.00;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("La Constructora - Cálculo de Materiales");

        TabPane tabPane = new TabPane();
        Tab tabRegistro = new Tab("Registro");
        Tab tabResumen = new Tab("Resumen");
        
        tabRegistro.setClosable(false);
        tabResumen.setClosable(false);

        // Estilo general del TabPane y Fondo Ventana
        tabPane.setStyle(
            "-fx-background-color: #1e1e2e; " +
            "-fx-tab-min-width: 100px; " +
            "-fx-font-family: 'Segoe UI', Helvetica, Arial, sans-serif;"
        );

        // --- PESTAÑA 1: REGISTRO ---
        VBox rootRegistro = new VBox(15);
        rootRegistro.setPadding(new Insets(25));
        rootRegistro.setAlignment(Pos.TOP_LEFT);
        rootRegistro.setStyle("-fx-background-color: #1e1e2e;");

        Label lblTituloReg = new Label("Añadir Nueva Superficie");
        lblTituloReg.setStyle("-fx-text-fill: #cdd6f4; -fx-font-size: 18px; -fx-font-weight: bold;");

        Label lblSeleccion = new Label("Seleccione el tipo de superficie:");
        lblSeleccion.setStyle("-fx-text-fill: #a6adc8; -fx-font-size: 13px;");

        ComboBox<String> comboFormas = new ComboBox<>();
        comboFormas.getItems().addAll("Circulo", "Rectangulo", "Triangulo");
        comboFormas.setPromptText("Seleccionar...");
        comboFormas.setMaxWidth(Double.MAX_VALUE);
        comboFormas.setStyle(
            "-fx-background-color: #313244; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 13px; " +
            "-fx-background-radius: 8px; " +
            "-fx-padding: 5px;"
        );

        GridPane gridFormulario = new GridPane();
        gridFormulario.setHgap(12);
        gridFormulario.setVgap(12);

        // Estilos reutilizables para los TextFields
        String estiloInput = 
            "-fx-background-color: #313244; " +
            "-fx-text-fill: #cdd6f4; " +
            "-fx-prompt-text-fill: #6c7086; " +
            "-fx-background-radius: 6px; " +
            "-fx-padding: 8px; " +
            "-fx-font-size: 13px;";

        TextField txtRadio = new TextField();
        txtRadio.setStyle(estiloInput);
        
        TextField txtBase = new TextField();
        txtBase.setStyle(estiloInput);
        
        TextField txtAltura = new TextField();
        txtAltura.setStyle(estiloInput);

        comboFormas.setOnAction(e -> {
            gridFormulario.getChildren().clear();
            String seleccion = comboFormas.getValue();

            if ("Circulo".equals(seleccion)) {
                Label lblRadio = new Label("Radio (m):");
                lblRadio.setStyle("-fx-text-fill: #cdd6f4; -fx-font-weight: bold;");
                gridFormulario.add(lblRadio, 0, 0);
                gridFormulario.add(txtRadio, 1, 0);
            } else if ("Rectangulo".equals(seleccion) || "Triangulo".equals(seleccion)) {
                Label lblBase = new Label("Base (m):");
                lblBase.setStyle("-fx-text-fill: #cdd6f4; -fx-font-weight: bold;");
                Label lblAltura = new Label("Altura (m):");
                lblAltura.setStyle("-fx-text-fill: #cdd6f4; -fx-font-weight: bold;");

                gridFormulario.add(lblBase, 0, 0);
                gridFormulario.add(txtBase, 1, 0);
                gridFormulario.add(lblAltura, 0, 1);
                gridFormulario.add(txtAltura, 1, 1);
            }
        });

        Button btnAgregar = new Button("Añadir a la Lista");
        btnAgregar.setMaxWidth(Double.MAX_VALUE);
        btnAgregar.setStyle(
            "-fx-background-color: #89b4fa; " +
            "-fx-text-fill: #11111b; " +
            "-fx-font-weight: bold; " +
            "-fx-font-size: 14px; " +
            "-fx-background-radius: 8px; " +
            "-fx-padding: 10px; " +
            "-fx-cursor: hand;"
        );

        btnAgregar.setOnAction(e -> {
            String seleccion = comboFormas.getValue();

            if (seleccion == null) {
                mostrarAlerta("Error", "Por favor, seleccione una forma geométrica.");
                return;
            }

            try {
                if ("Circulo".equals(seleccion)) {
                    double radio = Double.parseDouble(txtRadio.getText());
                    listaSuperficies.add(new Circular(radio));
                } else if ("Rectangulo".equals(seleccion)) {
                    double base = Double.parseDouble(txtBase.getText());
                    double altura = Double.parseDouble(txtAltura.getText());
                    listaSuperficies.add(new Rectangular(base, altura));
                } else if ("Triangulo".equals(seleccion)) {
                    double base = Double.parseDouble(txtBase.getText());
                    double altura = Double.parseDouble(txtAltura.getText());
                    listaSuperficies.add(new Triangular(base, altura));
                }

                txtRadio.clear();
                txtBase.clear();
                txtAltura.clear();

                actualizarListView();
                mostrarAlerta("Éxito", "Superficie añadida correctamente.");

            } catch (NumberFormatException ex) {
                mostrarAlerta("Error de formato", "Por favor, ingrese valores numéricos válidos.");
            }
        });

        rootRegistro.getChildren().addAll(lblTituloReg, lblSeleccion, comboFormas, gridFormulario, btnAgregar);
        tabRegistro.setContent(rootRegistro);

        // --- PESTAÑA 2: RESUMEN ---
        VBox rootResumen = new VBox(15);
        rootResumen.setPadding(new Insets(25));
        rootResumen.setStyle("-fx-background-color: #1e1e2e;");

        Label lblTituloRes = new Label("Resumen y Presupuesto");
        lblTituloRes.setStyle("-fx-text-fill: #cdd6f4; -fx-font-size: 18px; -fx-font-weight: bold;");

        listViewResumen.setStyle(
            "-fx-background-color: #313244; " +
            "-fx-control-inner-background: #313244; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 8px; " +
            "-fx-padding: 5px;"
        );

        Button btnCalcular = new Button("Calcular Total");
        btnCalcular.setMaxWidth(Double.MAX_VALUE);
        btnCalcular.setStyle(
            "-fx-background-color: #a6e3a1; " +
            "-fx-text-fill: #11111b; " +
            "-fx-font-weight: bold; " +
            "-fx-font-size: 14px; " +
            "-fx-background-radius: 8px; " +
            "-fx-padding: 10px; " +
            "-fx-cursor: hand;"
        );

        lblTotalCosto.setStyle(
            "-fx-text-fill: #f9e2af; " +
            "-fx-font-size: 16px; " +
            "-fx-font-weight: bold; " +
            "-fx-alignment: center;"
        );

        btnCalcular.setOnAction(e -> {
            double areaTotal = 0;
            for (Superficie s : listaSuperficies) {
                areaTotal += s.calcularArea();
            }

            double costoTotal = areaTotal * PRECIO_METRO_CUADRADO;
            lblTotalCosto.setText(String.format("Total Consulta: Q.%.2f", costoTotal));
        });

        rootResumen.getChildren().addAll(
            lblTituloRes,
            listViewResumen, 
            btnCalcular, 
            lblTotalCosto
        );
        tabResumen.setContent(rootResumen);

        tabPane.getTabs().addAll(tabRegistro, tabResumen);
        Scene scene = new Scene(tabPane, 450, 500);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void actualizarListView() {
        listViewResumen.getItems().clear();
        listViewResumen.getItems().addAll(listaSuperficies);
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}