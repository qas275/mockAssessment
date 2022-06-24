package vttp2022.mockAssessment;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class HttpServer {
    
    public int port;
    public String docRootListString;
    public List<String> docRootList = new LinkedList<>();
    
    //TODO parseint the input from main somewhere here in constructor or in start method or App
    public HttpServer(){
        port = 3000;
        docRootListString = "./target";
        docRootList.add(docRootListString);
    }

    public HttpServer(int portInput){
        port = portInput;
        docRootListString = "./target";
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
        }
    }


    public void start()throws IOException{
        ServerSocket serversocket = new ServerSocket(port);
        System.out.println("Waiting for connection");
        Socket sock = serversocket.accept();
        for(int i=0;i<docRootList.size();i++){
            
        }
    }
}
