package com.lanshark.software.security.passwordmanager;

import com.lanshark.software.util.KeyValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Manages the reading and writing of the file containing passwords and account information.
 * The file is encrypted with AES.
 */
public class FileManager
{

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    private String filePath;

    private File passwordFile;

    private Document document;

    public FileManager(String filePath)
    {
        this.filePath = filePath;
    }

    public ArrayList<Account> loadAccounts(String masterPass)
    {
        readAccountFile(masterPass);
        return parseAccountDOM();
    }

    public void savePasswordFile(ArrayList<Account> accountList, String masterPass)
    {
        updateDocument(accountList);
        writeDocumentToFile(masterPass);
    }

    /**
     * Initialized the Document object that contains the XML representation of the user's Account list.
     */
    private void readAccountFile(String masterPass)
    {
        FileInputStream fin = null;
        ByteArrayInputStream xmlData = null;
        DocumentBuilderFactory dbf = null;
        DocumentBuilder db = null;
        passwordFile = new File(filePath);
        byte[] encryptedData = new byte[(int)passwordFile.length()];

        try
        {
            fin = new FileInputStream(passwordFile);
            fin.read(encryptedData);
            xmlData = new ByteArrayInputStream(encryptDecrypt(Cipher.DECRYPT_MODE, masterPass, encryptedData));
            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            document = db.parse(xmlData);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (ParserConfigurationException e)
        {
            e.printStackTrace();
        }
        catch (SAXException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
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
                    e.printStackTrace();
                }
            }
        }
    }

    private void writeDocumentToFile(String masterPass)
    {
        TransformerFactory tf = null;
        Transformer t = null;
        DOMSource domSource = null;
        StreamResult result = null;
        FileOutputStream fout = null;
        ByteArrayOutputStream byteOutputStream = null;
        ByteArrayInputStream byteInputStream = null;

        try {
            tf = TransformerFactory.newInstance();
            t = tf.newTransformer();
            domSource = new DOMSource(document);
            byteOutputStream = new ByteArrayOutputStream();
            result = new StreamResult(byteOutputStream);
            t.transform(domSource, result);
            byteInputStream = new ByteArrayInputStream(
                    encryptDecrypt(Cipher.ENCRYPT_MODE, masterPass, byteOutputStream.toByteArray())
            );
            fout = new FileOutputStream(new File(filePath));
            byteOutputStream.writeTo(fout);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (fout != null)
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
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

                for (KeyValuePair<String, String> email : account.getEmailAddresses())
                {
                    Element emailElement = document.createElement("email");
                    emailElement.setAttribute("id", email.getKey());
                    emailElement.setTextContent(email.getValue());
                    emailListElement.appendChild(emailElement);
                }

                for (KeyValuePair<String, String> securityQuestion : account.getSecurityQuestions())
                {
                    Element secQuestionElement = document.createElement("securityQuestion");
                    Element questionElement = document.createElement("question");
                    questionElement.setTextContent(securityQuestion.getKey());
                    Element answerElement = document.createElement("answer");
                    answerElement.setTextContent(securityQuestion.getValue());
                    secQuestionElement.appendChild(questionElement);
                    secQuestionElement.appendChild(answerElement);
                    secQuestionListElement.appendChild(secQuestionElement);
                }

                for (KeyValuePair<String, String> customField : account.getCustomFields())
                {
                    Element customFieldElement = document.createElement("field");
                    customFieldElement.setAttribute("key", customField.getKey());
                    customFieldElement.setTextContent(customField.getValue());
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
        catch (ParserConfigurationException e)
        {
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
            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);

            FileInputStream fin = new FileInputStream(passwordFile);
            result = cipher.doFinal(data);
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchPaddingException e)
        {
            e.printStackTrace();
        }
        catch (InvalidKeyException e)
        {
            e.printStackTrace();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (BadPaddingException e)
        {
            e.printStackTrace();
        }
        catch (IllegalBlockSizeException e)
        {
            e.printStackTrace();
        }
        finally
        {
            return result;
        }
    }

}
