package com.makingdevs.ui

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle
import android.widget.EditText
import com.makingdevs.model.Account
import com.makingdevs.service.BankAccountManager
import com.makingdevs.service.BankAccountManagerImpl
import com.makingdevs.transaction.appservice.R
import groovy.transform.CompileStatic
import mehdi.sakout.fancybuttons.FancyButton;
@CompileStatic
public class NewUserActivity extends AppCompatActivity {
    FancyButton fButton_save
    EditText mEdit_name
    EditText mEdit_account
    Integer position
    BankAccountManager bankAccountManager = new BankAccountManagerImpl()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        fButton_save = (FancyButton) findViewById(R.id.btn_save)
        mEdit_name = (EditText) findViewById(R.id.editName)
        mEdit_account = (EditText) findViewById(R.id.editAccount)
        fButton_save.onClickListener = {

            Account newAccount = new Account(name: "${mEdit_name.getText().toString()}", accountNumber: "${mEdit_account.getText().toString()}")
          println newAccount.name
           bankAccountManager.addToAccounts(newAccount)
            fButton_save.setBorderColor(Color.CYAN)

        }

    }

}
