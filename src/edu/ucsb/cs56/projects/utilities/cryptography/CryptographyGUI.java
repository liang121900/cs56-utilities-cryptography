
package edu.ucsb.cs56.projects.utilities.cryptography;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import java.io.*;

/**
   A class to implement the Cryptography GUI.
   @author Callum Steele
   @author Miranda Aperghis
   @author Ryan Peffers
   @version Project CS56, W15, 3/10/2015
*/

public class CryptographyGUI
{
    
    ShiftCipher shiftCipher = null;
    AffineCipher affineCipher = null;
    VigenereCipher vigenereCipher = null;
    BifidCipher bifidCipher = null;

    String plainText = null;
    String cipherText = null;
    int shiftKey, keyA, keyB, affineKeyA, affineKeyB, last;
    String key;
    String[] inputs;
    String[] decryptInputs;
    String[] shiftInputs;
    String[] affineInputs;
    String[] vigenereInputs;
    String[] bifidInputs;

    JFrame frame;
    JButton shift, vigenere, affine, bifid, all, current, mode, info;
    JPanel buttonPanel, cipherButtonPanel, modeButtonInfoPanel, textFieldPanel, inputTextPanel, inputKeyTextPanel;
    JTextField input, keyInput;
    JLabel output, inputText, inputKeyText;

    boolean encryptMode = true;

    /** Calls the function to create the GUI.
	@param args Default arguments sent to main.
     */
    public static void main (String[] args) {
	    CryptographyGUI cryptoGUI = new CryptographyGUI();

	    cryptoGUI.go();
    }

