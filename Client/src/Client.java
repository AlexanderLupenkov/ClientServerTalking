import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (
                Socket socket = new Socket("127.0.0.1", 8000);

                BufferedWriter writer =
                        new BufferedWriter(
                                new OutputStreamWriter(
                                        socket.getOutputStream()));

                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(
                                        socket.getInputStream()))
        ) {
            String response = reader.readLine();
            System.out.println(response);

            Scanner scan = new Scanner(System.in);
            String request = scan.nextLine();

            writer.write(request);
            writer.newLine();
            writer.flush();

            while (response != null) {
                response = reader.readLine();
                if (response != null)
                    System.out.println(response);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

