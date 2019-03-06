package fahmi.tracking.com.tracking;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectActivity extends AppCompatActivity {
    @BindView(R.id.kond_submit)
    Button submit;
    @BindView(R.id.radioPilih)
    RadioGroup kondisiRadio;
    String kondisi;
    RadioButton radioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_select);

        ButterKnife.bind(this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Bundle b = new Bundle();
                int selected= kondisiRadio.getCheckedRadioButtonId();
                radioButton=(RadioButton) findViewById(selected);
                b.putString("kondisi", radioButton.getText().toString());

                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }
}
