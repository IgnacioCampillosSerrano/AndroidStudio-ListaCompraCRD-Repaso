package ignacio.campillos.androidstudio_listacompracrd_repaso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ignacio.campillos.androidstudio_listacompracrd_repaso.databinding.ActivityCrearProductoBinding;
import ignacio.campillos.androidstudio_listacompracrd_repaso.databinding.ActivityMainBinding;
import ignacio.campillos.androidstudio_listacompracrd_repaso.databinding.CreateProductViewBinding;
import ignacio.campillos.androidstudio_listacompracrd_repaso.modelos.Producto;

public class CrearProductoActivity extends AppCompatActivity {

    private CreateProductViewBinding binding;
    private EditText txtnombre;
    private EditText txtcantidad;
    private EditText txtprecio;
    private Button btncrear;
    private Button btncancelar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = CreateProductViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

binding.btnCancelarCreate.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }
});

        binding.btnCrearCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = binding.txtNombreCreateProductView.getText().toString();
                int cantidad = Integer.parseInt(binding.txtCantidadCreateProductView.getText().toString());
                int precio = Integer.parseInt(binding.txtPrecioCreateProductView.getText().toString());

                if (nombre.isEmpty() ||
                        binding.txtCantidadCreateProductView.getText().toString().isEmpty() ||
                        binding.txtNombreCreateProductView.getText().toString().isEmpty() ){

                    Toast.makeText(CrearProductoActivity.this, "FALTAN DATOS", Toast.LENGTH_SHORT).show();

                } else {
                    //CREAR PRODUCTO Y PASARLO A UN BUNDLE
                    Producto p = new Producto(nombre, cantidad, precio, false);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("PRODUCTO", p);

                    Intent intent = new Intent(); // NO PONER RUTA POR QUE VUELVE AL MAIN
                    intent.putExtras(bundle);

                    //AVISAR QUE ESTA BIEN Y ENVIAR EL INTENT
                    setResult(RESULT_OK, intent);

                    //CERRAR ACTIVIDAD
                    finish();


                }
            }
        });
    }
}