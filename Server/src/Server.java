import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(8000)) {

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
                    streamWriter.write("Congratulations! You're connected to local server. Enter your message:");
                    streamWriter.newLine();
                    streamWriter.flush();

                    String request = streamReader.readLine();
                    System.out.println(request);

                    streamWriter.write("Im get your message: " + request);
                    streamWriter.newLine();
                    streamWriter.flush();

                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
