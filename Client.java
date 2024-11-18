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

    public class handshake() {

    }

    public class request() {

    }

    public class disconnect() {

    }

    public class getSocket() {

    }

}