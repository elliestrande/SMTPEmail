import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/**
*  Email program
*  Receives input from the file email.input
*  Connects with SMTP server 
*  Sends the first sentence to the server.
*  Receives a response from the server and displays it.
*  Sends the second sentence to the server.
*  Receives a second response from the server and displays it.
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
    System.out.print("MAIL FROM: ");
    from = inFromUser.readLine();
    System.out.println(from);

    // Get client recipient
    String rcpt;
    System.out.print("RCPT TO: ");
    rcpt = inFromUser.readLine();
    System.out.println(rcpt);

    // Get client sender name 
    String nameFrom;
    System.out.print("NAME FROM: ");
    nameFrom = inFromUser.readLine();
    System.out.println(nameFrom);

    // Get client recipient name
    String nameTo;
    System.out.print("NAME TO: ");
    nameTo = inFromUser.readLine();
    System.out.println(nameTo);

    // Get client message subject
    String subject;
    System.out.print("Subject: ");
    subject = inFromUser.readLine();
    System.out.println(subject);

    System.out.print("DATA\n");

    // Get client message content
    String data = "";
    String message = "";
    while (data.equals(".") == false) {
      data = inFromUser.readLine();
      System.out.println(data);
      message += data + "\n";
    }

    //STARTING THE OUTPUT

    // Write data to SMTP server
    BufferedReader inFromServer = new BufferedReader(
        new InputStreamReader(clientSocket.getInputStream()));
    
    System.out.println("\n--- Server Input ---");
        
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

    System.out.println("From: " + nameFrom);
    outToServer.println("From: " + nameFrom);

    System.out.println("To: " + nameTo);
    outToServer.println("To: " + nameTo);

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