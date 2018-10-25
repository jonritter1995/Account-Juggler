package com.lanshark.software.security.accountmanager;

import com.lanshark.software.security.accountmanager.util.ComplexFieldPair;

import java.util.ArrayList;

/**
 * Represents an online Account which contains confidential information pertaining to the user.
 *
 * @author  Jonthan Ritter
 * @version 1.0
 */
public class Account implements Comparable<Account>
{

    public static final int USERNAME = 0;

    public static final int PASSWORD = 1;

    public static final int PIN = 2;

    public static final int EMAILS = 3;

    public static final int SEC_QUESTIONS = 4;

    public static final int CUSTOM_FIELDS = 5;

    public static final int NOTE = 6;

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
    private ArrayList<ComplexFieldPair<String, String>> emailAddresses = new ArrayList<ComplexFieldPair<String, String>>();

    /**
     * Security questions and answers linked to the account.
     */
    private ArrayList<ComplexFieldPair<String, String>> securityQuestions = new ArrayList<ComplexFieldPair<String, String>>();

    /**
     * Custom fields for accounts with other settings.
     */
    private ArrayList<ComplexFieldPair<String, String>> customFields = new ArrayList<ComplexFieldPair<String, String>>();

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
    public ArrayList<ComplexFieldPair<String, String>> getEmailAddresses()
    {
        return this.emailAddresses;
    }

    /**
     * Sets the List of email addresses to the specified List.
     *
     * @param emailAddresses	The List of email addresses to associate with the account.
     */
    public void setEmailAddresses(ArrayList<ComplexFieldPair<String, String>> emailAddresses)
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
        for (ComplexFieldPair<String, String> kv : this.emailAddresses)
        {
            if (kv.getKey().equals(key))
                return (String)kv.getValue();
        }

        return null;
    }

    public ComplexFieldPair<String, String> getEmailPair(String key)
    {
        for (ComplexFieldPair<String, String> kv : this.emailAddresses)
        {
            if (kv.getKey().equals(key))
                return kv;
        }

        return null;
    }

    public ComplexFieldPair<String, String> getEmailPair(int index)
    {
        return emailAddresses.get(index);
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
    public boolean addEmail(String emailName, String emailAddress)
    {
        ComplexFieldPair<String, String> email = new ComplexFieldPair<String, String>(emailName, emailAddress);
        if (!this.emailAddresses.contains(email))
        {
            this.emailAddresses.add(email);
            return true;
        }

        return false;
    }

    /**
     * Adds a email identifier and email address pair to the list of email addresses.
     *
     * @param email     The ComplexFieldPair that contains the email address and id.
     */
    public boolean addEmail(ComplexFieldPair<String, String> email)
    {
        if (!this.emailAddresses.contains(email))
        {
            this.emailAddresses.add(email);
            return true;
        }

        return false;
    }

    /**
     * Removes an email from the list of email addresses.
     *
     * @param emailName		The name of the email address to remove from the list of emails.
     */
    public void removeEmail(String emailName)
    {
        ComplexFieldPair<String, String> email = new ComplexFieldPair<String, String>(emailName, "");
        if (this.emailAddresses.contains(email))
            this.emailAddresses.remove(email);
    }

    /**
     * Returns the list of security questions and answers for the account.
     *
     * @return	The list of security questions and answers.
     */
    public ArrayList<ComplexFieldPair<String, String>> getSecurityQuestions()
    {
        return this.securityQuestions;
    }

    /**
     * Sets the list of security questions and answers for the account to the specified List.
     *
     * @param securityQuestions		The List of security questions and answers.
     */
    public void setSecurityQuestions(ArrayList<ComplexFieldPair<String, String>> securityQuestions)
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
        ComplexFieldPair<String, String> questionAnswer = new ComplexFieldPair<String, String>(question, answer);
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
    public ArrayList<ComplexFieldPair<String, String>> getCustomFields()
    {
        return this.customFields;
    }

    /**
     * Sets the List of custom fields contained in the account.
     *
     * @param customFields	The List of custom fields to add.
     */
    public void setCustomFields(ArrayList<ComplexFieldPair<String, String>> customFields)
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
        ComplexFieldPair<String, String> field = new ComplexFieldPair<String, String>(fieldName, fieldValue);
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
        ComplexFieldPair<String, String> field = new ComplexFieldPair<String, String>(fieldName, "");
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
     * Sets the
     * @param field
     * @param value
     */
    public void setStringField(int field, String value)
    {
        switch (field)
        {
            case USERNAME:    this.username = value;  break;
            case PASSWORD:    this.password = value;  break;
            case PIN:         this.pin = value;       break;
            case NOTE:        this.note = value;      break;
        }
    }

    /**
     * Compares two Account objects.
     * Account object comparison is based on the accountName string.
     *
     * @param other		The Account to compare with.
     * @return			1 if this.accountName comes before other.accountName alphabetically.
     */
    @Override
    public int compareTo(Account other)
    {
        return this.accountName.compareTo(other.accountName);
    }

    /**
     * Determines if two Account objects are equal in value.
     *
     * @param obj		The Account object to compare to.
     * @return			True if the Account objects share the same accountName.
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null || !(obj instanceof Account))
            return false;
        else
            return this.accountName.equals(((Account)obj).getAccountName());
    }

    /**
     * Returns a string containing each property of the Account on a separate line.
     *
     * @return  The Account details as a String.
     */
    @Override
    public String toString()
    {
        String emailString = "";
        String questionString = "";
        String fieldString = "";

        for (int i = 0; i < this.emailAddresses.size(); i++)
        {
            emailString += "\t" + this.emailAddresses.get(i).getKey() + ": " + this.emailAddresses.get(i).getValue() + "\n";
        }

        for (int i = 0; i < this.securityQuestions.size(); i++)
        {
            questionString += "\t" + this.securityQuestions.get(i).getKey() + ": " + this.securityQuestions.get(i).getValue() + "\n";
        }

        for (int i = 0; i < this.customFields.size(); i++)
        {
            fieldString += "\t" + this.customFields.get(i).getKey() + ": " + this.customFields.get(i).getValue() + "\n";
        }

        String accountAsString = "Account: " + this.accountName + "\n" +
                "Username: " + this.username + "\n" +
                "Password: " + this.password + "\n" +
                "Pin: " + this.pin + "\n" +
                "Email Addresses:" + "\n" +
                emailString +
                "Security Question:" + "\n" +
                questionString +
                "Custom Fields:" + "\n" +
                fieldString +
                "Note: " + this.note;

        return accountAsString;
    }
}
