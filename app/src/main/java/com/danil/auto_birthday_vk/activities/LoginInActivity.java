package com.danil.auto_birthday_vk.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.danil.auto_birthday_vk.R;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import com.vk.sdk.util.VKUtil;

public class LoginInActivity extends AppCompatActivity {
    private boolean isResumed = false;
    String response;
    private static final String[] sMyScope = new String[]{
            VKScope.FRIENDS,
            VKScope.MESSAGES,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_in);

        Log.i("VKLogin", (VKUtil.getCertificateFingerprint(this, this.getPackageName()))[0]);
        VKSdk.wakeUpSession(this, new VKCallback<VKSdk.LoginState>() {
            @Override
            public void onResult(VKSdk.LoginState res) {
                if (isResumed) {
                    switch (res) {
                        case LoggedOut:
                            break;
                        case LoggedIn:

                            finish();

                        case Pending:
                            break;
                        case Unknown:
                            break;
                    }
                }
            }

            @Override
            public void onError(VKError error) {

            }
        });

        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VKSdk.login(LoginInActivity.this, sMyScope);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        isResumed = true;
        if (VKSdk.isLoggedIn()) {
            Intent intent = new Intent();
            intent.putExtra("backpressed", "0");
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    protected void onPause() {
        isResumed = false;
        super.onPause();
    }

    @Override
    public void finish() {

        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        VKCallback<VKAccessToken> callback = new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                // User passed Authorization
                Intent intent = new Intent();
                intent.putExtra("backpressed", "0");
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onError(VKError error) {
                // User didn't pass Authorization
            }
        };

        if (!VKSdk.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("backpressed", "1");
        setResult(RESULT_OK, intent);
        finish();


    }

}