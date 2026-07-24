package org.andresaquino.view;

import javafx.application.Application;
import javafx.geometry.Insets;
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
 * Vista JavaFX para la gestión y cálculo de superficies.
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

        // --- PESTAÑA 1: REGISTRO ---
        VBox rootRegistro = new VBox(15);
        rootRegistro.setPadding(new Insets(20));

        Label lblSeleccion = new Label("Seleccione el tipo de superficie:");
        ComboBox<String> comboFormas = new ComboBox<>();
        comboFormas.getItems().addAll("Circulo", "Rectangulo", "Triangulo");

        GridPane gridFormulario = new GridPane();
        gridFormulario.setHgap(10);
        gridFormulario.setVgap(10);

        TextField txtRadio = new TextField();
        TextField txtBase = new TextField();
        TextField txtAltura = new TextField();

        comboFormas.setOnAction(e -> {
            gridFormulario.getChildren().clear();
            String seleccion = comboFormas.getValue();

            if ("Circulo".equals(seleccion)) {
                gridFormulario.add(new Label("Radio (m):"), 0, 0);
                gridFormulario.add(txtRadio, 1, 0);
            } else if ("Rectangulo".equals(seleccion) || "Triangulo".equals(seleccion)) {
                gridFormulario.add(new Label("Base (m):"), 0, 0);
                gridFormulario.add(txtBase, 1, 0);
                gridFormulario.add(new Label("Altura (m):"), 0, 1);
                gridFormulario.add(txtAltura, 1, 1);
            }
        });

        Button btnAgregar = new Button("Añadir a la Lista");

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

        rootRegistro.getChildren().addAll(lblSeleccion, comboFormas, gridFormulario, btnAgregar);
        tabRegistro.setContent(rootRegistro);

        // --- PESTAÑA 2: RESUMEN ---
        VBox rootResumen = new VBox(15);
        rootResumen.setPadding(new Insets(20));

        Button btnCalcular = new Button("Calcular Total");
        btnCalcular.setStyle("-fx-font-weight: bold;");

        btnCalcular.setOnAction(e -> {
            double areaTotal = 0;
            // AQUÍ ESTÁ EL CAMBIO: Superficie en lugar de SuperficieView
            for (Superficie s : listaSuperficies) {
                areaTotal += s.calcularArea();
            }

            double costoTotal = areaTotal * PRECIO_METRO_CUADRADO;
            lblTotalCosto.setText(String.format("Total Consulta: Q.%.2f", costoTotal));
        });

        rootResumen.getChildren().addAll(
            new Label("Elementos registrados:"), 
            listViewResumen, 
            btnCalcular, 
            lblTotalCosto
        );
        tabResumen.setContent(rootResumen);

        tabPane.getTabs().addAll(tabRegistro, tabResumen);
        Scene scene = new Scene(tabPane, 450, 600);
        
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