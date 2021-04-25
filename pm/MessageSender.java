package pm;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**0
 * This is the thread that works in the background and helps in sending messages by reading input from terminal
 * and then sending that information over the network.
 */

public class MessageSender extends Thread {
    private Socket outSocket;
    private int outPort;
    private boolean done;
    private boolean flag;
    
    private List<String> msgList;

    /**
     * Create a MessageSender with the specified port
     * @param outSocket The socket over which we communicate
     * @param outPort The port for communication
     * @throws InterruptedException
     * @throws IOException
     */
    public MessageSender(Socket outSocket) throws InterruptedException, IOException {
        super();
        this.done = false;
        this.flag = false;
        this.msgList = new LinkedList<>();
        this.outSocket = outSocket;
    }

    public Socket getOutSocket() {
        return outSocket;
    }

    public int getOutPort() {
        return outPort;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    //Maintain a FIFO ordering
    public String getNextMessage() {
        return msgList.get(msgList.size() - 1);
    }

    public void addMessage(String m)
    {
        msgList.add(m);
    }

    public boolean hasNextMessage() {
        return msgList.size() != 0;
    }

    /**
     * Run in the background, and read messages from terminal and send them over to the socket
     */
    @Override
    public void run()
    {
        try
        {
            //System.out.println(Peer.getCurrentSender().getIdentifer());
            //Set up IO streams for communication with the socket
            OutputStreamWriter osw = new OutputStreamWriter(outSocket.getOutputStream());
            PrintWriter pw = new PrintWriter(osw);
            Scanner s = new Scanner(System.in); //For reading messages from terminal
            
            if (!flag)
            {
                pw.println("PROTOCOL? " + Peer.getCurrentSender().getProtocol() + " " + Peer.getCurrentSender().getIdentifer());
                pw.flush();
                flag = true;
            }
            
            while(true)
            {
                if(done)
                {
                    return;
                }

                if(outSocket.isClosed())
                {
                    break;
                }

                String msg = s.nextLine();
                addMessage(msg);
                
                if(hasNextMessage())
                {
                    pw.println(getNextMessage());
                    pw.flush(); //Flush the buffer, just to make sure
                }
                
                if(msg.equals("BYE!"))
                {
                    break;
                }
            }
//            /System.out.println();
        }
        catch (IOException ioe) {
            System.out.println("Connection Error: " + ioe.getMessage());
        }
    }
}