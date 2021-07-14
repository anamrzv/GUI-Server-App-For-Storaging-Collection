package newclient;

import gui.GUIMain;
import lombok.Getter;
import other.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class Creation {

    private final String name;
    private final String height;
    private final String weight;
    private final String passport;
    private final String hair;
    private final String x;
    private final String y;
    private final String location;
    private final String nameLoc;
    private final String xLoc;
    private final String yLoc;
    private final String zLoc;
    private ServerResponse responseWithError;
    private Person newPerson;
    private Map<Integer, Location> readyLocations;
    private final ClientHandler clientHandler = ClientHandler.getInstance(GUIMain.port);

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
        if (responseWithError != null) return responseWithError;
        inputHeight();
        if (responseWithError != null) return responseWithError;
        inputWeight();
        if (responseWithError != null) return responseWithError;
        inputPassport();
        if (responseWithError != null) return responseWithError;
        inputHairColor();
        if (responseWithError != null) return responseWithError;
        inputLocation();
        if (responseWithError != null) return responseWithError;
        inputCoordinates();
        if (responseWithError != null) return responseWithError;
        List<Person> toAddPerson = new LinkedList<>();
        toAddPerson.add(newPerson);
        return ServerResponse.builder().personList(toAddPerson).build();
    }

    private void inputName() {
        try {
            boolean hasNoDigit = validateName(name);
            if (name.equals(""))
                responseWithError = ServerResponse.builder().error("name error empty").build();
            else if (!hasNoDigit)
                responseWithError = ServerResponse.builder().error("name error validate").build();
            else {
                newPerson.setName(name);
            }
        } catch (Exception e) {
            responseWithError = ServerResponse.builder().error("name error input").build();
        }
    }

    private void inputHeight() {
        try {
            if (!height.isEmpty()) {
                long hLong = Long.parseLong(height);
                if (hLong < 0) {
                    responseWithError = ServerResponse.builder().error("height error number").build();
                } else {
                    newPerson.setHeight(hLong);
                }
            }
        } catch (Exception e) {
            responseWithError = ServerResponse.builder().error("height error input").build();
        }
    }

    private void inputWeight() {
        try {
            if (!weight.isEmpty()) {
                long wLong = Long.parseLong(weight);
                if (wLong < 0) {
                    responseWithError = ServerResponse.builder().error("weight error number").build();
                } else {
                    newPerson.setWeight(wLong);
                }
            }
        } catch (Exception e) {
            responseWithError = ServerResponse.builder().error("weight error input").build();
        }
    }

    private void inputPassport() {
        try {
            boolean hasNoLetter = validatePassport(passport);
            if (passport.equals(""))
                responseWithError = ServerResponse.builder().error("passport error empty").build();
            else if (passport.length() > 27 || passport.length() < 10)
                responseWithError = ServerResponse.builder().error("passport error length").build();
            else if (!hasNoLetter)
                responseWithError = ServerResponse.builder().error("passport error validate").build();
            else {
                newPerson.setPassportID(passport);
            }
        } catch (Exception e) {
            responseWithError = ServerResponse.builder().error("passport error input").build();
        }
    }

    private void inputHairColor() {
        Color hairColor;
        try {
            if (hair.equals(""))
                responseWithError = ServerResponse.builder().error("hair error empty").build();
            else {
                hairColor = Color.valueOf(hair);
                newPerson.setHairColor(hairColor);
            }
        } catch (Exception e) {
            responseWithError = ServerResponse.builder().error("hair error input").build();
        }
    }

    private void inputLocation() {
        if (location.equals(clientHandler.getEncodedBundleString("new location"))) {
            Location newLocation = inputNewLocation();
            if (newLocation != null) {
                newPerson.setLocation(newLocation);
                readyLocations.put(readyLocations.size() + 1, newLocation);
            }
        } else {
            for (Location l : clientHandler.getReadyLocations().values()) {
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
                responseWithError = ServerResponse.builder().error("location error empty").build();
                return null;
            } else {
                x = Integer.parseInt(xLoc);
                y = Float.parseFloat(yLoc);
                z = Double.parseDouble(zLoc);
                newLocation.setLocation(x, y, z, nameLoc);
                return newLocation;
            }
        } catch (NumberFormatException e) {
            responseWithError = ServerResponse.builder().error("location error input").build();
            return null;
        }
    }

    private void inputCoordinates() {
        Coordinates c = new Coordinates();
        if (x.isEmpty() || y.isEmpty()) {
            responseWithError = ServerResponse.builder().error("coordinates error empty").build();
        } else {
            try {
                float newX = Float.parseFloat(x);
                double newY = Double.parseDouble(y);
                c.setCoordinatesFirst(newX, newY);
                newPerson.setCoordinates(c);
            } catch (NumberFormatException e) {
                responseWithError = ServerResponse.builder().error("coordinates error input").build();
            }
        }
    }

    private boolean validateName(String name) {
        Pattern pattern = Pattern.compile("^[\\p{L} ]+$");
        Matcher m = pattern.matcher(name);
        return m.matches();
    }

    private boolean validatePassport(String pass) {
        Pattern pattern = Pattern.compile("^[0-9]+$");
        Matcher m = pattern.matcher(pass);
        return m.matches();
    }
}
