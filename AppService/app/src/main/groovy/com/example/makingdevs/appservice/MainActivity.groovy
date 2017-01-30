package com.example.makingdevs.appservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.makingdevs.common.Fluent
import groovy.json.JsonSlurper
import groovy.transform.CompileStatic
import mehdi.sakout.fancybuttons.FancyButton;
import java.net.URLConnection


public class MainActivity extends AppCompatActivity {
    EditText mEditamount
    EditText mEditaccount
    EditText mEditdescription
    FancyButton mFancyB
    FancyButton mFancyC
    String NumberAccount
    String Description
    float amount
    Button mPrueba

    String generator(String NumberAccount, String amount, String Description){
        Date fecha = new Date();
        System.out.println(fecha.getDateString());
        def template = """\
<Abono>
<Clave>1101</Clave>
<FechaOperacion>${fecha.format("yyyyMMdd")}</FechaOperacion>
<InstitucionOrdenante clave="846"/>
<InstitucionBeneficiaria clave="90646"/>
<ClaveRastreo>GEM801</ClaveRastreo>
<Monto>${amount}</Monto>
<NombreBeneficiario>Techminds</NombreBeneficiario>
<TipoCuentaBeneficiario clave="40"/>
<CuentaBeneficiario>${NumberAccount}</CuentaBeneficiario>
<RfcCurpBeneficiario>RFC121212ABC</RfcCurpBeneficiario>
<ConceptoPago>${Description}</ConceptoPago>
<ReferenciaNumerica>27122016</ReferenciaNumerica>
<Empresa>STP</Empresa>
</Abono>
"""
        return template
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Ocultar teclado virtual



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditaccount = (EditText) findViewById(R.id.editA)
        mEditamount = (EditText) findViewById(R.id.editAm)
        mEditdescription = (EditText) findViewById(R.id.editD)
        mFancyB = (FancyButton) findViewById(R.id.btn_preview)
        mFancyC = (FancyButton) findViewById(R.id.btn_clear)
        mPrueba =  (Button) findViewById(R.id.prueba)

        mFancyB.setGhost(true)
        mFancyC.setGhost(true)
        mFancyB.onClickListener={

           if(mEditaccount.getText().toString().equals("") || mEditamount.getText().toString().equals("") || mEditdescription.getText().toString().equals("") ){
                println "no deje vaio"
                Toast.makeText (this ,"No dejé Campos vacíos ", 0).show()
            }
            else{

                NumberAccount = mEditaccount.getText()
                Description = mEditdescription.getText()
                amount = mEditamount.getText().toFloat()
                println "Número de cuenta: ${NumberAccount} Monto: ${amount} Descripcion: ${Description}"



            }
           /* if(mEditaccount.equals("")){ println "Vacioss"
                Toast.makeText (this ,"No dejé Campos vacíos ", 0).show()}
            String account = mEditaccount.getText().toString()
            if(account.equals("")){println "no debe ser vacío"}
            else{println account}*/






        }
        mFancyC.onClickListener = {
            mEditaccount.setText("")
            mEditdescription.setText("")
            mEditamount.setText("")

        }
        String xml = generator(NumberAccount, amount.toString(), Description);
        mPrueba.onClickListener = {
            Fluent.async {
                def jsonSlurper = new JsonSlurper()
                URLConnection connection = new URL("http://impulsomx-api-qa.modulusuno.com/STP/stpDepositNotification").openConnection()

                connection.requestMethod = 'POST'

                connection.doOutput = true
                def writer = new OutputStreamWriter(connection.outputStream)
                writer.write("notification=${xml}")
                writer.flush()
                writer.close()
                connection.connect()
                println connection
                def response = connection.content.text
                println response

                jsonSlurper.parseText(response)

            } then { result ->

                println result.properties
                //println result['message']

            }

            println generator(NumberAccount, amount.toString(), Description)
        }



    }
}
