import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ListeningThread extends Thread {

    ProxyGroups proxyGroups;
    public  ListeningThread(ProxyGroups proxyGroups){
        this.proxyGroups = proxyGroups;
    }

    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(5000);
            System.out.println("Server Socket established, Proxy is listening.....");
        } catch (IOException e) {
            System.out.println("an error occurred, server socket not established");
            e.printStackTrace();
        }

        while (true) {

            Socket recieved = null;

            try {
                recieved = serverSocket.accept();
                //System.out.println("Got a New Request!!");
                Request_thread reqThread = new Request_thread(recieved, proxyGroups);
                reqThread.start();

            } catch (IOException e) {
                System.out.println("an error occurred while listening");
                e.printStackTrace();
            }
        }
    }

}
