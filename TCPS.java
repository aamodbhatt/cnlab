import java.io.*;
import java.net.*;

public class TCPS {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        BufferedReader fileReader = null;
        PrintWriter out = null;
        
        try {
            serverSocket = new ServerSocket(4000);
            System.out.println("Server ready for connection...");
            
            // Accept client connection
            clientSocket = serverSocket.accept();
            System.out.println("Connection successful. Waiting for file request...");
            
            // Input stream from the client
            BufferedReader clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String fileName = clientReader.readLine(); // Read the filename from client
            
            // Open the file requested by the client
            File file = new File(fileName);
            if (!file.exists()) {
                // If file doesn't exist, send an error message to the client
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println("ERROR: File not found");
                System.out.println("File not found: " + fileName);
            } else {
                // Send the file contents to the client
                fileReader = new BufferedReader(new FileReader(file));
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                String line;
                while ((line = fileReader.readLine()) != null) {
                    out.println(line); // Send each line of the file to client
                }
            }
            
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        } finally {
            try {
                if (clientSocket != null) clientSocket.close();
                if (serverSocket != null) serverSocket.close();
                if (fileReader != null) fileReader.close();
                if (out != null) out.close();
            } catch (IOException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}