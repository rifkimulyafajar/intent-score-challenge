package id.putraprima.skorbola;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import id.putraprima.skorbola.model.Data;

public class MatchActivity extends AppCompatActivity {

    private static final String HASILKEY = "hasil";

    private TextView tvHome;
    private TextView tvAway;
    private TextView tvSkorHome;
    private TextView tvSkorAway;
    private ImageView imgHomeLogo;
    private ImageView imgAwayLogo;

    private TextView tvScorerHome;
    private TextView tvScorerAway;

    private int scoreHome=0;
    private int scoreAway=0;

    private Bitmap bitmapHome;
    private Bitmap bitmapAway;

    private Uri uriHome;
    private Uri uriAway;

    private static final int SCORER_REQUEST_HOME = 1;
    private static final int SCORER_REQUEST_AWAY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        tvHome = findViewById(R.id.txt_home);
        tvAway = findViewById(R.id.txt_away);

        tvSkorHome = findViewById(R.id.score_home);
        tvSkorAway = findViewById(R.id.score_away);

        tvScorerHome = findViewById(R.id.scorer_home);
        tvScorerAway = findViewById(R.id.scorer_away);

        tvSkorHome.setText(String.valueOf(0));
        tvSkorAway.setText(String.valueOf(0));

        imgHomeLogo = findViewById(R.id.home_logo);
        imgAwayLogo = findViewById(R.id.away_logo);

        //TODO
        //1.Menampilkan detail match sesuai data dari main activity
        //2.Tombol add score menambahkan memindah activity ke scorerActivity dimana pada scorer activity di isikan nama pencetak gol
        //3.Dari activity scorer akan mengirim kembali ke activity matchactivity otomatis nama pencetak gol dan skor bertambah +1
        //4.Tombol Cek Result menghitung pemenang dari kedua tim dan mengirim nama pemenang beserta nama pencetak gol ke ResultActivity, jika seri di kirim text "Draw",

        Bundle extras = getIntent().getExtras();
        Data data = extras.getParcelable("key");

        if (extras != null) {
            tvHome.setText(data.getTeamHome());
            tvAway.setText(data.getTeamAway());
            uriHome = data.getLogoHomeUri();
            uriAway = data.getLogoAwayUri();

            try {
                bitmapHome = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriHome);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bitmapAway = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriAway);
            } catch (IOException e) {
                e.printStackTrace();
            }

            imgHomeLogo.setImageBitmap(bitmapHome);
            imgAwayLogo.setImageBitmap(bitmapAway);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCORER_REQUEST_HOME) {
            if (resultCode == RESULT_OK) {
                String returnScorerNameHome = data.getStringExtra("score_key");
                scoreHome++;
                tvSkorHome.setText(String.valueOf(scoreHome));

                String scorerlamaHome;
                scorerlamaHome = tvScorerHome.getText().toString();

                tvScorerHome.setText(scorerlamaHome + "\n" + returnScorerNameHome);
            }
        }
        else if (requestCode == SCORER_REQUEST_AWAY) {
            if (resultCode == RESULT_OK) {
                String returnScoreNameAway = data.getStringExtra("score_key");
                scoreAway++;
                tvSkorAway.setText(String.valueOf(scoreAway));

                String scorerlamaAway;
                scorerlamaAway = tvScorerAway.getText().toString();

                tvScorerAway.setText(scorerlamaAway + "\n" + returnScoreNameAway);
            }
        }
    }

    public void btn_ScoreHome(View view) {
        Intent intent = new Intent(this, ScorerActivity.class);
        startActivityForResult(intent, SCORER_REQUEST_HOME);
    }

    public void btn_ScoreAway(View view) {
        Intent intent = new Intent(this, ScorerActivity.class);
        startActivityForResult(intent, SCORER_REQUEST_AWAY);
    }

    public void btn_cekHasil(View view) {
        String hasil;

        Intent intent = new Intent(this, ResultActivity.class);

        if (scoreHome > scoreAway) {
            hasil = tvHome.getText().toString() + " is winner";
        } else if (scoreHome < scoreAway) {
            hasil = tvAway.getText().toString() + " is winner";
        }
        else {
            hasil = "DRAW";
        }

        intent.putExtra(HASILKEY, hasil);
        startActivity(intent);
    }
}
