import java.net.*;

public class UDPClient {
    public static void main(String[] args) {
        DatagramSocket socket = null;

        try {
            // Create a DatagramSocket
            socket = new DatagramSocket();

            // Prepare the message to send
            String msg = "Hello, Server!";
            byte[] b = msg.getBytes();

            // Get the server's address (localhost in this case)
            InetAddress host = InetAddress.getByName("127.0.0.1");
            int serverPort = 2500;

            // Create a DatagramPacket to send the request to the server
            DatagramPacket request = new DatagramPacket(b, b.length, host, serverPort);
            socket.send(request); // Send the message

            // Buffer to receive the server's response
            byte[] buffer = new byte[1000];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            socket.receive(reply); // Receive the response from the server

            // Print the response from the server
            System.out.println("Client received: " + new String(reply.getData(), 0, reply.getLength()));

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close(); // Close the socket
            }
        }
    }
}
