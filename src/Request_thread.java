import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Request_thread extends Thread {

    public Socket received;
    public ProxyGroups proxyGroups;
    public HostGroup foundedGroup;

    public Request_thread(Socket received, ProxyGroups proxyGroups) {
        this.received = received;
        this.proxyGroups = proxyGroups;
    }

    public void run(){

        InputStream mInputStream = null;
        try {
            mInputStream = received.getInputStream();
        } catch (IOException e) {
            System.out.println("unable to get input stream from the received request");
        }
        byte[] data = new byte[1024];

        try {
            while (mInputStream.available() == 0) {
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        try {
            mInputStream.read(data);
        } catch (IOException e) {
            System.out.println("unable to read input data");

        }
        String message = "";

        for (int i = 0; i < data.length; i++) {
            int d = data[i];
            char c = (char) d;
            message = message + c;
        }

        //System.out.println("recieved request is : ");
        //System.out.println(message);
        String m[] = message.split("\r\n");
        String m2[] = m[1].split(" ");
        String host = m2[1];
        InetAddress dst_ip = received.getInetAddress();
        //System.out.println("request received from = " + dst_ip);
        //System.out.println("request to the host : " + host);
        //System.out.println("finding the host in the groups......");

        try {
            if (search(host)) {
                System.out.println("this host was found in group: " + foundedGroup.groupNumber + "." + foundedGroup.groupName);
                if (foundedGroup.isActive) {
                    InetAddress ip = InetAddress.getByName(host);
                    Socket sendSocket = new Socket(ip, 80);
                    OutputStream mOutputStream = sendSocket.getOutputStream();
                    PrintWriter out = new PrintWriter(mOutputStream);
                    out.print(message);
                    out.flush();
                    //BufferedReader br = new BufferedReader(new InputStreamReader(sendSocket.getInputStream()));
                    BufferedInputStream input = new BufferedInputStream(sendSocket.getInputStream());
                    byte[] res = new byte[10000000];
                    int numRead = input.read(res);
                    System.out.println(numRead);
                    byte[] response = new byte[numRead];
                    System.arraycopy(res, 0, response, 0, numRead);
                /*String t;
                String response = "";
                while ((t = br.readLine()) != null) response = response + t;
                br.close();*/

                    //Socket socket = new Socket(dst_ip, 80);
                    OutputStream mOutputStream2 = received.getOutputStream();
                    mOutputStream2.write(response);
                    mOutputStream2.flush();
                /*PrintWriter out2 = new PrintWriter(mOutputStream2);
                out2.println(response);
                out2.flush();*/
                } else {
                    System.out.println("the requested group is filtered!!");
                    OutputStream mOutputStream2 = received.getOutputStream();
                    PrintWriter out2 = new PrintWriter(mOutputStream2);
                    out2.println("HTTP/1.1 200 OK");
                    out2.println("Content-Type: text/html");
                    out2.println("\r\n");
                    out2.println("<p> sorry this page is filter for you!!!!</p>");
                    out2.println("<p> you can visit peyvandha instead!! </p>");
                    out2.flush();
                }
            }
        }catch (IOException e){
            System.out.println("oh god an error occurred again");
        }


    }

    public boolean search(String host) {

        if(proxyGroups.allGroups != null) {
            for (HostGroup hg : proxyGroups.allGroups) {
                if (hg.searchHost(host)) {
                    this.foundedGroup = hg;
                    return true;
                }
            }
        }

        return false;
    }


}
