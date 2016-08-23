package br.com.rogerio.agendaalura;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import br.com.rogerio.agendaalura.modelo.Aluno;

/**
 * Created by Rogerio on 27/07/2016.
 */
public class FormularioHelper {

    private final EditText campoNome;
    private final EditText campoTelefone;
    private final EditText campoEndereco;
    private final EditText campoSite;
    private final RatingBar campoNota;
    private final ImageView campoFoto;
    private final Button botaoRotacionar;
    private Aluno aluno;

    public FormularioHelper(FormularioActivity activity) {
        campoNome = (EditText) activity.findViewById(R.id.nome_aluno);
        campoTelefone = (EditText) activity.findViewById(R.id.telefone_aluno);
        campoEndereco = (EditText) activity.findViewById(R.id.endereco_aluno);
        campoSite = (EditText) activity.findViewById(R.id.site_aluno);
        campoNota = (RatingBar) activity.findViewById(R.id.nota_aluno);
        campoFoto = (ImageView) activity.findViewById(R.id.formulario_foto);
        botaoRotacionar = (Button) activity.findViewById(R.id.formulario_botao_rotacionar_foto);
        aluno = new Aluno();
    }

    public Aluno pegaAluno() {
        aluno.setNome(campoNome.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
        aluno.setEndereco(campoEndereco.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setNota(Double.valueOf(campoNota.getProgress()));
        aluno.setFoto((String) campoFoto.getTag());

        return aluno;
    }


    public void preencheFormulario(Aluno aluno) {
        campoNome.setText(aluno.getNome());
        campoEndereco.setText(aluno.getEndereco());
        campoNota.setProgress(aluno.getNota().intValue());
        campoTelefone.setText(aluno.getTelefone());
        campoSite.setText(aluno.getSite());
        pegaFoto(aluno.getFoto());

        this.aluno = aluno;
    }

    public void pegaFoto(String caminhoFoto) {
        if(caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap fotoRedimensionada = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            campoFoto.setImageBitmap(fotoRedimensionada);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
            campoFoto.setTag(caminhoFoto);
            botaoRotacionar.setVisibility(View.VISIBLE);
            botaoRotacionar.setEnabled(true);
            botaoRotacionar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    campoFoto.setRotation(campoFoto.getRotation() - 90);
                }
            });
        }
    }
}
