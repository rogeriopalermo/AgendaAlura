package br.com.rogerio.agendaalura;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.com.rogerio.agendaalura.modelo.Prova;

public class ProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        tx.replace(R.id.frame_principal, new ListaProvasFragment());
        if(estaNoModoPaisagem()) {
            tx.replace(R.id.frame_detalhes_provas, new DetalhesProvaFragment());
        }
        tx.commit();
    }

    private boolean estaNoModoPaisagem() {
        return getResources().getBoolean(R.bool.modoPaisagem);
    }

    public void selecionarProva(Prova prova) {
        FragmentManager manager = getSupportFragmentManager();
        if(!estaNoModoPaisagem()) {
            FragmentTransaction tx = manager.beginTransaction();
            DetalhesProvaFragment detalhesFragment = new DetalhesProvaFragment();
            Bundle args = new Bundle();
            args.putSerializable("prova", prova);
            detalhesFragment.setArguments(args);

            tx.replace(R.id.frame_principal, detalhesFragment);
            tx.addToBackStack(null);
            tx.commit();
        } else {
            DetalhesProvaFragment detalhesFragment = (DetalhesProvaFragment) manager.findFragmentById(R.id.frame_detalhes_provas);
            detalhesFragment.populaCamposCom(prova);
        }
    }
}
