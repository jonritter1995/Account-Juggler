package com.lanshark.software.security.accountmanager;

import com.lanshark.software.security.accountmanager.util.ComplexFieldPair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Manages the reading and writing of the file containing passwords and account information.
 * The file is encrypted with AES.
 */
public class FileManager
{
    /**
     * The encryption algorithm String.
     */
    private static final String ALGORITHM = "AES";

    /**
     * The encryption transformation String.
     */
    private static final String TRANSFORMATION = "AES";

    /**
     * The path to the user's password file.
     */
    private String filePath;

    /**
     * The user's password file.
     */
    private File passwordFile;

    /**
     * The XML document that represents the AccountManager.
     */
    private Document document;

    /**
     * Creates a new FileManager object.
     *
     * @param filePath  The file path of the user's password file.
     */
    public FileManager(String filePath)
    {
        this.filePath = filePath;
    }

    /**
     * Loads a list of accounts from the password file.
     *
     * @param masterPass    The password to decrypt the file with.
     * @return              An ArrayList of Account objects.
     */
    public ArrayList<Account> loadAccounts(String masterPass)
    {
        readAccountFile(masterPass);
        return parseAccountDOM();
    }

    /**
     * Saves the given list of Account objects to the account file and encrypts it.
     *
     * @param accountList   The current list of Account objects.
     * @param masterPass    The password to encrypt the file with.
     */
    public void saveAccounts(ArrayList<Account> accountList, String masterPass)
    {
        updateDocument(accountList);
        writeDocumentToFile(masterPass);
    }

