package br.com.rogerio.agendaalura;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.rogerio.agendaalura.DAO.AlunoDAO;
import br.com.rogerio.agendaalura.converter.AlunoConverter;
import br.com.rogerio.agendaalura.modelo.Aluno;

/**
 * Created by Rogerio on 08/08/2016.
 */
public class EnviarAlunosTask extends AsyncTask<Void, Void, String> {

    private Context context;
    private ProgressDialog dialog;

    public EnviarAlunosTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, "Calculo de notas", "Calculando...", true, true);
    }

    @Override
    protected String doInBackground(Void... params) {
        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        AlunoConverter conversor = new AlunoConverter();

        String json = conversor.converteParaJSON(alunos);
        WebClient client = new WebClient();
        String resposta = client.post(json);

        return resposta;
    }

    @Override
    protected void onPostExecute(String resposta) {
        dialog.dismiss();
        Toast.makeText(context, resposta, Toast.LENGTH_SHORT).show();

        super.onPostExecute(resposta);
    }
}
