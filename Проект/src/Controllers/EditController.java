package Controllers;

import Source.Reference;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Класс контроллер для редактирования ссылок
 */
public class EditController {
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtReference;

    private Reference reference;

    /**
     * Метод закрытия окна
     * @param actionEvent Нажатие на кнопку
     */
    public void CloseAE(ActionEvent actionEvent) {
        Node p = (Node) actionEvent.getSource();
        Stage st = (Stage) p.getScene().getWindow();
        st.close();
    }

    /**
     * Метод добавдения измененного значения в список с ссылками на веб-ресурс
     * @param actionEvent Нажатие на кнопку
     * @throws IOException Исключене ошибки существования файла
     */
    public void AddAE(ActionEvent actionEvent) throws IOException{
        if(txtName.getText().isEmpty() || txtReference.getText().isEmpty()){
            MessageBox.Show(Alert.AlertType.ERROR, "Ошибка", "Поле пустое!",
                    "Введите значение!");
        }
        else if(!checkSite(txtReference.getText())){
            MessageBox.Show(Alert.AlertType.ERROR, "Ошибка", "Введенная строка не является ссылкой!",
                    "Проверьте правильность ввода ссылки!");
        }
        else{
            reference.setName(txtName.getText());
            reference.setReference(txtReference.getText());
            CloseAE(actionEvent);
        }
    }

    /**
     * Метод изменения данных в таблице
     * @param reference Изменяемый объект
     */
    public void setReference(Reference reference) {
        this.reference = reference;
        txtName.setText(reference.getName());
        txtReference.setText(reference.getReference());
    }

    /**
     * Метод проверки на корректность ссылки
     * @param Site Ссылка
     * @return Корректно или некорректно
     */
    private boolean checkSite(String Site){
        Pattern regexp= Pattern.compile("^(http://)|(https://)");
        Matcher matcher=regexp.matcher(Site);
        if(matcher.find()){
            return true;
        }
        else{
            return false;
        }
    }
}
