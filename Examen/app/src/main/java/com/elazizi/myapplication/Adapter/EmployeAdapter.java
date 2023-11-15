package com.elazizi.myapplication.Adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.elazizi.myapplication.R;
import com.elazizi.myapplication.beans.Employe;


import java.util.List;

public class EmployeAdapter extends BaseAdapter {
    private List<Employe> employes;
    private LayoutInflater inflater;

    public EmployeAdapter(List<Employe> employes, Activity activity) {
        this.employes = employes;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public void updateProfesseursList(List<Employe> newStudents) {
        employes.clear();
        employes.addAll(newStudents);
        notifyDataSetChanged(); // Informez l'adaptateur du changement de données
    }


    @Override
    public int getCount() {
        return employes.size();
    }

    @Override
    public Object getItem(int position) {
        return employes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return employes.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = inflater.inflate(R.layout.employe_item, null);
        TextView id = convertView.findViewById(R.id.id);
        TextView firstName = convertView.findViewById(R.id.firstName);
        TextView lastName = convertView.findViewById(R.id.lastName);
        TextView  date= convertView.findViewById(R.id.date);
        firstName.setText(employes.get(position).getNom());
        lastName.setText(employes.get(position).getPrenom());
        date.setText(employes.get(position).getDatenaissance()+"");
        id.setText(employes.get(position).getId()+"");
        return convertView;
    }
}



