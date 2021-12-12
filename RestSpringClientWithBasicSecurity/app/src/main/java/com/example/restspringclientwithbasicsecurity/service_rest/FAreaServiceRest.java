package com.example.restspringclientwithbasicsecurity.service_rest;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.restspringclientwithbasicsecurity.model.FArea;
import com.example.restspringclientwithbasicsecurity.securityconfig.ApiAuthenticationClient;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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

public class FAreaServiceRest {
    protected static final String TAG = FAreaServiceRest.class.getSimpleName();
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

    public FAreaServiceRest(Context contex) {
        this.context = context;
        this.apiAuthenticationClient =  ApiAuthenticationClient.getInstance();;
    }

    public FArea getFAreaById(int id) {
        FAreaServiceRest.FAreaCrudAsyncTask asyncTask = (FAreaServiceRest.FAreaCrudAsyncTask) new FAreaServiceRest.FAreaCrudAsyncTask(apiAuthenticationClient, id, true);
        FArea fArea = null;
        try {
//            fArea = asyncTask.execute().get();
            fArea = asyncTask.execute().get(5, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }
        if (fArea==null) fArea =new FArea();
        return fArea;
    }
    public List<FArea> getAllFArea() {
        FAreaServiceRest.FAreaAllAsyncTask asyncTask = (FAreaServiceRest.FAreaAllAsyncTask) new FAreaServiceRest.FAreaAllAsyncTask(apiAuthenticationClient);
        List<FArea> listFArea = new ArrayList<>();
        try {
//            fArea = asyncTask.execute().get();
            listFArea = asyncTask.execute().get(5, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }
//        if (fArea==null) fArea =new FArea();
        return listFArea;

    }
    public void createFArea(FArea fArea) {
//        new FAreaCreateAsyncTask(apiAuthenticationClient, fArea).execute();
        new FAreaServiceRest.FAreaCrudAsyncTask(apiAuthenticationClient, fArea).execute();
    }
    public void updateFArea(Integer id, FArea fArea) {
//        new FAreaUpdateAsyncTask(apiAuthenticationClient, id, fArea).execute();
        new FAreaServiceRest.FAreaCrudAsyncTask(apiAuthenticationClient, id, fArea).execute();
    }
    public void deleteFArea(Integer id) {
//        new FAreaDeleteAsyncTask(apiAuthenticationClient, id).execute();
        new FAreaServiceRest.FAreaCrudAsyncTask(apiAuthenticationClient, id).execute();
    }

    public class FAreaCrudAsyncTask extends AsyncTask<Void, Void, FArea> {

        String operation = "";
        FArea newFArea = null;
        Integer id = 0;
        private ApiAuthenticationClient apiAuthenticationClient;

        private FAreaCrudAsyncTask(ApiAuthenticationClient apiAuthenticationClient,  Integer id_find, boolean isGetById ) {
            this.apiAuthenticationClient = apiAuthenticationClient;
            if (isGetById) {
                this.id = id_find;
                operation = "GET_BY_ID";
            }
        }
        private FAreaCrudAsyncTask(ApiAuthenticationClient apiAuthenticationClient,  FArea newFArea){
            this.apiAuthenticationClient = apiAuthenticationClient;
            this.newFArea = newFArea;
            operation = "ADD_NEW";
        }
        private FAreaCrudAsyncTask(ApiAuthenticationClient apiAuthenticationClient,  Integer id_update, FArea updateFArea){
            this.apiAuthenticationClient = apiAuthenticationClient;
            this.newFArea = updateFArea;
            this.id = id_update;
            operation = "UPDATE";
        }
        private FAreaCrudAsyncTask(ApiAuthenticationClient apiAuthenticationClient,  Integer id_delete){
            this.apiAuthenticationClient = apiAuthenticationClient;
            this.id = id_delete;
            operation = "DELETE";
        }

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
        }

        public FArea getFAreaById(int id) {
//        FAreaByIdAsyncTask asyncTask = (FAreaByIdAsyncTask) new FAreaByIdAsyncTask(apiAuthenticationClient, id);
            FAreaServiceRest.FAreaCrudAsyncTask asyncTask = (FAreaServiceRest.FAreaCrudAsyncTask) new FAreaServiceRest.FAreaCrudAsyncTask(apiAuthenticationClient, id, true);
            FArea fArea = null;
            try {
//            fArea = asyncTask.execute().get();
                fArea = asyncTask.execute().get(5, TimeUnit.SECONDS);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException | TimeoutException e) {
                e.printStackTrace();
            }
            if (fArea==null) fArea =new FArea();
            return fArea;
        }
        public List<FArea> getAllFArea() {
            FAreaServiceRest.FAreaAllAsyncTask asyncTask = (FAreaServiceRest.FAreaAllAsyncTask) new FAreaServiceRest.FAreaAllAsyncTask(apiAuthenticationClient);
            List<FArea> listFArea = new ArrayList<>();
            try {
//            fArea = asyncTask.execute().get();
                listFArea = asyncTask.execute().get(7, TimeUnit.SECONDS);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException | TimeoutException e) {
                e.printStackTrace();
            }
//        if (fArea==null) fArea =new FArea();
            return listFArea;

        }
        public void createFArea(FArea fArea) {
//        new FAreaCreateAsyncTask(apiAuthenticationClient, fArea).execute();
            new FAreaServiceRest.FAreaCrudAsyncTask(apiAuthenticationClient, fArea).execute();
        }
        public void updateFArea(Integer id, FArea fArea) {
//        new FAreaUpdateAsyncTask(apiAuthenticationClient, id, fArea).execute();
            new FAreaServiceRest.FAreaCrudAsyncTask(apiAuthenticationClient, id, fArea).execute();
        }
        public void deleteFArea(Integer id) {
//        new FAreaDeleteAsyncTask(apiAuthenticationClient, id).execute();
            new FAreaServiceRest.FAreaCrudAsyncTask(apiAuthenticationClient, id).execute();
        }


        @Override
        protected FArea doInBackground(Void... voids) {
            String url = apiAuthenticationClient.getBaseUrl();
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

            try {

//                ResponseEntity<FArea> response = restTemplate.exchange(url, HttpMethod.POST, FArea.class);
//                HttpEntity<Object> httpEntity = new HttpEntity<Object>(newFArea, apiAuthenticationClient.getRequestHeaders());
//                ResponseEntity<FArea> response = restTemplate.postForEntity(url, httpEntity,  FArea.class);
                ResponseEntity<FArea> response = null;
                try {
                    if (operation.equals("ADD_NEW")) {
                        url += "createFArea";
                        response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<Object>(newFArea, apiAuthenticationClient.getRequestHeaders()), FArea.class);
                    } else if (operation.equals("UPDATE")) {
                        url += "updateFAreaInfo/" + id;
                        response = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<Object>(newFArea, apiAuthenticationClient.getRequestHeaders()), FArea.class);
                    } else if (operation.equals("DELETE")) {
                        url += "deleteFArea/" + id;
                        response = restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<Object>(apiAuthenticationClient.getRequestHeaders()), FArea.class);
                    } else if (operation.equals("GET_BY_ID")) {
                        url += "getFAreaById/" + id;
                        response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<Object>(apiAuthenticationClient.getRequestHeaders()), FArea.class);
                    }

                    Log.d(TAG, url + " >> " + response.toString());
                }catch (Exception ex){
                }

