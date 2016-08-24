package br.com.rogerio.agendaalura;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.List;

import br.com.rogerio.agendaalura.modelo.Prova;

public class DetalhesProvaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_prova);

        Intent intent = getIntent();

        Prova prova = (Prova) intent.getSerializableExtra("prova");

        TextView tvMateria = (TextView) findViewById(R.id.detalhes_prova_materia);
        TextView tvData = (TextView) findViewById(R.id.detalhes_prova_data);
        ListView lvTopicos = (ListView) findViewById(R.id.detalhes_prova_topicos);

        tvMateria.setText(prova.getMateria());
        tvData.setText(prova.getData());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, prova.getTopicos());

        lvTopicos.setAdapter(adapter);
    }
}
