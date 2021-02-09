package jsonParser;

import org.json.JSONException;
import org.json.JSONObject;


public class GetJSONInfo {

    public static String[] parseInfo(String JSONtoString) throws NullPointerException {

        String[] info = null;

        try {
            JSONObject jOb = new JSONObject(JSONtoString);

            info = new String[]{
                    "Temperature: " + jOb.getJSONObject("main").getDouble("temp") + " °C",

                    "Feels like: " + jOb.getJSONObject("main").getDouble("feels_like") + " °C",

                    "Max: " + jOb.getJSONObject("main").getDouble("temp_max") + " °C",

                    "Min: " + jOb.getJSONObject("main").getDouble("temp_min") + " °C",

                    "Pressure: " + jOb.getJSONObject("main").getDouble("pressure") + " millimeters of mercury",

                    "Wind speed: " + jOb.getJSONObject("wind").getDouble("speed") + " m/s"
            };
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return info;
    }
}