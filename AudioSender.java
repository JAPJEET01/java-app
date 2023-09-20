import javax.sound.sampled.*;
import java.io.*;
import java.net.*;

public class AudioSender {
    public static void main(String[] args) {
        try {
            // Capture audio from the microphone
            AudioFormat format = new AudioFormat(44100, 16, 2, true, true);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);
            microphone.start();

            // Create a socket to connect to the receiver
            Socket socket = new Socket("192.168.", 12345); // Replace with the receiver's IP address

            // Get the output stream to send audio data
            OutputStream out = socket.getOutputStream();

            System.out.println("Sending audio...");

            byte[] buffer = new byte[1024];
            int bytesRead;

            // Continuously read audio data and send it
            while ((bytesRead = microphone.read(buffer, 0, buffer.length)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

            // Close the microphone and socket when done
            microphone.close();
            out.close();
            socket.close();
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
