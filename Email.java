import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/**
*  Email program
*  Receives input from the file email.input as client
*  Connects with SMTP server 
*  Message is transfered to mail server
*  Closes the socket and exits.

*  @author: Ellie Strande & Cal Hegstrom
*      Email:  strande@chapman.edu & hegstrom@chapman.edu
*      Date:  2/16/2023
*  @version: 3.2
*/



class Email {

  public static void main(String[] argv) throws Exception {
    // Get user input
    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

    // Connect to the server
    Socket clientSocket = null;

    // Open a socket connection with the SMTP server
    try {
      clientSocket = new Socket("smtp.chapman.edu", 25);
    } catch (Exception e) {
      System.out.println("Failed to open socket connection");
      System.exit(0);
    }

    // Get client from 
    String from;
    from = inFromUser.readLine();

    // Get client recipient
    String rcpt;
    rcpt = inFromUser.readLine();

    // Get client sender name 
    String nameFrom;
    nameFrom = inFromUser.readLine();

    // Get client recipient name
    String nameTo;
    nameTo = inFromUser.readLine();

    // Get client message subject
    String subject;
    subject = inFromUser.readLine();

    // Get client message content
    String data = "";
    String message = "";
    while (data.equals(".") == false) {
      data = inFromUser.readLine();
      message += data + "\n";
    }

    //STARTING THE OUTPUT

    // Write data to SMTP server
    BufferedReader inFromServer = new BufferedReader(
        new InputStreamReader(clientSocket.getInputStream()));
        
    System.out.println(inFromServer.readLine());

    PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);

    System.out.println("HELO icd.chapman.edu");
    outToServer.println("HELO icd.chapman.edu");
    System.out.println(inFromServer.readLine());

    System.out.println("MAIL FROM: " + from);
    outToServer.println("MAIL FROM: " + from);
    System.out.println(inFromServer.readLine());

    System.out.println("RCPT TO: " + rcpt);
    outToServer.println("RCPT TO: " + rcpt);
    System.out.println(inFromServer.readLine());

    System.out.println("DATA");
    outToServer.println("DATA");
    System.out.println(inFromServer.readLine());

    System.out.println("To: " + nameTo);
    outToServer.println("To: " + nameTo);

    System.out.println("From: " + nameFrom);
    outToServer.println("From: " + nameFrom);

    System.out.println("Subject: " + subject);
    outToServer.println("Subject: " + subject);

    // Split the message content into seperate lines to feed to server
    String[] lines = message.split("\n");

    for (String line : lines) {
      System.out.println(line);
      outToServer.println(line);
    }

    // outToServer.println(".");
    System.out.println(inFromServer.readLine());

    System.out.println("QUIT");
    outToServer.println("QUIT");
    System.out.println(inFromServer.readLine());

    // Close the socket connection
    clientSocket.close();
  }
}