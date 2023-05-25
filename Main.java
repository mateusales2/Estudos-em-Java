package application;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.*;
import java.util.List;

public class Main extends Application {

    private TableView<Person> tableView;
    private ObservableList<Person> data;
    private Connection connection;
    private TextField nomeField;
    private TextField emailField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Configuração da conexão com o banco de dados MySQL
        String url = "jdbc:mysql://localhost:3306/exemplo";
        String username = "root";
        String password = "lolpp123";

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Configuração da tabela e dos dados
        tableView = new TableView<>();
        data = FXCollections.observableArrayList();

        // Configuração das colunas da tabela
        TableColumn<Person, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());

        TableColumn<Person, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());

        TableColumn<Person, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

        tableView.getColumns().addAll(idColumn, nomeColumn, emailColumn);

        // Configuração dos controles de entrada
        nomeField = new TextField();
        nomeField.setPromptText("Nome");

        emailField = new TextField();
        emailField.setPromptText("Email");

        // Configuração dos botões
        Button addButton = new Button("Adicionar");
        addButton.setOnAction(e -> adicionarPessoa());

        Button deleteButton = new Button("Excluir");
        deleteButton.setOnAction(e -> excluirPessoa());

        // Configuração do layout
        HBox inputBox = new HBox(nomeField, emailField, addButton, deleteButton);
        inputBox.setSpacing(10);

        VBox root = new VBox(tableView, inputBox);
        root.setSpacing(10);
        root.setPadding(new Insets(10));

        // Configuração da cena
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Aplicativo JavaFX com MySQL");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Carrega os dados da tabela
        carregarDados();
    }

    @Override
    public void stop() {
        // Fecha a conexão com o banco de dados ao encerrar o aplicativo
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void carregarDados() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM usuarios");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");
                data.add(new Person(id, nome, email));
            }

            tableView.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void adicionarPessoa() {
        String nome = nomeField.getText();
        String email = emailField.getText();

        if (!nome.isEmpty() && !email.isEmpty()) {
            try {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO usuarios (nome, email) VALUES (?, ?)");
                statement.setString(1, nome);
                statement.setString(2, email);
                statement.executeUpdate();

                int id = getLastInsertId();
                data.add(new Person(id, nome, email));
                limparCampos();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void excluirPessoa() {
        Person selectedPerson = tableView.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            try {
                int id = selectedPerson.getId();
                PreparedStatement statement = connection.prepareStatement("DELETE FROM usuarios WHERE id = ?");
                statement.setInt(1, id);
                statement.executeUpdate();

                data.remove(selectedPerson);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private int getLastInsertId() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT LAST_INSERT_ID()");

        if (resultSet.next()) {
            return resultSet.getInt(1);
        }

        throw new SQLException("Falha ao obter o último ID inserido.");
    }

    private void limparCampos() {
        nomeField.clear();
        emailField.clear();
    }
}

class Person {

    private final IntegerProperty id;
    private final StringProperty nome;
    private final StringProperty email;

    public Person(int id, String nome, String email) {
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.email = new SimpleStringProperty(email);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getNome() {
        return nome.get();
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }
}
