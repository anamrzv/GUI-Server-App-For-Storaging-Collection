package client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import other.CollectionsKeeper;
import other.Location;
import other.Person;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class UpdatePerson {

    /**
     * Поле - логическая переменная, найден ли такой объект
     */
    private boolean isFound = false;

    /**
     * Поле - объект Person, поля которого будут обновлены
     */
    private Person updatePers;

    private final CollectionsKeeper collectionsKeeper;

    public UpdatePerson(CollectionsKeeper dc) {
        this.collectionsKeeper = dc;
    }

    private Person updatePerson(List<String> args) {
        long id;
        Map<Integer, Location> readyLocations = collectionsKeeper.getReadyLocations();
        LinkedList<Person> people = collectionsKeeper.getPeople();
        if (args == null || args.size() != 2) {
            System.out.println("У команды update должно быть два аргумента - необходимое id и слово Person/строка формата json.");
            return null;
        } else {
            try{
                id = Long.parseLong(args.get(0));
            }catch(NumberFormatException e){
                System.out.println("Второй аргумент должен быть числом. Повторите ввод.");
                return null;
            }
            for (Person p : people) {
                if (p.getId() == id) {
                    updatePers = p;
                    isFound = true;
                    break;
                }
            }
            if (!isFound) {
                System.out.println("Человека с таким id не существует");
                return null;
            } else if (!args.get(1).equalsIgnoreCase("person")) {
                try {
                    String jsonLine = args.get(1);
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    Person pers = gson.fromJson(jsonLine, Person.class);
                    if (pers.getName() == null || pers.getPassportID() == null || pers.getHairColor() == null || pers.getLocation() == null || pers.getCoordinates() == null) {
                        System.out.println("Проверьте, что заполнены все обязательны поля: name, passport id, hair color, location, coordinates, а в поле hair color правильно указан цвет.");
                        return null;
                    } else if (!collectionsKeeper.validateName(pers.getName())) {
                        System.out.println("В имени не могут содержаться цифры и спец. знаки");
                        return null;
                    } else if (!collectionsKeeper.validatePassport(pers.getPassportID())) {
                        System.out.println("В passport id должны содержаться только цифры.");
                        return null;
                    } else if (pers.getPassportID().length() < 10 || pers.getPassportID().length() > 27) {
                        System.out.println("Passport id должен содержать от 10 до 27 цифр, проверьте длину.");
                        return null;
                    } else {
                        pers.setId(id);
                        return pers;
                    }
                } catch (JsonSyntaxException e) {
                    System.out.println("Проверьте формат ввода строки json");
                    return null;
                }
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                do {
                    try {
                        System.out.println("Выберите, какие поля вы хотите обновить(Введите число):\n1.Nмя\n2.Рост\n3.Вес\n4.ID паспорта\n5.Цвет волос\n6.Локация\n7.Координаты\nNли нажмите enter чтобы закончить изменения");
                        System.out.print(">");
                        CreatePerson creation = new CreatePerson(collectionsKeeper);
                        String i = br.readLine().trim();
                        if (!i.isEmpty()) {
                            int choice = Integer.parseInt(i);
                            if (choice > 0 && choice < 8) {
                                switch (choice) {
                                    case (1):
                                        System.out.println("Введите новое имя персоны (не пустая строка).");
                                        creation.setInputName(br, updatePers);
                                        break;
                                    case (2):
                                        System.out.println("Введите новый рост. Он должен быть больше 0. Если хотите сбросить рост, нажмите enter.");
                                        creation.setInputHeight(br, updatePers);
                                        break;
                                    case (3):
                                        System.out.println("Введите новый вес. Он должен быть больше 0. Если хотите сбросить вес, нажмите enter.");
                                        creation.setInputWeight(br, updatePers);
                                        break;
                                    case (4):
                                        System.out.println("Введите новый ID паспорта. Длина ID должна лежать в диапазоне [10;27].");
                                        creation.setInputPassport(br, updatePers);
                                        break;
                                    case (5):
                                        System.out.println("Выберите новый цвет волос.");
                                        creation.setInputHairColor(br, updatePers);
                                        break;
                                    case (6):
                                        System.out.println("Выберите новое местоположение персоны.");
                                        creation.setInputLocation(br, updatePers, readyLocations);
                                        break;
                                    case (7):
                                        System.out.println("Введите новые координаты персоны.");
                                        creation.setInputCoordinates(br, updatePers);
                                        break;
                                }
                            } else System.out.println("Введите число от 1 до 7 или enter, чтобы окончить изменения.");
                        } else {
                            return updatePers;
                        }
                    } catch (Exception e) {
                        System.out.println("Неверный формат ввода. Введите число или enter, чтобы окончить изменения.");
                    }
                } while (true);
            }
        }
    }

    public Person setUpdate(List<String> args) {
        return updatePerson(args);
    }
}
