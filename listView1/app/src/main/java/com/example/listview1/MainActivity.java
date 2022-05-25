package com.example.listview1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btn;
    private List<String> palavras;
    private List<String> listaNomes;

    private ListView lista;
    private ArrayAdapter<String> listaAdapter;
    private ArrayAdapter<String> listaAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button)findViewById(R.id.button);
        lista = (ListView) findViewById(R.id.lvAlunos);
        // obter a lista da palavras para popular o list view
        palavras = getListFromXml(getResources().getXml(R.xml.alunos));

        // carregar um layout pre existente no android
        listaAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                palavras );

        lista.setAdapter(listaAdapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int i, long id) {
                String nome = palavras.get(i).toString();
                showAlert("Voce selecionou : " + nome);
            }
        });

        InputStream is = getResources().openRawResource(R.raw.nomes);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        }
        catch (IOException e){
            System.console().printf(e.getMessage());
        }
        finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String jsonString = writer.toString();
        try {
            JSONObject listaValores = new JSONObject(jsonString);
            JSONArray ArrayNomes = listaValores.getJSONArray("lista");

            listaNomes = new ArrayList<String>();
            JSONObject nome;
            for (int i = 0; i< ArrayNomes.length(); i++ ){
                nome = new JSONObject(ArrayNomes.getString(i));
                Log.e("JSON",nome.getString("nome"));
                listaNomes.add(nome.getString("nome"));
            }
        }
        catch (JSONException e){
            System.console().printf(e.getMessage());
        }

        listaAdapter2 = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                listaNomes );

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lista.setAdapter(listaAdapter2);

            }
        });

    }

    public static List<String> getListFromXml(XmlResourceParser xmlParser){
        List<String> _nomes = new ArrayList<String>();

        int eventType = -1;
        while (eventType != XmlResourceParser.END_DOCUMENT){
            if (eventType == XmlResourceParser.START_TAG){
                String strNode = xmlParser.getName();
                if (strNode.equals("item")){
                    _nomes.add(xmlParser.getAttributeValue(null,"titulo"));
                }
            }
            try{
                eventType = xmlParser.next();
            }catch (XmlPullParserException e){
                Log.e("Lista Nome", e.getMessage());
            }catch (IOException e){
                Log.e("Lista Nome", e.getMessage());
            }
        }
        return _nomes;
    }

    public void showAlert(String msg){

        AlertDialog.Builder builder = new
                AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Lista de Alunos");
        builder.setMessage(msg);
        builder.setNeutralButton(
                "OK",
                (DialogInterface dialog, int which)->{;}
        );
        builder.show();
    }
}


















