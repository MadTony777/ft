package FTtransport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class FTstatusGET extends BaseClass{

    static String statusMethod(String trace, String virus, String environment) throws Exception {
        Logger log = LoggerFactory.getLogger(UnitTests.class);
        String url = getStatusURL(environment, trace);
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        log.info("Sending 'GET' request to URL : " + url);
        log.info("Response Code : " + responseCode);
        String result = "";
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            String part = response.toString();
            String full = "[" + part + "]";
            List<String> cs;
            switch (virus){
                case "virus":
                    cs = getValuesForGivenKey(full, "description");
                    result = String.valueOf(cs);
                    log.info(result);
                    break;
                case "no":
                    cs = getValuesForGivenKey(full, "currentState");
                    log.info("Result:" + cs + "\n");
                    result = String.valueOf(cs);
                    break;
                }
        }
        return result;
    }
}