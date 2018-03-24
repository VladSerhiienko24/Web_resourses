package Controllers;

import Source.DataManager;
import Source.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Класс контроллер для регистрации
 */
public class RegistrationController {
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtLogin;
    @FXML
    private PasswordField txtPassword;

    private DataManager dt = new DataManager();

    /**
     * Метод регистрации нового пользователя
     * @param actionEvent Нажатие на кнопку
     * @throws IOException Исключене ошибки существования файла
     */
    public void regUser(ActionEvent actionEvent) throws IOException{
        if(!AuthorizationController.checkReadList){
            AuthorizationController.arrayUserList = dt.readUser(AuthorizationController.arrayUserList, "LoginPassword.txt");
            AuthorizationController.checkReadList = true;
        }

        User NewUser = new User(txtLogin.getText(), txtPassword.getText().hashCode(), txtName.getText());
        if(txtLogin.getText().isEmpty() || txtPassword.getText().isEmpty() || txtName.getText().isEmpty()){
            MessageBox.Show(Alert.AlertType.ERROR, "Ошибка", "Введены не все данные",
                    "Введите все требуемые данные для регистрации!");
        }
        else if(checkUserName(txtLogin.getText())){
            MessageBox.Show(Alert.AlertType.ERROR, "Ошибка", "Регистрация невозможна",
                    "Пользователь с таким именем уже существует!");
        }
        else{
            MessageBox.Show(Alert.AlertType.INFORMATION, "", "Регистрация прошла успешно!",
                    "Пользователь зарегистрирован!");
            AuthorizationController.arrayUserList.add(NewUser);
            dt.writeUser(AuthorizationController.arrayUserList);
            closeWind(actionEvent);
        }
    }

    /**
     * Метод, который проверяет логин,
     * введенный при регистрации, на занятость
     * @param Login Логин введенный пользователем
     * @return Занят или не занят
     */
    private boolean checkUserName(String Login){
        boolean check = false;
        for (User u: AuthorizationController.arrayUserList) {
            if(u.getUserLogin().equals(Login)){
                check = true;
                break;
            }
        }
        return check;
    }

    /**
     * Метод закрытия окна
     * @param actionEvent Нажатие на кнопку
     */
    public void closeWind(ActionEvent actionEvent) {
        Node p = (Node) actionEvent.getSource();
        Stage st = (Stage) p.getScene().getWindow();
        st.close();
    }
}
