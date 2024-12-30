package com.example.again;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import WebServices.Asynchtask;
import WebServices.WebService;

public class MainActivity extends AppCompatActivity implements Asynchtask {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ajustar el padding para bordes del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Consumir el API
        Map<String, String> datos = new HashMap<>();
        WebService ws = new WebService(
                "https://fakestoreapi.com/products",
                datos, MainActivity.this, MainActivity.this);
        ws.execute("GET");
    }

    @Override
    public void processFinish(String result) throws JSONException {
        // Crear un ArrayList para almacenar los productos
        ArrayList<Prendas> prenda = new ArrayList<>();

        // Parsear la respuesta como un JSONArray
        JSONArray JSONlista = new JSONArray(result);

        // Iterar por cada objeto dentro del array
        for (int i = 0; i < JSONlista.length(); i++) {
            JSONObject prendas = JSONlista.getJSONObject(i);

            // Extraer los datos necesarios
            String title = prendas.getString("title");
            String category = prendas.getString("category");
            double price = prendas.getDouble("price");
            String image = prendas.getString("image");
            String description = prendas.optString("description", "No description available.");

            prenda.add(new Prendas(title, category, price, image, description));
        }

        ListView idKimStore = findViewById(R.id.idKimStore);
        PrendasAdapter adapter = new PrendasAdapter(this, prenda);
        idKimStore.setAdapter(adapter);
// Manejar la selección de productos en el ListView
        idKimStore.setOnItemClickListener((parent, view, position, id) -> {
            Prendas productoSeleccionado = prenda.get(position);

            // Crear un intent para abrir DetalleProductoActivity
            Intent intent = new Intent(MainActivity.this, MostrarDatos.class);
            intent.putExtra("titulo", productoSeleccionado.getTitulo());
            intent.putExtra("categoria", productoSeleccionado.getCategoria());
            intent.putExtra("precio", productoSeleccionado.getPrecio());
            intent.putExtra("imagen", productoSeleccionado.getImagen());
            startActivity(intent);
        });
    }
    public static class Prendas {
        private final String titulo;
        private final String categoria;
        private final double precio;
        private final String imagen;
        private final String descripcion;

        public Prendas(String titulo, String categoria, double precio, String imagen, String descripcion) {
            this.titulo = titulo;
            this.categoria = categoria;
            this.precio = precio;
            this.imagen = imagen;
            this.descripcion = descripcion;
        }

        public String getTitulo() {
            return titulo;
        }

        public String getCategoria() {
            return categoria;
        }

        public double getPrecio() {
            return precio;
        }

        public String getImagen() {
            return imagen;
        }

        public String getDescripcion() {
            return descripcion;
        }
    }
    public class PrendasAdapter extends android.widget.BaseAdapter {
        private final Context context;
        private final List<Prendas> prenda;

        public PrendasAdapter(Context context, List<Prendas> prenda) {
            this.context = context;
            this.prenda = prenda;
        }

        @Override
        public int getCount() {
            return prenda.size();
        }

        @Override
        public Object getItem(int position) {
            return prenda.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = android.view.LayoutInflater.from(context).inflate(R.layout.imagenes, parent, false);
            }

            Prendas prendas = prenda.get(position);

            android.widget.ImageView imgProducto = convertView.findViewById(R.id.imgProducto);
            android.widget.TextView txtTitulo = convertView.findViewById(R.id.txtTitulo);
            android.widget.TextView txtCategoria = convertView.findViewById(R.id.txtCategoria);
            android.widget.TextView txtPrecio = convertView.findViewById(R.id.txtPrecio);
            android.widget.TextView txtDescripcion = convertView.findViewById(R.id.txtDescripcion);

            txtTitulo.setText(prendas.getTitulo());
            txtCategoria.setText("Category: " + prendas.getCategoria());
            txtPrecio.setText("Price: $" + prendas.getPrecio());
            txtDescripcion.setText(prendas.getDescripcion());

            Glide.with(context)
                    .load(prendas.getImagen())
                    .into(imgProducto);

            return convertView;
        }
    }
}