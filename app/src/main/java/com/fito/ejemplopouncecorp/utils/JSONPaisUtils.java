package com.fito.ejemplopouncecorp.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.fito.ejemplopouncecorp.aplicacion.AppController;
import com.fito.ejemplopouncecorp.modelos.JSONPais;
import com.fito.ejemplopouncecorp.modelos.RespuestaJSONLista;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fito on 5/01/16.
 */
public class JSONPaisUtils {

    public static List<JSONPais> getDataSet(Context context, final String dataUsuario){
        final List<JSONPais>[] dataSet = new List[]{null};

        // Etiqueta utilizada para cancelar la petición
        String tag_json_obj = "json_obj_req";

        String url = "http://planetpush.alfaro28.webfactional.com/examen/lista";

        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Cargando...");
        pDialog.show();

        StringRequest myReq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(AppController.TAG, response.toString());
                        pDialog.hide();

                        Gson gson = new Gson();
                        final RespuestaJSONLista jsonLista = gson.fromJson(response.toString(), RespuestaJSONLista.class);
                        dataSet[0] = jsonLista.getData();
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



        /////////////////////////////////////////////////
        //Este archivo se guarda en assets
        /*List<JSONPais> dataSet = new ArrayList<>();
        try {
            StringBuilder builder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getAssets().open("information.json")));
            String line = "";
            //Se lee el archivo JSON
            while ((line=bufferedReader.readLine()) != null){
                builder.append(line);
            }
            bufferedReader.close();
            String json = builder.toString();
            // Se convierte a un JSONArray
            JSONArray jsonArray = new JSONArray(json);
            for (int index = 0; index < jsonArray.length(); index++) {
                JSONPais jsonLista = new JSONPais();
                JSONObject jsonObject = jsonArray.getJSONObject(index);
                jsonLista.setId(jsonObject.getInt("id"));
                jsonLista.setName(jsonObject.getString("name"));
                jsonLista.setDescription(jsonObject.getString("description"));
                jsonLista.setIcon(index);
                jsonLista.setRating((float) jsonObject.getDouble("rating"));
                dataSet.add(jsonLista);
            }
        } catch (IOException ex) {
            Toast.makeText(context, "I/O Error", Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            Log.e(JSONPais.class.getName(), e.getMessage(), e);
            Toast.makeText(context,e.getMessage(), Toast.LENGTH_SHORT).show();
        }*/
        return dataSet[0];
    }
}