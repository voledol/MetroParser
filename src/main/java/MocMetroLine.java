import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MocMetroLine{
    private String name;
    private String number;
    private HashMap<Integer, String> stations = new HashMap<>();

    public MocMetroLine(String name, String number, HashMap<Integer, String> stations) {
        this.name = name;
        this.number = number;
        this.stations = stations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public HashMap<Integer, String> getStations() {
        return stations;
    }

    public void setStations(HashMap<Integer, String> stations) {
        this.stations = stations;
    }
/**
    public String toString() {
        StringBuilder line = new StringBuilder();
        line = line.append("номер линии:" + getNumber()+ "\n").append("Линия:" + getName() + "\n");
        for (Map.Entry<Integer, String> pair : getStations().entrySet()) {
            Integer key = pair.getKey();
            String value = pair.getValue();
            line.append("\t" + "номер станции:" + key+ "\n").append("\t" + "Станция:" + value + "\n");
        }
        return String.valueOf(line);
    }*/
}