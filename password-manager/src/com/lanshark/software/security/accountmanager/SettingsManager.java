package com.lanshark.software.security.accountmanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class SettingsManager
{

    public static final String PASSWORD_FILE_SAVE_LOCATION = "PasswordFileSaveLocation";

    public static final String UI_THEME = "Theme";

    /**
     * The application's settings.
     */
    private Properties properties;

    private File propertyFile;

    private static final String propertyFilePath = SettingsManager.class.getProtectionDomain().getCodeSource().getLocation().getPath()
        + "settings";

    /**
     * Creates a new SettingsManager object.
     */
    public SettingsManager()
    {
        properties = new Properties();
        loadPropertyFile();
    }

    private void loadPropertyFile()
    {
        if (propertyFile == null)
            propertyFile = new File(propertyFilePath);

        try
        {
            if (propertyFile.exists())
            {
                FileInputStream fin = new FileInputStream(propertyFile);
                properties.load(fin);
                fin.close();
            }
            else
            {
                propertyFile.createNewFile();
                FileOutputStream fout = new FileOutputStream(propertyFile);
                properties.setProperty(PASSWORD_FILE_SAVE_LOCATION, "");
                properties.store(fout, null);
                fout.close();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            System.err.println("Unable to load property file.");
        }
    }

    public String getProperty(String propertyName)
    {
        return properties.getProperty(propertyName);
    }

    public void setProperty(String propertyName, String propteryValue)
    {
        properties.setProperty(propertyName, propteryValue);
    }

    public void saveSettings()
    {
        try
        {
            if (propertyFile == null)
                propertyFile = new File(propertyFilePath);

            FileOutputStream fout = new FileOutputStream(propertyFile);
            properties.store(fout, null);
            fout.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            System.err.println("Unable to load property file.");
        }
    }

}
