package com.example.a21106_aula240522;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> palavras;

    private ListView lista1;

    private ArrayAdapter<String> listaAdapter1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista1 = (ListView) findViewById(R.id.lvAlunos);

        //obter a lista de palavras para popular o list view:
        // R é o resource, xml é a pasta, alunos é o arquivo
        palavras = getListFromXML(getResources().getXml(R.xml.alunos));

        // carregar um layout pré-existente no Android
        listaAdapter1 = new ArrayAdapter<String>(
                // parâmetros: contexto, quem é o layout, quem é a fonte do loyout.
         this,
          android.R.layout.simple_list_item_1,
                palavras
        );
        lista1.setAdapter(listaAdapter1);

        lista1.setOnItemClickListener(new AdapterView.OnItemClickListener(){
          public void onItemClick(AdapterView<?> adapterView,
                                  View view,
                                  int i,
                                  long id)
          {
           String nome = palavras.get(i).toString();
           showAlert ("Você selecionou : " + nome);
          }
    });

    }
    // para ler e capturar a lista
    // _ é a variável local da classe
    // cada tag do xml é um evento
    public static List <String> getListFromXML (XmlResourceParser xmlParser) {
    List<String> _nomes = new ArrayList<String>();
    // eventType é -1 porque quando dá o endDocuments, ele retorna -1.
    int eventType = -1;
    while (eventType != XmlResourceParser.END_DOCUMENT){
     if (eventType == XmlResourceParser.START_TAG){
         String strNode = xmlParser.getName();
         if (strNode.equals("item")){
             // o ideal é _nomes.add(xmlParser.getAttributeValue(s:null, s1:"titulo"));
             _nomes.add(xmlParser.getAttributeValue(null,"titulo"));
         }
         try{
             eventType = xmlParser.next();
         }
         catch (XmlPullParserException e){
             Log.e("Lista Nome", e.getMessage());
         } catch (IOException e){
             Log.e("Lista Nome", e.getMessage());

         }

     }
    }
    return _nomes;
    }
     // mostrando a interface com botão de ok quando mostrar a lista de alunos
    public void showAlert(String msg){
        AlertDialog.Builder  builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Lista de Alunos");
        builder.setMessage(msg);
        builder.setNeutralButton(
           "OK",
                (DialogInterface dialog, int which) -> {;}
        );
        builder.show();
    }
}