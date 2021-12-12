package com.example.restspringclientwithbasicsecurity.service_rest;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.restspringclientwithbasicsecurity.R;
import com.example.restspringclientwithbasicsecurity.model.Employee;
import com.example.restspringclientwithbasicsecurity.model.Person;
import com.example.restspringclientwithbasicsecurity.securityconfig.ApiAuthenticationClient;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PersonServiceRest  {

    protected static final String TAG = PersonServiceRest.class.getSimpleName();
    private ApiAuthenticationClient apiAuthenticationClient;

    private Context context;
    private ProgressDialog progressDialog;

    public void showProgressDialog(CharSequence message) {
        if (context !=null) {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(context);
                progressDialog.setIndeterminate(true);
            }
            progressDialog.setMessage(message);
            progressDialog.show();
        }
    }
    public void showLoadingProgressDialog() {
        this.showProgressDialog("Loading. Please wait...");
    }
    public void dismissProgressDialog() {
        if (progressDialog != null ) {
            progressDialog.dismiss();
        }
    }

    public PersonServiceRest(Context contex) {
        this.context = context;
        this.apiAuthenticationClient =  ApiAuthenticationClient.getInstance();;
    }

    public Person getPersonById(int id) {
//        PersonByIdAsyncTask asyncTask = (PersonByIdAsyncTask) new PersonByIdAsyncTask(apiAuthenticationClient, id);
        PersonCrudAsyncTask asyncTask = (PersonCrudAsyncTask) new PersonCrudAsyncTask(apiAuthenticationClient, id, true);
        Person person = null;
        try {
//            person = asyncTask.execute().get();
            person = asyncTask.execute().get(5, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }
        if (person==null) person =new Person();
        return person;
    }
    public List<Person> getAllPerson() {
        PersonAllAsyncTask asyncTask = (PersonAllAsyncTask) new PersonAllAsyncTask(apiAuthenticationClient);
        List<Person> listPerson = new ArrayList<>();
        try {
//            person = asyncTask.execute().get();
            listPerson = asyncTask.execute().get(5, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }
//        if (person==null) person =new Person();
        return listPerson;

    }
    public void createPerson(Person person) {
//        new PersonCreateAsyncTask(apiAuthenticationClient, person).execute();
        new PersonCrudAsyncTask(apiAuthenticationClient, person).execute();
    }
    public void updatePerson(Integer id, Person person) {
//        new PersonUpdateAsyncTask(apiAuthenticationClient, id, person).execute();
        new PersonCrudAsyncTask(apiAuthenticationClient, id, person).execute();
    }
    public void deletePerson(Integer id) {
//        new PersonDeleteAsyncTask(apiAuthenticationClient, id).execute();
        new PersonCrudAsyncTask(apiAuthenticationClient, id).execute();
    }



    //Ini Dummy
    private void displayResponsePerson(Person response) {
        Log.d(TAG, response.getName() + " >> " + response.getAddress());
    }

    public class PersonByIdAsyncTask extends  AsyncTask<Void, Void, Person> {

        int id = 0;
        private ApiAuthenticationClient apiAuthenticationClient;

        private PersonByIdAsyncTask(ApiAuthenticationClient apiAuthenticationClient, int id){
            this.apiAuthenticationClient = apiAuthenticationClient;
            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
        }
        @Override
        protected Person doInBackground(Void... voids) {
            final String url = apiAuthenticationClient.getBaseUrl() + "getperson/" + id;

            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

            try {
                // Make the network request
                Log.d(TAG, url);
                ResponseEntity<Person> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<Person>(apiAuthenticationClient.getRequestHeaders()), Person.class);

                Log.d(TAG, response.toString());

                return response.getBody();
            } catch (HttpClientErrorException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return new Person();
            } catch (ResourceAccessException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return new Person();
            }
        }

        @Override
        protected void onPostExecute(Person result) {
            dismissProgressDialog();
            if(result==null) result = new Person();
            displayResponsePerson(result);
        }


    }

    public class PersonAllAsyncTask extends  AsyncTask<Void, Void, List<Person>>{
        private ApiAuthenticationClient apiAuthenticationClient;

        private PersonAllAsyncTask(ApiAuthenticationClient apiAuthenticationClient){
            this.apiAuthenticationClient = apiAuthenticationClient;
        }

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
        }
        @Override
        protected List<Person> doInBackground(Void... voids) {
            final String url = apiAuthenticationClient.getBaseUrl() + "getallperson";

            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

            try {
                // Make the network request
                Log.d(TAG, url);
                ResponseEntity<Person[]> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<Object>(apiAuthenticationClient.getRequestHeaders()), Person[].class);
                List<Person> list = Arrays.asList(response.getBody());
                return list;

            } catch (HttpClientErrorException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return new ArrayList<Person>();
            } catch (ResourceAccessException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return new ArrayList<Person>();
            }
        }

        @Override
        protected void onPostExecute(List<Person> result) {
            dismissProgressDialog();
//            if(result.size()==0) result = new Person();
//            displayResponsePerson(result);
        }


    }

    public class PersonCreateAsyncTask extends  AsyncTask<Void, Void, Person> {

        Person newPerson = null;
        private ApiAuthenticationClient apiAuthenticationClient;

        private PersonCreateAsyncTask(ApiAuthenticationClient apiAuthenticationClient, Person newPerson){
            this.apiAuthenticationClient = apiAuthenticationClient;
            this.newPerson = newPerson;
        }

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
        }
        @Override
        protected Person doInBackground(Void... voids) {
            final String url = apiAuthenticationClient.getBaseUrl() + "createperson";

            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

            try {
                // Make the network request
                Log.d(TAG, url);

//                ResponseEntity<Person> response = restTemplate.exchange(url, HttpMethod.POST, Person.class);
                HttpEntity<Object> httpEntity = new HttpEntity<Object>(newPerson, apiAuthenticationClient.getRequestHeaders());
                ResponseEntity<Person> response = restTemplate.postForEntity(url, httpEntity,  Person.class);

//                ResponseEntity<Person> response = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<Object>(newPerson, apiAuthenticationClient.getRequestHeaders()), Person.class);

                Log.d(TAG, response.toString());
                return response.getBody();

            } catch (HttpClientErrorException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return new Person();
            } catch (ResourceAccessException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return new Person();
            }
        }

        @Override
        protected void onPostExecute(Person result) {
            dismissProgressDialog();
//            if(result==null) result = new Person();
//            displayResponsePerson(result);
        }


    }

    public class PersonUpdateAsyncTask extends  AsyncTask<Void, Void, Person> {

        Person newPerson = null;
        Integer id = 0;
        private ApiAuthenticationClient apiAuthenticationClient;

        private PersonUpdateAsyncTask(ApiAuthenticationClient apiAuthenticationClient,  Integer id, Person newPerson){
            this.apiAuthenticationClient = apiAuthenticationClient;
            this.newPerson = newPerson;
            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
        }

        @Override
        protected Person doInBackground(Void... voids) {
            final String url = apiAuthenticationClient.getBaseUrl() + "updateperson/" + id;

            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

            try {
                // Make the network request
                Log.d(TAG, url);

//                ResponseEntity<Person> response = restTemplate.exchange(url, HttpMethod.POST, Person.class);
//                HttpEntity<Object> httpEntity = new HttpEntity<Object>(newPerson, apiAuthenticationClient.getRequestHeaders());
//                ResponseEntity<Person> response = restTemplate.postForEntity(url, httpEntity,  Person.class);

                ResponseEntity<Person> response = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<Object>(newPerson, apiAuthenticationClient.getRequestHeaders()), Person.class);

                Log.d(TAG, response.toString());
                return response.getBody();

            } catch (HttpClientErrorException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return new Person();
            } catch (ResourceAccessException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return new Person();
            }
        }

        @Override
        protected void onPostExecute(Person result) {
            dismissProgressDialog();
//            if(result==null) result = new Person();
//            displayResponsePerson(result);
        }


    }

    public class PersonDeleteAsyncTask extends  AsyncTask<Void, Void, Person> {

        Integer id = 0;
        private ApiAuthenticationClient apiAuthenticationClient;

        private PersonDeleteAsyncTask(ApiAuthenticationClient apiAuthenticationClient,  Integer id){
            this.apiAuthenticationClient = apiAuthenticationClient;
            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
        }

        @Override
        protected Person doInBackground(Void... voids) {
            final String url = apiAuthenticationClient.getBaseUrl() + "deleteperson/" + id;

            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

            try {
                // Make the network request
                Log.d(TAG, url);

//                ResponseEntity<Person> response = restTemplate.exchange(url, HttpMethod.POST, Person.class);
//                HttpEntity<Object> httpEntity = new HttpEntity<Object>(newPerson, apiAuthenticationClient.getRequestHeaders());
//                ResponseEntity<Person> response = restTemplate.postForEntity(url, httpEntity,  Person.class);

                ResponseEntity<Person> response = restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<Object>(apiAuthenticationClient.getRequestHeaders()), Person.class);

                Log.d(TAG, response.toString());
                return response.getBody();

            } catch (HttpClientErrorException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return new Person();
            } catch (ResourceAccessException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return new Person();
            }
        }

        @Override
        protected void onPostExecute(Person result) {
            dismissProgressDialog();
//            if(result==null) result = new Person();
//            displayResponsePerson(result);
        }


    }


    public class PersonCrudAsyncTask extends  AsyncTask<Void, Void, Person> {

        String operation = "";
        Person newPerson = null;
        Integer id = 0;
        private ApiAuthenticationClient apiAuthenticationClient;

        private PersonCrudAsyncTask(ApiAuthenticationClient apiAuthenticationClient,  Integer id_find, boolean isGetById ) {
            this.apiAuthenticationClient = apiAuthenticationClient;
            if (isGetById) {
                this.id = id_find;
                operation = "GET_BY_ID";
            }
        }
        private PersonCrudAsyncTask(ApiAuthenticationClient apiAuthenticationClient,  Person newPerson){
            this.apiAuthenticationClient = apiAuthenticationClient;
            this.newPerson = newPerson;
            operation = "ADD_NEW";
        }
        private PersonCrudAsyncTask(ApiAuthenticationClient apiAuthenticationClient,  Integer id_update, Person updatePerson){
            this.apiAuthenticationClient = apiAuthenticationClient;
            this.newPerson = updatePerson;
            this.id = id_update;
            operation = "UPDATE";
        }
        private PersonCrudAsyncTask(ApiAuthenticationClient apiAuthenticationClient,  Integer id_delete){
            this.apiAuthenticationClient = apiAuthenticationClient;
            this.id = id_delete;
            operation = "DELETE";
        }

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
        }

        @Override
        protected Person doInBackground(Void... voids) {
            String url = apiAuthenticationClient.getBaseUrl();

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

            try {

//                ResponseEntity<Person> response = restTemplate.exchange(url, HttpMethod.POST, Person.class);
//                HttpEntity<Object> httpEntity = new HttpEntity<Object>(newPerson, apiAuthenticationClient.getRequestHeaders());
//                ResponseEntity<Person> response = restTemplate.postForEntity(url, httpEntity,  Person.class);
                ResponseEntity<Person> response = null;
                if (operation.equals("ADD_NEW")) {
                    url +=  "createperson";
                    response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<Object>(newPerson, apiAuthenticationClient.getRequestHeaders()), Person.class);
                }else if (operation.equals("UPDATE")) {
                    url +=  "updateperson/" + id;
                    response = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<Object>(newPerson, apiAuthenticationClient.getRequestHeaders()), Person.class);
                }else if(operation.equals("DELETE")) {
                    url +=  "deleteperson/" + id;
                    response = restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<Object>(apiAuthenticationClient.getRequestHeaders()), Person.class);
                }else if(operation.equals("GET_BY_ID")) {
                    url +=  "getperson/" + id;
                    response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<Object>(apiAuthenticationClient.getRequestHeaders()), Person.class);
                }

                Log.d(TAG, url + " >> " + response.toString());
                return response.getBody();

            } catch (HttpClientErrorException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return new Person();
            } catch (ResourceAccessException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return new Person();
            }
        }

        @Override
        protected void onPostExecute(Person result) {
            dismissProgressDialog();
//            if(result==null) result = new Person();
//            displayResponsePerson(result);
        }


    }

}
