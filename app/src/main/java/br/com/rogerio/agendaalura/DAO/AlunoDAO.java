package br.com.rogerio.agendaalura.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.rogerio.agendaalura.modelo.Aluno;

/**
 * Created by Rogerio on 27/07/2016.
 */
public class AlunoDAO extends SQLiteOpenHelper{

    public AlunoDAO(Context context) {
        super(context, "Agenda", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE Alunos (id INTEGER PRIMARY KEY, nome TEXT NOT NULL, endereco TEXT, telefone TEXT, site TEXT, nota REAL, foto TEXT);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        switch(i) {
            case 1:
                String sql = "ALTER TABLE ALUNOS ADD COLUMN foto TEXT";
                sqLiteDatabase.execSQL(sql);
        }
    }

    public void insere(Aluno aluno) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues values = getContentValuesAluno(aluno);

        sqLiteDatabase.insert("Alunos", null, values);
    }

    @NonNull
    private ContentValues getContentValuesAluno(Aluno aluno) {
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("telefone", aluno.getTelefone());
        values.put("endereco", aluno.getEndereco());
        values.put("site", aluno.getSite());
        values.put("nota", aluno.getNota());
        values.put("foto", aluno.getFoto());
        return values;
    }

    public List<Aluno> buscaAlunos() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        String sql = "SELECT * FROM Alunos";

        Cursor c = sqLiteDatabase.rawQuery(sql, null);

        List<Aluno> listaAlunos = new ArrayList<Aluno>();

        while(c.moveToNext()){
            Aluno aluno = new Aluno();
            aluno.setId(c.getLong(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
            aluno.setFoto(c.getString(c.getColumnIndex("foto")));

            listaAlunos.add(aluno);
        }

        c.close();
        return listaAlunos;
    }

    public void deletar(Aluno aluno) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        String[] params = {aluno.getId().toString()};

        sqLiteDatabase.delete("Alunos", "id = ?", params);
    }

    public void altera(Aluno aluno) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        String[] params = {aluno.getId().toString()};

        ContentValues values = getContentValuesAluno(aluno);

        sqLiteDatabase.update("Alunos", values, "id = ?", params);
    }

    public boolean isAluno(String telefone) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        String sql = "SELECT * FROM ALUNOS WHERE telefone = ?";

        String[] params = {telefone};

        Cursor c = sqLiteDatabase.rawQuery(sql, params);

        if(c.getCount() > 0) {
            c.close();
            return true;
        } else {
            c.close();
            return false;
        }
    }
}
