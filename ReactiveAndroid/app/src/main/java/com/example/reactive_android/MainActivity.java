package com.example.reactive_android;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.reactive_android.adapter.PictureAdapter;
import com.example.reactive_android.model.PictureImage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements PictureAdapter.onPictureListener {

    private static int REQUEST_CODE = 100;

//    ImageView imageView;
    Button loadImage, singleView, doubleView;

    ProgressBar progressBar;
    EditText searchKey;

    ImageRetrievalTask imageRetrievalTask;
    LinearLayoutManager linearLayoutManager;
    GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
    RecyclerView recyclerView;
    PictureAdapter pictureAdapter;
    List<String> newImageUrlList;
    List<PictureImage> newPictureImage;

    FirebaseStorage storage;
    StorageReference storageReference;

    OutputStream outputStream;
    Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadImage = findViewById(R.id.loadImage);
        singleView = findViewById(R.id.single_column_view);
        doubleView = findViewById(R.id.double_column_view);
//        imageView = findViewById(R.id.picureId);
        progressBar = findViewById(R.id.progressBarId);
        searchKey = findViewById(R.id.inputSearch);

        recyclerView = findViewById(R.id.recycle1);
        linearLayoutManager = new LinearLayoutManager(this);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        progressBar.setVisibility(View.INVISIBLE);
        loadImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
//                picture.setVisibility(View.INVISIBLE);
                searchImage();
            }
        });
    }

    public void searchImage(){
        Toast.makeText(MainActivity.this, "Searching starts", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.VISIBLE);
        SearchTask searchTask = new SearchTask(MainActivity.this);
        searchTask.setSearchkey(searchKey.getText().toString());
        Single<String> searchObservable = Single.fromCallable(searchTask);
        searchObservable = searchObservable.subscribeOn(Schedulers.io());
        searchObservable = searchObservable.observeOn(AndroidSchedulers.mainThread());
        searchObservable.subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onSuccess(@NonNull String s) {
                Toast.makeText(MainActivity.this, "Searching Ends", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
                loadImage(s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(MainActivity.this, "Searching Error", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);

            }
        });
    }

    public void loadImage(String response){
        ImageRetrievalTask imageRetrievalTask = new ImageRetrievalTask(MainActivity.this, progressBar, recyclerView, linearLayoutManager);
        imageRetrievalTask.setData(response);

        Toast.makeText(MainActivity.this, "Image loading starts", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.VISIBLE);

        Single<Bitmap> searchObservable = Single.fromCallable(imageRetrievalTask);
        searchObservable = searchObservable.subscribeOn(Schedulers.io());
        searchObservable = searchObservable.observeOn(AndroidSchedulers.mainThread());
        searchObservable.subscribe(new SingleObserver<Bitmap>()
        {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull Bitmap bitmap) {

                Toast.makeText(MainActivity.this, "Image loading Ends", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);

                newImageUrlList= new ArrayList<>(imageRetrievalTask.imageUrlList);
                newPictureImage = new ArrayList<>(imageRetrievalTask.tempPics);


                recyclerView.setLayoutManager(linearLayoutManager);

                Log.d("NEWPICTURIMAGE","temp pics list size is : " +newPictureImage.get(1));

                pictureAdapter = new PictureAdapter(MainActivity.this, imageRetrievalTask.tempPics, MainActivity.this::onPictureClick, MainActivity.this);
                recyclerView.setAdapter(pictureAdapter);
                pictureAdapter.notifyDataSetChanged();


                singleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recyclerView.setLayoutManager(linearLayoutManager);
                    }
                });

                doubleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        recyclerView.setLayoutManager(gridLayoutManager);
                    }
                });
            }

            @Override
            public void onError(@NonNull Throwable e) {

                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this, "Image loading error, search again", Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
    public void onPictureClick(int position) {

        progressBar.setVisibility(View.VISIBLE);
        openPhotosToSelectImage();
        progressBar.setVisibility(View.INVISIBLE);

    }

    private void openPhotosToSelectImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && data!=null && data.getData()!=null)
        {
            imageUri = data.getData();
            progressBar.setVisibility(View.VISIBLE);
            uploadPicture();
        }
    }

    private void uploadPicture() {

        final String randomKey = UUID.randomUUID().toString();
        StorageReference ref = storageReference.child("images/"+randomKey);

        ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this, "upload complete", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@androidx.annotation.NonNull Exception e) {

                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(MainActivity.this, "upload failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}