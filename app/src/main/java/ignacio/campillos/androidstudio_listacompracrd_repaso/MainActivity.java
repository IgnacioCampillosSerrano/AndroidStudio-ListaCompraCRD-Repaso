package ignacio.campillos.androidstudio_listacompracrd_repaso;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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

    private ActivityResultLauncher<Intent> launcherCreacion;

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
        crearProductos();
        actualizarLabels();

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // createProduct().show(); -> AlertDialog para crear productos
                //Lanzar activitidad sin esperar nada de vuelta
                // startActivity(new Intent(MainActivity.this, CrearProductoActivity.class));

                launcherCreacion.launch(new Intent(MainActivity.this,CrearProductoActivity.class));
            }
        });

        launcherCreacion = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), //Poner actividad en espera
                new ActivityResultCallback<ActivityResult>() {// que hacer a la vuelta de la actividad
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK){
                            //COMPROBAR QUE PASAN INFORMACION Y QUE LA INFORMACION NO ES NULL
                            if (result.getData() != null && result.getData().getExtras() !=null){
                                //COGER EL BUNDLE Y CREAR PRODUCTO CON EL, LUEGO ADD
                                Producto p = (Producto) result.getData().getExtras().getSerializable("PRODUCTO");
                                listaProductos.add(0,p);
                                adapter.notifyDataSetChanged();
                                actualizarLabels();
                            } else {
                                Toast.makeText(MainActivity.this, "DATOS INSUFICIENTES", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(MainActivity.this, "VENTANA CANCELADA", Toast.LENGTH_SHORT).show();
                        }
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

        Button btnCancelar = createProduct.findViewById(R.id.btnCrear_create);
        Button btnCrear = createProduct.findViewById(R.id.btnCancelar_create);

        btnCancelar.setVisibility(View.GONE); //Para que no se vean los botones
        btnCrear.setVisibility(View.GONE);

        builder.setView(createProduct); //IMPORTANTE NO ESTA EN APUNTES VANESA

        builder.setNegativeButton("CANCELAR", null);
        builder.setPositiveButton("CREAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!nombre.getText().toString().isEmpty() &&
                        !cantidad.getText().toString().isEmpty() &&
                        !precio.getText().toString().isEmpty()){

                    listaProductos.add( 0, new Producto(nombre.getText().toString(),
                            Integer.parseInt(cantidad.getText().toString()),
                            Integer.parseInt(precio.getText().toString()),false));
                    adapter.notifyDataSetChanged();
                    //quizas hace falta adapter. notify item add en posicion 0
                } else{
                    Toast.makeText(MainActivity.this, "FALTAN DATOS", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return builder.create();
    }

    private void crearProductos() {
        for (int i = 0; i < 100; i++) {
            listaProductos.add(new Producto("Nombre"+i, i,i,false));
        }
    }

    private void actualizarLabels(){
        int contP = listaProductos.size();
        int totalPrecio = 0;
        for (Producto p: listaProductos) {
             totalPrecio += p.getPrecio() * p.getCantidad();
        }
        binding.contentMain.lbCantidadContentMain.setText("PRODUCTOS: "+ contP);
        binding.contentMain.lbPrecioContentMain.setText("TOTAL: "+ totalPrecio + "€");
    }
}