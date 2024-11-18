import java.util.*;
import java.net.*;
import java.time.LocalDateTime;
import java.io.*;

public class Server{
    public ServerSocket serverSocket;
    public int port;
    public ArrayList<LocalDateTime> connectedTimes = new ArrayList<>();

    //constructor
    public Server(int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
    }

    // loops for the amount of clients in testcase
    // process client request in a seperate thread
    public void serve(int clients) {
        for (int i=0; i < clients; i++){
            //accepts new client connect, read passcode, record connection time
            try {
                Socket socket = serverSocket.accept();
                //ClientHandler clientHandler = new ClientHandler(socket);
                (new ClientHandler(socket)).start();
            }
            //send error if failed
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public class ClientHandler extends Thread{
        Socket socket;

        public ClientHandler(Socket socket){
            this.socket = socket;
        }

        public void run(){
            PrintWriter out = null;
            BufferedReader in = null;
            try{
                out = new PrintWriter(socket.getOutputStream());
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String msg = in.readLine();
                if(msg != "12345") {
                    out.println("couldn't handshake");
                    return; 
                }
                connectedTimes.add(LocalDateTime.now());
                String request = in.readLine();
                
                if(request != null) {
                    try{
                        int number = Integer.parseInt(request);         //parses string and turns it into int
                        int factors = calculatedFactors(number);
                        out.println("The number " + number + " has " + factors + " factors");
                        out.flush();
                    }
                    catch (Exception e) {
                        out.println("There was an exception on the server");//if parsing fials 
                        out.flush();//any buffered data will be written to it 
                    }
                }
                
            }
            catch(Exception e){
            e.printStackTrace();
            }
        }
        
        //returns count of many numbers can be factorized
        public int calculatedFactors(int number) {
            int count = 0;
            for(int i=0; i <= number; i++) {
                if(number % i == 0) {
                    count ++;
                }
            }
            return count;
        }
    }

    //array list of client connected times
    public ArrayList<LocalDateTime> getConnectedTimes() {
        return new ArrayList<>(connectedTimes);
    }

    //disconnects serversocket
    public void disconnect() {
        try{
            serverSocket.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}