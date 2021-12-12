package com.example.restspringclientwithbasicsecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.restspringclientwithbasicsecurity.model.Aktifitas;
import com.example.restspringclientwithbasicsecurity.model.Person;
import com.example.restspringclientwithbasicsecurity.service_rest.AktifitasServiceRest;
import com.example.restspringclientwithbasicsecurity.service_rest.PersonServiceRest;

import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // build the message object
        EditText usernameText = (EditText) findViewById(R.id.username);
        EditText passwordText = (EditText) findViewById(R.id.password);
        usernameText.setVisibility(View.INVISIBLE);
        passwordText.setVisibility(View.INVISIBLE);

        final Button submitButton = (Button) findViewById(R.id.submit);
        submitButton.setVisibility(View.INVISIBLE);


        final Button btnTest1 = (Button) findViewById(R.id.btnTes1);
        btnTest1.setOnClickListener((View v) -> {
            AktifitasServiceRest aktifitasServiceRest = new AktifitasServiceRest(this);
            Aktifitas domain = aktifitasServiceRest.getAktifitasById(1);
            displayResponsePerson(domain);
        });

        final Button btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener((View v) -> {
            AktifitasServiceRest aktifitasServiceRest = new AktifitasServiceRest(this);
            Aktifitas aktifitas = new Aktifitas();
            aktifitas.setId(2);
            aktifitas.setDescription("Tadi ada bug");
            aktifitasServiceRest.updateAktifitas(aktifitas.getId(), aktifitas);

        });

    }


    private void displayResponsePerson(Aktifitas response) {
        Toast.makeText(this, response.getDescription() , Toast.LENGTH_LONG).show();
    }
    private void displayResponsePerson(List<Aktifitas> responseList) {
        for (Aktifitas response: responseList) {
            Toast.makeText(this, response.getDescription() , Toast.LENGTH_LONG).show();
        }
    }
}