package com.fito.ejemplopouncecorp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.fito.ejemplopouncecorp.adapter.JSONPaisAdapter;
import com.fito.ejemplopouncecorp.aplicacion.AppController;
import com.fito.ejemplopouncecorp.modelos.JSONPais;
import com.fito.ejemplopouncecorp.modelos.RespuestaJSONLista;
import com.fito.ejemplopouncecorp.utils.IRecyclerViewOnItemClickListener;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListaActivity extends AppCompatActivity {
    private String dataUsuario;

    @Bind(R.id.toolbarLista)
    Toolbar toolbarTlb;

    @Bind(R.id.rvList)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        ButterKnife.bind(this);

        setSupportActionBar(toolbarTlb);

        dataUsuario = getIntent().getExtras().getString("data");

        obtieneListaPaises();
    }

    private void obtieneListaPaises() {
        final List<JSONPais>[] dataSet = new List[]{null};

        // Etiqueta utilizada para cancelar la petición
        String tag_json_obj = "json_obj_req";

        String url = "http://planetpush.alfaro28.webfactional.com/examen/lista";

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Cargando...");
        pDialog.show();

        StringRequest myReq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Log", response.toString());
                        pDialog.hide();

                        Gson gson = new Gson();
                        final RespuestaJSONLista jsonLista = gson.fromJson(response.toString(), RespuestaJSONLista.class);
                        dataSet[0] = jsonLista.getData();

                        //RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvList);
                        //List<JSONPais> jsonPais = JSONPaisUtils.getDataSet(ListaActivity.this, dataUsuario);
                        JSONPaisAdapter adapter =  new JSONPaisAdapter(dataSet[0], ListaActivity.this, new IRecyclerViewOnItemClickListener() {
                            @Override
                            public void onClick(View v, int position) {
                                /*Toast toast = Toast.makeText(ListaActivity.this, String.valueOf((position + 1)), Toast.LENGTH_SHORT);
                                toast.show();*/

                                Intent i = new Intent(ListaActivity.this, DetalleActivity.class);
                                i.putExtra("data", dataUsuario);
                                i.putExtra("paisID", (position + 1));
                                startActivity(i);
                            }
                        });
                        recyclerView.setAdapter(adapter);
                        recyclerView.setHasFixedSize(true);
                        // LinearLayout para que se mueste en fila y no en grilla
                        recyclerView.setLayoutManager(new LinearLayoutManager(ListaActivity.this));
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
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
                        return params;
                    };
        };

        // Añadimos la petición a la cola de peticiones de Volley
        AppController.getInstance().addToRequestQueue(myReq, tag_json_obj);
    }
    /*private void obtieneListaPaises() {
        // Etiqueta utilizada para cancelar la petición
        String tag_json_obj = "json_obj_req";

        String url = "http://planetpush.alfaro28.webfactional.com/examen/lista";

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Cargando...");
        pDialog.show();

        StringRequest myReq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e(AppController.TAG, "Resultado = " + response.toString());
                        pDialog.hide();

                        Gson gson = new Gson();
                        final RespuestaJSONLista jsonLista = gson.fromJson(response.toString(), RespuestaJSONLista.class);

                        Log.e(AppController.TAG, "Resultado = " + jsonLista.getData().get(0).getImagen());

                        final Bitmap[] loadedImage = {null};
                        new Thread(new Runnable() {
                            public void run() {
                                URL imageUrl = null;
                                try {
                                    imageUrl = new URL(jsonLista.getData().get(0).getImagen());
                                    HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                                    conn.connect();
                                    loadedImage[0] = BitmapFactory.decodeStream(conn.getInputStream());
                                } catch (IOException e) {
                                    Toast.makeText(getApplicationContext(), "Error cargando la imagen: "+e.getMessage(), Toast.LENGTH_LONG).show();
                                    e.printStackTrace();
                                }

                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        imagenPais.setImageBitmap(loadedImage[0]);
                                    }
                                });
                            }
                        }).start();

                        final Bitmap[] bitmap = {null};
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    bitmap[0] = BitmapFactory.decodeStream((InputStream)new URL(jsonLista.getData().get(0).getImagen()).getContent());
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        imagenPais.setImageBitmap(bitmap[0]);
                                    }
                                });
                            }
                        }).start();


                        //imagenPais.setImageDrawable(Drawable.createFromPath(jsonLista.getData().get(0).getImagen()));
                        nombrePais.setText(jsonLista.getData().get(0).getNombre());
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
                return params;
            };
        };

        // Añadimos la petición a la cola de peticiones de Volley
        AppController.getInstance().addToRequestQueue(myReq, tag_json_obj);
    }*/

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