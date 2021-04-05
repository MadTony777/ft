package FTtransport;

import org.apache.commons.lang3.RandomStringUtils;


//import org.apache.logging.log4j.Level;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BaseClass {
    private String arg = System.getProperty("arg", "test");
    String environment = arg;

    private String generatedString = RandomStringUtils.random(9, false, true);
    int randomCid = Integer.parseInt(generatedString);

    public static Logger log = LoggerFactory.getLogger(UnitTests.class);
//    static final Logger logger = LogManager.getLogger(UnitTests.class);
//    static final Level FTINFO = Level.toLevel("FTINFO");



    private static String urldmzSTAGE = "192.168.217.137:9092";
    private static String urllanSTAGE = "192.168.70.143:9092";
    private static String urldmzTEST = "192.168.217.93:9092";
    private static String urllanTEST = "192.168.70.100:9092";
    private static String urlswaggerTEST = "http://fs-app-lan-tst.vsk.ru:8083/ft-agent/status/";
    private static String urlswaggerSTAGE = "http://fs-app-lan-stg.vsk.ru:8083/ft-agent/status/";



    public static List<String> getValuesForGivenKey(String jsonArrayStr, String key) {
        JSONArray jsonArray = new JSONArray(jsonArrayStr);
        return IntStream.range(0, jsonArray.length())
                .mapToObj(index -> ((JSONObject) jsonArray.get(index)).optString(key))
                .collect(Collectors.toList());
    }

//    public static void pauseMethod() {
//        try {
//            Thread.sleep(50000);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public static void pauseMethod(int time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static String getURL(String environment, String source) {
        String service = null;
        switch (environment) {
            case "test":
                if (source.equals("system-5-dmz") || source.equals("system-2-dmz")) {
                    service = urldmzTEST;
                } else {
                    service = urllanTEST;
                }
                break;
            case "stage":
                if (source.equals("system-5-dmz") || source.equals("system-2-dmz")) {
                    service = urldmzSTAGE;
                } else {
                    service = urllanSTAGE;
                }
        }
        return service;
    }

    public static String getLanURL(String environment) {
        String service = "";
        switch (environment) {
            case "test":
                service = urllanTEST;
                break;
            case "stage":
                service = urllanSTAGE;
        }
        return service;
    }

    public static String getStatusURL(String environment, String trace) {
        String url = "";
        switch (environment) {
            case "test":
                url = urlswaggerTEST;
                break;
            case "stage":
                url = urlswaggerSTAGE;
        }
        return url + trace;
    }
}
