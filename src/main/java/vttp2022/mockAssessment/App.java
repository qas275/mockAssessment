package vttp2022.mockAssessment;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) throws IOException
    {
        int portdefault = 3000;
        String docRootdefault = "./static";
        boolean stop = false;
        for (int i=0;i<(args.length);i++){
            if(args[i].equals("--port")){
                portdefault = Integer.parseInt(args[i+1]);
                System.out.println(portdefault);
            }
            if(args[i].equals("--docRoot")){
                docRootdefault = args[i+1];
                System.out.println(docRootdefault);
            }
        }
        System.out.printf("Port is %d and docRoot is %s\n",portdefault,docRootdefault);

        HttpServer Server = new HttpServer(portdefault,docRootdefault); //create new server with given port and docRoots
        System.out.printf("Server variables initiated, starting server at port %d.\n",portdefault);
        Server.start(); //check given docRoots as required
        System.out.println("Server port and resource checked, limiting server to 3 threads");
        ExecutorService threadPool = Executors.newFixedThreadPool(3); // limit server to 3 threads
        ServerSocket serversocket = new ServerSocket(portdefault); //initiate server socket to given port, cannot be inside while loop because will keep initiating and restart server socket
        //serversocket is a socket created specifically for server to listen only and continuously 
        while(!stop){
            System.out.println("Waiting for connection\n");
            Socket sock = serversocket.accept(); //takes a connection when made to create a new socket connected to the client, while serversocket continues listening for other connections
            HttpClientConnection clientHandlingServer = new HttpClientConnection(sock, Server.docRootList);//this creates a new client handler with the given variables only 
            System.out.println("Connected...\n");
            threadPool.submit(clientHandlingServer); //submit to threadpool to start runnable function in the class
            System.out.println("submited to threadpool");
        }
        serversocket.close();
    }
}
