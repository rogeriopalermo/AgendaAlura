package br.com.rogerio.agendaalura.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import br.com.rogerio.agendaalura.R;

/**
 * Created by Rogerio on 04/08/2016.
 */
public class AlunosViewHolder {

    public final TextView campoNome;
    public final TextView campoTelefone;
    public final ImageView campoFoto;
    public final TextView campoEndereco;
    public final TextView campoSite;

    public AlunosViewHolder(View view) {
        this.campoNome = (TextView) view.findViewById(R.id.item_nome);
        this.campoTelefone = (TextView) view.findViewById(R.id.item_telefone);
        this.campoFoto = (ImageView) view.findViewById(R.id.item_foto);
        this.campoEndereco = (TextView) view.findViewById(R.id.item_endereco);
        this.campoSite = (TextView) view.findViewById(R.id.item_site);
    }
}
