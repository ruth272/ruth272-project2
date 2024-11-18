import java.util.*;
import java.net.*;
import java.time.LocalDateTime;
import java.io.*;

public class Server{
    private ServerSocket serverSocket;
    private int port;
    private ArrayList<LocalDateTime> connectedTimes = new ArrayList<>();

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
                Socket clientSock = serverSocket.accept();
                Thread t =new Thread(new ClientHandler(clientSock));
                t.start();
            }
            //send error if failed
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private class ClientHandler extends Thread{
        private Socket socket;

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
                if(!msg.equals("12345")) {
                    out.println("couldn't handshake");
                    out.flush(); 
                    socket.close();
                    return;
                }
                //out.close();
                //in.close();
                //socket.close();
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
                        out.println("There was an exception on the server");
                        out.flush();
                    }
                }
                
            }
            catch(Exception e){
            e.printStackTrace();
            }
        }
        
        //returns count of many numbers can be factorized
        private int calculatedFactors(int number) {
            int count = 0;
            for(int i=1; i <= number; i++) {
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
            if(serverSocket != null){
                serverSocket.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}