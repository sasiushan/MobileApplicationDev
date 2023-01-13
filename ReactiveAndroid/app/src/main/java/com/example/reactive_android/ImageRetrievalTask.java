package com.example.reactive_android;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reactive_android.adapter.PictureAdapter;
import com.example.reactive_android.model.PictureImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class ImageRetrievalTask implements Callable<Bitmap> {
    private Activity uiActivity;
    private String data;
    private RemoteUtilities remoteUtilities;

    Activity uiActivty;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    PictureAdapter pictureAdapter;

    public List<PictureImage> tempPics = new ArrayList<>();
    public List<JSONObject> jHitItems = new ArrayList<>();
    public List<String> imageUrlList = new ArrayList<>();



    public ImageRetrievalTask(Activity uiActivity, ProgressBar progressBar, RecyclerView recyclerView, LinearLayoutManager linearLayoutManager) {
        remoteUtilities = RemoteUtilities.getInstance(uiActivity);
        this.uiActivity=uiActivity;
        this.progressBar = progressBar;
        this.recyclerView = recyclerView;
        this.linearLayoutManager = linearLayoutManager;
        this.data = null;
    }
    @Override
    public Bitmap call() throws Exception
    {
        Bitmap image = null;
        String endpoint = getEndpoint(this.data);
        if(endpoint==null){
            uiActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(uiActivity,"No image found",Toast.LENGTH_LONG).show();
                }
            });
        }
        else {

            for(int i=0;i<imageUrlList.size();i++)
            {
                String img = imageUrlList.get(i);
                image = getImageFromUrl(img);
            }

            try {
                Thread.sleep(3000);
            } catch (Exception e)
            {

            }
        }
        return image;
    }

    private String getEndpoint(String data)
    {
        String imageUrl = null;
        try {
            JSONObject jBase = new JSONObject(data);
            JSONArray jHits = jBase.getJSONArray("hits");
            if(jHits.length()>0)
            {
                if(jHits.length()>15)
                {
                    int j = 0;
                    while (j < 15) {
                        //array that contains all the hits
                        jHitItems.add(jHits.getJSONObject(j));
                        j++;
//                    }
                    }
                }

                for(int i=0;i<jHitItems.size();i++)
                {
                    Log.d("IMAGE URL", "IMAGE URL : "+jHitItems.get(i).toString());
                }

                for(int i=0;i<jHitItems.size();i++)
                {
                    imageUrlList.add(jHitItems.get(i).getString("previewURL"));
                }
                imageUrl = jHitItems.get(0).getString("previewURL");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return imageUrl;
    }

    private Bitmap getImageFromUrl(String imageUrl){
        Bitmap image = null;

            Uri.Builder url = Uri.parse(imageUrl).buildUpon();
            String urlString = url.build().toString();
            HttpURLConnection connection = remoteUtilities.openConnection(urlString);
            if(connection!=null){
                if(remoteUtilities.isConnectionOkay(connection)==true)
                {
                    image = getBitmapFromConnection(connection);

                    PictureImage imageTemp = new PictureImage(image);
                    tempPics.add(imageTemp);

                    connection.disconnect();
                }
            }
//        }

        return image;
    }

    public Bitmap getBitmapFromConnection(HttpURLConnection conn){
        Bitmap data = null;
        try {
            InputStream inputStream = conn.getInputStream();
            byte[] byteData = getByteArrayFromInputStream(inputStream);
            data = BitmapFactory.decodeByteArray(byteData,0,byteData.length);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return data;
    }

    private byte[] getByteArrayFromInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[4096];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        return buffer.toByteArray();
    }

    public void setData(String data) {
        this.data = data;
    }
}
