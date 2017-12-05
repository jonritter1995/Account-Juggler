package com.lanshark.software.security.passwordmanager;

import com.lanshark.software.util.KeyValuePair;

import java.util.ArrayList;

/**
 * Represents an online Account which contains confidential information pertaining to the user.
 *
 * @author  Jonthan Ritter
 * @version 1.0
 */
public class Account
{

    /**
     * The name of the service that the account is for.
     * i.e. Walmart, Bank of America, League of Legends, etc.
     */
    private String accountName;

    /**
     * The username associated with the account.
     */
    private String username;

    /**
     * The password to the account.
     */
    private String password;

    /**
     * A pin number used to access the account.
     */
    private String pin;

    /**
     * A list of email addresses associated with the account.
     * i.e. Primary email, recovery email.
     */
    private ArrayList<KeyValuePair<String, String>> emailAddresses = new ArrayList<KeyValuePair<String, String>>();

    /**
     * Security questions and answers linked to the account.
     */
    private ArrayList<KeyValuePair<String, String>> securityQuestions = new ArrayList<KeyValuePair<String, String>>();

    /**
     * Custom fields for accounts with other settings.
     */
    private ArrayList<KeyValuePair<String, String>> customFields = new ArrayList<KeyValuePair<String, String>>();

    /**
     * A note about the account or account details.
     */
    private String note;


    /**
     * Creates an Account object.
     */
    public Account()
    {

    }

    /**
     * Creates an Account object with the specified account name.
     *
     * @param accountName	The name for the account.
     */
    public Account(String accountName)
    {
        this.accountName = accountName;
    }

    /**
     * Returns the name of the service that the account represents.
     *
     * @return	The name of the service the account represents.
     */
    public String getAccountName()
    {
        return this.accountName;
    }

