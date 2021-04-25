package pm;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Connection
{
    private MessageSender messageSender;
    private MessageReceiver messageReceiver;
    private List<MessageReceiver> messageServer;
    private ServerSocket serverSocket; //The socket to connect to
    private Socket socket; //The socket for transmitting the strings
    
    private InetAddress ip;
    private int port;

    public static int defaultPort = 20111;
    public static int maxNumTries = 20;
    public static int maxServerThreads = 10;

    public Connection()
    {
        this.messageSender = null;
        this.messageReceiver = null;
        this.serverSocket = null;
        this.socket = null;
        this.ip = null;
        this.port = 0;
    }
    
     /**
     * Create a Connection with the details of the given Sender.The port is set to the default port.
     * @param p
     * @param type
     * @throws IOException In case the connection fails
     * @throws InterruptedException We sleep for 1000ms after every failed connection try. This is in case the thread is interrupted
     */
    public Connection(InetAddress i, int p, int type) throws IOException, InterruptedException
    {
        this.ip = i;
        this.port = p;
        
        switch(type)
        {
            case 1: //Connect
                //In case the connection fails, we retry maxNumTries times. If we still don't connect, throw
                //an IOException
                                
                int numTries = Connection.maxNumTries;
                while(numTries > 0)
                {
                    try
                    {
                        this.socket = new Socket(this.ip, this.port);
                    }
                    catch (ConnectException ce)
                    {
                        System.out.println("Connection Failed. " + numTries + " tries left...");
                        this.socket = null;
                        Thread.sleep(1000);
                    }
                    if (this.socket == null)
                    {
                        numTries--;
                    }
                    else
                    {
                        break;
                    }
                }

                if(this.socket == null)
                {
                    throw new IOException("Connection failed!");
                }
                this.messageSender = new MessageSender(this.socket);
                this.messageReceiver = new MessageReceiver(this.socket);
                this.serverSocket = null;
                break;
            
            case 2: //Listen
                this.serverSocket = new ServerSocket(this.port);
                this.socket = this.serverSocket.accept();
                this.messageSender = new MessageSender(this.socket);
                this.messageReceiver = new MessageReceiver(this.socket);
                break;

            default:
                break;
        }  
    }

    public List<MessageReceiver> getMessageServer()
    {
        return this.messageServer;
    }
    
    public MessageSender getMessageSender()
    {
        return this.messageSender;
    }

    public MessageReceiver getMessageReceiver()
    {
        return this.messageReceiver;
    }

    public void connect() throws InterruptedException
    {
        this.messageSender.start();
        this.messageReceiver.start();
    }
    
    public void tJoin() throws InterruptedException
    {
        this.messageSender.join();
        this.messageReceiver.join();
    }

    public void disconnect()
    {
        try
        {
            this.messageSender.setDone(true);
            this.messageReceiver.setDone(true);
            this.socket.close();
            //Peer.setCurrentSender(null);
            System.out.println("Connection Terminated For Good");
        }
        catch (IOException ioe)
        {
            System.out.println("\nUnable to close connection");
        }
    }
}
