package com.example.android_executor_network_call;

import android.app.Activity;
import android.net.Uri;

import java.net.HttpURLConnection;
import java.util.concurrent.Callable;

public class SearchTask implements Callable<String> {

    private String searchkey;
    private String baseUrl;
    private RemoteUtilities remoteUtilities;
    private Activity uiActivity;

    public SearchTask(Activity uiActivity) {
        this.searchkey = null;
        baseUrl ="https://jsonplaceholder.typicode.com/";
        remoteUtilities = RemoteUtilities.getInstance(uiActivity);
        this.uiActivity = uiActivity;
    }

    @Override
    public String call() throws Exception {
        String response=null;
        String endpoint = getSearchEndpoint();
        HttpURLConnection connection = remoteUtilities.openConnection(endpoint);
        if(connection!=null){
            if(remoteUtilities.isConnectionOkay(connection)==true){
                response = remoteUtilities.getResponseString(connection);
                connection.disconnect();
                try {
                    Thread.sleep(3000);
                }
                catch (Exception e){

                }
            }
        }
        return response;
    }

    private String getSearchEndpoint(){

        return baseUrl + searchkey;
    }

    public void setSearchkey(String searchkey) {
        this.searchkey = searchkey;
    }
}
