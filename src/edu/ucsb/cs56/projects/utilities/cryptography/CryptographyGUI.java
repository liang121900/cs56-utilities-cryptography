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
    int keyA, keyB;
    String key;

    JFrame frame;
    JButton shift, vigenere, affine, bifid, mode, info;
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
        frame.setSize(520,180);
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

                // get key from key text field and set it as key in cipher object
                try {
                keyA = Integer.parseInt(keyInput.getText());
                shiftCipher.setCipherKey(keyA);

                // checks if encrypting or decrypting
                if (encryptMode)
                    cipherText = shiftCipher.encrypt(plainText);
                else
                    cipherText = shiftCipher.decrypt(plainText);

                // puts result in the output label
                output.setText(cipherText);
                } catch (Exception ex) {
                // create popup
                messagePopUp("Incorrect input for Shift Cipher.\nPlaintext is " +
                        "a string and can't have spaces or non-alphabetic " +
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

                // gets keys from key text field and sets as the keys in cipher object
                try {
                key = keyInput.getText();
                keyA = Integer.parseInt(key.substring(0, key.indexOf(' ')));
                keyB = Integer.parseInt(key.substring(key.indexOf(' ') + 1));
                affineCipher.setKeyA(keyA);
                affineCipher.setKeyB(keyB);

                // checks if encrypting or decrypting
                if (encryptMode)
                    cipherText = affineCipher.encrypt(plainText);
                else
                    cipherText = affineCipher.decrypt(plainText);

                // puts result in the output label
                output.setText(cipherText);
                } catch (Exception ex) {
                // create popup
                messagePopUp("Incorrect input for Affine Cipher.\nPlaintext " +
                        "input is a String with only letters.\nKey takes 2 integers " +
                        "(a and b) separated by a single space the first \ninteger " +
                        "being within the range 0 to 25 and the second greater than 0.\n" +
                        "If Decrypting, the second integer must not be a coprime with 26", "Affine Cipher Input Error");
                }
            }
            });
        vigenere = new JButton("Vigenere Cipher");
        vigenere.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // get text from plaintext text field
                plainText = input.getText();

                // gets key from key text field and sets as the key in cipher object
                try {
                key = keyInput.getText();
                vigenereCipher.setCipherKey(key);

                // checks if encrypting or decrypting
                if (encryptMode)
                    cipherText = vigenereCipher.encrypt(plainText);
                else
                    cipherText = vigenereCipher.decrypt(plainText);

                // puts result in the output label
                output.setText(cipherText);
                } catch (Exception ex) {
                // create popup
                messagePopUp("Incorrect input for Vigenere Cipher.\nPlaintext " +
                        "is a String with only letters.\nKey is a String with " +
                        "only letters.\nSpaces and numbers will cause an exception " +
                        "to be thrown", "Vigenere Cipher Input Error");
                }
            }
            });
        bifid = new JButton("Bifid Cipher");
        bifid.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // get text from plaintext text field
                plainText = input.getText();

                // get key from key text field and set it as key in cipher object
                try {
                    key = keyInput.getText();
                    bifidCipher.setCipherKey(key);

                    // checks if encrypting or decrypting
                    if (encryptMode)
                        cipherText = bifidCipher.encrypt(plainText);
                    else
                        cipherText = bifidCipher.decrypt(plainText);

                    // puts result in the output label
                    output.setText(cipherText);
                } catch (Exception ex) {
                    // create popup
                    messagePopUp("Incorrect input for Bifid Cipher.\nPlaintext " +
                            "input can be any String so long as there are no numbers.\nKey " +
                            "input can be any String that are not numbers.\nAll spaces and " +
                            "other characters will be deleted from the String.", "Bifid Cipher Input Error");
                }
            }
        });

        // adds cipher buttons to overall cipher button panel
        cipherButtonPanel.add(shift);
        cipherButtonPanel.add(affine);
        cipherButtonPanel.add(vigenere);
        cipherButtonPanel.add(bifid);

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
                messagePopUp("Format of Input for GUI:\n\nGeneral:\n    No spaces " +
                        "are to be used in the key or plaintext input fields. \nCapital " +
                        "letters can be used but will be translated to lowercase \nprior " +
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
                        "it will convert all characters to uppercase and remove all \nspaces. " +
                        "Key text field should contain a string of characters\n","Info message");
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
