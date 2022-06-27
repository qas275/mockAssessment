package vttp2022.mockAssessment;

import java.io.File;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class HttpServer{
    
    public int port;
    public String docRootListString;
    public List<String> docRootList = new LinkedList<>();
    public Socket sock;

    public HttpServer(int portInput, String docRootInput){
        port = portInput;
        System.out.println(port);
        String[] docRootSplit = docRootInput.split(":");
        for (int i = 0;i<docRootSplit.length;i++){
            docRootList.add(docRootSplit[i]);
            System.out.printf("%d. %s\n",i,docRootSplit[i]);
        }
    }


    public void start(){
        for(int i=0;i<docRootList.size();i++){
            File docRootFile = new File(docRootList.get(i));
            if (!docRootFile.exists()){
                System.out.println("Path does not exist");
                System.exit(1);
            }
            if (!docRootFile.isDirectory()){
                System.out.println("Path is not a directory");
                System.exit(1);
            }
            if(!docRootFile.canRead()){
                System.out.println("Path cannot be read");
                System.exit(1);
            }
            System.out.printf("%s directory exists and can be read\n",docRootList.get(i));
        }
    }
    
}

