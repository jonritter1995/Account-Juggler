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
        if (list == null)
            this.accounts = new ArrayList<Account>();
        else
            this.accounts = list;
    }

    /**
     * Adds a new Account to the list of Accounts and returns the index of the Account in the
     * list. If the List already contains an Account with the given name, -1 is returned.
     *
     * @param accountName	The name of the account that is being added.
     * @return              The index of the new Account in the list of Accounts, -1 if account is already in the list.
     */
    public int addAccount(String accountName)
    {
        Account account = new Account(accountName);
        if (!this.accounts.contains(account))
        {
            this.accounts.add(account);
            return this.accounts.indexOf(account);
        }

        return -1;
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
     * Removes the Account at the given index of the Account List.
     *
     * @param index     The index of the Account to remove from the list.
     */
    public void removeAccount(int index)
    {
        if (index >= 0 && index < this.accounts.size())
            this.accounts.remove(index);
    }

    /**
     * Returns the Account at the given index from the List of Accounts.
     *
     * @param index     The index of the Account to retrieve.
     * @return          The Account at the given index.
     */
    public Account getAccountByIndex(int index)
    {
        return this.accounts.get(index);
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
     * Determines if the List of Accounts contains an Account with the given name.
     *
     * @param accName   The name of the Account to check the List for.
     * @return          True if the List already contains an Account with the given name, false otherwise.
     */
    public boolean containsAccount(String accName)
    {
        return (this.accounts.indexOf(new Account(accName)) >= 0);
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
