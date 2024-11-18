import java.util.*;
import java.net.*;
import java.io.*;

public class Client{
    public Socket socket;
    public PrintWriter output;
    public BufferedReader input;

    //constructor
    public Client(String host, int port) {
        this.socket = new Socket(host,port);
        this.output = new PrintWriter(socket.getOutputStream());
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void handshake() {
        output.println("12345");        //client sends this key for validation
    }

    //sends and recieves messages
    public String request(int number) {
        output.println(number);             //sends number to the server
        return input.readLine();            //returns servers response
    }

    //disconnects input, output, and socket
    public void disconnect() {
        output.close();
        input.close();
        socket.close();
    }

    //getter
    public Socket getSocket() {
        return socket;
    }

}