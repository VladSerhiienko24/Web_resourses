package Source;

import javafx.beans.property.SimpleStringProperty;

/**
 * Класс папки, в которой находятся ссылки
 */
public class Folder {
    private SimpleStringProperty name = new SimpleStringProperty("");
    private SimpleStringProperty nameTxt = new SimpleStringProperty("");

    /**
     * Конструктор по-умолчанию
     */
    public Folder(){}

    /**
     * Конструктор с параметрами
     * @param name Название папки
     * @param nameTxt Название файла txt для хранения ссылок данной папки
     */
    public Folder(String name, String nameTxt){
        this.name = new SimpleStringProperty(name);
        this.nameTxt = new SimpleStringProperty(nameTxt);
    }

    /**
     * Метод получения названия папки
     * @return название папки
     */
    public String getName() {
        return name.get();
    }

    /**
     * Метод назначения названия папки
     * @param name название папки
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Метод получения названия файла txt для хранения ссылок данной папки
     * @return название файла txt для хранения ссылок данной папки
     */
    public String getNameTxt() {
        return nameTxt.get();
    }

    /**
     * Метод назначение названия файла txt для хранения ссылок данной папки
     * @param nameTxt название файла txt для хранения ссылок данной папки
     */
    public void setNameTxt(String nameTxt) {
        this.nameTxt.set(nameTxt);
    }

    /**
     * Метод работающий с автообновлением данных поля имя
     * @return имя
     */
    public SimpleStringProperty nameProperty() {
        return name;
    }

    /**
     * Метод работающий с автообновлением данных поля ссылка
     * @return ссылка
     */
    public SimpleStringProperty nameTxtProperty() {
        return nameTxt;
    }

    /**
     * Переопределенный метод toString
     * @return информацию об объекте
     */
    @Override
    public String toString() {
        return getName();
    }
}
