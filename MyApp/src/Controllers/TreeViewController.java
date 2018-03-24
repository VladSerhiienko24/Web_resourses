package Controllers;

import Source.DataManager;
import Source.Folder;
import Source.Reference;
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
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import com.sun.javafx.scene.control.skin.LabeledText;

import java.io.IOException;
import java.util.Optional;
/**
 * Класс контроллер для главного окна
 */
public class TreeViewController {

    public TextField addNameFolder;

    public TextField editNameFolder;

    @FXML
    private TreeView<Folder> Tree;
    @FXML
    private VBox RightVBox;
    @FXML
    private Button GoHome;

    private DataManager dt = new DataManager();
    public static ObservableList<Folder> folderList = FXCollections.observableArrayList();
    private Folder RootFolder = new Folder();
    /**
     * Метод инициализации всех данных при старте программы
     * @throws IOException Исключение ошибки существования файла
     */
    @FXML
    private void initialize() throws Exception{
        fxmlLoader.setLocation(getClass().getResource("/FXML/Edit.fxml"));
        fxmlEdit = fxmlLoader.load();
        edcontr = fxmlLoader.getController();

        Folder fold = new Folder("Учебные папки", "folder.txt");
        RootFolder.setName(fold.getName());
        RootFolder.setNameTxt(fold.getNameTxt());
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

        RightVBox.setVisible(false);
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
            RightVBox.setVisible(false);
        }
        else {
            RightVBox.setVisible(true);
            referencesList.removeAll();
            referencesBook.getItems().clear();
            colName.setCellValueFactory(new PropertyValueFactory<Reference, String>("name"));
            colRef.setCellValueFactory(new PropertyValueFactory<Reference, String>("reference"));
            referencesList = dt.read(referencesList, EditFolder.getNameTxt());
            referencesBook.setItems(getReferenceList(referencesList));
        }
        editNameFolder.setText(EditFolder.getName());
    }

    /**
     * Метод добавления нового узла в TreeView
     * @param actionEvent Нажатие на кнопку
     * @throws IOException Исключение ошибки существования файла
     */
    public void addFold(ActionEvent actionEvent)throws IOException {
        TreeItem<Folder> selectionFolder = Tree.getSelectionModel().getSelectedItem();
        if(selectionFolder == null){
            MessageBox.Show(Alert.AlertType.ERROR, "Ошибка", "Не выбран узел в который будет добавляться папка!",
                    "Выберите узел, в который хотите добавить.");
        }
        else{
            Folder newFolder = new Folder();
            newFolder.setName(addNameFolder.getText());
            newFolder.setNameTxt(addNameFolder.getText() + ".txt");

            if (addNameFolder.getText().isEmpty()) {
                MessageBox.Show(Alert.AlertType.ERROR, "Ошибка", "Имя папки не задано!",
                        "Введите название папки");
            }
            else if(checkFolderName(newFolder.getName())){
                MessageBox.Show(Alert.AlertType.ERROR, "Ошибка", "Такая папка уже существует!",
                        "Введите другое название папки");
            }
            else if(selectionFolder.getValue().getName() != RootFolder.getName() &&
                    selectionFolder.getValue().getNameTxt() != RootFolder.getNameTxt()){
                MessageBox.Show(Alert.AlertType.ERROR, "Ошибка", "Добавить папку можно только в корень!",
                        "");
            }
            else{
                selectionFolder.getChildren().add(new TreeItem<Folder>(newFolder));
                selectionFolder.setExpanded(true);
                folderList.add(newFolder);
                dt.writeFolder(folderList);
                dt.createFolderTxt(newFolder.getNameTxt());
            }
        }
        addNameFolder.clear();
    }

    /**
     * Проверка на существование папки с таким же именем
     * @param FolderName Название папки
     * @return Занято имя или не занято
     */
    private boolean checkFolderName(String FolderName){
        boolean check = false;
        for (Folder f: folderList) {
            if(f.getName().equals(FolderName)){
                check = true;
                break;
            }
        }
        return check;
    }

    /**
     * Метод удаления папки
     * @param actionEvent Нажатие на кнопку
     * @throws IOException Исключение ошибки существования файла
     */
    public void deleteFold(ActionEvent actionEvent) throws IOException {
        TreeItem<Folder> selectionFolder = Tree.getSelectionModel().getSelectedItem();
        if(selectionFolder == null){
            MessageBox.Show(Alert.AlertType.ERROR, "Ошибка", "Объект для удаления не выбран!",
                    "Выберите объект, который хотите удалить.");
        }
        else{
            Optional<ButtonType> result = MessageBox.Show(Alert.AlertType.CONFIRMATION, "Внимание", "Удаление объекта",
                    "Вы действительно хотите удалить?");
            if(result.get() == ButtonType.OK){
                Folder selectedFolder = (Folder) Tree.getSelectionModel().getSelectedItem().getValue();
                selectionFolder.getParent().getChildren().remove(selectionFolder);
                folderList.remove(selectedFolder);
                dt.writeFolder(folderList);
                dt.delete(selectedFolder.getNameTxt());
            }
        }
    }

    public void editFold(ActionEvent actionEvent) throws IOException{
        TreeItem<Folder> selectionFolder = Tree.getSelectionModel().getSelectedItem();
        if(selectionFolder == null){
            MessageBox.Show(Alert.AlertType.ERROR, "Ошибка", "Объект для изменения не выбран!",
                    "Выберите объект, который хотите изменить.");
        }
        else {
            String FileName = selectionFolder.getValue().getNameTxt();
            if(editNameFolder.getText().isEmpty()){
                MessageBox.Show(Alert.AlertType.ERROR, "Ошибка", "Имя папки не задано!",
                        "Введите название папки");
            }
            else if(checkFolderName(editNameFolder.getText())){
                MessageBox.Show(Alert.AlertType.ERROR, "Ошибка", "Такая папка уже существует!",
                        "Введите другое название папки");
            }
            else{
                selectionFolder.getValue().setName(editNameFolder.getText());
                selectionFolder.getValue().setNameTxt(editNameFolder.getText() + ".txt");
                String NewFileName = selectionFolder.getValue().getNameTxt();

                for (Folder f : folderList) {
                    if (selectionFolder.getValue().getName() == f.getName()) {
                        f.setName(selectionFolder.getValue().getName());
                        f.setNameTxt(selectionFolder.getValue().getNameTxt());
                    }
                }

                dt.writeFolder(folderList);
                dt.renameFile(FileName, NewFileName);
            }
        }
    }

    // =================================================================
    // Правая часть окна
    // Отображение таблицы

    private EditController edcontr;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private Parent fxmlEdit;
    private Stage editdialogstage;

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
     * Метод, который и добавляет новые объекты в таблицу,
     * и сохраняет изменения в таблице, и редактирует данные в таблице
     * и открывает ссылки в новом окне
     * @param actionEvent Нажатие на одну из кнопок
     * @throws Exception Исключение ошибки существования файла
     */
    public void actionClick(ActionEvent actionEvent) throws Exception {
        Object source = actionEvent.getSource();
        if (!(source instanceof Button)) {
            return;
        }
        Button clickedButton = (Button) source;
        Reference selectedReference = (Reference) referencesBook.getSelectionModel().getSelectedItem();
        Window parentwindow = ((Node) actionEvent.getSource()).getScene().getWindow();

        switch (clickedButton.getId()) {
            case "btnAdd":
                Stage a = new Stage();
                a.setTitle("Добавить");
                a.setResizable(false);
                Parent root = FXMLLoader.load(getClass().getResource("/FXML/AddEdit.fxml"));
                a.setScene(new Scene(root));
                a.initModality(Modality.WINDOW_MODAL);
                a.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
                a.show();
                break;
            case "btnSave":
                TreeItem<Folder> selectionFolder = Tree.getSelectionModel().getSelectedItem();
                String Folder = selectionFolder.getValue().getNameTxt();
                dt.write(referencesList, Folder);
                break;
            case "btnEdit":
                if(selectedReference == null){
                    MessageBox.Show(Alert.AlertType.ERROR, "Ошибка", "Объект для изменения не выбран!",
                            "Выберите объект, который хотите изменить.");
                }
                else{
                    edcontr.setReference(selectedReference);
                    showDialog(parentwindow);
                }
                break;
            case "btnDelete":
                if(selectedReference == null){
                    MessageBox.Show(Alert.AlertType.ERROR, "Ошибка", "Объект для удаления не выбран!",
                            "Выберите объект, который хотите удалить.");
                }
                else{
                    Optional<ButtonType> result = MessageBox.Show(Alert.AlertType.CONFIRMATION, "Внимание", "Удаление объекта",
                            "Вы действительно хотите удалить?");
                    if(result.get() == ButtonType.OK){
                        referencesList.remove(selectedReference);
                    }
                }
                break;
            case "btnGo":
                if(selectedReference == null) {
                    MessageBox.Show(Alert.AlertType.ERROR, "Ошибка", "Объект для удаления не выбран!",
                            "Выберите объект, который хотите удалить.");
                }
                else {
                    showWeb(selectedReference.getReference());
                }
                break;
        }
    }

    /**
     * Метод, который запускает окно редактирования данных в таблице
     * @param parentwindow Родительское окно
     */
    private void showDialog(Window parentwindow) {
        if (editdialogstage == null) {
            editdialogstage = new Stage();
            editdialogstage.setTitle("Редактировать");
            editdialogstage.setResizable(false);
            editdialogstage.setScene(new Scene(fxmlEdit));
            editdialogstage.initModality(Modality.WINDOW_MODAL);
            editdialogstage.initOwner(parentwindow);
        }
        editdialogstage.show();
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