    /**
     * Sets the account name to the specified String.
     *
     * @param accountName	The String to be used as the account name.
     */
    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }

    /**
     * Returns the username associated with the account.
     *
     * @return		The account username.
     */
    public String getUsername()
    {
        return this.username;
    }

    /**
     * Sets the username associated with the account to the specified string.
     *
     * @param username	The String to be used as the username for the account.
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * Returns the account password.
     *
     * @return	The password for the account.
     */
    public String getPassword()
    {
        return this.password;
    }

    /**
     * Sets the account password to the specified String.
     *
     * @param password	The String to use for the account password.
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * Returns the account pin number as a String.
     *
     * @return	The account pin.
     */
    public String getPin()
    {
        return this.pin;
    }

    /**
     * Sets the account pin number to the specified String.
     *
     * @param pin	The String to use for the account pin number.
     */
    public void setPin(String pin)
    {
        this.pin = pin;
    }

    /**
     * Returns the list of email addresses associated with the account.
     *
     * @return	The List of NameValuePair objects representing email addresses associated with the account.
     */
    public ArrayList<KeyValuePair<String, String>> getEmailAddresses()
    {
        return this.emailAddresses;
    }

    /**
     * Sets the List of email addresses to the specified List.
     *
     * @param emailAddresses	The List of email addresses to associate with the account.
     */
    public void setEmailAddresses(ArrayList<KeyValuePair<String, String>> emailAddresses)
    {
        this.emailAddresses = emailAddresses;
    }

    /**
     * Returns an email address based on the email name provided.
     *
     * @param key	The name of the email to fetch.
     * @return		The email address for the given email name.
     */
    public String getEmailAddress(String key)
    {
        for (KeyValuePair<String, String> kv : this.emailAddresses)
        {
            if (kv.getKey().equals(key))
                return (String)kv.getValue();
        }

        return null;
    }

    /**
     * Adds an email address and email name to the list of email addresses associated to the account.
     * Email addresses must have a unique name. Emails will fail to be added if there is already an
     * email addresses associated with the given email name.
     * i.e. Cannot add email address Primary: email@address.com if Primary is the name of an existing
     * email address.
     *
     * @param emailName			The name of the email address.
     * @param emailAddress		The email address.
     */
    public void addEmail(String emailName, String emailAddress)
    {
        KeyValuePair<String, String> email = new KeyValuePair<String, String>(emailName, emailAddress);
        if (!this.emailAddresses.contains(email))
            this.emailAddresses.add(email);
    }

    /**
     * Removes an email from the list of email addresses.
     *
     * @param emailName		The name of the email address to remove from the list of emails.
     */
    public void removeEmail(String emailName)
    {
        KeyValuePair<String, String> email = new KeyValuePair<String, String>(emailName, "");
        if (this.emailAddresses.contains(email))
            this.emailAddresses.remove(email);
    }

    /**
     * Returns the list of security questions and answers for the account.
     *
     * @return	The list of security questions and answers.
     */
    public ArrayList<KeyValuePair<String, String>> getSecurityQuestions()
    {
        return this.securityQuestions;
    }

    /**
     * Sets the list of security questions and answers for the account to the specified List.
     *
     * @param securityQuestions		The List of security questions and answers.
     */
    public void setSecurityQuestions(ArrayList<KeyValuePair<String, String>> securityQuestions)
    {
        this.securityQuestions = securityQuestions;
    }

    /**
     * Adds a security question and answer to the account.
     *
     * @param question	The question to add.
     * @param answer	The answer to add with the question.
     */
    public void addSecurityQuestion(String question, String answer)
    {
        KeyValuePair<String, String> questionAnswer = new KeyValuePair<String, String>(question, answer);
        if (!this.securityQuestions.contains(questionAnswer))
            this.securityQuestions.add(questionAnswer);
    }

    /**
     * Removes the security question at the given index.
     *
     * @param index		The index number of the security question to remove.
     */
    public void removeSecurityQuestion(int index)
    {
        this.securityQuestions.remove(index);
    }

    /**
     * Returns the List of custom fields that have been added to the account.
     *
     * @return	The List of custom fields associate with the account.
     */
    public ArrayList<KeyValuePair<String, String>> getCustomFields()
    {
        return this.customFields;
    }

    /**
     * Sets the List of custom fields contained in the account.
     *
     * @param customFields	The List of custom fields to add.
     */
    public void setCustomFields(ArrayList<KeyValuePair<String, String>> customFields)
    {
        this.customFields = customFields;
    }

    /**
     * Adds a custom field and value to the List of custom fields.
     *
     * @param fieldName		The custom field's name.
     * @param fieldValue	The custom field's value.
     */
    public void addCustomField(String fieldName, String fieldValue)
    {
        KeyValuePair<String, String> field = new KeyValuePair<String, String>(fieldName, fieldValue);
        if (!this.customFields.contains(field))
            this.customFields.add(field);
    }

    /**
     * Removes a custom field from the account.
     *
     * @param fieldName		The name of the field to be removed.
     */
    public void removeCustomField(String fieldName)
    {
        KeyValuePair<String, String> field = new KeyValuePair<String, String>(fieldName, "");
        if (this.customFields.contains(field))
            this.customFields.remove(field);
    }

    /**
     * Returns the note attached to the account.
     *
     * @return	The account note.
     */
    public String getNote()
    {
        return this.note;
    }

    /**
     * Sets the note attached to the account to the specified String.
     *
     * @param note	The String to assign to the note.
     */
    public void setNote(String note)
    {
        this.note = note;
    }

    /**
     * Compares two Account objects.
     * Account object comparison is based on the accountName string.
     *
     * @param other		The Account to compare with.
     * @return			1 if this.accountName comes before other.accountName alphabetically.
     */
    public int compareTo(Account other)
    {
        return this.accountName.compareTo(other.accountName);
    }

    /**
     * Determines if two Account objects are equal in value.
     *
     * @param other		The Account object to compare to.
     * @return			True if the Account objects share the same accountName.
     */
    public boolean equals(Account other)
    {
        return this.accountName.equals(other.accountName);
    }

}
