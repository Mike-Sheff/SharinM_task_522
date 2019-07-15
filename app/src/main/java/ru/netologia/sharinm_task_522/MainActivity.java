package ru.netologia.sharinm_task_522;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private CheckBox checkbox;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("MySharedPreference", MODE_PRIVATE);

        spinner = findViewById(R.id.spinner);

        checkbox = findViewById(R.id.checkbox);

        if (sharedPreferences.getBoolean(LoginActivity.SHARED_PREFERENCES_FILES,false)){
            checkbox.setChecked(true);
        } else {
            checkbox.setChecked(false);
        }

        Button btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lang;

                SharedPreferences.Editor myEditor = sharedPreferences.edit();
                myEditor.putBoolean(LoginActivity.SHARED_PREFERENCES_FILES, checkbox.isChecked());
                myEditor.apply();

                int selected = (int) spinner.getSelectedItemId();

                switch (selected) {
                    default:
                    case 0:
                        lang = "ru";
                        break;
                    case 1:
                        lang = "en";
                        break;
                    case 2:
                        lang = "fr";
                        break;
                    case 3:
                        lang = "de";
                        break;
                }

                Locale locale = new Locale(lang);
                Configuration config = new Configuration();
                config.setLocale(locale);
                getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                recreate();
                Toast.makeText(MainActivity.this, getString(R.string.textTextView), Toast.LENGTH_LONG).show();

            }
        });


        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkbox.isChecked()) {

                } else {

                }
            }
        });
    }
/*
    @Override
    public void onSaveInstanceState(Bundle saveInstanceState){

        if (checkbox.isChecked()){
            saveInstanceState.putBoolean(SHARED_PREFERENCES_FILES, true);
        } else {
            saveInstanceState.putBoolean(SHARED_PREFERENCES_FILES, false);
        }

        super.onSaveInstanceState(saveInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle saveInstanceState){
        super.onRestoreInstanceState(saveInstanceState);

        if (saveInstanceState.getBoolean(SHARED_PREFERENCES_FILES)){
            checkbox.setChecked(true);
        } else {
            checkbox.setChecked(false);
        }
    }*/
}