package com.example.user.cobaasynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ProgressBar pb;
    TextView tvHasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvHasil = (TextView) findViewById(R.id.textView);
        pb = (ProgressBar) findViewById(R.id.progressBar2);
    }

    private class KerjakanBackgroundtask extends
            AsyncTask<Void, Integer, String>{

        /*
        Tiga parameter (Void, Integer, String):
        1. Parameter ke background task;
        2. Pogress saat background task dijalankan
        3. Result hasil dari background task
        Untuk kasus di app ini (untuk app lain bisa berbeda):
        1.Param ke bacground task: Void (tidak ada)
        2.Progress saat background task dijalankan: Integer (setiap loop diincrement dan dikirim ke progressbar
        3.Result: String (output)
        */

        //sebelum process
        protected void onPreExecute(){
            pb.setProgress(0);
            tvHasil.setText("Mulai");
        }

        @Override
        protected String doInBackground(Void... v) {

            String hasil="-";
            try{
                for(int i =0; i<100;i++){
                    Thread.sleep(50);
                    //delay selama 0.05 detik
                    publishProgress(i);

                    if(isCancelled()){
                        hasil="batal";
                        break;   //biar user bisa cancel task di tengah jalan
                    }

                    if(i==99){
                        hasil="Berhasil";
                    }
                }
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            return hasil;
        }

        protected  void onProgressUpdate(Integer... progress){
            //ini untuk update user interface
            pb.setProgress(progress[0]);
        }

        protected void onPostExecute(String result){
            tvHasil.setText(result);
        }
    }

    public  void klikMulai(View v){
        pb.setMax(100);
        new KerjakanBackgroundtask().execute();  //jangan kasih parameter
    }
}