                return response!=null? response.getBody(): new FArea();

            } catch (HttpClientErrorException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return new FArea();
            } catch (ResourceAccessException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return new FArea();
            }
        }

        @Override
        protected void onPostExecute(FArea result) {
            dismissProgressDialog();
//            if(result==null) result = new FArea();
//            displayResponseFArea(result);
        }
    }

    public class FAreaAllAsyncTask extends  AsyncTask<Void, Void, List<FArea>>{
        private ApiAuthenticationClient apiAuthenticationClient;

        private FAreaAllAsyncTask(ApiAuthenticationClient apiAuthenticationClient){
            this.apiAuthenticationClient = apiAuthenticationClient;
        }

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
        }
        @Override
        protected List<FArea> doInBackground(Void... voids) {
            final String url = apiAuthenticationClient.getBaseUrl() + "getAllFArea";

            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

            try {
                // Make the network request
                Log.d(TAG, url);
                ResponseEntity<FArea[]> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<Object>(apiAuthenticationClient.getRequestHeaders()), FArea[].class);
                List<FArea> list = Arrays.asList(response.getBody());
                return list;

            } catch (HttpClientErrorException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return new ArrayList<FArea>();
            } catch (ResourceAccessException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return new ArrayList<FArea>();
            }
        }

        @Override
        protected void onPostExecute(List<FArea> result) {
            dismissProgressDialog();
//            if(result.size()==0) result = new FArea();
//            displayResponseFArea(result);
        }


    }


}
