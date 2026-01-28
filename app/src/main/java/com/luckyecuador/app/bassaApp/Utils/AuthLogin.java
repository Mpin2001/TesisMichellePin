package com.luckyecuador.app.bassaApp.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.luckyecuador.app.bassaApp.Conexion.Constantes;
import com.luckyecuador.app.bassaApp.Conexion.Mensajes;
import com.luckyecuador.app.bassaApp.Conexion.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AuthLogin {

    Context context;

    public interface LoginCallback {
        void onLoginResponse(boolean login);
    }

    public AuthLogin(Context context) {
        this.context = context;
    }

    public void login(String usuario, String pass, LoginCallback callback) {
        try{
            HashMap<String, String> map = new HashMap<>();
            map.put("user", usuario);
            map.put("pass", pass);

            JSONObject jobject = new JSONObject(map);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constantes.LOGIN, jobject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    int respuesta = procesarRespuestaLogin(response);
                    Log.i("RESPUESTA LOGIN", respuesta + "");
                    if (respuesta == 1) {
                        if (!usuario.trim().isEmpty() && pass != null) {
                            Toast.makeText(context, Mensajes.ON_SYNC,Toast.LENGTH_LONG).show();
                            callback.onLoginResponse(true);
                        } else {
                            Toast.makeText(context,Mensajes.SYNC_NO_USER ,Toast.LENGTH_LONG).show();
                            callback.onLoginResponse(false);
                        }
                    } else {
                        callback.onLoginResponse(false);
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Manejo de errores
                            callback.onLoginResponse(false);
                        }
                    });

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
        }catch (Exception e){
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    public int procesarRespuestaLogin(JSONObject response){
        int estado = 0;
        if (response!=null){
            try{
                //Obtener atributo estado
                estado = response.getInt("estado");
                String mensaje2 = response.getString("mensaje");
                Toast.makeText(context, mensaje2, Toast.LENGTH_LONG).show();
            }catch (JSONException e){
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context,"VACIO",Toast.LENGTH_SHORT).show();
        }
        return estado;
    }
}
