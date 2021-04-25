package pm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This is the thread that works in the background and helps in receiving messages by reading input from the socket
 * and then printing the message to the terminal.
 */

public class MessageReceiver extends Thread
{
    private Socket inSocket;
    private int inPort;
    private boolean done;
    private List<String> msgList;
    private List<String> findList;
    private List<Message> mList;
  
    /**
     * Create a MessageReceiver with the specified port
     * @param iS
     * @param mList
     * @throws IOException
     * @throws InterruptedException
     */
    public MessageReceiver(Socket iS, List<Message> mList) throws IOException, InterruptedException
    {
        super();
        this.inPort = iS.getLocalPort();
        this.done = false;
        this.msgList = new LinkedList<>();
        this.mList = mList;
        this.inSocket = iS;
    }
    /**
     * Create a MessageReceiver with the specified port
     * @param iS
     * @throws IOException
     * @throws InterruptedException
     */
    public MessageReceiver(Socket iS) throws IOException, InterruptedException
    {
        super();
        this.inPort = iS.getLocalPort();
        this.done = false;
        this.msgList = new LinkedList<>();
        
        this.mList = new LinkedList<>();
            Message m = new Message();
            m.generateExample();
        this.mList.add(m);
        
        this.inSocket = iS;
    }

    public Socket getInSocket()
    {
        return inSocket;
    }

    public int getInPort()
    {
        return inPort;
    }

    /**
     * If we want to stop the thread, set done to true. The thread will only stop when it wakes up from
     * readLine().
     * @param done Set whether the thread is done its job or not.
     */
    public void setDone(boolean done)
    {
        this.done = done;
    }

    public boolean hasNextMessage()
    {
        return msgList.size() != 0;
    }

    public String getNextMessage()
    {
        return msgList.get(msgList.size() - 1);
        //return messageList.remove(0);
    }

    public void addMessage(String m)
    {
        msgList.add(m);
    }
    
    public void saveMessages()
    {
        System.out.println("");
    }
    
    public String getTime()
    {
        String cmd = "date +%s";
        String time = "";
        try
        {
            Process process = Runtime.getRuntime().exec(cmd);

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            time = reader.readLine();
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * Run in the background and keep reading input from the Socket, and printing it on the screen
     */
    @Override
    public void run()
    {
        //System.out.println(Peer.getCurrentSender().getIdentifer());
        try
        {
            OutputStreamWriter osw = new OutputStreamWriter(inSocket.getOutputStream());
            PrintWriter pw = new PrintWriter(osw);
            Scanner s = new Scanner(System.in); //For reading messages from terminal
            
            InputStreamReader isr = new InputStreamReader(inSocket.getInputStream());
            BufferedReader br = new BufferedReader(isr);

            //String sName = Peer.getCurrentSender().getIdentifer();

            while(true)
            {
                if(done)
                {
                    break;
                }

                if(inSocket.isClosed())
                {
                    break;
                }

                String msg;
                try
                {
                    msg = br.readLine();
                }
                catch (NoSuchElementException nsee)
                {
                    msg = null;
                }
                if(msg == null)
                {
                    inSocket.close();
                    break;
                }
                
                addMessage(msg);
                
                String[] msgArr = msg.split(" ");
                //System.out.println(msgArr[0]);
                
                switch(msgArr[0])
                {
                    case "test":
                        
                        break;
                        
                    case "TIME?":
                        pw.println("NOW " + getTime());
                        pw.flush(); //Flush the buffer, just to make sure
                        break;
                        
                    case "LIST?":
                        
                        this.findList = new LinkedList<>();
                        
                        if (Integer.parseInt(msgArr[2]) == 0)
                        {
                            List<String> found = new LinkedList<>();
                            for (int i = 0; i < this.mList.size(); i++)
                            {
                                if (Integer.parseInt(msgArr[1]) < this.mList.get(i).getTimeSent())
                                {
                                    found.add(this.mList.get(i).getMsgID());
                                }
                            }
                            pw.println("MESSAGES " + found.size());
                            for (int i = 0; i < found.size(); i++)
                            {
                                pw.println(found.get(i));
                            }
                            pw.flush();
                        }
                        break;
                    
                    case "GET?":
                        for (int i = 0; i < this.mList.size(); i++)
                        {
                            if (this.mList.get(i).getMsgID().equals(msgArr[1]))
                            {
                                pw.println("FOUND");
                                pw.println(this.mList.get(i));
                                pw.flush();
                            }
                            else
                            {
                                pw.println("SORRY");
                                pw.flush();
                            }
                        }
                        break;
                        
                    case "BYE!":
                        System.exit(0);
                        break;
                   
                    case "LOAD":
                        try
                        {
                            FileInputStream fileIn = new FileInputStream("msgBackup.txt");
                            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                            this.mList = (List<Message>) objectIn.readObject();
                            objectIn.close();
                            pw.println("LOADED msgBackup.txt");
                            pw.flush();
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                        break;
                        
                    case "SAVE":
                        try
                        {
                            FileOutputStream fileOut = new FileOutputStream("msgBackup.txt");
                            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                            objectOut.writeObject(this.mList);
                            objectOut.close();
                            pw.println("SAVED to msgBackup.txt");
                            pw.flush();
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                        break;
                    
                    case "":
                        System.out.println(getNextMessage());
                        break;
                    
                    case "PROTOCOL?":
                        System.out.println(getNextMessage());
                        break;
                        
                    default:
                        System.out.println(getNextMessage());
                        //System.out.println("CLIENT MADE A WRONG REQUEST, TERMINATING CONNECTION.");
                        break;
                }
            }
        }
        catch (IOException ioe)
        {
            System.out.println("Connection Error: " + ioe.getMessage());
        }
    }
    
}
