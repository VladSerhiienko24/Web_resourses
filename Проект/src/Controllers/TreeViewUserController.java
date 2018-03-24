package Controllers;

import Source.DataManager;
import Source.Folder;
import Source.Reference;
import com.sun.javafx.scene.control.skin.LabeledText;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;
/**
 * Класс контроллер для главного окна в режиме пользователя
 */
public class TreeViewUserController {

    @FXML
    private TreeView<Folder> Tree;
    @FXML
    private AnchorPane RightPane;
    @FXML
    private Button GoHome;

    private DataManager dt = new DataManager();
    public static ObservableList<Folder> folderList = FXCollections.observableArrayList();

    /**
     * Метод инициализации всех данных при старте программы
     * @throws IOException Исключение ошибки существования файла
     */
    @FXML
    private void initialize() throws IOException{
        Folder fold = new Folder("Учебные папки", "folder.txt");

        folderList = dt.readFolder(folderList);

        TreeItem root = new TreeItem(fold);
        if (!folderList.isEmpty()) {
            root.setExpanded(true);
        }
        Tree.setRoot(root);
        Tree.setShowRoot(true);
        Tree.setEditable(true);


        for (Folder f : folderList) {
            root.getChildren().add(new TreeItem<Folder>(f));
        }

        Tree.setOnMouseClicked((e) -> {
            Node node = e.getPickResult().getIntersectedNode();
            if (node instanceof LabeledText) {
                try {
                    openFolders();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        RightPane.setVisible(false);
    }

    /**
     * Метод открытия нужной папки при нажатии на определенный узел
     * @throws IOException Исключение ошибки существования файла
     */
    private void openFolders() throws IOException {
        TreeItem<Folder> selectionFolder = Tree.getSelectionModel().getSelectedItem();
        Folder EditFolder = new Folder(selectionFolder.getValue().getName(), selectionFolder.getValue().getNameTxt());

        if(selectionFolder.getValue().getName() == Tree.getRoot().getValue().getName()
                && selectionFolder.getValue().getNameTxt() == Tree.getRoot().getValue().getNameTxt()){
            referencesBook.getItems().clear();
            RightPane.setVisible(false);
        }
        else {
            RightPane.setVisible(true);
            referencesList.removeAll();
            referencesBook.getItems().clear();
            colName.setCellValueFactory(new PropertyValueFactory<Reference, String>("name"));
            colRef.setCellValueFactory(new PropertyValueFactory<Reference, String>("reference"));
            referencesList = dt.read(referencesList, EditFolder.getNameTxt());
            referencesBook.setItems(getReferenceList(referencesList));
        }

    }

    // =================================================================
    // Правая часть окна
    // Отображение таблицы

    private WebView myWeb;
    private WebEngine eng;
    private VBox VB;
    private Scene nsWeb;
    private Stage stWeb;

    public static ObservableList<Reference> referencesList = FXCollections.observableArrayList();

    @FXML
    private TableView referencesBook;
    @FXML
    private TableColumn<Reference, String> colName;
    @FXML
    private TableColumn<Reference, String> colRef;

    /**
     * Метод открытия ссылки в окне браузера
     * @param actionEvent Нажатие на кнопку
     */
    public void actionClick(ActionEvent actionEvent){
        Reference selectedReference = (Reference) referencesBook.getSelectionModel().getSelectedItem();
        if(selectedReference == null) {
            MessageBox.Show(Alert.AlertType.INFORMATION, "Ошибка", "Ссылка не выбрана!",
                    "Выберите ссылку, которую хотите открыть.");
        }
        else {
            showWeb(selectedReference.getReference());
        }
    }

    /**
     * Метод открытия ссылки в новом окне встроенного браузера
     * @param site Ссылка
     */
    private void showWeb(String site) {
        if (myWeb == null) {
            myWeb = new WebView();
            eng = myWeb.getEngine();
            VB = new VBox();
            VB.getChildren().addAll(myWeb);
            nsWeb = new Scene(VB, 1280, 600);
            stWeb = new Stage();
            stWeb.setScene(nsWeb);
            stWeb.setResizable(false);
        }
        eng.load(site);
        stWeb.show();
    }

    /**
     * Метод возвращения списка ссылок
     * @param referencesList Список ссылок
     * @return Список ссылок
     */
    public ObservableList<Reference> getReferenceList(ObservableList<Reference> referencesList) {
        return referencesList;
    }

    public void goHome(ActionEvent actionEvent) throws IOException {
        Optional<ButtonType> result = MessageBox.Show(Alert.AlertType.CONFIRMATION, "Внимание",
                "Вы действительно хотите вернуться на главную страницу?","");
        if (result.get() == ButtonType.OK) {
            Stage stage = (Stage) GoHome.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/Authorization.fxml"));
            stage.setTitle("Авторизация");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            folderList.clear();
            referencesList.clear();
            stage.show();
        }
    }
}
