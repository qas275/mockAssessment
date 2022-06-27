package vttp2022.mockAssessment;
//
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class HttpClientConnection implements Runnable{
    
    public Socket sock;
    public List<String> docRootList = new LinkedList<>();

    public HttpClientConnection(Socket sockinput, List<String> docList){
        sock = sockinput;
        docRootList = docList;
    }

    @Override
    public void run(){
        System.out.println("Starting a client thread");
        NetworkIO NetIO = null;
        String req = "";
        String response = "";
        try{
            NetIO = new NetworkIO(sock);
            try{req = NetIO.read();}catch(EOFException e){ //code for end of file exception, try another way to handle
                
            }
            System.out.printf("Client wants to %s\n", req);
            String[] cmmdList = req.split(" ");
            String resource = cmmdList[1];
            System.out.println(resource);
            for(int i=0;i<cmmdList.length; i++){
                System.out.println(cmmdList[i]);
            }
            if(!cmmdList[0].equals("GET")){
                System.out.println("Not GET method\n");
                response = "HTTP/1.1 405 Method Not Allowed\r\n\r\n"+cmmdList[0]+"not supported\r\n";
                NetIO.write(response);
                NetIO.close();
            }
            else{//maybe should do a equals GET here
                System.out.println("asdasdasd");
                System.out.println(resource);
                boolean resourceFound = false;
                if(resource.equals("/")){
                    resource = "/index.html";
                }
                for(int i=0;i<docRootList.size();i++){
                    File docRootFile = new File(docRootList.get(i));
                    List<String> resourcesList = Arrays.asList(docRootFile.list());
                    System.out.println("files in this direcotry");
                    for(int j=0; j<resourcesList.size();j++){
                        System.out.println(resourcesList.get(j));
                    }
                    if (resourcesList.contains(resource.replace("/", ""))){ //convert resource from /index.html to index.html
                        resourceFound = true;
                    }
                }
                System.out.println(resourceFound);
                if (!resourceFound){
                    response = "HTTP/1.1 404 Not Found\r\n\r\n"+resource+"not found\r\n";
                    System.out.println(response);
                    NetIO.write(response);
                    NetIO.close();
                }else{
                    System.out.println("asdasdasd4");
                    StringBuilder html = new StringBuilder();
                    FileReader fr = new FileReader(resource.replace("/", "")); //able to find resource but unable to reach file here, find a way to get into the correct folder and get the index.html
                    BufferedReader br = new BufferedReader(fr);
                    String val;
                    while((val = br.readLine())!=null){
                        html.append(val);
                    }
                    br.close();
                    fr.close();
                    String result = html.toString();
                    response = "HTTP/1.1 200 OK\r\n\r\n" + result;
                    if(resource.contains(".png")){
                        response = "HTTP/1.1 200 OK\r\nContent-Type: image/png\r\n\r\n" + result;
                    }
                    NetIO.write(response);
                    NetIO.close();
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }
}
