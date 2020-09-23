package com.example.trabalhofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trabalhofinal.model.Imovel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CadastrarImovel extends AppCompatActivity {

    EditText editTipo;
    EditText editEndereco;
    EditText editDescricao;
    EditText editImagem;
    EditText editPreco;
    EditText editMetragem;
    Button btnCadastrar;
    FirebaseDatabase database;
    DatabaseReference referenceImoveis;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_imovel);

        editTipo = findViewById(R.id.textTipo);
        editEndereco = findViewById(R.id.editEndereco);
        editDescricao = findViewById(R.id.editDescricao);
        editImagem = findViewById(R.id.textImagem);
        editPreco = findViewById(R.id.textPreco);
        editMetragem = findViewById(R.id.textMetragem);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        database = FirebaseDatabase.getInstance();
        referenceImoveis = database.getReference("imoveis");

        Bundle b = getIntent().getExtras();
        if (b == null){
            key = "";
            getSupportActionBar().setTitle("Adicionar Imóvel");
        } else {
            key = b.getString("key");
            getSupportActionBar().setTitle("Alterar Imóvel");
            btnCadastrar.setText("Alterar");
            referenceImoveis.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Imovel imovel = snapshot.getValue(Imovel.class);
                    editTipo.setText(imovel.tipo);
                    editEndereco.setText(imovel.endereco);
                    editDescricao.setText(imovel.descricao);
                    editImagem.setText(imovel.imagem);
                    editPreco.setText(String.valueOf(imovel.preco));
                    editMetragem.setText(String.valueOf(imovel.metragem));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(CadastrarImovel.this, "Erro ao consultar pessoa clicada.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tipo = editTipo.getText().toString();
                String endereco = editEndereco.getText().toString();
                String descricao = editDescricao.getText().toString();
                String imagem = editImagem.getText().toString();
                if(imagem.isEmpty()){
                    imagem = "https://www.mercantec.com.br/wp-content/uploads/2019/06/Padr%C3%A3o-img_.jpg";
                }
                String prec = editPreco.getText().toString();
                if(prec.isEmpty()){
                    prec = "00.0";
                }
                double preco = Double.parseDouble(prec);
                String metrage = editMetragem.getText().toString();
                if(metrage.isEmpty()){
                    metrage = "00.0";
                }
                double metragem = Double.parseDouble(metrage);
                Imovel imovel = new Imovel(preco,metragem,tipo,endereco,imagem,descricao);
                if(key.equals("")){
                    referenceImoveis.push().setValue(imovel);
                    Toast.makeText(CadastrarImovel.this, "Imóvel cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                } else{
                    referenceImoveis.child(key).setValue(imovel);
                    Toast.makeText(CadastrarImovel.this, "Imóvel alterado com sucesso!", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }
}