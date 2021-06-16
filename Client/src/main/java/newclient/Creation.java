package newclient;

import lombok.AllArgsConstructor;
import other.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Creation {

    private String name;
    private String height;
    private String weight;
    private String passport;
    private String hair;
    private String x;
    private String y;
    private String location;
    private String nameLoc;
    private String xLoc;
    private String yLoc;
    private String zLoc;
    private ServerResponse answer;
    private Person newPerson;
    private Map<Integer, Location> readyLocations;

    public Creation(String name, String height, String weight, String passport, String hair, String x, String y, String location, String nameLoc, String xLoc, String yLoc, String zLoc) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.passport = passport;
        this.hair = hair;
        this.x = x;
        this.y = y;
        this.location = location;
        this.nameLoc = nameLoc;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        this.zLoc = zLoc;
    }

    public ServerResponse createNewPerson(Map<Integer, Location> readyLocations) {
        this.readyLocations = readyLocations;
        newPerson = new Person();
        inputName();
        if (answer != null) return answer;
        inputHeight();
        if (answer != null) return answer;
        inputWeight();
        if (answer != null) return answer;
        inputPassport();
        if (answer != null) return answer;
        inputHairColor();
        if (answer != null) return answer;
        inputLocation();
        if (answer != null) return answer;
        inputCoordinates();
        if (answer != null) return answer;
        List<Person> toAddPerson = new LinkedList<>();
        toAddPerson.add(newPerson);
        answer = ServerResponse.builder().personList(toAddPerson).build();
        return answer;
    }

    private void inputName() {
        try {
            boolean hasNoDigit = validateName(name);
            if (name.equals(""))
                answer = ServerResponse.builder().error("Нельзя ввести пустую строку в это поле, пожалуйста, введите данные.").build();
            else if (!hasNoDigit)
                answer = ServerResponse.builder().error("В имени не могут содержаться цифры и спец. знаки. Проверьте,что в веденной строке они отсутствуют и введите имя еще раз.").build();
            else {
                newPerson.setName(name);
            }
        } catch (Exception e) {
            answer = ServerResponse.builder().error("Неверный формат ввода, введите имя еще раз.").build();
        }
    }

    private void inputHeight() {
        try {
            if (!height.isEmpty()) {
                long hLong = Long.parseLong(height);
                if (hLong <= 0) {
                    answer = ServerResponse.builder().error("Рост не может быть меньше нуля, пожалуйста, введите рост еще раз.").build();
                } else {
                    newPerson.setHeight(hLong);
                }
            }
        } catch (Exception e) {
            answer = ServerResponse.builder().error("Неверный формат ввода роста, введите число или ничего нн вводите.").build();
        }
    }

    private void inputWeight() {
        try {
            if (!weight.isEmpty()) {
                long wLong = Long.parseLong(weight);
                if (wLong <= 0) {
                    answer = ServerResponse.builder().error("Вес не может быть меньше нуля, пожалуйста, введите вес еще раз.").build();
                } else {
                    newPerson.setWeight(wLong);
                }
            }
        } catch (Exception e) {
            answer = ServerResponse.builder().error("Неверный формат ввода веса, введите число или ничего не вводите.").build();
        }
    }

    private void inputPassport() {
        try {
            boolean hasNoLetter = validatePassport(passport);
            if (passport.equals(""))
                answer = ServerResponse.builder().error("Паспорт не может быть пустой строкой, пожалуйста, введите данные.").build();
            else if (passport.length() > 27 || passport.length() < 10)
                answer = ServerResponse.builder().error("Паспортные данные не подходят по длине, введите данные снова (10-27 цифр).").build();
            else if (!hasNoLetter)
                answer = ServerResponse.builder().error("Паспортные данные должны состоять только из цифр, введите данные снова.").build();
            else {
                newPerson.setPassportID(passport);
            }
        } catch (Exception e) {
            answer = ServerResponse.builder().error("Неверный формат ввода, введите паспортные данные ещё раз").build();
        }
    }

    private void inputHairColor() {
        Color hairColor = null;
        try {
            if (hair.equals(""))
                answer = ServerResponse.builder().error("Выберете цвет волос, это обязательное поле.").build();
            else {
                hairColor = Color.valueOf(hair);
                newPerson.setHairColor(hairColor);
            }
        } catch (Exception e) {
            answer = ServerResponse.builder().error("Попробуйте ввести цвет волос ещё раз.").build();
        }
    }

    private void inputLocation() {
        if (location.equals("Новая локация")) {
            Location newLocation = inputNewLocation();
            if (newLocation != null) {
                newPerson.setLocation(newLocation);
            }
        } else {
            for (Location l : readyLocations.values()) {
                if (location.equals(l.getName())) {
                    newPerson.setLocation(l);
                    break;
                }
            }
        }
    }

    private Location inputNewLocation() {
        Location newLocation = new Location();
        int x;
        float y;
        double z;
        try {
            if (xLoc.isEmpty() || yLoc.isEmpty() || zLoc.isEmpty()) {
                answer = ServerResponse.builder().error("Все координаты нового места должны быть заполнены, пожалуйста, введите числа.").build();
                return null;
            } else {
                x = Integer.parseInt(xLoc);
                y = Float.parseFloat(yLoc);
                z = Double.parseDouble(zLoc);
                newLocation.setLocation(x, y, z, nameLoc);
                return newLocation;
            }
        } catch (NumberFormatException e) {
            answer = ServerResponse.builder().error("Неверный формат ввода, введите координаты места еще раз. Проверьте,что в веденной строке отсутствуют буквы.").build();
            return null;
        }
    }

    private void inputCoordinates() {
        Coordinates c = new Coordinates();
        if (x.isEmpty() || y.isEmpty()) {
            answer = ServerResponse.builder().error("Все координаты человека должны быть заполнены, пожалуйста, введите числа.").build();
        } else {
            try {
                float newX = Float.parseFloat(x);
                double newY = Double.parseDouble(y);
                c.setCoordinatesFirst(newX, newY);
                newPerson.setCoordinates(c);
            } catch (NumberFormatException e) {
                answer = ServerResponse.builder().error("Неверный формат ввода, введите координаты человека еще раз. Проверьте,что в веденной строке отсутствуют буквы.").build();
            }
        }
    }


    /**
     * Метод - валидация на отсутствие цифр и спец символов в строке
     *
     * @return boolean true/false
     */
    private boolean validateName(String name) {
        Pattern pattern = Pattern.compile("^[\\p{L} ]+$");
        Matcher m = pattern.matcher(name);
        return m.matches();
    }

    /**
     * Метод - валидация на отсутствие букв в строке
     *
     * @return boolean true/false
     */
    private boolean validatePassport(String pass) {
        Pattern pattern = Pattern.compile("^[0-9]+$");
        Matcher m = pattern.matcher(pass);
        return m.matches();
    }
}
