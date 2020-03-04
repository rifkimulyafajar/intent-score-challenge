package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ScorerActivity extends AppCompatActivity {

    private EditText scorerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorer);

        scorerName = findViewById(R.id.editText);
    }

    public void btnSendResult(View view) {
        String scorer = scorerName.getText().toString();

        Intent intent = new Intent();
        intent.putExtra("score_key", scorer);
        setResult(RESULT_OK, intent);
        finish();
    }
}
