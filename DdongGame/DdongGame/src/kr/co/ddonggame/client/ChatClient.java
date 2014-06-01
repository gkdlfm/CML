// This file contains material covering the answer of Project 3.X from
// the textbook: "Object Oriented Software Engineering". It is issued under
// a special answer license found at www.lloseng.com.  The contents of this
// file must neither be posted on a public server nor made available in any
// other way that would permit public access to it.

package kr.co.ddonggame.client;


import java.io.*;

import android.util.Log;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 

  /**
   * This variable contains the client's login ID.
   */
  String loginID;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param login The login ID to use.  Added in phase 2.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String host, int port, String login, ChatIF clientUI)
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    loginID = login;
    try
    {
      openConnection();
      sendToServer("#login " + login); //Added in phase 2
    }
    catch(IOException e)
    {
      clientUI.display("Cannot open connection.  Awaiting command.");
    }
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
	  Log.i("client Thread from server", msg.toString());
    clientUI.display(msg.toString());
  }
  
  /**
   * This method handles all data coming from the UI
   *
   * @param message The message from the UI.
   */
  public void handleMessageFromClientUI(String message)
  {
    //If the command is #login. Added in phase 2
    if (message.startsWith("#login") && !isConnected())
    {
      try
      {
        openConnection();
      }
      catch(IOException e)
      {
        clientUI.display("Cannot establish connection."
             + " Awaiting command.");
	return;   
      }
    }

    //If the command is #quit.  Added in phase 2
    if (message.startsWith("#quit"))
      quit();

    //If the command is #logoff.  Added in phase 2
    if (message.startsWith("#logoff"))
    {
      try
      {
        closeConnection();
      }
      catch(IOException e)
      {
        clientUI.display("Cannot logoff normally.  Terminating client.");
        quit();
      }
      connectionClosed(false);
      return;
    }

    //If the command is #gethost.  Added in phase 2
    if (message.startsWith("#gethost"))
    {
      clientUI.display("Current host: " + getHost());
      return;
    }
 
    //If the command is #getport.  Added in phase 2
    if (message.startsWith("#getport"))
    {
      clientUI.display("Current port: " + getPort());
      return;
    }
  
    //If the command is #sethost.  Added in phase 2
    if (message.startsWith("#sethost"))
    {
      if (isConnected())
        clientUI.display("Cannot change host while connected.");
      else
        try
        {
          setHost(message.substring(9));
          clientUI.display("Host set to: " + getHost());
        }
        catch(IndexOutOfBoundsException e)
        {
          clientUI.display("Invalid host. Use #sethost <host>.");
        }
        return;
    }

    //If the command is #setport.  Added in phase 2
    if (message.startsWith("#setport"))
    {
      if (isConnected())
        clientUI.display("Cannot change port while connected.");
      else
        try
        {
          int port = 0;
          port = Integer.parseInt(message.substring(9));

          //If the port number is invalid
          if ((port < 1024) || (port > 65535))
          {
            clientUI.display("Invalid port number.  Port unchanged.");
          }
          else
          {
            setPort(port);
            clientUI.display("Port set to " + port);
          }
        }
        catch(Exception e)
        {
          clientUI.display("Invalid port. Use #setport <port>.");
          clientUI.display("Port unchanged.");
        }
      return;
    }

    //If not a client-side command or is a message to be displayed
    if ((message.startsWith("#login")) 
      || ((message.startsWith("#"))))
      try
      {
        sendToServer(message);
      }
      catch(IOException e)
      {
        clientUI.display("Cannot send the message to the server."
             + " Disconnecting.");
        try
        {
          closeConnection();
        }
        catch(IOException ex)
        {
          clientUI.display(
            "Cannot logoff normally.  Terminating client.");
          quit();
        }
      }
    else{
    	clientUI.display(message);
      clientUI.display("Invalid command.");
 
    }
  }
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
  
  /**
   * This method is called when a connection with the server is
   * terminated.  Added in phase 2.
   *
   * @param isAbnormal TRUE if the connection was terminated 
   *        abnormally FALSE otherwise.
   */
  protected void connectionClosed(boolean isAbnormal)
  {
    if (isAbnormal)
      clientUI.display("Abnormal termination of connection.");
    else
      clientUI.display("Connection closed.");
  }
  
  /**
   * This method is called when a connection with a server is
   * established.  Added in phase 2.
   */
  protected void connectionEstablished()
  {
    clientUI.display("Connection established with " + getHost() +
                     " on port " + getPort());
  }
  
  /**
   * This method is called when an exception is detected in the method that
   * awaits input from the server.
   *
   * @param exception The exception thrown by the method.
   */
  protected void connectionException(Exception exception)
  {
    clientUI.display("Connection to server terminated.");
  }
}
//End of ChatClient class
