package Controllers;

import Source.DataManager;
import Source.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
/**
 * Класс контроллер для авторизации
 */
public class AuthorizationController {
    @FXML
    private Button closeButton;
    @FXML
    private Button openButton;
    @FXML
    private TextField IdPassField;
    @FXML
    private TextField IdLogField;
    @FXML
    private Hyperlink AboutProgramm;
    @FXML
    private Hyperlink RegistrButton;

    private DataManager dt = new DataManager();
    public static ArrayList<User> arrayUserList = new ArrayList<User>();
    public static boolean checkReadList = false;

    /**
     * Метод отвечающий за открытие одного из двух
     * режимов работы программы: пользовательский режим
     * или режим администратора
     * @param actionEvent Нажатие на кнопку
     * @throws IOException Иключение ошибки считывание с файла
     */
    public void openWin(ActionEvent actionEvent) throws IOException {
        Stage stage=null;
        Scene scene=null;
        if(!checkReadList){
            arrayUserList = dt.readUser(arrayUserList, "LoginPassword.txt");
            checkReadList = true;
        }
        String Login = IdLogField.getText();
        String Password = IdPassField.getText();
        int Hash = IdPassField.getText().hashCode();

        if(arrayUserList.isEmpty()){
            MessageBox.Show(Alert.AlertType.INFORMATION, "Внимание", "Пользователей пока не существует.",
                    "Зарегистрируйте пользователя!");
        }else if(Login.equals(arrayUserList.get(0).getUserLogin()) && arrayUserList.get(0).getUserHashCode() == Hash){
            scene = new Scene(FXMLLoader.load(getClass().getResource("/FXML/TreeView.fxml")));
            stage = (Stage) openButton.getScene().getWindow();
            stage.setTitle("Администратор");
            stage.setScene(scene);
        }
        else if(identUser(arrayUserList)){
            scene = new Scene(FXMLLoader.load(getClass().getResource("/FXML/TreeViewUser.fxml")));
            stage = (Stage) openButton.getScene().getWindow();
            stage.setTitle("Пользователь: " + Login);
            stage.setScene(scene);
        }
        else if(Login.isEmpty() || Password.isEmpty() ){
            MessageBox.Show(Alert.AlertType.WARNING, "Ошибка", "Для продолжения нужен логин и пароль!",
                    "Введите правильный логин или пароль!");
        }
        else{
            MessageBox.Show(Alert.AlertType.ERROR, "Ошибка", "Пользователя с таким логином или паролем не существует!",
                    "Введите правильный логин или пароль!");
        }

    }

    /**
     * Метод закрытия окна
     * @param actionEvent Нажатие на кнопку
     */
    public void closeWin(ActionEvent actionEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Метод проверки на существование в базе
     * таких данных о пользователе, которые
     * введены в текстовые поля
     * @param arrayUserList Список всех пользователей
     * @return Существует или не существует такой пользователь
     * @throws IOException Исключение ощибки считывания с файла
     */
    private boolean identUser(ArrayList<User> arrayUserList) throws IOException{
        int Hash = IdPassField.getText().hashCode();

        for (User u: arrayUserList) {
            if(u.getUserLogin().equals(IdLogField.getText()) && u.getUserHashCode() == Hash){
                    return true;
            }
        }
        return false;
    }

    /**
     * Метод вызывающий диалоговое окно с
     * информацией об этой программе
     * @param actionEvent Нажатие на гиперссылку
     * @throws IOException Исключене ошибки существования файла
     */
    public void aboutProgramm(ActionEvent actionEvent) throws IOException {
        Stage a = new Stage();
        a.setTitle("О программе");
        a.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/AboutProgram.fxml"));
        a.setScene(new Scene(root));
        a.initModality(Modality.WINDOW_MODAL);
        a.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
        a.show();
        AboutProgramm.visitedProperty().setValue(false);
    }

    /**
     * Метод вызывающий диалоговое окно в
     * котором происходит регистрация нового пользователя
     * @param actionEvent Нажатие на гиперссылку
     * @throws IOException Исключение ошибки существования файла
     */
    public void registrationAction(ActionEvent actionEvent) throws IOException {
        Stage a = new Stage();
        a.setTitle("Регистрация");
        a.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Registration.fxml"));
        a.setScene(new Scene(root));
        a.initModality(Modality.WINDOW_MODAL);
        a.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
        a.show();
        RegistrButton.visitedProperty().setValue(false);
    }

}
