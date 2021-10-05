import java.util.HashMap;

public class Transition {
    private String line = "";
    private String station = "";

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public Transition(String line, String station) {
        this.line = line;
        this.station = station;
    }
}