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

public class MainActivity2 extends AppCompatActivity {

    private List<String> comidas;
    private ListView lista2;
    private ArrayAdapter<String> listaAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        lista2 = (ListView) findViewById(R.id.lvComidas);
        comidas = getListFromXML2(getResources().getXml(R.xml.comidas));

        listaAdapter2 = new ArrayAdapter<String>(
                // parâmetros: contexto, quem é o layout, quem é a fonte do loyout.
                this,
                android.R.layout.simple_list_item_1,
                comidas
        );
        lista2.setAdapter(listaAdapter2);

        lista2.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> adapterView,
                                    View view,
                                    int i,
                                    long id)
            {
                String nomeComidas = comidas.get(i).toString();
                showAlert2 ("Você selecionou : " + nomeComidas);
            }
        });

    }

    public static List <String> getListFromXML2 (XmlResourceParser xmlParser) {
        List<String> _nomesDasComidas = new ArrayList<String>();
        // eventType é -1 porque quando dá o endDocuments, ele retorna -1.
        int eventType = -1;
        while (eventType != XmlResourceParser.END_DOCUMENT){
            if (eventType == XmlResourceParser.START_TAG){
                String strNode = xmlParser.getName();
                if (strNode.equals("item")){
                    // o ideal é _nomes.add(xmlParser.getAttributeValue(s:null, s1:"titulo"));
                    _nomesDasComidas.add(xmlParser.getAttributeValue(null,"titulo"));
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
        return _nomesDasComidas;
    }

    public void showAlert2(String msg){
        AlertDialog.Builder  builder = new AlertDialog.Builder(MainActivity2.this);
        builder.setTitle("Lista de Comidas");
        builder.setMessage(msg);
        builder.setNeutralButton(
                "OK",
                (DialogInterface dialog, int which) -> {;}
        );
        builder.show();
    }

}