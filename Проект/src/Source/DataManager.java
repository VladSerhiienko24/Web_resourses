package Source;
import Controllers.MessageBox;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.*;
import java.util.ArrayList;

/**
 * Класс работы с txt файлами
 */
public class DataManager {

    /**
     * Метод считывания из файла в список с ссылками
     * @param LIST Список, в который будут записываться данные из файла
     * @param NameTxt Название файла из которого будут считаться данные
     * @return Список со всеми данными
     * @throws IOException Исключение ошибки считывания из файла
     */
    public ObservableList<Reference> read(ObservableList<Reference> LIST, String NameTxt) throws IOException{
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/TXT/"+NameTxt), "UTF-8"))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(";");
                LIST.add(new Reference(tokens[0], tokens[1]));
            }
        }
        return LIST;
    }

    /**
     * Метод записи в файл данных со списка
     * @param LIST Список, в котором хранятся данные
     * @param NameTxt Название файла в который запишутся данные
     * @throws IOException Исключение ошибки записи в файл
     */
    public void write(ObservableList<Reference> LIST, String NameTxt) throws IOException{
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("src/TXT/"+NameTxt))){
            for (Reference m : LIST)
            {
                bw.write(m.getName() + ";" + m.getReference() +"\n");
            }
        }
    }

    /**
     * Метод считывания из файла в список с папками
     * @param LIST Список, в который будут записываться данные из файла
     * @return Список со всеми данными
     * @throws IOException Исключение ошибки считывания из файла
     */
    public ObservableList<Folder> readFolder(ObservableList<Folder> LIST) throws IOException{
        try (BufferedReader reader = new BufferedReader(new FileReader("src/TXT/folder.txt"))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(";");
                LIST.add(new Folder(tokens[0], tokens[1]));
            }
        }
        return LIST;
    }

    /**
     * Метод записи в файл данных со списка
     * @param LIST Список, в котором хранятся данные
     * @throws IOException  Исключение ошибки записи в файл файла
     */
    public void writeFolder(ObservableList<Folder> LIST) throws IOException{
        String FileName = "src/TXT/folder.txt";
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(FileName))){
            for (Folder m : LIST)
            {
                bw.write(m.getName() + ";" + m.getNameTxt() +"\n");
            }
        }
    }

    /**
     * Метод создания txt документа, который будет соответствовать конкретной папке и хранить ссылки внутри
     * @param FileName  имя txt документа который мы создаем
     * @throws IOException Исключение ошибки создания файла
     */
    public void createFolderTxt(String FileName) throws IOException{
        File newFile = new File("src/TXT/" + FileName);
        newFile.createNewFile();
        return;
    }

    /**
     * Метод для удаления файла
     * @param Filename имя даляемого файла
     * @throws FileNotFoundException Исключение отсутствия файла
     */
    public void delete (String Filename) throws FileNotFoundException{
        File file = new File("src/TXT/"+Filename);
        if(file.exists()){
            new File("src/TXT/"+Filename).delete();
        }
        else{
            MessageBox.Show(Alert.AlertType.ERROR, "Ошибка", "При удалении файла возникла ошибка!",
                    "Файла, который вы хотите удалить, не существует!");
        }
    }

    /**
     * Метод для переименования Файла txt
     * @param FileName старое имя
     * @param NewFileName новое имя
     */
    public void renameFile(String FileName, String NewFileName){
        java.io.File file = new java.io.File("src/TXT/" + FileName);
        if(file.exists()){ // если файл существует, то переименовываем его
            file.renameTo(new java.io.File("src/TXT/" + NewFileName));
        }
        else{
            MessageBox.Show(Alert.AlertType.ERROR, "Ошибка", "При переименовании файла возникла ошибка!",
                    "Файла, который вы хотите переименовать, не существует!");
        }
    }

    /**
     * Метод считывания с файла в список с пользователями
     * @param LIST Список с пользователями
     * @param NameTxt Название файла
     * @return список с данными
     * @throws IOException Исключение отсутствия файла
     */
    public ArrayList<User> readUser(ArrayList<User> LIST, String NameTxt) throws IOException{
        try (BufferedReader reader = new BufferedReader(new FileReader("src/TXT/"+NameTxt))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(";");
                LIST.add(new User(tokens[0], Integer.parseInt(tokens[1]), tokens[2]));
            }
        }
        return LIST;
    }

    /**
     * Метод записи в файл данных из списка
     * @param LIST Список, в котором хранятся данные
     * @throws IOException Исключение ошибки записи в файл
     */
    public void writeUser (ArrayList<User> LIST) throws IOException{
        String FileName = "src/TXT/LoginPassword.txt";
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(FileName))){
            for (User m : LIST)
            {
                bw.write(m.getUserLogin() + ";" + m.getUserHashCode() + ";" + m.getUserName() +"\n");
            }
        }
    }
}

