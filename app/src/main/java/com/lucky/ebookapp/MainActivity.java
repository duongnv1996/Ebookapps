package com.lucky.ebookapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar ;
    Spinner spinner;
    ListView listView;
    String url_spinner;

    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.ReView_id);
    RecyclerView.Adapter ReAdapter;
            //Categorie list parsing with Jsoup library
    ArrayList<String> CategorieList = new ArrayList<>();

    ArrayAdapter<String> SpinnerAdapter ;

    ArrayAdapter<String> ListAdapter ;

    ArrayList<String> BookTitels =new ArrayList<>();

    ArrayList<String> BookImgaes = new ArrayList<>();

    ArrayList<String> CategorieLink = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        spinner = (Spinner) findViewById(R.id.spinner);
//        listView = (ListView) findViewById(R.id.list_view);

        ListAdapter = new ArrayAdapter<>(MainActivity.this , android.R.layout.simple_list_item_1 , CategorieLink);

        SpinnerAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.spinner_text, CategorieList);
        SpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        CategorieList.add("AllCategorie");
        CategorieLink.add("http://sachlaptrinh.com/");

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                url_spinner = CategorieLink.get(i);
                JsoupParsing(url_spinner);

                Toast.makeText(MainActivity.this,url_spinner,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        new JsoupParse().execute();


    }


    public void JsoupParsing (final String url ){


        new AsyncTask<BookInfo, String, String>() {
            @Override
            protected void onPostExecute(String s) {


//                ReAdapter = new RecyclerAdapter(BookTitels , getApplicationContext());

                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(BookInfo... strings) {


                try {

                    Document url_doc =  Jsoup.connect(url).get();

                    Elements book_title = url_doc.select(".news-header[title]");
                    Elements book_img = url_doc.select(".new-header[href]");

                    for (int j = 0 ; j < book_title.size() ; j++){


                        BookTitels.add(book_title.get(j).text());
                        BookImgaes.add(book_img.get(j).attr("abs:href"));
                    }





                } catch (IOException e) {
                    e.printStackTrace();
                }


                return null;
            }
        };

    }


    private class JsoupParse extends AsyncTask<Void , Void , Void>{

        String url = "http://sachlaptrinh.com/";
        Document doc=null ;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }



        @Override
        protected Void doInBackground(Void... voids) {

            try {

                doc = Jsoup.connect(url).get();

                Elements Cat_name = doc.select(".collection-item");
                Elements cat_link = doc.select(".collection-item[href]");

                int i ;
                for (i = 0; i <Cat_name.size() ; i++){

                    CategorieList.add(Cat_name.get(i).text());
                    CategorieLink.add(cat_link.get(i).attr("abs:href"));



                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            spinner.setAdapter(SpinnerAdapter);
            listView.setAdapter(ListAdapter);
        }
    }








}
