package com.lanshark.software.security.passwordmanager;

import com.lanshark.software.security.passwordmanager.gui.MainGUI;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Main
{

    public static final String TITLE = "Password Jester";
    public static final String VERSION = "v0.0.1";

    public static MainGUI mainGUI;
    public static FileManager fileManager;
    public static AccountManager accountManager;
    public static SettingsManager settingsManager;

    public static void main(String[] args)
    {
        // Set the UI Look and Feel to Nimbus.
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

        settingsManager = new SettingsManager();

        // Make sure the password file is a valid location.
        while (settingsManager.getProperty(SettingsManager.PASSWORD_FILE_SAVE_LOCATION) == null
                || "".equals(settingsManager.getProperty(SettingsManager.PASSWORD_FILE_SAVE_LOCATION)))
        {
            JOptionPane.showMessageDialog(null,
                    "Please select a location to save your account information.");
            JFileChooser fileChooser = new JFileChooser();

            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                settingsManager.setProperty(SettingsManager.PASSWORD_FILE_SAVE_LOCATION, fileChooser.getSelectedFile().getPath());
                settingsManager.saveSettings();
                File accountFile = new File(settingsManager.getProperty(SettingsManager.PASSWORD_FILE_SAVE_LOCATION));

                try
                {
                    if (!accountFile.exists())
                        accountFile.createNewFile();
                }
                catch (IOException ex)
                {
                    System.err.println("Could not create account file.");
                    ex.printStackTrace();
                }
            }
        }

        fileManager = new FileManager(settingsManager.getProperty(SettingsManager.PASSWORD_FILE_SAVE_LOCATION));
        accountManager = new AccountManager(fileManager.loadAccounts("elderscrolls"));
        mainGUI = new MainGUI();
        //fileManager.saveAccounts(accountManager.getAllAccounts(), "elderscrolls");
    }

}
