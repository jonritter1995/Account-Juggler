package com.lanshark.software.security.passwordmanager;

import com.lanshark.software.security.passwordmanager.com.lanshark.software.security.passwordmanager.gui.MainGUI;
import com.lanshark.software.security.passwordmanager.com.lanshark.software.security.passwordmanager.gui.PasswordGeneratorDialog;

import javax.swing.UIManager;

public class Main
{

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

        /*PasswordGenerator pg = new PasswordGenerator(20);
        Account account = new Account("Walmart");
        account.addEmail("Primary", "jonathan_ritter@troweprice.com");
        account.setUsername("jonritter1995");
        account.setPassword(pg.generatePassword());
        account.setPin("1995");
        account.addSecurityQuestion("What is your mother's maiden name?", "hargest");
        account.setNote("For shopping online at wally world.");

        System.out.println(account.getAccountName());
        System.out.println(account.getEmailAddress("Primary"));
        System.out.println(account.getUsername());
        System.out.println(account.getPassword());
        System.out.println(account.getPin());
        System.out.println(account.getNote());*/

        //new MainGUI();
        FileManager f = new FileManager("C:/Users/Jonathan/Desktop/password_file.xml");
        AccountManager manager = new AccountManager(f.loadAccounts(""));
        System.out.println(manager.toString());
        manager.getAccountByName("Walmart").setAccountName("target");
        f.savePasswordFile(manager.getAllAccounts(), "");
    }

}
