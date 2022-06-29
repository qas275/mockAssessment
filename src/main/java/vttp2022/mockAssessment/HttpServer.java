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
        System.out.printf("Port number %d requested\n",port); //assign given port number to port here
        String[] docRootSplit = docRootInput.split(":"); //split given directories by :
        for (int i = 0;i<docRootSplit.length;i++){ 
            docRootList.add(docRootSplit[i]); //add directories into list here
            System.out.printf("Directory %d. %s requested\n",i+1,docRootSplit[i]);
        }
    }


    public void start(){
        for(int i=0;i<docRootList.size();i++){ //for each directory stated on startup
            File docRootFile = new File(docRootList.get(i)); //create File 
            if (!docRootFile.exists()){ //check if file exists
                System.out.println("Path does not exist");
                System.exit(1);
            }
            if (!docRootFile.isDirectory()){ //is a directory
                System.out.println("Path is not a directory");
                System.exit(1);
            }
            if(!docRootFile.canRead()){ //can be read
                System.out.println("Path cannot be read");
                System.exit(1);
            }
            System.out.printf("%s exists, is a directory and can be read\n",docRootList.get(i));
        }
    }   
}

