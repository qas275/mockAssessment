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
        String docRootdefault = "./target";
        boolean stop = false;
        if(args.length>3){ //more than 3 args
            if (args[0]=="--port"){ // change default port and docRoot
                portdefault = Integer.parseInt(args[1]);
            }
            if (args[2]=="--docRoot"){
                docRootdefault = args[3];
            }
        }else if(args.length>0){ //has arguments
            if (args[0]=="--port"){ //
                portdefault = Integer.parseInt(args[1]);
            }else if(args[0]=="--docRoot"){
                docRootdefault = args[1];
            }
        }

        System.out.printf("Port is %d and docRoot is %s\n",portdefault,docRootdefault);
        HttpServer server = new HttpServer(portdefault,docRootdefault);

        ExecutorService threadPool = Executors.newFixedThreadPool(3);


        while(!stop){

            
        }

    }
}
