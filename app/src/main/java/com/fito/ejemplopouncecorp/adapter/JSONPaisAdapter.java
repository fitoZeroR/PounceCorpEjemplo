package com.fito.ejemplopouncecorp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.fito.ejemplopouncecorp.R;
import com.fito.ejemplopouncecorp.modelos.JSONPais;
import com.fito.ejemplopouncecorp.utils.IRecyclerViewOnItemClickListener;
import com.fito.ejemplopouncecorp.utils.PaletteTransformation;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fito on 5/01/16.
 */
public class JSONPaisAdapter extends RecyclerView.Adapter<JSONPaisAdapter.ViewHolder> implements Filterable {
    List<JSONPais> elements;
    Context context;
    private IRecyclerViewOnItemClickListener iRecyclerViewOnItemClickListener;

    public JSONPaisAdapter(List<JSONPais> elements, Context context, IRecyclerViewOnItemClickListener iRecyclerViewOnItemClickListener) {
        this.elements = elements;
        if (this.elements == null) {
            this.elements = new ArrayList<>();
        }
        this.iRecyclerViewOnItemClickListener = iRecyclerViewOnItemClickListener;
        this.context = context;
    }

    @Override
    public JSONPaisAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // Inflamos la vista que correspode a la tarjeta.
        View rowCard = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_card, viewGroup, false);
        // Creamos el holder
        ViewHolder holder = new ViewHolder(rowCard, iRecyclerViewOnItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(final JSONPaisAdapter.ViewHolder viewHolder, int i) {
        // Configuramos los datos del dataSet.
        // La imagen la traemos del string array y cogemos la posición
        //final JSONPais jsonPais = this.elements.get(i);
        final int posicion = i;

        viewHolder.tvTitle.setText(elements.get(i).getNombre());

        Picasso.with(context)
                .load(elements.get(posicion).getImagen())
                .fit().centerCrop()
                .into(viewHolder.ivLogo, new Callback.EmptyCallback() {
                //When the image is loaded, palette will take the color and apply it to the other imageView
                    @Override
                    public void onSuccess() {
                        final Bitmap bitmap = ((BitmapDrawable) viewHolder.ivLogo.getDrawable()).getBitmap();// Ew!

                        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
                            public void onGenerated(Palette palette) {

                                if (palette != null) {

                                    Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();

                                    if (vibrantSwatch != null) {
                                        viewHolder.tvTitle.setTextColor(vibrantSwatch.getTitleTextColor());

                                        viewHolder.tarjeta.setBackgroundColor(vibrantSwatch.getRgb());
                                    }
                                }
                            }
                        });
                    }
                });


        /*final Bitmap[] bitmap = {null};
        final Palette[] p = {null};
        new Thread(new Runnable() {
            public void run() {
                try {
                    bitmap[0] = BitmapFactory.decodeStream((InputStream) new URL(elements.get(posicion).getImagen()).getContent());
                    p[0] = Palette.from(bitmap[0]).generate();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //Opción 3: Acceso a todos los swatches generados
                //for (Palette.Swatch sw : p[0].getSwatches()) {
                    //Log.e("Palette",
                  //          "Color: #" + Integer.toHexString(sw.getRgb()) + " (" + sw.getPopulation() + " píxeles)");
                //}

                viewHolder.ivLogo.post(new Runnable() {
                    public void run() {
                        viewHolder.ivLogo.setImageBitmap(bitmap[0]);
                    }
                });

                viewHolder.tvTitle.post(new Runnable() {
                    @Override
                    public void run() {
                        viewHolder.tvTitle.setTextColor(p[0].getVibrantSwatch().getTitleTextColor());
                    }
                });

                viewHolder.tarjeta.post(new Runnable() {
                    @Override
                    public void run() {
                        viewHolder.tarjeta.setBackgroundColor(p[0].getLightMutedColor(Color.WHITE));
                    }
                });
            }
        }).start();*/
    }

    @Override
    public int getItemCount() {
        return elements.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView tarjeta;
        private ImageView ivLogo;
        private TextView tvTitle;

        private IRecyclerViewOnItemClickListener iRecyclerViewOnItemClickListener;

        public ViewHolder(View itemView, IRecyclerViewOnItemClickListener iRecyclerViewOnItemClickListener) {
            super(itemView);
            tarjeta = (CardView) itemView.findViewById(R.id.card_view);
            ivLogo = (ImageView) itemView.findViewById(R.id.ivLogo);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);

            this.iRecyclerViewOnItemClickListener = iRecyclerViewOnItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            iRecyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
        }
    }
}