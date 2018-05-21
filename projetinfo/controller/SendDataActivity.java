package com.example.xx_laphoune_xx.projetinfo.controller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xx_laphoune_xx.projetinfo.R;
import com.example.xx_laphoune_xx.projetinfo.model.ClientConnexion;

import static java.security.AccessController.getContext;import android.content.Context;
import android.view.View;


public class SendDataActivity extends AppCompatActivity {

    Button laucnhbutton;
    EditText edittext_IPadress;
    EditText Edittext_port;
    private String port;
    private String IPadress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_data);

        laucnhbutton = findViewById(R.id.button);
        edittext_IPadress = findViewById(R.id.edittext);
        Edittext_port = findViewById(R.id.edittext2);


        laucnhbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IPadress = edittext_IPadress.getText().toString();
                port = Edittext_port.getText().toString();
                Log.i("CO","click atteinte");


                Toast.makeText(v.getContext(),"onclick detected",Toast.LENGTH_LONG).show();


                if(IPadress != null || port != null) {
                    Thread t=new Thread(new ClientConnexion(IPadress,Integer.parseInt(port)));
                    t.start();
                //    ClientConnexion connexion = new ClientConnexion(IPadress,Integer.parseInt(port));
                    Log.i("CO","connexion = new atteinte");

               //     connexion.open();
                    Log.i("CO","Connexion.open() atteinte");

                    Toast.makeText(v.getContext(),"connexion finie",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

}
