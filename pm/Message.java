package pm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Message implements Serializable
{
    private String msgID;
    private int timeSent;
    private String from;
    private String to;
    private String topic;
    private String subject;
    private int contents;
    private List<String> body;
    
    
    public Message()
    {
        this.body = new LinkedList<>();
    }
    
    /**
     * Create a message
     * @param msgID SHA-256 sum of all headers and body of the message.
     * @param timeSent Message creation time
     * @param from From
     * @param to To
     * @param topic Topic
     * @param subject Subject
     * @param contents Number of lines of the body
     * @param body Message body
     */
    public Message(String msgID, int timeSent, String from, String to, String topic, String subject, int contents, List<String> body)
    {
        this.msgID = msgID;
        this.timeSent = timeSent;
        this.from = from;
        this.to = to;
        this.topic = topic;
        this.subject = subject;
        this.contents = contents;
        this.body = body;
    }
    
    public String getMsgID()
    {
        return msgID;
    }
    
    public void generateExample()
    {   
        this.msgID = "bc18ecb5316e029af586fdec9fd533f413b16652bafe079b23e021a6d8ed69aa";
        this.timeSent = 1614686400;
        this.from = "martin.brain@city.ac.uk";
        this.topic = "#announcemnts";
        this.subject = "Hello!";
        this.contents = 2;
        this.body.add("Hello everyone!");
        this.body.add("This is the first message sent using PM.");
    }

    public int getTimeSent()
    {
        return timeSent;
    }

    public String getTopic()
    {
        return topic;
    }

    public String getSubject()
    {
        return subject;
    }

    public int getContents()
    {
        return contents;
    }
    
    @Override
    public String toString()
    {
        String msg = "Message-id: SHA-256 " + this.msgID + "\n"
                    + "Time-sent: " + this.timeSent + "\n"
                    + "From: " + this.from + "\n"
                    + "To: " + this.to + "\n"
                    + "Topic: " + this.topic + "\n"
                    + "Subject: " + this.subject + "\n"
                    + "Contents: " + this.contents + "\n";
        
        for (int i = 0; i < this.body.size(); i++)
        {
            msg += (this.body.get(i) + "\n");
        }
        return msg;
    }
    
    //Taken From https://www.geeksforgeeks.org/sha-256-hash-in-java/
    public static byte[] getSHA(String input) throws NoSuchAlgorithmException
    { 
        // Static getInstance method is called with hashing SHA 
        MessageDigest md = MessageDigest.getInstance("SHA-256"); 
  
        // digest() method called 
        // to calculate message digest of an input 
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8)); 
    }
    
    //Taken From https://www.geeksforgeeks.org/sha-256-hash-in-java/
    public static String toHexString(byte[] hash)
    {
        // Convert byte array into signum representation 
        BigInteger number = new BigInteger(1, hash); 
  
        // Convert message digest into hex value 
        StringBuilder hexString = new StringBuilder(number.toString(16)); 
  
        // Pad with leading zeros
        while (hexString.length() < 32) 
        { 
            hexString.insert(0, '0'); 
        } 
  
        return hexString.toString(); 
    }
    
    public void computeHash()
    {
        String msg = "Time-sent: " + this.timeSent + "\n"
                + "From: " + this.from + "\n"
                + "To: " + this.to + "\n"
                + "Topic: " + this.topic + "\n"
                + "Subject: " + this.subject + "\n"
                + "Contents: " + this.contents + "\n";
        
        for (int i = 0; i < body.size(); i++)
        {
            msg += (body.get(i) + "\n");    
        } 
        try
        {
            this.msgID = toHexString(getSHA(msg));
        }
        catch (NoSuchAlgorithmException ex)
        {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void inputMessage()
    {
        Scanner s = new Scanner(System.in);
        
        System.out.print("From: ");
        this.from = s.nextLine();
        
        System.out.print("To: ");
        this.to = s.nextLine();
        
        System.out.print("Topic: ");
        this.topic = s.nextLine();
        
        System.out.print("Subject: ");
        this.subject = s.nextLine();
        
        System.out.print("Contents: ");
        this.contents = s.nextInt();
        s.nextLine();
        
        System.out.println("Body: ");
        this.body = new LinkedList<>();
        for (int i = 0; i < this.contents; i++)
        {
            this.body.add(s.nextLine());
        }
        
        this.timeSent = Integer.parseInt(this.getCurrentTime());
        this.computeHash();
    }
    
    public String getCurrentTime()
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
}