    /** Function that populates and creates the GUI.
     */
    public void go () {
        // initialise Cipher objects
        shiftCipher = new ShiftCipher();
        affineCipher = new AffineCipher();
        vigenereCipher = new VigenereCipher();
        bifidCipher = new BifidCipher();

        // setup overall frame options
        frame = new JFrame();
        frame.setSize(720,180);
        frame.setTitle("Cryptography Interface");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // create panel for text fields
        textFieldPanel = new JPanel();
        textFieldPanel.setLayout(new BoxLayout(textFieldPanel, BoxLayout.Y_AXIS));

        // create panel containing label and textfield for plaintext input
        inputTextPanel = new JPanel();
        inputTextPanel.setLayout(new BoxLayout(inputTextPanel, BoxLayout.X_AXIS));
        inputText = new JLabel();
        inputText.setText("Plaintext: ");
        input = new JTextField();
        inputTextPanel.add(inputText);
        inputTextPanel.add(input);

        // create panel containing label and textfield for key input
        inputKeyTextPanel = new JPanel();
        inputKeyTextPanel.setLayout(new BoxLayout(inputKeyTextPanel, BoxLayout.X_AXIS));
        inputKeyText = new JLabel();
        inputKeyText.setText("Key(s): ");
        keyInput = new JTextField();
        inputKeyTextPanel.add(inputKeyText);
        inputKeyTextPanel.add(keyInput);

        // add panels to overall text field panel
        textFieldPanel.add(inputTextPanel);
        textFieldPanel.add(inputKeyTextPanel);

        // create panel for cipher buttons
        cipherButtonPanel = new JPanel();
        cipherButtonPanel.setLayout(new BoxLayout(cipherButtonPanel, BoxLayout.X_AXIS));

        // create cipher buttons and add listeners
        shift = new JButton("Shift Cipher");
        shift.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // get text from plaintext text field
                plainText = input.getText();
		inputs = plainText.split("\\s+");

                // get key from key text field and set it as key in cipher object
                try {
                shiftKey = Integer.parseInt(keyInput.getText());
                shiftCipher.setCipherKey(shiftKey);
		cipherText = "";
                // checks if encrypting or decrypting
		for(int i = 0; i < inputs.length; i++) {
		    if (encryptMode)
			cipherText += shiftCipher.encrypt(inputs[i]);
		    else
			cipherText += shiftCipher.decrypt(inputs[i]);
		    if(i < inputs.length - 1)
			cipherText += " ";
		}
		

		try {
		    PrintWriter out = new PrintWriter("output.txt");
		    
		    for(int i = 0; i < inputs.length; i++) {
			out.print(inputs[i]);
			out.print(" ");
		    }
		    out.println();
		    out.println(keyInput.getText());
		    out.println(cipherText);
		    out.close();
		}
		catch (Exception ex){
		    ex.printStackTrace();
		}
		// puts result in the output label
                output.setText(cipherText);
		last = 0;

                } catch (Exception ex) {
                // create popup
                messagePopUp("Incorrect input for Shift Cipher.\nPlaintext is " +
                        "a string and can't have non-alphabetic " +
                        "characters.\nKey should contain a single integer within " +
                        "the range 0 to 25.\n", "Shift Cipher Input Error");
                }
            }
            });
        affine = new JButton("Affine Cipher");
        affine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // get text from plaintext text field
                plainText = input.getText();
	        inputs = plainText.split("\\s+");
		                // gets keys from key text field and sets as the keys in cipher object
                try {
                key = keyInput.getText();
                keyA = Integer.parseInt(key.substring(0, key.indexOf(' ')));
                keyB = Integer.parseInt(key.substring(key.indexOf(' ') + 1));
                affineCipher.setKeyA(keyA);
                affineCipher.setKeyB(keyB);
		cipherText = "";
		
                // checks if encrypting or decrypting
		for(int i = 0; i < inputs.length; i++) {		
		    if (encryptMode)
			cipherText += affineCipher.encrypt(inputs[i]);
		    else
			cipherText += affineCipher.decrypt(inputs[i]);
		    if(i < inputs.length - 1)
			cipherText += " ";
		}
                
		try {
		    PrintWriter out = new PrintWriter("output.txt");
		    
		    for(int i = 0; i < inputs.length; i++) {
			out.print(inputs[i]);
			out.print(" ");
		    }
		    out.println();
		    out.println(keyInput.getText());
		    out.println(cipherText);
		    out.close();
		}
		catch (Exception ex){
		    ex.printStackTrace();
		}
		// puts result in the output label
                output.setText(cipherText);
		last = 1;
		
                } catch (Exception ex) {
                // create popup
                messagePopUp("Incorrect input for Affine Cipher.\nPlaintext " +
                        "input is a String without non-alphabetic characters.\nKey takes 2 integers " +
                        "(a and b) separated by a single space the first \ninteger " +
                        "being within the range 0 to 25 and the second greater than 0.\n" +
                        "If Decrypting, the second integer must not be a coprime with 26",
			     "Affine Cipher Input Error");
                }
            }
            });
        vigenere = new JButton("Vigenere Cipher");
        vigenere.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // get text from plaintext text field
                plainText = input.getText();
	        inputs = plainText.split("\\s+");
		
                // gets key from key text field and sets as the key in cipher object
                try {
                key = keyInput.getText();
                vigenereCipher.setCipherKey(key);
		cipherText = "";
		
		// checks if encrypting or decrypting
		for(int i = 0; i < inputs.length; i++) {		
		    if (encryptMode)
			cipherText += vigenereCipher.encrypt(inputs[i]);
		    else
			cipherText += vigenereCipher.decrypt(inputs[i]);
		    if(i < inputs.length - 1)
			cipherText += " ";
		}

		try {
		    PrintWriter out = new PrintWriter("output.txt");
		    
		    for(int i = 0; i < inputs.length; i++) {
			out.print(inputs[i]);
			out.print(" ");
		    }
		    out.println();
		    out.println(keyInput.getText());
		    out.println(cipherText);
		    out.close();
		}
		catch (Exception ex){
		    ex.printStackTrace();
		}
		
                // puts result in the output label
                output.setText(cipherText);
		last = 2;
		
                } catch (Exception ex) {
                // create popup
                messagePopUp("Incorrect input for Vigenere Cipher.\nPlaintext " +
                        "is a String with only letters.\nKey is a String with " +
                        "only letters.\nNumbers will cause an exception " +
                        "to be thrown", "Vigenere Cipher Input Error");
                }
            }
            });
        bifid = new JButton("Bifid Cipher");
        bifid.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // get text from plaintext text field
                plainText = input.getText();
	        inputs = plainText.split("\\s+");
		
                // get key from key text field and set it as key in cipher object
                try {
                key = keyInput.getText();
		bifidCipher.setCipherKey(key);
		cipherText = "";
		
		// checks if encrypting or decrypting
		for(int i = 0; i < inputs.length; i++) {		
		    if (encryptMode)
			cipherText += bifidCipher.encrypt(inputs[i]);
		    else
			cipherText += bifidCipher.decrypt(inputs[i]);
		    if(i < inputs.length - 1)
			cipherText += " ";
		}
		
		try {
		    PrintWriter out = new PrintWriter("output.txt");
		    
		    for(int i = 0; i < inputs.length; i++) {
			out.print(inputs[i]);
			out.print(" ");
		    }
		    out.println();
		    out.println(keyInput.getText());
		    out.println(cipherText);
		    out.close();
		}
		catch (Exception ex){
		    ex.printStackTrace();
		}

		// Puts result in the output label
		output.setText(cipherText);
		last = 3;
		
                } catch (Exception ex) {
                    // create popup
                    messagePopUp("Incorrect input for Bifid Cipher.\nPlaintext " +
                            "input can be any String so long as there are no numbers.\nKey " +
                            "input can be any String with at least one uppercase letter " +
			    "as well as no numbers.\nAll non-alphabetic characters will " +
			    "be deleted from the String.",
			    "Bifid Cipher Input Error");
                }
            }	
	    });

	all = new JButton("All Ciphers");
	all.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		    
		// get text from plaintest test field
		plainText = input.getText();
	        inputs = plainText.split("\\s+");
		// if in decryption, stores all texts for each string
		if(!(encryptMode)) {
		        decryptInputs = plainText.split(",");
			for(int x = 0; x < decryptInputs.length; x++)
			    System.out.println(decryptInputs[x]);
			
			shiftInputs = decryptInputs[0].split("\\s+");
			affineInputs = decryptInputs[1].split("\\s+");
			vigenereInputs = decryptInputs[2].split("\\s+");
			bifidInputs = decryptInputs[3].split("\\s+");			
	        }
		    
		try {
		    
		    // get keys for each cypher type
		    key = keyInput.getText();
		    String[] keyInputs = key.split(",");
		    for(int x = 0; x < keyInputs.length; x++)
			System.out.println(keyInputs[x]);
		    int shiftKey = Integer.parseInt(keyInputs[0]);
		    shiftCipher.setCipherKey(shiftKey);
		    affineKeyA = Integer.parseInt(keyInputs[1].substring(0,
						  keyInputs[1].indexOf(' ')));
		    affineKeyB = Integer.parseInt(keyInputs[1].substring
						  (keyInputs[1].indexOf(' ') + 1));
		    affineCipher.setKeyA(affineKeyA);
		    affineCipher.setKeyB(affineKeyB);
		    vigenereCipher.setCipherKey(keyInputs[2]);
		    bifidCipher.setCipherKey(keyInputs[3]);
		    cipherText = "";
		    //Adds all encryption/decryption to output string
		    if(encryptMode) {
			cipherText = "Shift: ";
			for(int a = 0; a < inputs.length; a++) {
			    cipherText += shiftCipher.encrypt(inputs[a]);
			    cipherText += " ";
			}
			cipherText += "Affine: ";
			for(int b = 0; b < inputs.length; b++) {
			    cipherText += affineCipher.encrypt(inputs[b]);
			    cipherText += " ";
			}
			cipherText += "Vigenere: ";
			for(int c = 0; c < inputs.length; c++) {
			    cipherText += vigenereCipher.encrypt(inputs[c]);
			    cipherText += " ";
			}
			cipherText += "Bifid: ";
			for(int d = 0; d < inputs.length; d++) {
			    cipherText += bifidCipher.encrypt(inputs[d]);
			    cipherText += " ";
			}
		    }
		    else {
			cipherText = "Shift: ";
			for(int a = 0; a < shiftInputs.length; a++) {
			    cipherText += shiftCipher.decrypt(shiftInputs[a]);
			    cipherText += " ";
			}
			
			cipherText += "Affine: ";
			for(int b = 0; b < affineInputs.length; b++) {
			    cipherText += affineCipher.decrypt(affineInputs[b]);
			    cipherText += " ";
			}
			
			cipherText += "Vigenere: ";
			for(int c = 0; c < vigenereInputs.length; c++) {
			    cipherText += vigenereCipher.decrypt(vigenereInputs[c]);
			    cipherText += " ";
			}
			
			cipherText += "Bifid: ";
			for(int d = 0; d < shiftInputs.length; d++) {
			    cipherText += bifidCipher.decrypt(bifidInputs[d]);
			    cipherText += " ";
			}
		    }
		    
		    try {
			PrintWriter out = new PrintWriter("output.txt");
		    
			for(int i = 0; i < inputs.length; i++) {
			    out.print(inputs[i]);
			    out.print(" ");
			}
			out.println();
			out.println(keyInput.getText());
			out.println(cipherText);
			out.close();
		    }
		    catch (Exception ex){
			ex.printStackTrace();
		    }
		    System.out.println(cipherText);
                    // puts result in the output label
                    output.setText(cipherText);
		    
                } catch (Exception ex) {
                    // create popup
                    messagePopUp("Wrong keys, please see info", "All Cipher Input Error");
                }
            }
        });		    

	current = new JButton("Current");
	current.addActionListener(new ActionListener() {
	   public void actionPerformed(ActionEvent e) {
	        // checks if encryption or decryption mode and switches
                if (encryptMode) {
                encryptMode = false;
                } else {
                encryptMode = true;
                }
		output.setText("");
		input.setText("");
		keyInput.setText("");
		String currentCipher = "";
		try {
		    inputs = cipherText.split("\\s+");
		    if(last == 0) {
			shiftCipher.setCipherKey(shiftKey);
		    }
		    if(last == 1) {
			affineCipher.setKeyA(keyA);
			affineCipher.setKeyB(keyB);
		    }
		    if(last == 2)
			vigenereCipher.setCipherKey(key);
		    if(last == 3)
		    bifidCipher.setCipherKey(key);
		    //Adds all encryption/decryption to output string

		    if(last == 0) {
			for(int i = 0; i < inputs.length; i++) {
			    if (encryptMode)
				currentCipher += shiftCipher.encrypt(inputs[i]);
			    else
				currentCipher += shiftCipher.decrypt(inputs[i]);
			    if(i < inputs.length - 1)
				currentCipher += " ";
			}
		    }
		    if(last == 1) {
		        for(int i = 0; i < inputs.length; i++) {		
			    if (encryptMode)
				currentCipher += affineCipher.encrypt(inputs[i]);
			    else
				currentCipher += affineCipher.decrypt(inputs[i]);
			    if(i < inputs.length - 1)
				currentCipher += " ";
			}
		    }
		    if(last == 2) {
			for(int i = 0; i < inputs.length; i++) {		
			    if (encryptMode)
				currentCipher += vigenereCipher.encrypt(inputs[i]);
			    else
				currentCipher += vigenereCipher.decrypt(inputs[i]);
			    if(i < inputs.length - 1)
				currentCipher += " ";
			}
		    }
		    if(last == 3) {
		        for(int i = 0; i < inputs.length; i++) {		
			    if (encryptMode)
				currentCipher += bifidCipher.encrypt(inputs[i]);
			    else
				currentCipher += bifidCipher.decrypt(inputs[i]);
			    if(i < inputs.length - 1)
				currentCipher += " ";
			}
		    }
		    try {
			PrintWriter out = new PrintWriter("output.txt");
		    
			for(int i = 0; i < inputs.length; i++) {
			    out.print(inputs[i]);
			    out.print(" ");
			}
			out.println();
			out.println(keyInput.getText());
			out.println(cipherText);
			out.close();
		    }
		    catch (Exception ex){
			ex.printStackTrace();
		    }
		    
                    // puts result in the output label
		    
                    output.setText(currentCipher);
		    if (encryptMode) {
			encryptMode = false;
		    } else {
			encryptMode = true;
		    }
                } catch (Exception ex) {
                    // create popup
                    messagePopUp("Please do not change input fields to decrypt " +
				 " or encrypt current cipher text.", "Curent Cipher Error");
                }
	   }
	   });
	
		 
        // adds cipher buttons to overall cipher button panel
        cipherButtonPanel.add(shift);
        cipherButtonPanel.add(affine);
        cipherButtonPanel.add(vigenere);
        cipherButtonPanel.add(bifid);
	cipherButtonPanel.add(all);
	cipherButtonPanel.add(current);

        // creates panel for mode button
        modeButtonInfoPanel = new JPanel();


        // create mode button and adds listener
        mode = new JButton("Switch to decryption");
        mode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // checks if encryption or decryption mode
                if (encryptMode) {
                encryptMode = false;
                mode.setText("Switch to encryption");
                } else {
                encryptMode = true;
                mode.setText("Switch to decryption");
                }
            }
            });

        // create info button and adds listener
        info = new JButton("Info");
        info.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // checks if encryption or decryption mode
                messagePopUp("Format of Input for GUI:\n\nGeneral:\n    Spaces " +
                        "can be used for plaintext input fields, but only certain keys " +
			"utilize spaces. \nCapital letters for plaintext can be used " +
			"but will be translated to lowercase \nprior " +
                        "to any cryptographic operation being performed on them.\n\nShift " +
                        "Cipher:\n    Key text field should contain a single integer " +
                        "within the range of 0 to 25 inclusive.\n\nAffine Cipher:\n    Key " +
                        "text field should contain two integers seperated by a single " +
                        "space, \nwith the first integer being with the range of 0 to 25 " +
                        "\ninclusive and the second integer being greater than 0. \nIf decrypting, " +
                        "the additional condition that the first integer must \nnot be a " +
                        "coprime with 26 also holds.\n\nVigenere Cipher:\n    Key text field " +
                        "should contain a string of characters.\n\nBifid Cipher:\n    The " +
                        "plaintext for the Bifid Cipher can contain spaces but no numbers \nand " +
                        "it will convert all characters to uppercase. " +
                        "Key text field should contain\n a string of characters with at least " +
			"one uppercase letter.\n\n All Ciphers:\n    Keys should follow the format " +
			"of the respective cyphers. \nAll four keys must be used and there " +
                        "cannot be any spaces \nwithin the key string except " +
			"for the affine cypher key.\nCurrent: Do not change or input new strings " +
			"into the input or key text fields. \nThe program will clear the textfields " +
			"and will encrypt or decrypt the last encrypted string."+
                        "\nWhen decrypting, separate the plaintext inputs " +
			"using \ncommas with no spaces before or after the comma."
			     ,"Info message");
            }
        });

        // adds mode button to mode panel
        modeButtonInfoPanel.add(mode);
        modeButtonInfoPanel.add(info);

        // creates overall button panel and adds cipher/mode button panels
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(cipherButtonPanel);
        buttonPanel.add(modeButtonInfoPanel);

        // creates output label
        output = new JLabel();

        // adds components to overall frame
        frame.getContentPane().add(BorderLayout.CENTER, buttonPanel);
        frame.getContentPane().add(BorderLayout.NORTH, textFieldPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, output);

        // sets frame visible
        frame.setVisible(true);
    }

    public void messagePopUp(String message, String title) {
	    JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
