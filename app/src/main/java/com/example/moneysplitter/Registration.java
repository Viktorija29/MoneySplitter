package com.example.moneysplitter;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

class T{
    String token;

    T(){

    }
}

import com.example.moneysplitter.utils.ServerRequest;

import org.json.JSONException;
import org.json.JSONObject;


public class Registration extends AppCompatActivity {

    private Button Next;

    private EditText Text1;
    private EditText Text2;
    private RadioButton Radio1;
    private RadioButton Radio2;

    private EditText FName, SecName, Email, Phone, PassOriginal, PassCopy, Age, Password;
    private RadioButton Gender, Male, Female;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
        }

        String gettoken = "";
        JSONObject obj = new JSONObject();
        try {
            obj.put("email", "eve.holt@reqres.in");
            obj.put("password", "pistol");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,"https://reqres.in/api/register",obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("JSON - ответ:\n");
                        System.out.println(response);
/*
                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();
                        T token = gson.fromJson(String.valueOf(response), T.class);
                        System.out.println("ОБЪЕКТ " + token.token);
                        System.out.println("КОНЕЦ:\n");*/
                        //hideProgressDialog();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //hideProgressDialog();
                    }
                });
        queue.add(jsObjRequest);

        addListenerOnButton();
    }


    public void addListenerOnButton () {
        Next = (Button) findViewById(R.id.Next);
        Text1 = (EditText) findViewById(R.id.editTextNumberPassword);
        Text2 = (EditText) findViewById(R.id.editTextNumberPassword2);

        Next.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String pass = String.valueOf(Text1.getText());
                        String pass2 = String.valueOf(Text2.getText());

                        if (pass.length() < 8 || pass2.length() < 8){
                            Toast toast = Toast.makeText(getApplicationContext(), "Пароль должен быть не менее 8 символов",Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        else if (!pass.equals(pass2)) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Пароли не совпадают",Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        else{
                            Intent intent = new Intent("com.example.moneysplitter.RegistrationAddPhoto");
                            startActivity(intent);
                        }

                    }
                }
        );
    };

        addListenerOnButton();
    }

    public void addListenerOnButton() {
        Next = (Button) findViewById(R.id.Next);
        FName = (EditText) findViewById(R.id.FName);
        SecName = (EditText) findViewById(R.id.SecName);
        Email = (EditText) findViewById(R.id.Email);
        Phone = (EditText) findViewById(R.id.Phone);
        PassOriginal = (EditText) findViewById(R.id.PassOriginal);
        PassCopy = (EditText) findViewById(R.id.PassCopy);
        if (PassOriginal == PassCopy) {
            Password = PassOriginal;
        } else {
            //todo проверить совпадают ли пароли. Если нет, придумать как остановить всё и выдать ошибку
        }
        Age = (EditText) findViewById(R.id.year);
        Male = (RadioButton) findViewById(R.id.male);
        Female = (RadioButton) findViewById(R.id.female);
        if (Male == null) {
            Gender = Female;
        } else {
            Gender = Male;
        }


        Next.setOnClickListener(v -> {// метод создание запроса
                    //todo задизейблить кнопку от многочисленного нажатия на неё, чтобы не отправлялось куча запросов на сервак
                    JSONObject jsonBody = new JSONObject();
                    try {
                        jsonBody.put("Name: ", FName);
                        jsonBody.put("Surname: ", SecName);
                        jsonBody.put("Email: ", Email);
                        jsonBody.put("Phone: ", Phone);
                        jsonBody.put("Password: ", Password);
                        jsonBody.put("Age: ", Age);
                        jsonBody.put("Gender: ", Gender);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String transitionContextName = "com.example.moneysplitter.RegistrationAddPhoto";
                    String errorText = "Заполните все обязателььные поля";
                    ServerRequest.getInstance().Send(this, transitionContextName, errorText, jsonBody);
                }
        );
    }

    ;
}