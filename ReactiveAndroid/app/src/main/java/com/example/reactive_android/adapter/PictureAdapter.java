package com.example.reactive_android.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reactive_android.ImageRetrievalTask;
import com.example.reactive_android.MainActivity;
import com.example.reactive_android.R;
import com.example.reactive_android.model.PictureImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.core.SingleObserver;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder> {

    Context context;
    List<PictureImage> list;
    Activity activity;
    private onPictureListener onPictureListener2;

    OutputStream outputStream;




    public PictureAdapter(List<PictureImage> list) {
        this.list = list;
    }

    public PictureAdapter(Context context, List<PictureImage> list, onPictureListener onPictureListener2, Activity activity) {
        this.context = context;
        this.list = list;
        this.onPictureListener2 = onPictureListener2;
        this.activity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_item, parent, false);
        return new ViewHolder(view, onPictureListener2);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.img.setImageBitmap(list.get(position).getBitmap());

        //method to save images to photos
        saveImages(holder);
    }



    private void saveImages(ViewHolder holder)
    {
        ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

        FileOutputStream fileOutputStream = null;
        File file = getDesc();

        if(!file.exists() && !file.mkdirs())
        {

            file.mkdirs();

        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmsshhmmss");
        String date = simpleDateFormat.format(new Date());
        String name = "IMG" + date + ".jpg";
        String fileName = file.getAbsolutePath()+"/"+name;
        File new_File = new File(fileName);

        try{

            BitmapDrawable drawable = (BitmapDrawable) holder.img.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            fileOutputStream = new FileOutputStream(new_File);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fileOutputStream);

//            Toast.makeText(context, "images saved", Toast.LENGTH_SHORT).show();
            fileOutputStream.flush();
            fileOutputStream.close();


        }catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }catch (IOException e)
        {
            e.printStackTrace();
        }

        refreshGallery(new_File);
    }

    private void refreshGallery(File file)
    {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(file));
        context.sendBroadcast(intent);
    }

    private File getDesc()
    {
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(file, "Assignment2_PartB");

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView img;
        onPictureListener onPictureListener;

        public ViewHolder(@NonNull View itemView, onPictureListener onPictureListener){
            super(itemView);

            img = itemView.findViewById(R.id.picureId);


            this.onPictureListener = onPictureListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onPictureListener.onPictureClick(getAdapterPosition());
        }
    }

    public interface onPictureListener
    {
           void onPictureClick(int position);
    }
}
