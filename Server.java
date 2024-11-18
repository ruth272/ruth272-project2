import java.util.*;
import java.net.*;
import java.io.*;

public class Server{
    public ServerSocket serverSocket;
    public int port;

    //constructor
    public Server(int port) {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
    }

    // loops for the amount of clients in testcase
    // process client request in a seperate thread
    public void serve(int clients) {
        for (int i=0; i < clients; i++){
            //accepts new client connect, read passcode, record connection time
            try {

            }
            //send error if failed
            catch {

            }
        }
    }

    //array list of client connected times
    public class getConnectedTimes() {

    }

    //disconnects serversocket
    public void disconnect() {
        serverSocket.close();
    }
}