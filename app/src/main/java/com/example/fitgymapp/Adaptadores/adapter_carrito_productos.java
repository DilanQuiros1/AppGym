package com.example.fitgymapp.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitgymapp.Entidades.Entidad_producto;
import com.example.fitgymapp.Entidades.Entidad_productosCarrito;
import com.example.fitgymapp.R;
import com.example.fitgymapp.VerProductoParaComprar;

import java.util.List;

public class adapter_carrito_productos extends RecyclerView.Adapter<adapter_carrito_productos.ViewProducto>{

    List<Entidad_productosCarrito> listaProductos;

    public adapter_carrito_productos( List<Entidad_productosCarrito> listaProductos)
    {

        this.listaProductos=listaProductos;
    }

    @NonNull
    @Override
    public adapter_carrito_productos.ViewProducto onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ver_carrito_productos, null, false);
        return new adapter_carrito_productos.ViewProducto(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_carrito_productos.ViewProducto holder, int position) {
        holder.qr.setText(listaProductos.get(position).getQR_pro());
        holder.nombre.setText(listaProductos.get(position).getNombre_pro());
        holder.precio.setText(listaProductos.get(position).getPrecio_pro());
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public class ViewProducto extends RecyclerView.ViewHolder{
        TextView qr, nombre, precio;

        public ViewProducto(@NonNull View itemView) {
            super(itemView);
            qr = itemView.findViewById(R.id.QRPro);
            nombre = itemView.findViewById(R.id.NombrePro);
            precio = itemView.findViewById(R.id.PrecioPro);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();

                    Intent intent = new Intent(context, VerProductoParaComprar.class);
                    intent.putExtra("QR", listaProductos.get(getAdapterPosition()).getQR_pro());
                    intent.putExtra("CA", "CA");//para modificar vista de comprar producto
                    context.startActivity(intent);

                }
            });

        }
    }

}
