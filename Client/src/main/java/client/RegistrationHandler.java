package client;

import lombok.Data;
import other.Message;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class RegistrationHandler {
    private boolean firstStepRegistered;
    private boolean newRegistration;
    private String resultLogin;
    private String resultPassword = "";
    private BufferedReader br;

    public Message startRegistration() {
        br = new BufferedReader(new InputStreamReader(System.in));
        do {
            String input = "";
            System.out.println("Для работы с коллекцией войдите в систему (введите 1) или зарегистрируйтесь (введите 2).\n 1.Войти\n 2.Зарегестрироваться");
            try {
                input = br.readLine();
                if (Integer.parseInt(input.trim()) == 1) {
                    firstStepRegistered = true;
                    return login();
                } else if (Integer.parseInt(input.trim()) == 2) {
                    firstStepRegistered = true;
                    return register();
                } else System.out.println("Вы ввели неправильные данные.");
            } catch (IOException e) {
                System.out.println("Введите в строку цифру. Попробуйте снова");
            } catch (NumberFormatException ne){
                System.out.println("Введите в стрку цифру. Попробуйте снова");
            }
        } while (!firstStepRegistered);
        return null;
    }

    private Message login() throws IOException {
        System.out.println("Вы выбрали опцию 'вход'. Введите логин.");
        newRegistration = false;
        do {
            System.out.print(">");
            String login = br.readLine().trim();
            if (login.length() < 4)
                System.out.println("Логин состоит из минимум четырех знаков. Повторите ввод логина");
            else {
                resultLogin = login;
                break;
            }
        } while (true);
        System.out.println("Введите пароль. Если пароля нет, нажмите enter");
        do {
            System.out.print(">");
            Console console = System.console();
            String password = "";
            if (console != null) {
                char[] symbols = console.readPassword();
                password = String.valueOf(symbols);
            } else password = br.readLine().trim();
            if (password.length() < 3 && password.length() > 0)
                System.out.println("Пароль состоит из минимум трех знаков или отсутствует вообще. Повторите ввод пароля.");
            else if (password.length() != 0) {
                Pattern pattern = Pattern.compile("[a-zA-z.\\d_]{3,}");
                Matcher matcher = pattern.matcher(password);
                if (matcher.matches()) {
                    resultPassword = password;
                    break;
                } else
                    System.out.println("Пароль содержит недопустимые символы. Убедитесь, что вы использовали только латинские буквы, цифры, точку и подчеркивание.");

            } else {
                resultPassword="";
                break;
            }
        } while (true);
        return sendUserInfo();
    }

    private Message register() {
        String login = "";
        Pattern pattern = Pattern.compile("[a-zA-z.\\d_]{4,}");
        System.out.println("Вы выбрали опцию 'регистрация'");
        do {
            try {
                System.out.println("Пожалуйста, введите логин.\nЛогин должен содержать минимум 4 символа и может содержать латинские буквы, цифры, знаки подчеркивания и точки");
                login = br.readLine().trim();
                Matcher matcher = pattern.matcher(login);
                if (matcher.matches()) {
                    resultLogin = login;
                    System.out.println("Ваш логин сохранен. У вас есть возможность ввести пароль. Если вы хотите ввести пароль, введите 1, если нет, введите 0.");
                    String input = "";
                    do {
                        try {
                            input = br.readLine();
                        } catch (IOException e) {
                            System.out.println("Ошибка при чтении данных на этапе выбора опции пароля. Попробуйте снова");
                        }
                        Pattern pattern1 = Pattern.compile("[\\d]{1}");
                        Matcher matcher1 = pattern1.matcher(input);
                        if (matcher1.matches() && Integer.parseInt(input.trim()) == 0) {
                            //System.out.println("Регистрация завершена. Вы можете войти в систему.");
                            resultPassword = "";
                            newRegistration = true;
                            return sendUserInfo();
                        } else if (matcher1.matches() && Integer.parseInt(input.trim()) == 1) {
                            newRegistration = true;
                            inputPassword();
                            return sendUserInfo();
                        } else System.out.println("Вы ввели неправильные данные. Попробуйте снова");
                    } while (true);
                } else {
                    System.out.println("Логин не соответствует требованиям.");
                }
            } catch (IOException e) {
                System.out.println("Ошибка при чтениии логина. Попробуйте еще раз.");
            }
        }
        while (true);
    }

    private void inputPassword() {
        String password = "";
        Pattern pattern = Pattern.compile("[a-zA-z.\\d_]{3,}");
        System.out.println("Вы выбрали опцию 'добавить пароль'");
        do {
            try {
                System.out.println("Пожалуйста, введите пароль.\nПароль должен содержать минимум 3 символа и может содержать латинские буквы, цифры, знаки подчеркивания и точки");
                Console console = System.console();
                if (console != null) {
                    char[] symbols = console.readPassword();
                    if (symbols == null) continue;
                    password = String.valueOf(symbols);
                } else password = br.readLine().trim();
                Matcher matcher = pattern.matcher(password);
                if (matcher.matches()) {
                    System.out.println("Ваш пароль сохранен.");
                    resultPassword = password;
                    firstStepRegistered = false;
                    break;
                } else {
                    System.out.println("Пароль не соответствует требованиям.");
                }
            } catch (IOException e) {
                System.out.println("Ошибка при чтениии логина. Попробуйте еще раз.");
            }
        } while (true);
    }

    private Message sendUserInfo() {
        List<String> userInfoList = new LinkedList<>();
        userInfoList.add(resultLogin);
        userInfoList.add(resultPassword);
        Message userInfo;
        if (newRegistration) {
            userInfo = Message.builder().commandName("register").commandArgs(userInfoList).build();
        } else {
            userInfo = Message.builder().commandName("login").commandArgs(userInfoList).build();
        }
        return userInfo;
    }
}
