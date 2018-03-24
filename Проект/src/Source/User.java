package Source;
/**
 * Класс пользователя
 */
public class User {

    private String UserLogin;
    private int UserHashCode;
    private String UserName;

    /**
     * Конструктор по-умолчанию
     */
    public User(){}

    /**
     * Конструктор с параметрами
     * @param UserLogin Логин пользователя
     * @param UserHashCode Хэш-код пароля
     * @param UserName ФИО
     */
    public User(String UserLogin, int UserHashCode,  String UserName){
        this.setUserLogin(UserLogin);
        this.setUserName(UserName);
        this.setUserHashCode(UserHashCode);
    }

    /**
     * Метод получения логина пользователя
     * @return Логин пользователя
     */
    public String getUserLogin() {
        return UserLogin;
    }

    /**
     * Метод назначения логина
     * @param UserLogin Логин пользователя
     */
    public void setUserLogin(String UserLogin) {
        this.UserLogin = UserLogin;
    }

    /**
     * Метод получения ФИО пользователя
     * @return ФИО пользователя
     */
    public String getUserName() {
        return UserName;
    }

    /**
     * Метод назначения ФИО
     * @param UserName ФИО пользователя
     */
    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    /**
     * Метод получения хэш-кода
     * @return
     */
    public int getUserHashCode() {
        return UserHashCode;
    }

    /**
     * Метод назначения хэш-кода
     * @param UserHashCode Хэш-код пользователя
     */
    public void setUserHashCode(int UserHashCode) {
        this.UserHashCode = UserHashCode;
    }
}
