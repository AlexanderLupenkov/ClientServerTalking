import jsonParser.GetJSONInfo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import exceptions.WrongLocationException;
import interfaces.IConstants;
import url.URLContent;


public class Server implements IConstants {

    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(PORT_NUMBER)) {

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

                    try {
                        String JSONInfoToString = URLContent.getURLContent(URL_ADDRESS_PART_ONE + requestLocation + URL_ADDRESS_PART_TWO);

                        String[] weatherConditions = GetJSONInfo.parseInfo(JSONInfoToString);

                        if (weatherConditions == null)
                            throw new WrongLocationException("Wrong location name.");

                        streamWriter.write("Location: " + requestLocation);
                        streamWriter.newLine();
                        streamWriter.flush();

                        for (String str : weatherConditions) {
                            streamWriter.write(str);
                            streamWriter.newLine();
                            streamWriter.flush();
                        }
                    } catch (WrongLocationException ex) {
                        ex.printStackTrace();
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}