package com.lanshark.software.security.accountmanager;

import com.lanshark.software.security.accountmanager.gui.MainGUI;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * Launches the Account-Juggler application.
 *
 * @author Jonathan Ritter
 */
public class Main
{
    /**
     * Name of the application. Displays on the top of the application window.
     */
    public static final String TITLE = "Password Jester";

    /**
     * The build version of the application.
     */
    public static final String VERSION = "v0.0.1";

    public static MainGUI mainGUI;
    public static FileManager fileManager;
    public static AccountManager accountManager;
    public static SettingsManager settingsManager;

    public static void main(String[] args)
    {
        // Load the application settings and set UI theme.
        settingsManager = new SettingsManager();
        setLookAndFeel(settingsManager.getProperty(SettingsManager.UI_THEME));

        // Make sure the account file is a valid location. If not, set account file save location.
        while (settingsManager.getProperty(SettingsManager.PASSWORD_FILE_SAVE_LOCATION) == null
                || "".equals(settingsManager.getProperty(SettingsManager.PASSWORD_FILE_SAVE_LOCATION)))
        {
            JOptionPane.showMessageDialog(null,
                    "Please select a location to save your account information.");
            JFileChooser fileChooser = new JFileChooser();

            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                settingsManager.setProperty(SettingsManager.PASSWORD_FILE_SAVE_LOCATION,
                        fileChooser.getSelectedFile().getPath());
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

        // Load the account file and launch UI.
        fileManager = new FileManager(settingsManager.getProperty(SettingsManager.PASSWORD_FILE_SAVE_LOCATION));
        accountManager = new AccountManager(fileManager.loadAccounts("elderscrolls"));
        mainGUI = new MainGUI();
        //fileManager.saveAccounts(accountManager.getAllAccounts(), "elderscrolls");
    }

    /**
     * Sets the look and feel of the UI based on the application settings.
     */
    private static void setLookAndFeel(String theme)
    {
        try
        {
            switch (theme)
            {
                case "Native":
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    break;
                case "Nimbus":
                    for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
                    {
                        if ("Nimbus".equals(info.getName()))
                        {
                            UIManager.setLookAndFeel(info.getClassName());
                            break;
                        }
                    }

                    break;
                default: // Todo: handle more look & feels
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