    /**
     * Initialized the Document object that contains the XML representation of the user's Account list.
     *
     * @param masterPass    The password to use for decrypting the account file.
     */
    private void readAccountFile(String masterPass)
    {
        FileInputStream fin = null;
        ByteArrayInputStream xmlData = null;
        DocumentBuilderFactory dbf = null;
        DocumentBuilder db = null;

        if (passwordFile == null)
            passwordFile = new File(filePath);

        byte[] encryptedData = new byte[(int)passwordFile.length()];

        try
        {
            fin = new FileInputStream(passwordFile);
            fin.read(encryptedData);
            xmlData = new ByteArrayInputStream(encryptDecrypt(Cipher.DECRYPT_MODE, masterPass, encryptedData));
            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();

            if (encryptedData.length > 0)
                document = db.parse(xmlData);
            else
                document = db.newDocument();
        }
        catch (Exception e)
        {
            System.err.println("Unable to read account file.\n\n");
            e.printStackTrace();
            document = db.newDocument();
        }
        finally
        {
            if (fin != null)
            {
                try
                {
                    fin.close();
                }
                catch (IOException e)
                {
                    System.err.println("Unable to close account file input stream.\n\n");
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Writes the XML representation of the account list to a file.
     *
     * @param masterPass    The password to encrypt the file with.
     */
    private void writeDocumentToFile(String masterPass)
    {
        TransformerFactory tf = null;
        Transformer t = null;
        DOMSource domSource = null;
        StreamResult result = null;
        FileOutputStream fout = null;
        ByteArrayOutputStream xmlByteStream = null;
        ByteArrayOutputStream encryptedByteStream = null;
        ByteArrayInputStream byteInputStream = null;

        if (passwordFile == null)
            passwordFile = new File(filePath);

        try
        {
            tf = TransformerFactory.newInstance();
            t = tf.newTransformer();
            domSource = new DOMSource(document);
            xmlByteStream = new ByteArrayOutputStream();
            result = new StreamResult(xmlByteStream);
            t.transform(domSource, result);
            byte[] inputBytes = encryptDecrypt(Cipher.ENCRYPT_MODE, masterPass, xmlByteStream.toByteArray());
            encryptedByteStream = new ByteArrayOutputStream();
            encryptedByteStream.write(inputBytes);
            fout = new FileOutputStream(passwordFile);
            encryptedByteStream.writeTo(fout);
        }
        catch (Exception ex)
        {
            System.err.println("Unable to write to document file.\n\n");
            ex.printStackTrace();
        }
        finally
        {
            if (fout != null)
            {
                try
                {
                    fout.close();
                    xmlByteStream.close();
                    encryptedByteStream.close();
                }
                catch (IOException e)
                {
                    System.err.println("Could not close document output stream.");
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Parses the Document containing the user's account information and creates a list of Account objects from
     * the data.
     *
     * @return  An ArrayList<Account> object that can be used to initialize an AccountManager object.
     */
    private ArrayList<Account> parseAccountDOM()
    {
        ArrayList<Account> accountList = new ArrayList<Account>();
        NodeList accountNodes = document.getElementsByTagName("Account");

        for (int i = 0; i < accountNodes.getLength(); i++)
        {
            Element accountElement = (Element)accountNodes.item(i);
            Account account = new Account(accountElement.getAttribute("name"));
            account.setUsername(accountElement.getElementsByTagName("username").item(0).getTextContent());
            account.setPassword(accountElement.getElementsByTagName("password").item(0).getTextContent());
            account.setPin(accountElement.getElementsByTagName("pin").item(0).getTextContent());
            NodeList emailList = accountElement.getElementsByTagName("email");
            NodeList securityQuestions = accountElement.getElementsByTagName("securityQuestion");
            NodeList customFields = accountElement.getElementsByTagName("field");

            for (int x = 0; x < emailList.getLength(); x++)
            {
                account.addEmail(emailList.item(x).getAttributes().item(0).getTextContent(),
                        emailList.item(x).getTextContent());
            }

            for (int x = 0; x < securityQuestions.getLength(); x++)
            {
                Element e = (Element)securityQuestions.item(x);
                account.addSecurityQuestion(e.getElementsByTagName("question").item(0).getTextContent(),
                        e.getElementsByTagName("answer").item(0).getTextContent());
            }

            for (int x = 0; x < customFields.getLength(); x++)
            {
                account.addCustomField(customFields.item(x).getAttributes().item(0).getTextContent(),
                        customFields.item(x).getTextContent());
            }

            account.setNote(accountElement.getElementsByTagName("note").item(0).getTextContent());
            accountList.add(account);
        }

        return accountList;
    }

    /**
     * Updates the XML document to reflect the given Account List
     *
     * @param accountList   The account list to write as XML.
     */
    private void updateDocument(ArrayList<Account> accountList)
    {
        DocumentBuilderFactory dbf = null;
        DocumentBuilder db = null;

        try
        {
            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            document = db.newDocument();

            Element rootElement = document.createElement("Accounts");
            document.appendChild(rootElement);

            for (Account account : accountList)
            {
                Element accountElement = document.createElement("Account");
                accountElement.setAttribute("name", account.getAccountName());

                Element usernameElement = document.createElement("username");
                usernameElement.setTextContent(account.getUsername());
                accountElement.appendChild(usernameElement);

                Element passwordElement = document.createElement("password");
                passwordElement.setTextContent(account.getPassword());
                accountElement.appendChild(passwordElement);

                Element pinElement = document.createElement("pin");
                pinElement.setTextContent(account.getPin());
                accountElement.appendChild(pinElement);

                Element emailListElement = document.createElement("emailAddresses");
                Element secQuestionListElement = document.createElement("securityQuestions");
                Element customFieldListElement = document.createElement("customFields");

                for (ComplexFieldPair<String, String> email : account.getEmailAddresses())
                {
                    Element emailElement = document.createElement("email");
                    emailElement.setAttribute("id", (String)email.getKey());
                    emailElement.setTextContent((String)email.getValue());
                    emailListElement.appendChild(emailElement);
                }

                for (ComplexFieldPair<String, String> securityQuestion : account.getSecurityQuestions())
                {
                    Element secQuestionElement = document.createElement("securityQuestion");
                    Element questionElement = document.createElement("question");
                    questionElement.setTextContent((String)securityQuestion.getKey());
                    Element answerElement = document.createElement("answer");
                    answerElement.setTextContent((String)securityQuestion.getValue());
                    secQuestionElement.appendChild(questionElement);
                    secQuestionElement.appendChild(answerElement);
                    secQuestionListElement.appendChild(secQuestionElement);
                }

                for (ComplexFieldPair<String, String> customField : account.getCustomFields())
                {
                    Element customFieldElement = document.createElement("field");
                    customFieldElement.setAttribute("key", (String)customField.getKey());
                    customFieldElement.setTextContent((String)customField.getValue());
                    customFieldListElement.appendChild(customFieldElement);
                }

                accountElement.appendChild(emailListElement);
                accountElement.appendChild(secQuestionListElement);
                accountElement.appendChild(customFieldListElement);

                Element noteElement = document.createElement("note");
                noteElement.setTextContent(account.getNote());
                accountElement.appendChild(noteElement);
                rootElement.appendChild(accountElement);
            }
        }
        catch (Exception e)
        {
            System.err.println("Unable to update document\n\n");
            e.printStackTrace();
        }
    }

    /**
     * Encrypts or decrypts the specified byte array.
     * Returns null if encryption/decryption failed.
     *
     * @param cipherMode    The cipher mode (encrypt or decrypt).
     * @param key           The key to use for encrypting or decrypting.
     * @param data          The data to encrypt or decrypt.
     * @return              The encrypted/decrypted data.
     */
    public byte[] encryptDecrypt(int cipherMode, String key, byte[] data)
    {
        if ("".equals(key) || key == null)
            return data;

        byte[] result = null;

        try
        {
            Key secretKey = new SecretKeySpec(padKey(key), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);
            result = cipher.doFinal(data);
        }
        catch (Exception e)
        {
            System.err.println("Unable to " + (cipherMode == Cipher.DECRYPT_MODE ? "decrypt " : "encrypt ") + "data\n\n");
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Pads the key if it is not 16 or 32 bytes.
     *
     * @param key   The master key to pad.
     * @return      A padded key of length 16/32.
     * todo cap length at 32 bytes.
     */
    private byte[] padKey(String key)
    {
        return (key.getBytes().length % 16 == 0 ? key.getBytes() : Arrays.copyOf(key.getBytes(),
                (key.getBytes().length / 16) * 16 + 16));
    }

}
