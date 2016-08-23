package br.com.rogerio.agendaalura;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.rogerio.agendaalura.DAO.AlunoDAO;
import br.com.rogerio.agendaalura.adapter.AlunosAdapter;
import br.com.rogerio.agendaalura.converter.AlunoConverter;
import br.com.rogerio.agendaalura.modelo.Aluno;

public class AgendaActivity extends AppCompatActivity {

    private ListView listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECEIVE_SMS}, 125);
        }

        listaAlunos = (ListView) findViewById(R.id.lista_alunos);

        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(i);
                Intent intentFormulario = new Intent(AgendaActivity.this, FormularioActivity.class);

                intentFormulario.putExtra("Aluno", aluno);

                startActivity(intentFormulario);
            }
        });

        Button botaoNovoAluno = (Button) findViewById(R.id.agenda_novo_aluno);

        botaoNovoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AgendaActivity.this, FormularioActivity.class);
                startActivity(intent);
            }
        });

        registerForContextMenu(listaAlunos);

    }

    @Override
    protected void onResume() {
        super.onResume();
        buscaAlunos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_agenda, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_enviar_notas) {
            new EnviarAlunosTask(this).execute();

        }
        return super.onOptionsItemSelected(item);
    }

    private void buscaAlunos() {
        AlunoDAO dao = new AlunoDAO(this);

        List<Aluno> alunos = dao.buscaAlunos();

        dao.close();

        AlunosAdapter adapter = new AlunosAdapter(this, alunos);

        listaAlunos.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo adapterMenu = (AdapterView.AdapterContextMenuInfo) menuInfo;

        final Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(adapterMenu.position);

        menu.setHeaderTitle("Aluno: " + aluno.getNome());

        MenuItem menuSite = menu.add("Acessar site");
        Intent intentSite = new Intent(Intent.ACTION_VIEW);
        String url = aluno.getSite();
        if(!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        intentSite.setData(Uri.parse(url));
        menuSite.setIntent(intentSite);

        MenuItem menuSMS = menu.add("Mandar SMS");
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:" + aluno.getTelefone()));
        menuSMS.setIntent(intentSMS);

        MenuItem menuEndereco = menu.add("Endereço no Maps");
        Intent intentEndereco = new Intent(Intent.ACTION_VIEW);
        intentEndereco.setData(Uri.parse("geo:0,0?q=" + aluno.getEndereco()));
        menuEndereco.setIntent(intentEndereco);

        MenuItem menuLigar = menu.add("Ligar");
        menuLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(ActivityCompat.checkSelfPermission(AgendaActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AgendaActivity.this, new String[] {Manifest.permission.CALL_PHONE}, 123);
                }
                else {
                    Intent intentLigar = new Intent(Intent.ACTION_CALL);
                    intentLigar.setData(Uri.parse("tel:" + aluno.getTelefone()));
                    startActivity(intentLigar);
                }

                return false;
            }
        });

        MenuItem menuDeletar = menu.add("Excluir");
        menuDeletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
               AlunoDAO dao = new AlunoDAO(AgendaActivity.this);

               dao.deletar(aluno);

               dao.close();

               Toast.makeText(AgendaActivity.this, "Aluno " + aluno.getNome() + " excluído!", Toast.LENGTH_SHORT).show();

               buscaAlunos();

               return false;
           }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 123:
                Toast.makeText(AgendaActivity.this, "Permissão de ligação concedida.", Toast.LENGTH_SHORT).show();
                break;
            case 125:
                Toast.makeText(AgendaActivity.this, "Permissão de SMS concedida.", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
