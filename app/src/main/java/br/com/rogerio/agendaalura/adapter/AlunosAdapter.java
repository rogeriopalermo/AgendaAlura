package br.com.rogerio.agendaalura.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.rogerio.agendaalura.AgendaActivity;
import br.com.rogerio.agendaalura.R;
import br.com.rogerio.agendaalura.modelo.Aluno;
import br.com.rogerio.agendaalura.viewHolder.AlunosViewHolder;

/**
 * Created by Rogerio on 04/08/2016.
 */
public class AlunosAdapter extends BaseAdapter {
    private final List<Aluno> alunos;
    private final Context context;

    public AlunosAdapter(Context context, List<Aluno> alunos) {
        this.context = context;
        this.alunos = alunos;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int i) {
        return alunos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return alunos.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Aluno aluno = alunos.get(i);

        LayoutInflater inflater  = LayoutInflater.from(context);

        AlunosViewHolder viewHolder;

        View tView;
        if(view == null) {
            tView = inflater.inflate(R.layout.list_item, viewGroup, false);
            viewHolder = new AlunosViewHolder(tView);
            tView.setTag(viewHolder);
        }
        else {
            tView = view;
            viewHolder = (AlunosViewHolder) tView.getTag();
        }

        viewHolder.campoNome.setText(aluno.getNome());
        viewHolder.campoTelefone.setText(aluno.getTelefone());

        String caminhoFoto = aluno.getFoto();
        if(caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap fotoRedimensionada = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            viewHolder.campoFoto.setImageBitmap(fotoRedimensionada);
            viewHolder.campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
            viewHolder.campoFoto.setTag(caminhoFoto);
        }

        if(viewHolder.campoEndereco != null) {
            viewHolder.campoEndereco.setText(aluno.getEndereco());
        }

        if(viewHolder.campoSite != null) {
            viewHolder.campoSite.setText(aluno.getSite());
        }

        return tView;
    }
}
