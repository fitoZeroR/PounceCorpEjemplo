package com.fito.ejemplopouncecorp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.fito.ejemplopouncecorp.aplicacion.AppController;
import com.fito.ejemplopouncecorp.modelos.RespuestaJSONLogin;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @Bind(R.id.toolbarLogin)
    Toolbar toolbarTlb;

    @Bind(R.id.usuarioLogin_vistaID)
    EditText usurioEtx;
    @Bind(R.id.contraseña_vistaID)
    EditText contraseñaEtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        setSupportActionBar(toolbarTlb);
        Log.i("RLM", "Cambio 1");
        Log.i("RLM", "Nuevo branch EjerciciosGitKraken");
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

    @OnClick(R.id.validarse_vistaID)
    public void validarUsuario(View view) {
        if (TextUtils.isEmpty(usurioEtx.getText()) | TextUtils.isEmpty(contraseñaEtx.getText())) {
            Snackbar.make(view, "No deben existir campos vacios.", Snackbar.LENGTH_SHORT).setAction("Alerta", null).show();
        } else {
            validaUsuarioWS(view);
        }
    }

    private void validaUsuarioWS(final View view) {
        // Etiqueta utilizada para cancelar la petición
        String tag_json_obj = "json_obj_req";

        String url = "http://planetpush.alfaro28.webfactional.com/examen/login";

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
                        RespuestaJSONLogin jsonLogin = gson.fromJson(response.toString(), RespuestaJSONLogin.class);

                        if (jsonLogin.getMsg().equals("Ok")) {
                            Intent i = new Intent(LoginActivity.this, ListaActivity.class);
                            i.putExtra("data", jsonLogin.getData());
                            startActivity(i);

                            finish();
                        } else if (jsonLogin.getMsg().equals("Usuario incorrecto")) {
                            Snackbar.make(view, "Usuario incorrecto", Snackbar.LENGTH_SHORT).setAction("Alerta", null).show();
                        }
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
                params.put("usuario", usurioEtx.getText().toString().trim());
                params.put("password", contraseñaEtx.getText().toString().trim());
                return params;
            };
        };

        // Añadimos la petición a la cola de peticiones de Volley
        AppController.getInstance().addToRequestQueue(myReq, tag_json_obj);
    }
}