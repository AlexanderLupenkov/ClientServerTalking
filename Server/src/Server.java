import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;


public class Server {
    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(8000)) {

            System.out.println("Server started");

            while (true)
                try (
                        Socket socket = server.accept();

                        BufferedWriter streamWriter =
                                new BufferedWriter(
                                        new OutputStreamWriter(
                                                socket.getOutputStream()));

                        BufferedReader streamReader =
                                new BufferedReader(
                                        new InputStreamReader(
                                                socket.getInputStream()))
                ) {
                    streamWriter.write("Hello! You're connected to WeatherServer. Enter city name: ");
                    streamWriter.newLine();
                    streamWriter.flush();

                    String requestLocation = streamReader.readLine();
                    String infoJSON = getURLContent("http://api.openweathermap.org/data/2.5/weather?q=" + requestLocation + "&appid=92ce4ca7ebfc9888c099a769d30dda86&units=metric");

                    JSONObject jOb = new JSONObject(infoJSON);

                    String[] weatherInfo = new String[]{
                            "Temperature: " + jOb.getJSONObject("main").getDouble("temp") + " °C",

                            "Feels like: " + jOb.getJSONObject("main").getDouble("feels_like") + " °C",

                            "Max: " + jOb.getJSONObject("main").getDouble("temp_max") + " °C",

                            "Min: " + jOb.getJSONObject("main").getDouble("temp_min") + " °C",

                            "Pressure: " + jOb.getJSONObject("main").getDouble("pressure") + " millimeters of mercury",

                            "Wind speed: " + jOb.getJSONObject("wind").getDouble("speed") + " m/s"
                    };

                    streamWriter.write("Location: " + requestLocation);
                    streamWriter.newLine();
                    streamWriter.flush();

                    for (String str : weatherInfo) {
                        streamWriter.write(str);
                        streamWriter.newLine();
                        streamWriter.flush();
                    }

                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static String getURLContent(String URLAddress) {

        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(URLAddress);
            URLConnection urlConnection = url.openConnection();

            BufferedReader urlReader = new BufferedReader(new InputStreamReader((urlConnection.getInputStream())));

            String str;
            while ((str = urlReader.readLine()) != null) {
                content.append(str + "\n");
            }

            urlReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
