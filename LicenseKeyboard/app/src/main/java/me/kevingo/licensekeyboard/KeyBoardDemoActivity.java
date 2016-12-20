package me.kevingo.licensekeyboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class KeyBoardDemoActivity extends AppCompatActivity implements KeyBoardPopWindow.LicenseListener {
    Button button;
    KeyBoardPopWindow keyBoardPopWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_board_demo);

        keyBoardPopWindow = new KeyBoardPopWindow(this ,this);

        button = (Button) findViewById(R.id.btn_add_car);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!keyBoardPopWindow.isShowing()) {
                    keyBoardPopWindow.showKeyBoardPopWindow(button);
                }else {
                    return;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setLicense(String license) {
        Toast.makeText(this,license,Toast.LENGTH_LONG).show();
    }
}
