package ru.netologia.sharinm_task_522;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class LoginActivity extends AppCompatActivity {

    private EditText login;
    private EditText password;

    private SharedPreferences sharedPreferences;
    public static final String SHARED_PREFERENCES_FILES = "FileInternal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        sharedPreferences = getSharedPreferences("MySharedPreference", MODE_PRIVATE);

        login = findViewById(R.id.editTextLogin);
        password = findViewById(R.id.editTextPassword);

        Button btnOK = findViewById(R.id.buttonOK);
        Button btnRegistration = findViewById(R.id.buttonRegistration);
        Button btnClear = findViewById(R.id.buttonClear);
        Button btnSettings = findViewById(R.id.buttonSettings);

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sharedPreferences.getBoolean(LoginActivity.SHARED_PREFERENCES_FILES,false)) {
                    if (login.getText().toString().replace(" ", "").equals("")) {
                        Toast.makeText(LoginActivity.this, getString(R.string.textAlarmLogin), Toast.LENGTH_SHORT).show();
                    } else {
                        if (password.getText().toString().replace(" ", "").equals("")) {
                            Toast.makeText(LoginActivity.this, getString(R.string.textAlarmPassword), Toast.LENGTH_SHORT).show();
                        } else {
                            WriteFile(getString(R.string.loginFileName), login.getText().toString());
                            WriteFile(getString(R.string.passwordFileName), password.getText().toString());
                            ClearEditTexts(getString(R.string.textMessageSave));
                        }
                    }
                } else {
//TODO:
                }
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginValue = ReadFile(getString(R.string.loginFileName));
                String passwordValue = ReadFile(getString(R.string.passwordFileName));
                if(sharedPreferences.getBoolean(LoginActivity.SHARED_PREFERENCES_FILES,false)) {

                    if ((loginValue.equals("")) || (passwordValue.equals(""))) {
                        Toast.makeText(LoginActivity.this, getString(R.string.textErrorNoEntered), Toast.LENGTH_SHORT).show();
                    } else {
                        if ((loginValue.equals(login.getText().toString().replace(" ", "")))
                                && (passwordValue.equals(password.getText().toString().replace(" ", "")))) {
                            Toast.makeText(LoginActivity.this, getString(R.string.textMessageGood, loginValue, passwordValue), Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(LoginActivity.this, getString(R.string.textErrorChech), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
//TODO:
                }
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearEditTexts(getString(R.string.textMessageClear));
            }
        });
    }

    private void ClearEditTexts(String text){
        password.setText("");
        login.setText("");
        login.requestFocus();

        Toast.makeText(LoginActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    private void WriteFile(String nameFile, String text){
        try {
            // Создадим файл и откроем поток для записи данных
            FileOutputStream fileOutputStream = openFileOutput(nameFile, MODE_PRIVATE);
            // Обеспечим переход символьных потока данных к байтовым потокам.
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            // Запишем текст в поток вывода данных, буферизуя символы так, чтобы обеспечить эффективную запись отдельных символов.
            BufferedWriter bw = new BufferedWriter(outputStreamWriter);
            // Осуществим запись данных
            bw.write(text);
            // закроем поток
            bw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String ReadFile(String nameFile){
        try {
            // Получим входные байты из файла которых нужно прочесть.
            FileInputStream fileInputStream = openFileInput(nameFile);
            // Декодируем байты в символы
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            // Читаем данные из потока ввода, буферизуя символы так, чтобы обеспечить эффективную запись отдельных символов.
            BufferedReader reader = new BufferedReader(inputStreamReader);

            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
