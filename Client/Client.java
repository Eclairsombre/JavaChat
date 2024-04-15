package Client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;


public class Client{
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    private String pseudo;

    public Client(){
        
    }

    public void createConnection(String ipServeur, int port){
        try {
            clientSocket = new Socket(ipServeur, port);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message){
        out.println(message);
    }

    public String receiveMessage(){
        try {
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Client client = new Client();

        client.createConnection("192.168.228.169",8080);
        while(true){    
            
            client.sendMessage("Hello from client 1");

            System.out.println(client.receiveMessage());
        }
        
        //client.closeConnection();
    }
}
