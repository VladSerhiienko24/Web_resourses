package Controllers;

import Source.Reference;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
/**
 * Класс контроллер для добавления ссылок
 */
public class AddEditController {

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtReference;
    /**
     * Метод закрытия окна
     * @param actionEvent действие
     */
    public void CloseAE(ActionEvent actionEvent) {
        Node p = (Node) actionEvent.getSource();
        Stage st = (Stage) p.getScene().getWindow();
        st.close();
    }
    /**
     * Метод открытия окна
     * @param actionEvent действие
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
        else {
            Reference r = new Reference();
            r.setName(getTxtName());
            r.setReference(getTxtReference());
            TreeViewController.referencesList.add(r);
            CloseAE(actionEvent);
        }
    }

    /**
     * Метод проверки на корректность ссылки
     * @param Site Ссылка
     * @return Правильно или неправильно
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

    /**
     * Метод получения введенного имени в текстовом поле
     * @return имя в текстовом поле
     */
    public String getTxtName(){
        return txtName.getText();
    }

    /**
     *Метод получения введенной ссылки в текстовом поле
     * @return ссылка в текстовом поле
     */
    public String getTxtReference(){
        return txtReference.getText();
    }
}
