package com.makingdevs.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.Toast
import com.makingdevs.model.Account
import com.makingdevs.modulusuno.R
import com.makingdevs.service.BankAccountManager
import com.makingdevs.service.BankAccountManagerDBImpl
import groovy.transform.CompileStatic
import mehdi.sakout.fancybuttons.FancyButton

@CompileStatic
public class NewAccountActivity extends AppCompatActivity {
    FancyButton fButton_save
    EditText mEdit_name
    EditText mEdit_account
    Integer position
    BankAccountManager bankAccountManager

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bankAccountManager = BankAccountManagerDBImpl.getInstance(getApplicationContext()) // Lo cambie de lugar porque debe obtener el contexto del onCreate
        setContentView(R.layout.activity_new_account);
        fButton_save = (FancyButton) findViewById(R.id.btn_save)
        mEdit_name = (EditText) findViewById(R.id.editName)
        mEdit_account = (EditText) findViewById(R.id.editAccount)
        fButton_save.onClickListener = {

            if(!mEdit_account.text || !mEdit_name.text){
                Toast.makeText(this, "No deje campos vacios", 1).show()
            }
            else if(mEdit_account.text.length() != 18){
                Toast.makeText(this, "Deben ser 18 digitos ", 1).show()
            }
            else{
                Account newAccount = new Account(name: "${mEdit_name.text.toString()}", accountNumber: "${mEdit_account.text.toString()}")
                if(bankAccountManager.accountAlreadyExists(newAccount)){
                    Toast.makeText(this, "La cuenta ya existe",1).show()

                }
                else {
                    bankAccountManager.addToAccounts(newAccount)
                    onBackPressed();
                }



            }


        }

    }

}
