package com.lanshark.software.security.passwordmanager;

import java.util.ArrayList;

/**
 * Manages the users Accounts.
 *
 * @author  Jonathan Ritter
 * @version 1.0
 */
public class AccountManager
{

    /**
     * The list of accounts the user has created.
     */
    private ArrayList<Account> accounts;

    /**
     * Creates an AccountManager with an empty ArrayList of Accounts.
     */
    public AccountManager()
    {
        accounts = new ArrayList<Account>();
    }

    /**
     * Creates an AccountManager object from the given List of Accounts.
     */
    public AccountManager(ArrayList<Account> list)
    {
        this.accounts = list;
    }

    /**
     * Adds an account to the list of accounts.
     *
     * @param accountName	The name of the account that is being added.
     */
    public void addAccount(String accountName)
    {
        Account account = new Account(accountName);
        if (!this.accounts.contains(account))
        {
            this.accounts.add(account);
        }
    }

    /**
     * Returns an ArrayList containing all of the Account objects being managed.
     *
     * @return  All Account objects in an ArrayList.
     */
    public ArrayList<Account> getAllAccounts()
    {
        return this.accounts;
    }

    /**
     * Removes the specified account from the list of accounts if it is in the list.
     *
     * @param accountName	The name of the account to remove.
     */
    public void removeAccount(String accountName)
    {
        this.accounts.remove(new Account(accountName));
    }

    /**
     * Returns the Account with the specified name.
     *
     * @param accountName	The name of the Account to return.
     * @return				The Account object with the given name.
     */
    public Account getAccountByName(String accountName)
    {
        return this.accounts.get(accounts.indexOf(new Account(accountName)));
    }

    /**
     * Returns a String that includes the account details of each account separated by an empty line.
     *
     * @return  List of Account details that are being managed.
     */
    @Override
    public String toString()
    {
        String accountListString = "";

        for (Account a : this.accounts)
        {
            accountListString += a.toString() + "\n\n";
        }

        return accountListString;
    }
}
