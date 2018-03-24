package Source;

import javafx.beans.property.SimpleStringProperty;

/**
 * Класс ссылки, по которой будет ощуществляться переход
 */
public class Reference {
    private SimpleStringProperty name = new SimpleStringProperty("");
    private SimpleStringProperty reference = new SimpleStringProperty("");

    /**
     * Конструктор по-умолчанию
     */
    public Reference(){}

    /**
     * Конструктор с параметрами
     * @param name Название ссылки
     * @param reference Ссылка для перехода
     */
    public Reference(String name, String reference){
        this.reference = new SimpleStringProperty(reference);
        this.name = new SimpleStringProperty(name);
    }

    /**
     * Метод получения названия ссылки
     * @return название ссылки
     */
    public String getName() {
        return name.get();
    }

    /**
     * Метод назначение названия ссылки
     * @param name название ссылки
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Метод получения ссылки для перехода
     * @return ссылка для перехода
     */
    public String getReference() {
        return reference.get();
    }

    /**
     * Метод назначения ссылки для перехода
     * @param reference ссылка для перехода
     */
    public void setReference(String reference) {
        this.reference.set(reference);
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
    public SimpleStringProperty referenceProperty() {
        return reference;
    }

    /**
     * Переопределенный метод toString
     * @return информацию об объекте
     */
    @Override
    public String toString() {
        return "Reference{" +
                "name=" + name +
                ", reference=" + reference +
                '}';
    }
}
