package com.example.trabalhofinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trabalhofinal.model.Imovel;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ImovelAdapter extends BaseAdapter {

    Context context;
    ArrayList<Imovel> imoveis;

    public ImovelAdapter(Context context, ArrayList<Imovel> imoveis) {
        this.context = context;
        this.imoveis = imoveis;
    }


    @Override
    public int getCount() {
        return imoveis.size();
    }

    @Override
    public Object getItem(int i) {
        return imoveis.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_imovel, viewGroup, false);

        }
        Imovel imovel = imoveis.get(i);

        TextView editTipo = view.findViewById(R.id.textTipo);
        //TextView editEndereco = view.findViewById(R.id.textEndereco);
        //TextView editDescricao = view.findViewById(R.id.textDescricao);
        TextView editPreco = view.findViewById(R.id.textPreco);
        TextView editMetragem = view.findViewById(R.id.textMetragem);
        ImageView editImagem = view.findViewById(R.id.textImagem);

        DecimalFormat df = new DecimalFormat("#,###.##");

        editTipo.setText(imovel.tipo + " - " + imovel.endereco);
        //editEndereco.setText(imovel.endereco);
        //editDescricao.setText(imovel.descricao);
        editPreco.setText("Valor: R$ " + df.format(imovel.preco));
        editMetragem.setText("Área privada " + imovel.metragem+"m²");
        Picasso.get().load(imovel.imagem).into(editImagem);

        return view;
    }
}