package com.example.trabalhofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.trabalhofinal.model.Imovel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class IndexActivity extends AppCompatActivity {

    Button btnCadastrar;
    ListView listImoveis;
    ArrayList<Imovel> imoveis = new ArrayList<>();
    ImovelAdapter adapterImoveis;
    FirebaseDatabase database;
    DatabaseReference referencePessoas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        getSupportActionBar().setTitle("Venda de Imóveis");
        btnCadastrar = findViewById(R.id.btnCadastrar);
        listImoveis = findViewById(R.id.listImoveis);

        adapterImoveis = new ImovelAdapter(IndexActivity.this, imoveis);
        listImoveis.setAdapter(adapterImoveis);
        database = FirebaseDatabase.getInstance();
        referencePessoas = database.getReference("imoveis");
        referencePessoas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                imoveis.clear();
                for(DataSnapshot dataPessoa : snapshot.getChildren()){
                    Imovel imovel = dataPessoa.getValue(Imovel.class);
                    imovel.setKey(dataPessoa.getKey());
                    imoveis.add(imovel);
                }
                adapterImoveis.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(IndexActivity.this, "Erro ao consultar lista de imóveis.", Toast.LENGTH_SHORT).show();
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IndexActivity.this, CadastrarImovel.class));
            }
        });

        listImoveis.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Imovel imovelClicado = imoveis.get(i);
                AlertDialog alerta = new AlertDialog.Builder(IndexActivity.this)
                        .setTitle("Excluir")
                        .setMessage("Deseja excluir " + imovelClicado.tipo + imovelClicado.endereco + " ?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(IndexActivity.this, "Imóvel removido com sucesso!", Toast.LENGTH_SHORT).show();
                                referencePessoas.child(imovelClicado.getKey()).removeValue();
                            }
                        })
                        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(IndexActivity.this, "Operação Cancelada", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create();

                alerta.show();
                return true;
            }
        });

        listImoveis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Imovel imovelClicado = imoveis.get(i);
                Intent intentCadastrar = new Intent(IndexActivity.this, CadastrarImovel.class);
                Bundle b = new Bundle();
                b.putString("key", imovelClicado.getKey());
                intentCadastrar.putExtras(b);
                startActivity(intentCadastrar);
            }
        });
    }
}