import javax.sound.sampled.*;
import java.io.*;
import java.net.*;

public class AudioReceiver {
    public static void main(String[] args) {
        try {
            // Create a server socket to listen for incoming connections
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Waiting for a connection...");

            // Accept a client connection
            Socket socket = serverSocket.accept();
            System.out.println("Client connected.");

            // Playback audio to speakers
            AudioFormat format = new AudioFormat(44100, 16, 2, true, true);
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine speakers = (SourceDataLine) AudioSystem.getLine(info);
            speakers.open(format);
            speakers.start();

            // Get the input stream to receive audio data
            InputStream in = socket.getInputStream();

            System.out.println("Receiving audio...");

            byte[] buffer = new byte[1024];
            int bytesRead;

            // Continuously receive and play audio data
            while ((bytesRead = in.read(buffer, 0, buffer.length)) != -1) {
                speakers.write(buffer, 0, bytesRead);
            }

            // Close the speakers, input stream, and socket when done
            speakers.close();
            in.close();
            socket.close();
            serverSocket.close();
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
