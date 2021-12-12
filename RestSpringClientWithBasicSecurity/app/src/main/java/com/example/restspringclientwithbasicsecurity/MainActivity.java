package com.example.restspringclientwithbasicsecurity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.restspringclientwithbasicsecurity.model.FArea;
import com.example.restspringclientwithbasicsecurity.model.Person;
import com.example.restspringclientwithbasicsecurity.securityconfig.ApiAuthenticationClient;
import com.example.restspringclientwithbasicsecurity.service_rest.FAreaServiceRest;
import com.example.restspringclientwithbasicsecurity.service_rest.PersonServiceRest;

import java.util.List;

public class MainActivity extends AbstractAsyncActivity {

    protected static final String TAG = MainActivity.class.getSimpleName();

    ApiAuthenticationClient apiAuthenticationClient;

    private String username;
    private String password;
    EditText usernameText;
    EditText passwordText;
    // ***************************************
    // Activity methods
    // ***************************************
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // build the message object
        usernameText = (EditText) findViewById(R.id.username);
        passwordText = (EditText) findViewById(R.id.password);

        // Initiate the request to the protected service
        final Button submitButton = (Button) findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                username = usernameText.getText().toString();
                password = passwordText.getText().toString();

                 apiAuthenticationClient = ApiAuthenticationClient.getInstance();
                 apiAuthenticationClient.setUsername(username);
                 apiAuthenticationClient.setPassword(password);
                 apiAuthenticationClient.setBaseUrl(getString(R.string.base_uri));

//                new FetchSecuredResourceTask().execute();
//                new FetchSecuredResourceTaskPerson().execute();
//                new FetchSecuredResourceTaskAktifitas().execute();

            }
        });

        final Button btnTest1 = (Button) findViewById(R.id.btnTes1);
        btnTest1.setOnClickListener((View v) -> {
//            PersonServiceRest personServiceRest = new PersonServiceRest(this);
//            Person domain = personServiceRest.getPersonById(1);
//            displayResponsePerson(domain);

            FAreaServiceRest fAreaServiceRest = new FAreaServiceRest(this);
            FArea domain = fAreaServiceRest.getFAreaById(262);
            Toast.makeText(this, domain.getKode1() + " >> " + domain.getDescription(), Toast.LENGTH_LONG).show();

            domain.setDescription("diupdate");
            fAreaServiceRest.updateFArea(domain.getId(), domain);

            FArea newFArea = new FArea();
            newFArea.setKode1("003B");
            newFArea.setDescription("Siap bos");
            newFArea.setFregionBean(260);
            newFArea.setFdivisionBean(105);
            fAreaServiceRest.createFArea(newFArea);


        });

        final Button btnTest2 = (Button) findViewById(R.id.btnTes2);
        btnTest2.setOnClickListener((View v) -> {
            PersonServiceRest personServiceRest = new PersonServiceRest(this);
            List<Person> list = personServiceRest.getAllPerson();
            displayResponsePerson(list);
        });


        final Button btnCreate = (Button) findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener((View v) -> {
            PersonServiceRest personServiceRest = new PersonServiceRest(this);
            Person person = new Person();
            person.setID(0);
            person.setName("Crud 1");
            person.setAddress("Pak Dhe");
            personServiceRest.createPerson(person);
        });


        final Button btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener((View v) -> {
            PersonServiceRest personServiceRest = new PersonServiceRest(this);
            Person person = new Person();
            person.setID(1);
            person.setName("Curd1 diganti put");
            person.setAddress("Oke beres");
            personServiceRest.updatePerson(person.getID(), person);

        });

        final Button btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener((View v) -> {
            PersonServiceRest personServiceRest = new PersonServiceRest(this);
            personServiceRest.deletePerson(14);

        });

        final Button btnNextPage = (Button) findViewById(R.id.btnNextPage);
        btnNextPage.setOnClickListener((View v) -> {
            Intent intent = new Intent(this, MainActivity2.class);
            intent.putExtra(EXTRA_MESSAGE, "hello");
            startActivity(intent);
            Toast.makeText(this, "Oke di jalankan", Toast.LENGTH_SHORT).show();
        });


    }

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private void displayResponsePerson(Person response) {
        Toast.makeText(this, response.getName() + " >> " + response.getAddress(), Toast.LENGTH_LONG).show();
    }
    private void displayResponsePerson(List<Person> responseList) {
        for (Person response: responseList) {
            Toast.makeText(this, response.getName() + " >> " + response.getAddress(), Toast.LENGTH_LONG).show();
        }
    }



}