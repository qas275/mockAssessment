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
        //dis = new DataInputStream(is);
        os = sock.getOutputStream();
        dos = new DataOutputStream(os);
    }

    public String read() throws IOException {
        return br.readLine();
        //return dis.readLine();
    }

    public void write(String msg) throws IOException {
        dos.writeUTF(msg);
        dos.flush();
     }

     public void close(){
         try{
            dis.close();
            is.close();
            dos.close();
            os.close();
         }catch(IOException e){
             e.printStackTrace();
         }
     }
}
