import java.util.*;
import java.net.*;
import java.io.*;

public class Client{
    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;

    //constructor
    public Client(String host, int port) throws IOException{
        this.socket = new Socket(host,port);
        this.output = new PrintWriter(socket.getOutputStream());
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void handshake() {
        output.println("12345");        //client sends this key for validation
        output.flush();
    }

    //sends and recieves messages
    public String request(String number) throws IOException {
        output.println(number);             //sends number to the server
        output.flush();
        return input.readLine();            //returns servers response
    }

    //disconnects input, output, and socket
    public void disconnect() throws IOException {
        output.close();
        input.close();
        socket.close();
    }

    //getter
    public Socket getSocket() {
        return socket;
    }

}