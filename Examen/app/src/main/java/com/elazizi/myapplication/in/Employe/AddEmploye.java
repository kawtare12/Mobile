package com.elazizi.myapplication.in.Employe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.elazizi.myapplication.R;
import com.elazizi.myapplication.beans.Service;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddEmploye extends AppCompatActivity {
    RequestQueue requestQueue;
    private List<Service> services = new ArrayList<>();

    private Service selectedService;
    private EditText firstname, lastName,  date;

    private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employe);
        getService();
        firstname = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        date = findViewById(R.id.date);

        submit = findViewById(R.id.add);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitEmploye();
            }
        });
    }
    private void getService() {
        String getFUrl = "http://192.168.1.106:9090/api/service";
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                getFUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();
                Log.d("services",response.toString());
                TypeToken<List<Service>> token = new TypeToken<List<Service>>() {};
                services = gson.fromJson(response.toString(), token.getType());

                // Créer un HashMap pour associer les noms des filières à leurs objets correspondants
                final HashMap<String, Service> specialiteMap = new HashMap<>();
                for (Service specialite : services) {
                    specialiteMap.put(specialite.getNom(), specialite);
                }


                List<String> nomSpecialite = new ArrayList<>(specialiteMap.keySet());


                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddEmploye.this, android.R.layout.simple_spinner_item, nomSpecialite);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Spinner spinner = findViewById(R.id.spinner);
                spinner.setAdapter(adapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                        String selectedFiliereNom = (String) parentView.getItemAtPosition(position);


                        selectedService = specialiteMap.get(selectedFiliereNom);


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {

                    }
                });
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Erreur", error.toString());
            }
        });
        requestQueue.add(request);
    }
    public void submitEmploye() {
        String insertUrl = "http://192.168.1.106:9090/api/employe";
        JSONObject jsonBody = new JSONObject();
        try {





            jsonBody.put("id", "");
            jsonBody.put("nom", firstname.getText().toString());
            jsonBody.put("prenom", lastName.getText().toString());
            jsonBody.put("date", date.getText().toString());
            JSONObject filiereObject = new JSONObject();
            filiereObject.put("id", selectedService.getId());
            filiereObject.put("nom", selectedService.getNom());
            jsonBody.put("service", filiereObject);
            Log.d("service", jsonBody.toString());
            Toast.makeText(AddEmploye.this, "Student Added", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddEmploye.this, ListEmploye.class);
            startActivity(intent);
            AddEmploye.this.finish();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                insertUrl, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("resultat", response+"");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Erreur", error.toString());
            }
        });
        requestQueue.add(request);
    }

}