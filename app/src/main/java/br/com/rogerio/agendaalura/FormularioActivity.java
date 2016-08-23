package br.com.rogerio.agendaalura;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.zip.Inflater;

import br.com.rogerio.agendaalura.DAO.AlunoDAO;
import br.com.rogerio.agendaalura.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {

    public static final int CAMERA_CODE = 124;
    private FormularioHelper helper;
    private String caminhoFoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        helper = new FormularioHelper(this);

        Intent intent = getIntent();

        Aluno aluno = (Aluno) intent.getSerializableExtra("Aluno");

        if(aluno != null) {
            helper.preencheFormulario(aluno);
        }
        Button botaoNovaFoto = (Button) findViewById(R.id.formulario_botao_nova_foto);
        botaoNovaFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                File novaFoto = new File(caminhoFoto);
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(novaFoto));
                startActivityForResult(intentCamera,  CAMERA_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            if(requestCode == CAMERA_CODE) {
                helper.pegaFoto(caminhoFoto);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_formulario_ok:
                Aluno aluno = helper.pegaAluno();
                AlunoDAO dao = new AlunoDAO(this);

                if(aluno.getId() != null) {
                    dao.altera(aluno);
                    Toast.makeText(FormularioActivity.this, aluno.getNome() + " alterado.", Toast.LENGTH_SHORT).show();
                }
                else {
                    dao.insere(aluno);
                    Toast.makeText(FormularioActivity.this, aluno.getNome() + " inserido.", Toast.LENGTH_SHORT).show();
                }
                dao.close();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
