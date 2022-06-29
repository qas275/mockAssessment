package vttp2022.mockAssessment;
//
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class HttpClientConnection implements Runnable{
    
    public Socket sock;
    public List<String> docRootList = new LinkedList<>();
    public String FileHTMLorPNG;

    public HttpClientConnection(Socket sockinput, List<String> docList){
        sock = sockinput;
        docRootList = docList;
    }

    @Override //must do an override of run when implementing runnable, only runs when submitted to threadpool
    public void run(){
        System.out.println("Starting a client thread");
        NetworkIO NetIO = null;
        String req = "";
        String response = "";
        try{
            OutputStream os = sock.getOutputStream(); //can either write this as a write method in NetIO class or here
            NetIO = new NetworkIO(sock);
            // try{req = NetIO.read();}catch(EOFException e){}  //previously used this because NetIO from kenneth uses readUTF 
                                                                //which hits EOFException 
                                                                //but now modded his readUTF to readline 
                                                                //as task only requires first line from http as request
            req = NetIO.read();
            System.out.printf("Client wants to %s\n", req);
            String[] cmmdList = req.split(" ");
            String resource = cmmdList[1];
            //System.out.println(resource);
            for(int i=0;i<cmmdList.length; i++){
                System.out.println(cmmdList[i]);
            }
            if(!cmmdList[0].equals("GET")){
                System.out.println("Not GET method\n");
                response = "HTTP/1.1 405 Method Not Allowed\r\n\r\n"+cmmdList[0]+"not supported\r\n";
                os.write(response.getBytes());//can either write this as a write method in NetIO class or here
                os.flush();
                os.close();
                NetIO.close();
            }
            else{//maybe should do a equals GET here
                System.out.println(resource);
                boolean resourceFound = false;
                if(resource.equals("/")){
                    resource = "/index.html";
                }
                resource = resource.replace("/", "");//change resource from /index.thml to index.html
                for(int i=0;i<docRootList.size();i++){//for each String directory specified
                    File docRootFile = new File(docRootList.get(i));//change string to file
                    List<String> resourcesList = Arrays.asList(docRootFile.list());//add all files in directory to list
                    System.out.println("files in this direcotry");
                    for(int j=0; j<resourcesList.size();j++){
                        System.out.println(resourcesList.get(j));//print all files in directory as listed
                    }
                    if (resourcesList.contains(resource)){ //check if resource exists in listed files
                        resourceFound = true;
                        FileHTMLorPNG = docRootFile.getName()+"/"+resource;// "static/index.html"--> assign path <directory>/<resource> to FileHTMLorPNG as a String
                        System.out.printf("Directory with html/png is %s.\n", FileHTMLorPNG);
                    }
                }
                System.out.println(resourceFound);
                if (!resourceFound){
                    response = "HTTP/1.1 404 Not Found\r\n\r\n"+resource+" not found\r\n";
                    System.out.println(response);
                    os.write(response.getBytes());
                    os.flush();
                    os.close();
                    NetIO.close();
                }else{
                    System.out.println("asdasdasd4");
                    if(resource.contains(".png")){ //run if requested for png
                        FileInputStream imageStream = new FileInputStream(FileHTMLorPNG);
                        response = "HTTP/1.1 200 OK\r\nContent-Type: image/png\r\n\r\n";
                        os.write(response.getBytes());
                        os.write(imageStream.readAllBytes());
                        os.flush();
                        os.close();
                        imageStream.close();
                        NetIO.close();
                    }else{ //run if requested for html
                        StringBuilder html = new StringBuilder();
                        FileReader fr = new FileReader(FileHTMLorPNG); //able to find resource but unable to reach file here, find a way to get into the correct folder and get the index.html
                        BufferedReader br = new BufferedReader(fr);
                        String htmlString;
                        System.out.println("build string");
                        while((htmlString = br.readLine())!=null){
                            html.append(htmlString);
                        }
                        br.close();
                        fr.close();
                        String result = html.toString();
                        System.out.println("built string htmlString");
                        response = "HTTP/1.1 200 OK\r\n\r\n" + result;
                        os.write(response.getBytes());
                        os.flush();
                        os.close();
                        NetIO.close();
                    }
                }
            }
        }catch(IOException e){
            System.out.println("IOexception met");
            System.exit(1);
        }
        
    }
}
