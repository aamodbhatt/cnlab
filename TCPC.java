import java.io.*;
import java.net.*;

public class TCPC {
    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader keyReader = null;
        BufferedReader socketReader = null;
        PrintWriter out = null;
        
        try {
            // Connect to the server on localhost:4000
            socket = new Socket("127.0.0.1", 4000);
            System.out.println("Enter the filename:");

            keyReader = new BufferedReader(new InputStreamReader(System.in));
            String fileName = keyReader.readLine(); // Read filename from the user
            
            // Send the filename to the server
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println(fileName);
            
            // Read the file content sent by the server
            socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            while ((line = socketReader.readLine()) != null) {
                System.out.println(line); // Print file content or error message
            }
            
        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
        } finally {
            try {
                if (socket != null) socket.close();
                if (keyReader != null) keyReader.close();
                if (socketReader != null) socketReader.close();
                if (out != null) out.close();
            } catch (IOException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}