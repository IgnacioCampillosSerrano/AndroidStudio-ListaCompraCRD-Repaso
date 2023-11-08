package ignacio.campillos.androidstudio_listacompracrd_repaso.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ignacio.campillos.androidstudio_listacompracrd_repaso.R;
import ignacio.campillos.androidstudio_listacompracrd_repaso.modelos.Producto;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductAdapterVH> {

    private int resource;
    private Context context;
    private ArrayList<Producto> objects;

    public ProductAdapter(int posicion, Context context, ArrayList<Producto> objects) {
        this.resource = posicion;
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public ProductAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View productView = LayoutInflater.from(context).inflate(resource,null);

        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, //ANCHO
                ViewGroup.LayoutParams.MATCH_PARENT //ALTO
        );

        return new ProductAdapterVH(productView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapterVH holder, int position) {
        Producto producto = objects.get(position);
        holder.lbNombre.setText(producto.getNombre());
        holder.lbCantidad.setText(String.valueOf(producto.getCantidad()));
        holder.lbPrecio.setText(String.valueOf(producto.getPrecio()));

        if (producto.isComprado()){
            holder.btnComprado.setImageResource(android.R.drawable.checkbox_on_background);
        } else {
            holder.btnComprado.setImageResource(android.R.drawable.checkbox_off_background);
        }

        holder.btnBorrar.setImageResource(android.R.drawable.ic_menu_delete);

        holder.btnComprado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmarComprado("SEGURO QUE LO QUIERES CAMBIAR?", producto).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    private AlertDialog confirmarComprado(String titulo, Producto producto){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titulo);
        builder.setCancelable(false);
        builder.setNegativeButton("NO",null);
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                producto.setComprado(!producto.isComprado());
                notifyDataSetChanged();
            }
        });
        return builder.create();
    }


    public class ProductAdapterVH extends RecyclerView.ViewHolder{
        TextView lbNombre, lbCantidad, lbPrecio;
        ImageButton btnComprado, btnBorrar;
        public ProductAdapterVH(@NonNull View itemView) {
            super(itemView);

            lbNombre = itemView.findViewById(R.id.lbNombre_productModelView);
            lbPrecio = itemView.findViewById(R.id.lbPrecio_productModelView);
            lbCantidad = itemView.findViewById(R.id.lbCantidad_productModelView);
            btnBorrar = itemView.findViewById(R.id.btnBorrar_productModelView);
            btnComprado = itemView.findViewById(R.id.btnComprado_productModelView);
        }
    }
}
