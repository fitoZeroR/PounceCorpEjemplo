package com.fito.ejemplopouncecorp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.fito.ejemplopouncecorp.adapter.JSONPaisAdapter;
import com.fito.ejemplopouncecorp.aplicacion.AppController;
import com.fito.ejemplopouncecorp.modelos.JSONDetallePais;
import com.fito.ejemplopouncecorp.modelos.RespuestaJSONLista;
import com.fito.ejemplopouncecorp.modelos.RespuestaJSONLogin;
import com.fito.ejemplopouncecorp.utils.IRecyclerViewOnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetalleActivity extends AppCompatActivity {
    private int idPais;
    private String dataUsuario;

    @Bind(R.id.toolbarDetalle)
    Toolbar toolbar;

    @Bind(R.id.nombrePaisDetalle)
    TextView nombre;
    @Bind(R.id.imagenPaisDetalle)
    ImageView imagenDetalle;
    @Bind(R.id.descripcionPaisDetalle)
    TextView descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        dataUsuario = getIntent().getExtras().getString("data");
        idPais = getIntent().getExtras().getInt("paisID");

        obtieneDetallePais();
    }

    private void obtieneDetallePais() {
        String tag_json_obj = "json_obj_req";
        String url = "http://planetpush.alfaro28.webfactional.com/examen/detalle";

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Cargando...");
        pDialog.show();

        StringRequest myReq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Respuesta Detalle", response.toString());
                        pDialog.hide();

                        Gson gson = new Gson();
                        /*Type collectionType = new TypeToken<List<JSONDetallePais>>(){}.getType();
                        List<JSONDetallePais> lcs = (List<JSONDetallePais>) new Gson()
                                .fromJson(response.toString(), collectionType);
                                //List<JSONDetallePais> lcs = (List<JSONDetallePais>) new Gson().fromJson(response.toString(), JSONDetallePais.class);*/

                        final JSONDetallePais jsonDetalle = gson.fromJson(response.toString(), JSONDetallePais.class);
                        //nombre.setText(jsonDetalle.getMsg());
                        nombre.setText(jsonDetalle.getData().getNombre());
                        descripcion.setText(jsonDetalle.getData().getDescripcion());

                        Picasso.with(getBaseContext()).load(jsonDetalle.getData().getImagen()).into(imagenDetalle);
                        /*final Bitmap[] bitmap = {null};
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    bitmap[0] = BitmapFactory.decodeStream((InputStream) new URL(jsonDetalle.getData().getImagen()).getContent());
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                imagenDetalle.post(new Runnable() {
                                    public void run() {
                                        imagenDetalle.setImageBitmap(bitmap[0]);
                                    }
                                });
                            }
                        }).start();*/
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(AppController.TAG, "Pinche Error: " + error.getMessage());
                        VolleyLog.e(AppController.TAG, "Error: " + error.getMessage());
                        pDialog.hide();
                    }
                }
                ) {
                    protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user", dataUsuario);
                        params.put("pais", String.valueOf(idPais));
                        return params;
                    };
        };

        // Añadimos la petición a la cola de peticiones de Volley
        AppController.getInstance().addToRequestQueue(myReq, tag_json_obj);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}