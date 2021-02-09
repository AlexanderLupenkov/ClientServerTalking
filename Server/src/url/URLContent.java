package url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class URLContent {
    public static String getURLContent(String URLAddress) {

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

        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}