package Controllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * Класс отвечающий за управление окном
 */
public class AboutProgramController {
    /**
     * Метод закрытия окна
     * @param actionEvent Нажатие на кнопку
     */
    public void close(ActionEvent actionEvent) {
        Node p = (Node) actionEvent.getSource();
        Stage st = (Stage) p.getScene().getWindow();
        st.close();
    }
}
