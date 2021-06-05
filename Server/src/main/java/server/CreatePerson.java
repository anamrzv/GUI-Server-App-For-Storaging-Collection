package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import lombok.Data;
import other.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class CreatePerson {
    /**
     * Поле - отображение объектов Location
     */
    private Map<Integer, Location> readyLocations;

    private final CollectionsKeeper collectionsKeeper;

    private final Object maxOrMinAdd;

    public CreatePerson(CollectionsKeeper dc, Object addIfMax) {
        this.collectionsKeeper = dc;
        maxOrMinAdd = addIfMax;
    }

    private Person createPerson(List<String> args) {
        Person newPers = new Person();
        readyLocations = collectionsKeeper.getReadyLocations();

        if (args == null) {
            System.out.println("У команды add должен быть один аргумент - слово 'Person' или строка формата json. Введите команду снова.");
            return null;
        } else if (args.size() == 1 && args.get(0).equalsIgnoreCase("Person")) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Вы выбрали добавление элемента вручную через консоль.");

                System.out.println("Введите имя персоны (не пустая строка). Это обязательное поле");
                inputName(br, newPers);

                System.out.println("Введите рост. Он должен быть больше 0. Это необязательное поле. ");
                inputHeight(br, newPers);

                System.out.println("Введите вес. Он должен быть больше 0. Это необязательное поле");
                inputWeight(br, newPers);

                System.out.println("Введите ID паспорта. Длина ID должна лежать в диапазоне [10;27]. Это обязательное поле");
                inputPassport(br, newPers);

                System.out.println("Выберите цвет волос. Это обязательное поле.");
                inputHairColor(br, newPers);

                System.out.println("Выберите местоположение персоны из существующих. Это обязательное поле.");
                inputLocation(br, newPers, readyLocations);

                System.out.println("Введите координаты персоны. Это обязательное поле.");
                inputCoordinates(br, newPers);

                if (maxOrMinAdd == null) System.out.println("Элемент " + newPers.getName() + " добавлен в коллекцию");
                return newPers;
            } catch (Exception e) {
                System.out.println("Ошибка при чтении данных");
                return null;
            }
        } else if (args.size() == 1) {
            String regex = "\\{.+}";
            Pattern pattern = Pattern.compile(regex);
            Matcher m = pattern.matcher(args.get(0));
            boolean isJson = m.matches();
            if (isJson) {
                System.out.println("Вы выбрали автоматическое добавление элемента через строку в формате json.");
                String jsonLine = args.get(0);
                try {
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    Person pers = gson.fromJson(jsonLine, Person.class);
                    if (pers.getName() == null || pers.getPassportID() == null || pers.getHairColor() == null || pers.getLocation() == null || pers.getCoordinates() == null) {
                        System.out.println("Проверьте, что заполнены все обязательны поля: name, passport id, hair color, location, coordinates, а в поле hair color правильно указан цвет.\nВведите команду снова с правильными данными.");
                        return null;
                    } else if (!collectionsKeeper.validateName(pers.getName())) {
                        System.out.println("В имени не могут содержаться цифры и спец. знаки.\nВведите команду снова с правильными данными.");
                        return null;
                    } else if (!collectionsKeeper.validatePassport(pers.getPassportID())) {
                        System.out.println("В passport id должны содержаться только цифры.\nВведите команду снова с правильными данными.");
                        return null;
                    } else if (pers.getPassportID().length() < 10 || pers.getPassportID().length() > 27) {
                        System.out.println("Passport id должен содержать от 10 до 27 цифр.\nВведите команду снова с правильными данными.");
                        return null;
                    } else {
                        if (maxOrMinAdd == null)
                            System.out.println("Элемент " + pers.getName() + " добавлен в коллекцию");
                        boolean alreadyLocation = false;
                        Location currentLocation = pers.getLocation();
                        for (Location l : readyLocations.values()) {
                            if (currentLocation.equals(l)) {
                                alreadyLocation = true;
                                break;
                            }
                        }
                        if (!alreadyLocation) readyLocations.put(readyLocations.size() + 1, pers.getLocation());
                        return pers;
                    }
                } catch (JsonSyntaxException e) {
                    System.out.println("Ошибка при чтении данных из строки. Проверьте, что в полях, где\nтребуются числа не введены буквы и наоборот, а также то, что в поле 'цвет волос'\nнаписан цвет волос yellow, white, orange или brown.\nВведите команду снова с правильными данными.");
                    return null;
                } catch (Exception e) {
                    System.out.println("Ошибка при чтении объекта из строки. Проверьте корректность данных аргумента и введите команду снова.");
                    return null;
                }
            } else {
                System.out.println("Неверный формат аргумента: строка должна быть непустой, в фигурных скобках. Введите команду снова с правильной строкой или словом 'Person'.");
                return null;
            }
        } else {
            System.out.println("У команды add должен быть только один аргумент - слово 'Person' или строка формата json. Введите команду снова.");
            return null;
        }
    }

    /**
     * Метод - добавляет в отображение местоположений readyLocations новое, если его еще не было.
     *
     * @param br - буферный поток ввода
     * @param dc - обработчик команд
     */
    public void addLocation(BufferedReader br, CollectionsKeeper dc) throws IOException {
        Location l = new Location();
        int x;
        float y;
        double z;
        try {
            System.out.println("Введите координату х. Это обязательное поле.");
            x = enterSomeNumber(br);

            System.out.println("Введите координату y. Это обязательное поле.");
            y = (float) enterSomeNumber(br);

            System.out.println("Введите координату z. Это обязательное поле.");
            z = enterSomeNumber(br);

            System.out.println("По желанию назовите ваше местоположение.");
            System.out.print(">");
            String i = br.readLine().trim();
            l.setLocation(x, y, z, i);
            boolean alreadyLocation = false;
            for (Location loc : readyLocations.values()) {
                if (l.equals(loc)) {
                    alreadyLocation = true;
                    break;
                }
            }
            if (!alreadyLocation) dc.addLocation(l);
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат ввода, введите координаты еще раз. Проверьте,что в веденной строке отсутствуют буквы.");
        }
    }

    /**
     * Метод - получает на вход строку, проверяет, записано ли в ней число и возвращает число.
     *
     * @param br - буферный поток ввода
     * @return int - число
     */
    private int enterSomeNumber(BufferedReader br) {
        int x;
        do {
            try {
                System.out.print(">");
                String i = br.readLine().trim();
                if (i.isEmpty())
                    System.out.println("Нельзя ввести пустую строку в это поле, пожалуйста, введите число.");
                else {
                    x = Integer.parseInt(i);
                    break;
                }
            } catch (Exception e) {
                System.out.println("Неверный формат ввода, введите координаты еще раз.");
            }
        } while (true);
        return x;
    }

    /**
     * Возвращает имя команды
     *
     * @return имя
     */
    public String getName() {
        return "add";
    }

    /**
     * Возвращает описание команды
     *
     * @return описание
     */
    public String getDescription() {
        return "add Person : добавить новый элемент в коллекцию";
    }

    /**
     * Метод - получает на вход строку, проводит валидацию, инициализирует поле 'имя' у объекта p.
     *
     * @param br - буферный поток ввода
     * @param p  - объект Person, которому будет присвоено имя
     */
    private void inputName(BufferedReader br, Person p) {
        do {
            try {
                System.out.print(">");
                String name = br.readLine().trim();
                boolean hasNoDigit = collectionsKeeper.validateName(name);
                if (name.equals(""))
                    System.out.println("Нельзя ввести пустую строку в это поле, пожалуйста, введите данные.");
                else if (!hasNoDigit)
                    System.out.println("В имени не могут содержаться цифры и спец. знаки. Проверьте,что в веденной строке они отсутствуют и введите имя еще раз.");
                else {
                    p.setName(name);
                    break;
                }
            } catch (Exception e) {
                System.out.println("Неверный формат ввода, введите имя еще раз.");
            }
        } while (true);
    }

    /**
     * Метод - получает на вход строку, проводит валидацию, инициализирует поле 'рост' у объекта p.
     *
     * @param br - буферный поток ввода
     * @param p  - объект Person, которому будет присвоен рост
     */
    private void inputHeight(BufferedReader br, Person p) {
        do {
            try {
                System.out.print(">");
                String i = br.readLine().trim();
                if (!i.isEmpty()) {
                    long height = Long.parseLong(i);
                    if (height <= 0) {
                        do {
                            System.out.println("Нельзя ввести число меньше нуля, пожалуйста, введите число еще раз.");
                            System.out.print(">");
                            i = br.readLine().trim();
                            height = Long.parseLong(i);
                        } while (height <= 0);
                    } else {
                        p.setHeight(height);
                        break;
                    }
                }
                break;
            } catch (Exception e) {
                System.out.println("Неверный формат ввода, введите число или enter.");
            }
        } while (true);
    }

    /**
     * Метод - получает на вход строку, проводит валидацию, инициализирует поле 'вес' у объекта p.
     *
     * @param br - буферный поток ввода
     * @param p  - объект Person, которому будет присвоен вес
     */
    private void inputWeight(BufferedReader br, Person p) {
        do {
            try {
                System.out.print(">");
                String i = br.readLine().trim();
                if (!i.isEmpty()) {
                    long weight = Long.parseLong(i);
                    if (weight <= 0) {
                        do {
                            System.out.println("Нельзя ввести число меньше нуля, пожалуйста, введите число еще раз.");
                            System.out.print(">");
                            i = br.readLine().trim();
                            weight = Long.parseLong(i);
                        } while (weight <= 0);
                    } else {
                        p.setWeight(weight);
                        break;
                    }
                }
                break;
            } catch (Exception e) {
                System.out.println("Неверный формат ввода, введите число или enter.");
            }
        } while (true);
    }

    /**
     * Метод - получает на вход строку, проводит валидацию, инициализирует поле 'id паспорта' у объекта p.
     *
     * @param br - буферный поток ввода
     * @param p  - объект Person, которому будет присвоено id
     */
    private void inputPassport(BufferedReader br, Person p) {
        try {
            do {
                System.out.print(">");
                String passport = br.readLine().trim();
                boolean hasNoLetter = collectionsKeeper.validatePassport(passport);
                if (passport.equals(""))
                    System.out.println("Нельзя ввести пустую строку в данное поле, пожалуйста, введите данные.");
                else if (passport.length() > 27 || passport.length() < 10)
                    System.out.println("ID не подходит по длине, введите данные снова.");
                else if (!hasNoLetter)
                    System.out.println("PassportID должен состоять только из цифр, введите данные снова.");
                else {
                    p.setPassportID(passport);
                    break;
                }
            } while (true);
        } catch (Exception e) {
            System.out.println("Неверный формат ввода, введите ID паспорта ещё раз");
        }
    }

    /**
     * Метод - получает на вход строку, проводит валидацию, инициализирует поле 'цвет волос' у объекта p.
     *
     * @param br - буферный поток ввода
     * @param p  - объект Person, которому будет присвоен цвет волос
     */
    private void inputHairColor(BufferedReader br, Person p) {
        Color hair = null;
        do {
            try {
                System.out.println("Пожалуйста, введите цвет волос из представленных (на английском) :\nYELLOW \nORANGE \nWHITE \nBROWN");
                boolean isFound = false;
                do {
                    System.out.print(">");
                    String input = br.readLine().trim();
                    if (input.isEmpty())
                        System.out.println("Нельзя ввести пустую строку в данное поле, пожалуйста, введите данные.");
                    else {
                        for (Color c : Color.values()) {
                            if (input.equalsIgnoreCase(c.toString())) {
                                isFound = true;
                                hair = c;
                                break;
                            }
                        }
                        if (!isFound)
                            System.out.println("Пожалуйста, введите вариант из предложенных на английском языке.");
                    }
                } while (!isFound);
                p.setHairColor(hair);
                break;
            } catch (Exception e) {
                System.out.println("Неверный формат ввода. Попробуйте ввести цвет волос ещё раз.");
            }
        } while (true);
    }

    /**
     * Метод - получает на вход строку, проводит валидацию, инициализирует поле 'локация' у объекта p.
     *
     * @param br - буферный поток ввода
     * @param p  - объект Person, которому будет присвоена локация
     */
    private void inputLocation(BufferedReader br, Person p, Map<Integer, Location> readyLocations) throws IOException {
        int num;
        if (readyLocations.size() == 0) {
            System.out.println("Пока не существует готовых местоположений. Добавьте одно.");
            addLocation(br, collectionsKeeper);
        }
        do {
            try {
                System.out.println("Введите номер доступного местоположения или добавьте новое: ");
                System.out.println("0. Добавить новое местоположение");
                for (int key : readyLocations.keySet()) {
                    System.out.print(key);
                    Location value = readyLocations.get(key);
                    System.out.println(". " + value.getName() + value.getLocation());
                }
                num = enterSomeNumber(br);
                if (num == 0) {
                    addLocation(br, collectionsKeeper);
                }
                if (num < 0 || num > readyLocations.size()) {
                    System.out.println("Был введен неправильный номер. Введите номер от 0 до " + readyLocations.size());
                    num = 0;
                }
            } catch (Exception e) {
                System.out.println("Неверный формат ввода. Попробуйте ввести расположение ещё раз");
                num = 0;
            }
        } while (num == 0);
        Location location = readyLocations.get(num);
        p.setLocation(location);
    }

    /**
     * Метод - получает на вход строку, проводит валидацию, инициализирует поле 'координаты' у объекта p.
     *
     * @param br - буферный поток ввода
     * @param p  - объект Person, которому будут присвоены координаты
     */
    private void inputCoordinates(BufferedReader br, Person p) {
        Coordinates c = new Coordinates();
        System.out.println("Введите координату х. Это обязательное поле.");
        float x = (float) enterSomeNumber(br);
        System.out.println("Введите координату y. Это обязательное поле.");
        double y = enterSomeNumber(br);
        c.setCoordinatesFirst(x, y);
        p.setCoordinates(c);
    }

    public Person setCreation(List<String> args) {
        return createPerson(args);
    }
}
