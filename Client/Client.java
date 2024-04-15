package Client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Client {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    private String pseudo;

    public Client() {
        System.out.print("Your name : ");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        String inputString = "";
        try {
            inputString = bufferRead.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.pseudo  = inputString;
    }

    public void createConnection(String ipServeur, int port) {
        try {
            clientSocket = new Socket(ipServeur, port);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (UnknownHostException e) {
            System.out.print("Can't connect to the server...");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.print("Can't connect to the server...");
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public String receiveMessage() {
        try {
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String askForSms() {
        System.out.print(this.pseudo + " : ");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        String inputString = "";
        try {
            inputString = bufferRead.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputString;
    }

    public static void main(String[] args) {

        Client client = new Client();

        client.createConnection("192.168.228.169", 8080);
        while (true) {
            String message = client.pseudo + " : " + client.askForSms();
            new Thread(
                () -> {
                    
                        client.sendMessage(message);
                    
                }
            ).start();
            
            client.sendMessage(message);
            new Thread(null, () -> {
                
                        System.out.println(client.receiveMessage());
                    
                
            }).start();

            


        }
    }

}
