import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Frame {
    int frameNumber;
    boolean isAcknowledged;

    public Frame(int frameNumber) {
        this.frameNumber = frameNumber;
        this.isAcknowledged = false;
    }
}

class Sender {
    ArrayList<Frame> frames;
    int windowSize;
    int sentFrames = 0;

    public Sender(int totalFrames, int windowSize) {
        this.windowSize = windowSize;
        frames = new ArrayList<>();
        for (int i = 0; i < totalFrames; i++) {
            frames.add(new Frame(i));
        }
    }

    public void sendFrames() {
        System.out.println("Sender: Sending frames...");
        for (int i = sentFrames; i < sentFrames + windowSize && i < frames.size(); i++) {
            System.out.println("Sending Frame: " + frames.get(i).frameNumber);
        }
    }

    public void receiveAcknowledgment(int ack) {
        if (ack >= sentFrames && ack < sentFrames + windowSize) {
            frames.get(ack).isAcknowledged = true;
            System.out.println("Acknowledgment received for Frame: " + ack);
            moveWindow();
        } else {
            System.out.println("Sender: Received acknowledgment for an unexpected frame.");
        }
    }

    private void moveWindow() {
        while (sentFrames < frames.size() && frames.get(sentFrames).isAcknowledged) {
            sentFrames++;
        }
    }

    public boolean hasMoreFrames() {
        return sentFrames < frames.size();
    }
}

class Receiver {
    Random random = new Random();

    public int receiveFrames(Sender sender, int windowSize) {
        for (int i = sender.sentFrames; i < sender.sentFrames + windowSize && i < sender.frames.size(); i++) {
            Frame frame = sender.frames.get(i);
            if (random.nextInt(10) < 8) { // 80% chance of success
                System.out.println("Receiver: Received Frame: " + frame.frameNumber);
                return frame.frameNumber;
            } else {
                System.out.println("Receiver: Error in Frame: " + frame.frameNumber);
                break; // Stop and request retransmission
            }
        }
        return -1;
    }
}

public class SlidingWindowProtocol {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter total number of frames to send: ");
        int totalFrames = scanner.nextInt();
        System.out.print("Enter window size: ");
        int windowSize = scanner.nextInt();

        Sender sender = new Sender(totalFrames, windowSize);
        Receiver receiver = new Receiver();

        while (sender.hasMoreFrames()) {
            sender.sendFrames();
            int ack = receiver.receiveFrames(sender, windowSize);
            if (ack != -1) {
                sender.receiveAcknowledgment(ack);
            } else {
                System.out.println("Sender: Retransmitting from Frame: " + sender.sentFrames);
            }
        }
        System.out.println("All frames sent and acknowledged successfully!");
        scanner.close();
    }
}