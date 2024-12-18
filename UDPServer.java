import java.net.*;
import java.util.*;

public class UDPServer {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        Scanner sc = new Scanner(System.in);
        

        try {
            // Create a DatagramSocket that listens on port 2400
            socket = new DatagramSocket(2500);
            byte[] buffer = new byte[1000];

            while (true) {
                // Create a DatagramPacket to receive the incoming request
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                socket.receive(request); // Receive the request

                // Read the message from the console (server-side)
                System.out.print("Enter message to send back to client: ");
                String message = sc.nextLine();

                // Convert the message to a byte array
                byte[] sendMsg = message.getBytes();

                // Create a DatagramPacket to send the response back to the clientdddwdw
                DatagramPacket reply = new DatagramPacket(sendMsg, sendMsg.length, request.getAddress(), request.getPort());
                socket.send(reply); // Send the response
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close(); // Close the socket
            }
        }
    }
}
