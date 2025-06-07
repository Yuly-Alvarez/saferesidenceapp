package com.sena.saferesidenceapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class UserAdapter extends BaseAdapter {

    private Context context;
    private List<Usuario> userList;

    public UserAdapter(Context context, List<Usuario> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.item_usuario, viewGroup, false);

        TextView name = view.findViewById(R.id.name_text);
        TextView email = view.findViewById(R.id.email_text);
        TextView phone = view.findViewById(R.id.phone_text);
        TextView dni = view.findViewById(R.id.dni_text);
        TextView apartment = view.findViewById(R.id.apartment_text);
        TextView username = view.findViewById(R.id.username_text);
        TextView role = view.findViewById(R.id.role_text);

        Usuario usuario = userList.get(i);
        name.setText("Nombre: " + usuario.name);
        email.setText("Email: " + usuario.email);
        phone.setText("Teléfono: " + usuario.phone);
        dni.setText("DNI: " + usuario.dni);
        apartment.setText("Apartamento: " + usuario.apartment);
        username.setText("Usuario: " + usuario.username);
        role.setText("Rol: " + usuario.role);

        return view;
    }
}
