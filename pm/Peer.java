package pm;

import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class describes the person we are communicating with. It contains the person's name and his IP address
 */
public class Peer
{
    private String identifier;
    private int protocol;
    private Connection connection;

    /**
     * Represents the current sender.
     */
    private static Peer currentSender = null;

    /**
     * Create a new Peer.
     * @param ip
     * @param p
     */
    public Peer(String i)
    {
        this.identifier = i;
        this.protocol = 1;
    }
    
    public void connect(InetAddress ip, int port, int type)
    {
        try
        {
            this.connection = new Connection(ip,port,type);
            Peer.setCurrentSender(this);
            this.connection.getMessageSender().start();
            this.connection.getMessageReceiver().start();
        }
        catch (IOException | InterruptedException ex)
        {
            Logger.getLogger(Peer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void join()
    {
        try {
            this.connection.tJoin();
        } catch (InterruptedException ex) {
            Logger.getLogger(Peer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getIdentifer()
    {
        return this.identifier;
    }

    public void setIdentifer(String identifer)
    {
        this.identifier = identifer;
    }

    public int getProtocol()
    {
        return this.protocol;
    }

    public void setProtocol(int protocol)
    {
        this.protocol = protocol;
    }
    
    public static Peer getCurrentSender()
    {
        return currentSender;
    }

    public static void setCurrentSender(Peer s)
    {
        currentSender = s;
    }

    public static boolean currentSenderExists()
    {
        return currentSender != null;
    }
}
