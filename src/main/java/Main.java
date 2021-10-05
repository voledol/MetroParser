import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static List<MocMetroLine> mocMetro = new ArrayList<>();
    public static  int transitions;
    public static List<Transition[]> conections = new ArrayList<>();

    public static void main(String[] args) throws IOException {

       File file = new File("C:\\Users\\voled\\IdeaProjects\\java_basics\\13_FilesAndNetwork\\homework_13.3\\resourses\\MOSmetro.html");
       Document doc = Jsoup.parse(file, "UTF-8", "www.moscowmap.ru");
        Elements elements = doc.select("div#metrodata");
        Elements stationsList = elements.select("div[data-line]");
        Elements lines = elements.select("span[data-line]");

        for(int  i = 0; i < lines.size(); i++){
            mocMetro.add(new MocMetroLine(lines.get(i).text(),
                    lines.get(i).attr("data-line"),
                    takeStationsList(stationsList.get(i))));
        }

        ObjectMapper mapper = new ObjectMapper();
        String json= "";
        for(int i = 0 ; i < mocMetro.size(); i++) {
            try {
                json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mocMetro);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        takeTransitions(stationsList);
        for(int i = 0 ; i < conections.size(); i++) {
            try {
                json += mapper.writerWithDefaultPrettyPrinter().writeValueAsString(conections);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        toFile(json);
    }
        public static int takeNum(String station){
            Pattern pat = Pattern.compile("[0-9].");
            Matcher mat = pat.matcher(station);
            mat.find();
            int st =  mat.start();
            int end = mat.end();
            return Integer.parseInt(station.substring(st, end).replaceAll("\\.", ""));
        }
        public static String takeName(String station){
            return station.replaceAll("[0-9]{1,2}\\.", "").trim();
        }
        public static HashMap<Integer, String> takeStationsList(Element line){
            HashMap<Integer, String> stationsList = new HashMap<>();
        Elements stations = line.select("a");

            for (Element element : stations) {
                String station = element.text();
                stationsList.put(takeNum(station), takeName(station));
            }
            return stationsList;
        }
        public static void toFile(String json){
            try {
                FileWriter jsonFile = new FileWriter("data.json");
                jsonFile.write(json);
                jsonFile.flush();
                jsonFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public static void takeTransitions(Elements stationsList){
            Elements station = stationsList.select("a");
            String st = "";
            Pattern transition = Pattern.compile("ln-[0-9]{1,2}");
            Pattern destSt = Pattern.compile("(?<=[«])[^»]+");
            String numberOfLine = "";
            String numberOfLineOfDest = "";

            int start;
            int end;

            for (int i = 0; i < station.size();i++ ){
                st = String.valueOf(station.get(i));
                Matcher trMatch = transition.matcher(st);
                Matcher destStMach = destSt.matcher(st);
            if (trMatch.find()) {
                transitions++;
            }
            String nameOfStationOfLine = takeName(station.get(i).text());
            if (destStMach.find()){
                    start = destStMach.start();
                    end = destStMach.end();
                    String nameOfDestinationStation = st.substring(start, end).trim();
                    String[] lines = takeLine(nameOfStationOfLine, nameOfDestinationStation);
                    numberOfLine = lines[0];
                    numberOfLineOfDest = lines[1];
                    Transition[] connection = {new Transition(numberOfLine, nameOfStationOfLine), new Transition(numberOfLineOfDest,nameOfDestinationStation)};
                    conections.add(connection);
            }
            }
            System.out.println("Transitions count " + transitions);
    }
    public static String[] takeLine(String stationOfline, String destStation){
        String[] line = {"" , ""};
        int j = 0;

        for (int i = 0 ; i < mocMetro.size(); i++){
            if (mocMetro.get(i).getStations().containsValue(stationOfline)){
                line[0] = mocMetro.get(i).getNumber();
                j = i;
            }
        }
        for (int v = 0; v < mocMetro.size(); v++){
            if(mocMetro.get(v).getStations().containsValue(destStation)){
                if(v==j){
                    v++;
                }
                else {line[1] = mocMetro.get(v).getNumber();}
            }
        }
        return line;
    }
}