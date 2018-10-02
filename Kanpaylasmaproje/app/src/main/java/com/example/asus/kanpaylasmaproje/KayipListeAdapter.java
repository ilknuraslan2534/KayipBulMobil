package com.example.asus.kanpaylasmaproje;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 25.02.2018.
 */

public class KayipListeAdapter extends ArrayAdapter<KayipPaylasModel> {

    LayoutInflater layoutInflater;
    ArrayList<KayipPaylasModel> paylasilanlist;
    ArrayList<KisiModel> kayipList1;
    FirebaseDatabase database;
    private String merakgetid;
    private String userID;
    private Context context;
    private Button mesajlas;
    private DatabaseReference mDatabase;
    private TextView kullaniciAdi, aciklama, iletisim, yakinlik, kategori;
    ImageView kayipresim;


    public KayipListeAdapter(Context context, List<KayipPaylasModel> paylasilanlist) {
        super(context, 0, paylasilanlist);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.paylasilanlist = (ArrayList<KayipPaylasModel>) paylasilanlist;
        this.context = context;
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        final ViewHolder holder;
        if (v == null) {

            LayoutInflater vi =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.kayip_list_item, null);

            holder = new ViewHolder();
            holder.kullaniciadi = (TextView) v.findViewById(R.id.kullaniciadi);
            holder.aciklama = (TextView) v.findViewById(R.id.aciklama);
            holder.kayipresim = (ImageView) v.findViewById(R.id.resim);
            holder.iletisim = (TextView) v.findViewById(R.id.iletisim);
            holder.yakinlik = (TextView) v.findViewById(R.id.yakinlik);
            holder.kategori = (TextView) v.findViewById(R.id.kategori);


            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        KayipPaylasModel kayipPaylasModel = paylasilanlist.get(position);
        holder.kullaniciadi.setText(kayipPaylasModel.getKullanici_adi());
        holder.aciklama.setText(kayipPaylasModel.getAciklama());
        holder.iletisim.setText(kayipPaylasModel.getIletisim());
        holder.kategori.setText(kayipPaylasModel.getKategori());
        holder.yakinlik.setText(kayipPaylasModel.getYakinlik());
            //String    id=setText(kayipPaylasModel.getId());

        FirebaseStorage fStorage = FirebaseStorage.getInstance();

        Log.d("merakgetid",String.valueOf(kayipPaylasModel.getId()));

       StorageReference storageRef = fStorage.getReference().child("paylasResim").child(kayipPaylasModel.getId());
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Picasso.with(context).load(uri).fit().centerCrop().into(holder.kayipresim);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


            }
        });
        return v;


    }

        static class ViewHolder {
            TextView kullaniciadi;
            TextView iletisim;
            TextView yakinlik;
            TextView aciklama;
            TextView kategori;
            ImageView kayipresim;
        }

}
