package vttp2022.mockAssessment;

import java.io.IOException;
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
        for (int i=0;i<(args.length-1);i++){
            if(args[i].equals("--port")){
                portdefault = Integer.parseInt(args[i+1]);
            }
            if(args[i].equals("--docRoot")){
                docRootdefault = args[i+1];
            }
        }
        System.out.printf("Port is %d and docRoot is %s\n",portdefault,docRootdefault);

        HttpServer Server = new HttpServer(portdefault,docRootdefault);
        Server.start();
        HttpClientConnection clientHandlingServer = new HttpClientConnection(Server.sock, Server.docRootList);
        ExecutorService threadPool = Executors.newFixedThreadPool(3);


        while(!stop){
            Server.listen();
            threadPool.submit(clientHandlingServer);
            System.out.println("");
        }

    }
}
