package vttp2022.mockAssessment;
//
import java.io.BufferedReader;
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
            req = NetIO.read();
            System.out.printf("Client wants to %s", req);
            String[] cmmdList = req.split(" ");
            if(!cmmdList[0].equals("GET")){
                System.out.println("Not GET method");
                response = "HTTP/1.1 405 Method Not Allowed\r\n\r\n"+cmmdList[0]+"not supported\r\n";
                NetIO.write(response);
                NetIO.close();
            }
            else{//maybe should do a equals GET here
                String resource = cmmdList[1];
                boolean resourceFound = false;
                if(resource.equals("/")){
                    resource = "/index.html";
                }
                for(int i=0;i<docRootList.size();i++){
                    File docRootFile = new File(docRootList.get(i));
                    List<String> resourcesList = Arrays.asList(docRootFile.list());
                    if (resourcesList.contains(resource)){
                        resourceFound = true;
                    }
                }
                if (!resourceFound){
                    response = "HTTP/1.1 404 Not Found\r\n\r\n"+resource+"not found\r\n";
                    NetIO.write(response);
                    NetIO.close();
                }else{
                    //File resourceF = new File(resource);
                    StringBuilder html = new StringBuilder();
                    FileReader fr = new FileReader(resource);
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
