package com.sena.saferesidenceapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder> {

    ArrayList<Usuario> usuarios;

    public UsuarioAdapter(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuario, parent, false);
        return new UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        Usuario usuario = usuarios.get(position);
        holder.nameText.setText("Nombre: " + usuario.name);
        holder.dniText.setText("DNI: " + usuario.dni);
        holder.phoneText.setText("Teléfono: " + usuario.phone);
        holder.emailText.setText("Email: " + usuario.email);
        holder.apartmentText.setText("Apartamento: " + usuario.apartment);
        holder.usernameText.setText("Usuario: " + usuario.username);
        holder.roleText.setText("Rol: " + usuario.role);
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public static class UsuarioViewHolder extends RecyclerView.ViewHolder {

        TextView nameText, dniText, phoneText, emailText, apartmentText, usernameText, roleText;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.name_text);
            dniText = itemView.findViewById(R.id.dni_text);
            phoneText = itemView.findViewById(R.id.phone_text);
            emailText = itemView.findViewById(R.id.email_text);
            apartmentText = itemView.findViewById(R.id.apartment_text);
            usernameText = itemView.findViewById(R.id.username_text);
            roleText = itemView.findViewById(R.id.role_text);
        }
    }
}
