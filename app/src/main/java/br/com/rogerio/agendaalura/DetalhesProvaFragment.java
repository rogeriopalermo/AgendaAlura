package br.com.rogerio.agendaalura;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import br.com.rogerio.agendaalura.modelo.Prova;

public class DetalhesProvaFragment extends Fragment {

    private  TextView tvMateria;
    private TextView tvData;
    private ListView lvTopicos;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalhes_prova, container, false);
        tvMateria = (TextView) view.findViewById(R.id.detalhes_prova_materia);
        tvData = (TextView) view.findViewById(R.id.detalhes_prova_data);
        lvTopicos = (ListView) view.findViewById(R.id.detalhes_prova_topicos);

        Bundle bundle = getArguments();
        if(bundle != null) {
            Prova prova = (Prova) bundle.getSerializable("prova");
            if(prova != null)
                populaCamposCom(prova);
        }
        return view;
    }

    public void populaCamposCom(Prova prova) {

        tvMateria.setText(prova.getMateria());
        tvData.setText(prova.getData());
        ArrayAdapter<String> adatper = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, prova.getTopicos());
        lvTopicos.setAdapter(adatper);
    }

}
