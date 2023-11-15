package com.elazizi.myapplication.in.Employe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import com.elazizi.myapplication.Adapter.EmployeAdapter;
import com.elazizi.myapplication.R;
import com.elazizi.myapplication.beans.Employe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ListEmploye extends AppCompatActivity {

    private List<Employe>  employes = new ArrayList<>();
    private ListView employesList;
    RequestQueue requestQueue;
    EmployeAdapter employeAdapter ;
    private ImageButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_employe);
        employeAdapter = new EmployeAdapter(employes, this);
        getEmploye();
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListEmploye.this, AddEmploye.class);
                startActivity(intent);
                ListEmploye.this.finish();
            }
        });


    }

    public void getEmploye(){
        String getSUrl = "http://172.20.10.7:8087/api/employe";
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                getSUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();
//                Log.d("students",response.toString());
                TypeToken<List<Employe>> token = new TypeToken<List<Employe>>() {};
                employes = gson.fromJson(response.toString(), token.getType());
                employesList = findViewById(R.id.listprof);
                Log.d("student",employes.toString());
                employeAdapter.updateProfesseursList(employes);
                employesList.setAdapter(employeAdapter);
                employesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final TextView idstudent = view.findViewById(R.id.id);
                        Intent intent = new Intent(ListEmploye.this, AddEmploye.class);
                        intent.putExtra("id",idstudent.getText().toString());
                        ListEmploye.this.finish();
                        ListEmploye.this.finish();
                        startActivity(intent);
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

}