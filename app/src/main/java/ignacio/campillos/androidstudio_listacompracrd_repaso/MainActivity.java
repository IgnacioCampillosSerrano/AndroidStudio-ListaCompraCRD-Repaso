package ignacio.campillos.androidstudio_listacompracrd_repaso;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import ignacio.campillos.androidstudio_listacompracrd_repaso.adapters.ProductAdapter;
import ignacio.campillos.androidstudio_listacompracrd_repaso.databinding.ActivityMainBinding;
import ignacio.campillos.androidstudio_listacompracrd_repaso.modelos.Producto;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Producto> listaProductos;
    private ActivityMainBinding binding;

    private ProductAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        listaProductos = new ArrayList<>();
        // crearProductos();

        //El int del constructor se refiere a la vista "layout", a su ID.
        adapter = new ProductAdapter(R.layout.product_mode_view,MainActivity.this,  listaProductos);
        binding.contentMain.contenidoContentMain.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(MainActivity.this);
        binding.contentMain.contenidoContentMain.setLayoutManager(layoutManager);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createProduct().show();
            }
        });
    }

    private AlertDialog createProduct(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("AÑADIR AL CARRITO");
        builder.setCancelable(false);

        View createProduct = LayoutInflater.from(this).inflate(R.layout.create_product_view,null);
        EditText nombre = createProduct.findViewById(R.id.txtNombre_create_product_view);
        EditText cantidad = createProduct.findViewById(R.id.txtCantidad_create_product_view);
        EditText precio = createProduct.findViewById(R.id.txtPrecio_create_product_view);

        builder.setNegativeButton("CANCELAR", null);
        builder.setPositiveButton("CREAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!nombre.getText().toString().isEmpty() && !cantidad.getText().toString().isEmpty() && !precio.getText().toString().isEmpty()){
                    Producto producto = new Producto(nombre.getText().toString(),Integer.parseInt(cantidad.getText().toString()),Integer.parseInt(precio.getText().toString()),false);
                } else{
                    Toast.makeText(MainActivity.this, "FALTAN DATOS", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return builder.create();
    }

   /* private void crearProductos() {
        for (int i = 0; i < 100; i++) {
            listaProductos.add(new Producto("Nombre"+i, i,i,false));
        }
    } */
}