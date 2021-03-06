package com.fourthstatelab.confession.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.fourthstatelab.confession.R;
import com.fourthstatelab.confession.Utils.Account;
import com.fourthstatelab.confession.Utils.DataHolder;
import com.fourthstatelab.confession.Utils.HttpRequest;
import com.fourthstatelab.confession.Utils.Preference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import static com.fourthstatelab.confession.Utils.Preference.EMAIL;
import static com.fourthstatelab.confession.Utils.Preference.JOBHI_KEY;
import static com.fourthstatelab.confession.Utils.Preference.PASSWORD;
import static com.fourthstatelab.confession.Utils.Preference.PROFILE_JSON;
import static com.fourthstatelab.confession.Utils.Preference.USERNAME;
import static com.fourthstatelab.confession.Utils.Preference.get;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        String z=getSharedPreferences("pass",Context.MODE_PRIVATE).getString("email","");
        if(z==null || z.equals("")==true)
        {
            navigate_to_signup();
        }
        else
        {

            try_signin();
        }
    }

    public void navigate_to_signup()
    {
        startActivity(new Intent(SplashScreen.this,Sign_Up.class));
        finish();
    }

    public void try_signin()
    {
        HttpRequest signin=(new HttpRequest(getApplicationContext(),"/signin"))
                .addParam("email", getSharedPreferences("pass",Context.MODE_PRIVATE).getString("email",""))
                .addParam("password", getSharedPreferences("pass", Context.MODE_PRIVATE).getString("password",""))
                .sendRequest(new HttpRequest.OnResponseListener() {
                    @Override
                    public void OnResponse(String response) {
                        if(response!=null && response.equals("0")!=true){
                            DataHolder.account=new Gson().fromJson(response.toString(),new TypeToken<Account>(){}.getType());

                            startActivity(new Intent(SplashScreen.this,Dashboard.class));
                            finish();
                        }
                        else{
                            startActivity(new Intent(SplashScreen.this,Sign_Up.class));
                            finish();
                        }
                    }
                });
    }
}
