import java.util.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.net.*;
import java.math.*;

public class SecureChatClient extends JFrame implements Runnable, ActionListener {

	public static final int PORT = 8765;
	ObjectOutputStream myWriter;
	ObjectInputStream  myReader;
	BigInteger e;
	BigInteger n;
	JTextArea outputArea;
	JLabel prompt;
	JTextField inputField;
	String myName, serverName, encrType;
	Socket connection;
	SymCipher cipher;

	public SecureChatClient ()
	{
		try {
		myName = JOptionPane.showInputDialog(this, "Enter your user name: ");
		serverName = JOptionPane.showInputDialog(this, "Enter the server name: ");
		InetAddress addr =
				InetAddress.getByName(serverName);
		connection = new Socket(addr, PORT);   // Connect to server with new
		
		myWriter = new ObjectOutputStream(connection.getOutputStream());
		myWriter.flush();
		myReader = new ObjectInputStream(connection.getInputStream());

		
		e = (BigInteger)myReader.readObject();
		n = (BigInteger)myReader.readObject();

		encrType = (String)myReader.readObject();
		
		if (encrType.equals("Add"))
		{
			System.out.println("\nChosen Cipher is Add128");
			cipher = new Add128();
		}
		else
		{
			System.out.println("\nChosen Cipher is Substitute");
			cipher = new Substitute();
		}
		byte[] keyTemp = cipher.getKey();
		BigInteger key = new BigInteger(1, keyTemp);
		System.out.println("\nN: "+n);
		System.out.println("\nE: "+e);
		System.out.println("\nKey: "+key);

		key = key.modPow(e, n);
		myWriter.writeObject(key);
		myWriter.flush();
		byte[] encName = cipher.encode(myName);
		// #2 For each encryption performed by the client, output the following to the console:
			// The original String message.
			// A BigInteger representation of the corresponding array of bytes.
			// A BigInteger representation of the encrypted array of bytes.
		byte [] msg1 = myName.getBytes();
		BigInteger biRep = new BigInteger(1, msg1);
		BigInteger biEnc = new BigInteger(1, encName);
		System.out.println("\n\t\t##### Encryption Performed #####\n-------------------------------------------------------------------------------");
		System.out.println("Original String Message:\t\t\t\t "+myName);
		System.out.println("BigInteger representation of array:\t\t\t "+biRep);
		System.out.println("BigInteger representation of encrypted array:\t\t "+biEnc);
		myWriter.writeObject(encName);
		myWriter.flush();

		Box b = Box.createHorizontalBox();  // Set up graphical environment for
		outputArea = new JTextArea(8, 30);  // user
		outputArea.setEditable(false);
		b.add(new JScrollPane(outputArea));
		outputArea.append("Welcome to the Chat Group, " + myName + "\n");
		inputField = new JTextField("");  // This is where user will type input
		inputField.addActionListener(this);
		prompt = new JLabel("Type your messages below:");
		Container c = getContentPane();
		c.add(b, BorderLayout.NORTH);
		c.add(prompt, BorderLayout.CENTER);
		c.add(inputField, BorderLayout.SOUTH);

		Thread outputThread = new Thread(this);  // Thread is to receive strings
		outputThread.start();					// from Server
		addWindowListener(
				new WindowAdapter()
				{
					public void windowClosing(WindowEvent e)
					{ 
						try
						{
							myWriter.writeObject(cipher.encode("CLIENT CLOSING"));
							myWriter.flush();
							byte[] encName = cipher.encode("CLIENT CLOSING");
							// #2 For each encryption performed by the client, output the following to the console:
								// The original String message.
								// A BigInteger representation of the corresponding array of bytes.
								// A BigInteger representation of the encrypted array of bytes.
							byte [] msg1 = "CLIENT CLOSING".getBytes();
							BigInteger biRep = new BigInteger(1, msg1);
							BigInteger biEnc = new BigInteger(1, encName);
							System.out.println("\n\t\t##### Encryption Performed #####\n-------------------------------------------------------------------------------\nOriginal String Message:\t\t\t\t CLIENT CLOSING\nBigInteger representation of array:\t\t\t "+biRep+"\nBigInteger representation of encrypted array:\t\t "+biEnc);
							myWriter.close();
						}
						catch (IOException ee)
						{
							ee.printStackTrace();
						}
					 System.exit(0);
					}
				}
			);

		setSize(500, 200);
		setVisible(true);

		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Problem starting client!");
		}
	}

	public void run()
	{
		while (true)
		{
			 try {
				byte[] tempMsg = (byte[])myReader.readObject();
				String currMsg = cipher.decode(tempMsg);

				// For each dencryption performed by the client, output the following to the console:
					// A BigInteger representation of the array of bytes received.
					// A BigInteger representation of the decrypted array of bytes.
					//The corresponding String.
				byte [] msg1 = currMsg.getBytes();
				BigInteger biRep = new BigInteger(1, tempMsg);
				BigInteger biDcrypt = new BigInteger(1, msg1);
				System.out.println("\n\t\t##### Decryption Performed #####\n-------------------------------------------------------------------------------");
				System.out.println("BigInteger representation of bytes received:\t\t "+biRep);
				System.out.println("BigInteger representation of decrypted array of bytes:\t "+biDcrypt);				
				System.out.println("Corresponding String Message:\t\t\t\t "+currMsg);

				outputArea.append(currMsg+"\n");
			 }
			 catch (Exception e)
			 {
				System.out.println(e +  ", closing client!");
				break;
			 }
		}
		System.exit(0);
	}

	public void actionPerformed(ActionEvent e)
	{
		String currMsg = e.getActionCommand();	  // Get input value
		inputField.setText("");
		try
		{
			myWriter.writeObject(cipher.encode(myName+":"+currMsg));
			myWriter.flush();   // Add name and send it
			// #2 For each encryption performed by the client, output the following to the console:
			// The original String message.
			// A BigInteger representation of the corresponding array of bytes.
			// A BigInteger representation of the encrypted array of bytes.
		byte [] msg2 = (myName+":"+currMsg).getBytes();
		BigInteger biRep = new BigInteger(1, msg2);
		BigInteger biEnc = new BigInteger(1, cipher.encode(myName+":"+currMsg));
		System.out.println("\n\t\t##### Encryption Performed #####\n-------------------------------------------------------------------------------\nOriginal String Message:\t\t\t\t "+(myName+":"+currMsg)+"\nBigInteger representation of array:\t\t\t "+biRep+"\nBigInteger representation of encrypted array:\t\t "+biEnc);
		}
		catch (IOException ee)
		{
			ee.printStackTrace();
		}
	}											 	// to Server

	public static void main(String [] args)
	{
		 SecureChatClient JR = new SecureChatClient();
		 JR.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
}


