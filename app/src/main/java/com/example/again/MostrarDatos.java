package com.example.again;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class MostrarDatos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_datos);

        // Obtener las referencias de los elementos de la vista
        ImageView imgProductoDetalle = findViewById(R.id.imgProductoDetalle);
        TextView txtCategoriaDetalle = findViewById(R.id.txtCategoriaDetalle);
        TextView txtTituloProductoDetalle = findViewById(R.id.txtTituloProductoDetalle);
        TextView txtPrecioDetalle = findViewById(R.id.txtPrecioDetalle);
        Button btnPagar = findViewById(R.id.btnPagar);

        // Recibir los datos enviados desde MainActivity
        String titulo = getIntent().getStringExtra("titulo");
        String categoria = getIntent().getStringExtra("categoria");
        double precio = getIntent().getDoubleExtra("precio", 0.0);
        String imagen = getIntent().getStringExtra("imagen");

        // Configurar los datos en los elementos de la vista
        txtCategoriaDetalle.setText("Category: " + categoria);
        txtTituloProductoDetalle.setText(titulo);
        txtPrecioDetalle.setText("Total a pagar: $" + precio);

        // Cargar la imagen del producto usando Glide
        Glide.with(this).load(imagen).into(imgProductoDetalle);

        // Configurar el botón de pagar (puedes agregar funcionalidad aquí)
        btnPagar.setOnClickListener(v -> {
            // Lógica para pagar (puedes implementar navegación o mostrar un mensaje)
        });
    }
}