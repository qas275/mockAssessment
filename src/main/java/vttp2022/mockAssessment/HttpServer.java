package vttp2022.mockAssessment;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class HttpServer{
    
    public static int port;
    public String docRootListString;
    public List<String> docRootList = new LinkedList<>();
    public Socket sock;
    
    //TODO parseint the input from main somewhere here in constructor or in start method or App
    public HttpServer(){
        port = 3000;
        docRootListString = "./static";
        docRootList.add(docRootListString);
    }

    public HttpServer(int portInput){
        port = portInput;
        docRootListString = "./static";
        docRootList.add(docRootListString);
    }

    public HttpServer(String docRootInput){
        port = 3000;
        String[] docRootSplit = docRootInput.split(":");
        for (int i = 0;i<docRootSplit.length;i++){
            docRootList.add(docRootSplit[i]);
        }
    }

    public HttpServer(int portInput, String docRootInput){
        port = portInput;
        String[] docRootSplit = docRootInput.split(":");
        for (int i = 0;i<docRootSplit.length;i++){
            docRootList.add(docRootSplit[i]);
            System.out.printf("%d. %s\n",i,docRootSplit[i]);
        }
    }


    public void start()throws IOException{
        for(int i=0;i<docRootList.size();i++){
            File docRootFile = new File(docRootList.get(i));
            if (!docRootFile.exists()){
                System.out.println("Path does not exist");
                sock.close();
                System.exit(1);
            }
            if (!docRootFile.isDirectory()){
                System.out.println("Path is not a directory");
                sock.close();
                System.exit(1);
            }
            if(!docRootFile.canRead()){
                System.out.println("Path cannot be read");
                sock.close();
                System.exit(1);
            }
            
        }
    }
    
    public void listen()throws IOException{
        ServerSocket serversocket = new ServerSocket(port);
        System.out.println("Waiting for connection\n");
        sock = serversocket.accept();
        System.out.println("Connected...\n");
    }
    
    
}

