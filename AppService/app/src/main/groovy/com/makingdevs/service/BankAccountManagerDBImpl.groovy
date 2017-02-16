package com.makingdevs.service

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.makingdevs.database.AccountBaseHelper
import com.makingdevs.database.AccountDbSchema
import com.makingdevs.model.Account
import groovy.transform.CompileStatic

@CompileStatic
class BankAccountManagerDBImpl implements BankAccountManager {

    private static final String TAG = "BankAccountManagerDBImpl"

    private static BankAccountManagerDBImpl bankAccountManagerDB
    private Context mContext
    private SQLiteDatabase mDatabase

    static BankAccountManagerDBImpl getInstance(Context context){ // Tiene que ser static porque el context no puede cambiar
        if(bankAccountManagerDB == null)
            bankAccountManagerDB = new BankAccountManagerDBImpl(context)
        return bankAccountManagerDB
    }

    private BankAccountManagerDBImpl(Context context){
        this.mContext = context.getApplicationContext()
        this.mDatabase = new AccountBaseHelper(mContext).getWritableDatabase()
    }

    @Override
    Integer getTotalAccounts() {
        Cursor c = mDatabase.rawQuery("SELECT * FROM accounts", null);
        c.moveToNext();
        String prueba = c.getColumnName(0)
        println(prueba)
        int query = c.getCount();
        return query;
    }

    @Override
    Account addToAccounts(Account account) {
        SQLiteDatabase db = mDatabase;
        ContentValues item = new ContentValues()
        item.put(AccountDbSchema.AccountTable.Column.NAME,account.name)
        item.put(AccountDbSchema.AccountTable.Column.ACCOUNT_NUMBER,account.accountNumber)
        db.insert(AccountDbSchema.AccountTable.NAME,null,item)


        account
    }

    @Override
    Boolean updateAccount(Account account) {
       ContentValues item = new ContentValues()
        item.put(AccountDbSchema.AccountTable.Column.NAME,account.name)
        item.put(AccountDbSchema.AccountTable.Column.ACCOUNT_NUMBER,account.accountNumber)
        mDatabase.update(AccountDbSchema.AccountTable.NAME,item,"_id = ${account.id}")
        //mDatabase.update(AccountDbSchema.AccountTable.NAME,item, AccountDbSchema.AccountTable.Column.ACCOUNT_NUMBER +" = '${account.accountNumber}' or "+AccountDbSchema.AccountTable.Column.NAME +"= '${account.name}'")
    }

    @Override
    Boolean deleteAccount(Account account) {
        String name= "hola";
       //mDatabase.delete(AccountDbSchema.AccountTable.NAME,"username = "+ 'Diego',null )>0
        mDatabase.execSQL("DELETE FROM accounts WHERE account_number = '${account.accountNumber}'")
        //println account.accountNumber
        println("*********************************************1**")
        println account.accountNumber
        println("***********************************************")
        return true
    }

    @Override
    Account getAccountById(Long id) {
        Account a = new Account()
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM accounts WHERE _id = ${id}", null)
        cursor.moveToNext()
        a.id = cursor.getInt(0)
        a.name = cursor.getString(1)
        a.accountNumber = cursor.getString(2)
        a
    }

    @Override
    List<Account> retrieveAccounts() {
        List<Account> accounts = []
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM accounts", null)
        while(cursor.moveToNext()){
            Account a = new Account()
            a.name = cursor.getString(1)
            a.accountNumber = cursor.getString(2)

            accounts << a
        }
        accounts
    }

    @Override
    Integer getId(String name, String account) {
       // Cursor cursor = mDatabase.rawQuery("SELECT * FROM accounts WHERE "+AccountDbSchema.AccountTable.Column.ACCOUNT_NUMBER+" = '${account}' and "+AccountDbSchema.AccountTable.Column.NAME +"= '${name}'", null)
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM accounts WHERE username = '${name}' AND account_number = '${account}'", null)
        cursor.moveToNext()
        println(cursor.getInt(0))
        cursor.getInt(0)
    }
    @Override
    Cursor AccountAlreadyExists(Account account){
        String query = "SELECT * FROM accounts where account_number ='${account.accountNumber}'"
        Cursor mCursor = mDatabase.rawQuery(query, null);
        if (mCursor != null) {
            mCursor.moveToFirst()
        }

        return mCursor

    }


}