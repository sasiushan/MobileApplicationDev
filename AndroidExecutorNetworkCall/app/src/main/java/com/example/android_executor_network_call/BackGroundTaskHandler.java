package com.example.android_executor_network_call;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_executor_network_call.adapter.PostAdapter;
import com.example.android_executor_network_call.adapter.UserAdapter;
import com.example.android_executor_network_call.model.Post;
import com.example.android_executor_network_call.model.User;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class BackGroundTaskHandler implements Runnable{

    Activity uiActivity;
    String searchKey;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    UserAdapter userAdapter;
    PostAdapter postAdapter;
    public List<User> userList = new ArrayList<>();
    public List<Post> postList = new ArrayList<>();

    String id, name, username, email;
    String street, suite, city, zipcode, lat, lng;
    String phone, website, companyName, catchPhrase, bs;

    String userId;


    public BackGroundTaskHandler(MainActivity uiActivity, String searchKey, ProgressBar progressBar, RecyclerView recyclerView, LinearLayoutManager linearLayoutManager) {
        this.uiActivity = uiActivity;
        this.searchKey = searchKey;
        this.progressBar = progressBar;
        this.recyclerView = recyclerView;
        this.linearLayoutManager = linearLayoutManager;
    }

//    public BackGroundTaskHandler(MainActivity2 mainActivity2, String searchKey, ProgressBar progressBar)
//    {
//        this.uiActivity = mainActivity2;
//        this.searchKey = searchKey;
//        this.progressBar = progressBar;
//    }

    public BackGroundTaskHandler(MainActivity3 uiActivity, String searchKey, ProgressBar progressBar, RecyclerView recyclerView, LinearLayoutManager linearLayoutManager, String userId)
    {
        this.uiActivity = uiActivity;
        this.searchKey = searchKey;
        this.progressBar = progressBar;
        this.recyclerView = recyclerView;
        this.linearLayoutManager = linearLayoutManager;
        this.userId = userId;
    }


    @Override
    public void run() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        SearchTask searchTask = new SearchTask(uiActivity);
        searchTask.setSearchkey(searchKey);
        Future<String> searchResponsePlaceholder = executorService.submit(searchTask);
        String searchResult = waitingForSearch(searchResponsePlaceholder); //will contain response

        if(searchResult!=null){
                uiActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(searchKey.equals("users")) {
                            recyclerView.setLayoutManager(linearLayoutManager);
//                        userAdapter = new UserAdapter(context, userList);
//                        recyclerView.setAdapter(userAdapter);

                            try {
                                JSONArray jsonArray = new JSONArray(searchResult);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                    id = jsonObject.getString("id");
                                    name = jsonObject.getString("name");
                                    username = jsonObject.getString("username");
                                    email = jsonObject.getString("email");

                                    JSONObject address = (JSONObject) jsonObject.getJSONObject("address");
                                    street = address.getString("street");
                                    suite = address.getString("suite");
                                    city = address.getString("city");
                                    zipcode = address.getString("zipcode");

                                    JSONObject geo = (JSONObject) address.getJSONObject("geo");
                                    lat = geo.getString("lat");
                                    lng = geo.getString("lng");

                                    phone = jsonObject.getString("phone");
                                    website = jsonObject.getString("website");

                                    JSONObject company = (JSONObject) jsonObject.getJSONObject("company");
                                    companyName = company.getString("name");
                                    catchPhrase = company.getString("catchPhrase");
                                    bs = company.getString("bs");

//                                Log.d("STREET", "address : "+street.toString());
                                    User newUser = new User(id, name, username, email, street, suite, city, zipcode, lat, lng, phone, website, companyName, catchPhrase, bs);
                                    userList.add(newUser);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            userAdapter = new UserAdapter(uiActivity, userList);
                            recyclerView.setAdapter(userAdapter);
                            userAdapter.notifyDataSetChanged();
                        }
                        if(searchKey.equals("posts"))
                        {
                            recyclerView.setLayoutManager(linearLayoutManager);

                            try {
                                JSONArray jsonArrayPosts = new JSONArray(searchResult);
                                for(int i=0;i<jsonArrayPosts.length();i++)
                                {
                                    JSONObject jsonObjectPosts = (JSONObject) jsonArrayPosts.get(i);

                                    //i am comparing the id with userid to make a connection between user and posts.
                                    String user_Id = jsonObjectPosts.getString("userId");
                                    if(userId.equals(user_Id)) {
                                        String id = jsonObjectPosts.getString("id");
                                        String title = jsonObjectPosts.getString("title");
                                        String body = jsonObjectPosts.getString("body");

                                        Post newPost = new Post(user_Id, id, title, body);
                                        Log.d("POST", "POST IS " + title.toString());
                                        postList.add(newPost);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            postAdapter = new PostAdapter(uiActivity, postList, userId);
                            recyclerView.setAdapter(postAdapter);
                            postAdapter.notifyDataSetChanged();

                        }

                    }
                });

        }
        else{
            showError(4,"Search");
        }
        executorService.shutdown();
    }

    public String waitingForSearch(Future<String> searchResponsePlaceholder){
        uiActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        showToast("Search Starts");
        String searchResponseData =null;
        try {
            searchResponseData = searchResponsePlaceholder.get(10000, TimeUnit.MILLISECONDS);
        } catch (ExecutionException e) {
            e.printStackTrace();
            showError(1, "Search");
        } catch (InterruptedException e) {
            e.printStackTrace();
            showError(2, "Search");
        } catch (TimeoutException e) {
            e.printStackTrace();
            showError(3, "Search");
        }
        showToast("Search Ends");
        uiActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        return  searchResponseData;
    }

    public Bitmap waitingForImage(Future<Bitmap> imageResponsePlaceholder){
        uiActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        showToast("Image Retrieval Starts");
        Bitmap imageResponseData =null;
        try {
            imageResponseData = imageResponsePlaceholder.get(6000, TimeUnit.MILLISECONDS);
        } catch (ExecutionException e) {
            e.printStackTrace();
            showError(1, "Image Retrieval");
        } catch (InterruptedException e) {
            e.printStackTrace();
            showError(2, "Image Retrieval");
        } catch (TimeoutException e) {
            e.printStackTrace();
            showError(3, "Image Retrieval");
        }
        showToast("Image Retrieval Ends");
        uiActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        return  imageResponseData;
    }

    public void showError(int code, String taskName){
        if(code ==1){
            showToast(taskName+ " Task Execution Exception");
        }
        else if(code ==2){
            showToast(taskName+" Task Interrupted Exception");
        }
        else if(code ==3){
            showToast(taskName+" Task Timeout Exception");
        }
        else{
            showToast(taskName+" Task could not be performed. Restart!!");
        }
    }

    public void showToast(String text){
        uiActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(uiActivity,text,Toast.LENGTH_SHORT).show();
            }
        });
    }

}
