import pm.*;
import java.io.IOException;
import java.util.Scanner;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class main
{
    public static final String TERMINAL_PROMPT = "(1) Connect\n" + "(2) Listen\n" + "(3) Exit\n" + "Select: ";
    
    public static void main(String[] args)
    {
        prompt();
    }

    public static void prompt()
    {
        //Connection connection = null;
        Scanner s = new Scanner(System.in);

        while(true)
        {
            System.out.print(TERMINAL_PROMPT);
//            try
//            {
                switch (s.nextLine())
                {
                    case "1":
                    {
                        System.out.print("IP ADDRESS? ");
                        String sIP = s.nextLine();
                        InetAddress IP = null;
                        try
                        {
                            IP = InetAddress.getByName(sIP);
                        }
                        catch (UnknownHostException uhe)
                        {
                            System.out.println("Error! Invalid IP address!");
                        }
                        
                        System.out.print("PORT? ");
                        int Port = s.nextInt();
                        Peer p = new Peer("left");
                        p.connect(IP,Port,1);
                        System.out.println("Connected!");
                        p.join();
                        break;
                    }
                    case "2":
                    {
                        System.out.print("PORT? ");
                        int Port = s.nextInt();
                        System.out.println("Waiting for connection...");
                        Peer p = new Peer("right");
                        try
                        {
                            p.connect(InetAddress.getByName("127.0.0.1"),Port,2);
                        }
                        catch (UnknownHostException ex)
                        {
                            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        System.out.println("Connected!");
                        p.join();
                        break;
                    }
                    case "3":
                    {
                        System.exit(0);
                    }
                    default:
                    {
                        System.out.println("Error: Incorrect Option");
                        break;
                    }
                }
                
                //System.out.println("Connection Terminated!");
//            }
//            catch (IOException | InterruptedException e)
//            {
//                System.out.println("Connection error! Disconnecting...");
//                if(connection != null)
//                {
//                    connection.disconnect();
//                }
//            }
        }
    }
}
