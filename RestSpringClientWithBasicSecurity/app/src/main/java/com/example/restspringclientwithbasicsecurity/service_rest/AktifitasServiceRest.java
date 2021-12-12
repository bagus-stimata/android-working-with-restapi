package com.example.restspringclientwithbasicsecurity.service_rest;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.restspringclientwithbasicsecurity.model.Aktifitas;
import com.example.restspringclientwithbasicsecurity.model.Aktifitas;
import com.example.restspringclientwithbasicsecurity.model.Aktifitas;
import com.example.restspringclientwithbasicsecurity.model.Aktifitas;
import com.example.restspringclientwithbasicsecurity.securityconfig.ApiAuthenticationClient;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AktifitasServiceRest {
    protected static final String TAG = AktifitasServiceRest.class.getSimpleName();
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

    public AktifitasServiceRest(Context contex) {
        this.context = context;
        this.apiAuthenticationClient =  ApiAuthenticationClient.getInstance();;
    }

    public Aktifitas getAktifitasById(int id) {
        AktifitasServiceRest.AktifitasCrudAsyncTask asyncTask = (AktifitasServiceRest.AktifitasCrudAsyncTask) new AktifitasServiceRest.AktifitasCrudAsyncTask(apiAuthenticationClient, id, true);
        Aktifitas aktifitas = null;
        try {
//            aktifitas = asyncTask.execute().get();
            aktifitas = asyncTask.execute().get(5, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }
        if (aktifitas==null) aktifitas =new Aktifitas();
        return aktifitas;
    }
    public List<Aktifitas> getAllAktifitas() {
        AktifitasServiceRest.AktifitasAllAsyncTask asyncTask = (AktifitasServiceRest.AktifitasAllAsyncTask) new AktifitasServiceRest.AktifitasAllAsyncTask(apiAuthenticationClient);
        List<Aktifitas> listAktifitas = new ArrayList<>();
        try {
//            aktifitas = asyncTask.execute().get();
            listAktifitas = asyncTask.execute().get(5, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }
//        if (aktifitas==null) aktifitas =new Aktifitas();
        return listAktifitas;

    }
    public void createAktifitas(Aktifitas aktifitas) {
//        new AktifitasCreateAsyncTask(apiAuthenticationClient, aktifitas).execute();
        new AktifitasServiceRest.AktifitasCrudAsyncTask(apiAuthenticationClient, aktifitas).execute();
    }
    public void updateAktifitas(Integer id, Aktifitas aktifitas) {
//        new AktifitasUpdateAsyncTask(apiAuthenticationClient, id, aktifitas).execute();
        new AktifitasServiceRest.AktifitasCrudAsyncTask(apiAuthenticationClient, id, aktifitas).execute();
    }
    public void deleteAktifitas(Integer id) {
//        new AktifitasDeleteAsyncTask(apiAuthenticationClient, id).execute();
        new AktifitasServiceRest.AktifitasCrudAsyncTask(apiAuthenticationClient, id).execute();
    }

    public class AktifitasCrudAsyncTask extends AsyncTask<Void, Void, Aktifitas> {

        String operation = "";
        Aktifitas newAktifitas = null;
        Integer id = 0;
        private ApiAuthenticationClient apiAuthenticationClient;

        private AktifitasCrudAsyncTask(ApiAuthenticationClient apiAuthenticationClient,  Integer id_find, boolean isGetById ) {
            this.apiAuthenticationClient = apiAuthenticationClient;
            if (isGetById) {
                this.id = id_find;
                operation = "GET_BY_ID";
            }
        }
        private AktifitasCrudAsyncTask(ApiAuthenticationClient apiAuthenticationClient,  Aktifitas newAktifitas){
            this.apiAuthenticationClient = apiAuthenticationClient;
            this.newAktifitas = newAktifitas;
            operation = "ADD_NEW";
        }
        private AktifitasCrudAsyncTask(ApiAuthenticationClient apiAuthenticationClient,  Integer id_update, Aktifitas updateAktifitas){
            this.apiAuthenticationClient = apiAuthenticationClient;
            this.newAktifitas = updateAktifitas;
            this.id = id_update;
            operation = "UPDATE";
        }
        private AktifitasCrudAsyncTask(ApiAuthenticationClient apiAuthenticationClient,  Integer id_delete){
            this.apiAuthenticationClient = apiAuthenticationClient;
            this.id = id_delete;
            operation = "DELETE";
        }

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
        }

        public Aktifitas getAktifitasById(int id) {
//        AktifitasByIdAsyncTask asyncTask = (AktifitasByIdAsyncTask) new AktifitasByIdAsyncTask(apiAuthenticationClient, id);
            AktifitasServiceRest.AktifitasCrudAsyncTask asyncTask = (AktifitasServiceRest.AktifitasCrudAsyncTask) new AktifitasServiceRest.AktifitasCrudAsyncTask(apiAuthenticationClient, id, true);
            Aktifitas aktifitas = null;
            try {
//            aktifitas = asyncTask.execute().get();
                aktifitas = asyncTask.execute().get(5, TimeUnit.SECONDS);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException | TimeoutException e) {
                e.printStackTrace();
            }
            if (aktifitas==null) aktifitas =new Aktifitas();
            return aktifitas;
        }
        public List<Aktifitas> getAllAktifitas() {
            AktifitasServiceRest.AktifitasAllAsyncTask asyncTask = (AktifitasServiceRest.AktifitasAllAsyncTask) new AktifitasServiceRest.AktifitasAllAsyncTask(apiAuthenticationClient);
            List<Aktifitas> listAktifitas = new ArrayList<>();
            try {
//            aktifitas = asyncTask.execute().get();
                listAktifitas = asyncTask.execute().get(5, TimeUnit.SECONDS);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException | TimeoutException e) {
                e.printStackTrace();
            }
//        if (aktifitas==null) aktifitas =new Aktifitas();
            return listAktifitas;

        }
        public void createAktifitas(Aktifitas aktifitas) {
//        new AktifitasCreateAsyncTask(apiAuthenticationClient, aktifitas).execute();
            new AktifitasServiceRest.AktifitasCrudAsyncTask(apiAuthenticationClient, aktifitas).execute();
        }
        public void updateAktifitas(Integer id, Aktifitas aktifitas) {
//        new AktifitasUpdateAsyncTask(apiAuthenticationClient, id, aktifitas).execute();
            new AktifitasServiceRest.AktifitasCrudAsyncTask(apiAuthenticationClient, id, aktifitas).execute();
        }
        public void deleteAktifitas(Integer id) {
//        new AktifitasDeleteAsyncTask(apiAuthenticationClient, id).execute();
            new AktifitasServiceRest.AktifitasCrudAsyncTask(apiAuthenticationClient, id).execute();
        }


        @Override
        protected Aktifitas doInBackground(Void... voids) {
            String url = apiAuthenticationClient.getBaseUrl();

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

            try {

//                ResponseEntity<Aktifitas> response = restTemplate.exchange(url, HttpMethod.POST, Aktifitas.class);
//                HttpEntity<Object> httpEntity = new HttpEntity<Object>(newAktifitas, apiAuthenticationClient.getRequestHeaders());
//                ResponseEntity<Aktifitas> response = restTemplate.postForEntity(url, httpEntity,  Aktifitas.class);
                ResponseEntity<Aktifitas> response = null;
                if (operation.equals("ADD_NEW")) {
                    url +=  "createaktifitas";
                    response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<Object>(newAktifitas, apiAuthenticationClient.getRequestHeaders()), Aktifitas.class);
                }else if (operation.equals("UPDATE")) {
                    url +=  "updateaktifitas/" + id;
                    response = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<Object>(newAktifitas, apiAuthenticationClient.getRequestHeaders()), Aktifitas.class);
                }else if(operation.equals("DELETE")) {
                    url +=  "deleteaktifitas/" + id;
                    response = restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<Object>(apiAuthenticationClient.getRequestHeaders()), Aktifitas.class);
                }else if(operation.equals("GET_BY_ID")) {
                    url +=  "getaktifitas/" + id;
                    response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<Object>(apiAuthenticationClient.getRequestHeaders()), Aktifitas.class);
                }

                Log.d(TAG, url + " >> " + response.toString());
                return response.getBody();

            } catch (HttpClientErrorException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return new Aktifitas();
            } catch (ResourceAccessException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return new Aktifitas();
            }
        }

        @Override
        protected void onPostExecute(Aktifitas result) {
            dismissProgressDialog();
//            if(result==null) result = new Aktifitas();
//            displayResponseAktifitas(result);
        }
    }

    public class AktifitasAllAsyncTask extends  AsyncTask<Void, Void, List<Aktifitas>>{
        private ApiAuthenticationClient apiAuthenticationClient;

        private AktifitasAllAsyncTask(ApiAuthenticationClient apiAuthenticationClient){
            this.apiAuthenticationClient = apiAuthenticationClient;
        }

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
        }
        @Override
        protected List<Aktifitas> doInBackground(Void... voids) {
            final String url = apiAuthenticationClient.getBaseUrl() + "getallaktifitas";

            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

            try {
                // Make the network request
                Log.d(TAG, url);
                ResponseEntity<Aktifitas[]> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<Object>(apiAuthenticationClient.getRequestHeaders()), Aktifitas[].class);
                List<Aktifitas> list = Arrays.asList(response.getBody());
                return list;

            } catch (HttpClientErrorException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return new ArrayList<Aktifitas>();
            } catch (ResourceAccessException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return new ArrayList<Aktifitas>();
            }
        }

        @Override
        protected void onPostExecute(List<Aktifitas> result) {
            dismissProgressDialog();
//            if(result.size()==0) result = new Aktifitas();
//            displayResponseAktifitas(result);
        }


    }


}
