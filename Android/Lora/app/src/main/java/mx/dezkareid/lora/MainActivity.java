package mx.dezkareid.lora;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import mx.dezkareid.lora.sound.SoundReceiver;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    private RecyclerView recyclerView;
    private List<Weather> weathers;
    private SoundReceiver mReceiver;
    RVAdapter adapter;
    private String message;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.MyToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        message = "";
        weathers = new ArrayList<Weather>();

        recyclerView = (RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        adapter = new RVAdapter(weathers);
        recyclerView.setAdapter(adapter);
        mReceiver = new SoundReceiver(mHandler);

    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            if (0 < msg.what) {
            } else {
                if (msg.getData() != null) {
                    String s = msg.getData().getString("Text");
                    if (s != null){
                        try {
                            int code = Integer.parseInt(s);
                            i++;
                            message += Character.toString ((char) code);
                            if (i %2 == 0){
                                Weather w = new Weather("Temperatura: "+message+"ºC", R.drawable.sun);
                                weathers.add(w);
                                adapter.notifyDataSetChanged();
                                message = "";
                            }

                        }catch (Exception e){

                        }

                    }
                }
            }
        }
    };


    @Override
    protected void onPause() {
        mReceiver.stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mReceiver.start();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mReceiver.destroy();
        super.onDestroy();
    }

}
