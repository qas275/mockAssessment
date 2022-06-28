package vttp2022.mockAssessment;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
//
public class NetworkIO {
    public InputStream is;
    public InputStreamReader isr;
    public BufferedReader br;
    public DataInputStream dis;
    public OutputStream os;
    public static DataOutputStream dos;

    public NetworkIO(Socket sock) throws IOException{
        is = sock.getInputStream();
        isr = new InputStreamReader(is);
        br = new BufferedReader(isr);
    }

    public String read() throws IOException {
        return br.readLine();
    }


     public void close(){
         try{
            is.close();
         }catch(IOException e){
             e.printStackTrace();
         }
     }
}
