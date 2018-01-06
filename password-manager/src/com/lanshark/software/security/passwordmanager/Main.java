package com.lanshark.software.security.passwordmanager;

import com.lanshark.software.security.passwordmanager.com.lanshark.software.security.passwordmanager.gui.MainGUI;

import javax.crypto.Cipher;
import javax.swing.UIManager;
import java.io.*;

public class Main
{

    public static MainGUI mainGUI;
    public static FileManager fileManager;
    public static AccountManager accountManager;
    public static SettingsManager settingsManager;

    public static void main(String[] args)
    {
        // Set the UI Look and Feel to Nimbus
        try
        {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        mainGUI = new MainGUI();
        fileManager = new FileManager("C:/Users/Jonathan/Desktop/password_file.xml");

        /*try {
            File f = new File("C:/Users/Jonathan/Desktop/encrypted.txt");
            FileInputStream fin = new FileInputStream(f);
            byte[] b = new byte[(int)f.length()];
            fin.read(b);
            System.out.println(new String(fileManager.encryptDecrypt(Cipher.DECRYPT_MODE, "1234567890123456", b)));
        } catch (Exception ex) { ex.printStackTrace();}*/
        accountManager = new AccountManager(fileManager.loadAccounts("elderscrolls"));
        System.out.println(accountManager.toString());
        fileManager.saveAccounts(accountManager.getAllAccounts(), "elderscrolls");
    }

}
