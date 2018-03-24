package Controllers;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * Класс для вывода окна сообщения об ошибке
 */
public class MessageBox {
    private static Alert alert = new Alert(AlertType.NONE);

    /**
     * Метод отображения окна ошибки
     * @param messageType тип сообщения об ошибке
     * @param title заголовок
     * @param header главная часть сообщения об ошибке
     * @param content содержание сообщения об ошибке
     * @return окно
     */
    public static Optional<ButtonType> Show(AlertType messageType, String title, String header, String content) {
        alert.setAlertType(messageType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert.showAndWait();
    }
}
