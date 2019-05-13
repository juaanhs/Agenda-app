package br.com.juaanhs.agenda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.juaanhs.agenda.converter.AlunoConverter;
import br.com.juaanhs.agenda.dao.AlunoDAO;
import br.com.juaanhs.agenda.modelo.Aluno;

public class EnviaAlunosTask extends AsyncTask<Void, Void, String> {
    private Context context;
    private ProgressDialog dialog;

    public EnviaAlunosTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
       dialog = ProgressDialog.show(context,"Aguarde", "Enviando alunos...",
                true, true);
    }

    @Override
    protected String doInBackground(Void... objects) {
        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        AlunoConverter conversor = new AlunoConverter();
        String json = conversor.converterParaJSON(alunos);

        WebClient client =  new WebClient();
        String resposta = client.post(json);
        return resposta;
    }

    @Override
    protected void onPostExecute(String resposta) {
        Toast.makeText(context, resposta, Toast.LENGTH_LONG).show();
        dialog.dismiss();
    }
}
