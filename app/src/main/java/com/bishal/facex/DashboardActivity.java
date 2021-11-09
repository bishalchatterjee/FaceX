package com.bishal.facex;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;


public class DashboardActivity extends AppCompatActivity {


    BottomNavigationView bottomNav;
    EditText codeBox;
    Button joinBtn, shareBtn;

    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        database=FirebaseFirestore.getInstance();


        bottomNav = findViewById(R.id.bottomNavigationView);
        codeBox = findViewById(R.id.codeBox);
        joinBtn = findViewById(R.id.joinBtn);
        shareBtn = findViewById(R.id.shareBtn);






        bottomNav.setOnNavigationItemSelectedListener(navListner);


        URL serverURL;

        try {
            serverURL = new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defaultOptions =
                    new JitsiMeetConferenceOptions.Builder()
                            .setServerURL(serverURL)
                            .setWelcomePageEnabled(false)
                            .build();
            JitsiMeet.setDefaultConferenceOptions(defaultOptions);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                        .setRoom(codeBox.getText().toString())
                        .setWelcomePageEnabled(false)
                        .build();

                JitsiMeetActivity.launch(DashboardActivity.this, options);

            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Code Copyied", codeBox.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(DashboardActivity.this, "Code Copied to Clipboard", Toast.LENGTH_LONG).show();

            }
        });


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.home_menu_item:
                            openNewActivity();
                        case R.id.about_menu_item:
                            selectedFragment = new AboutFragment();
                            break;
                        case R.id.settings_menu_item:
                            selectedFragment = new SettingsFragment();
                            break;
                        case R.id.logout_menu_item:
                            selectedFragment = new AboutFragment();
                            onBackPressed();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().add(R.id.fraghold, selectedFragment).commit();
                    return true;
                }
            };

    private void onBackPressed(MenuItem item) {
        finish();
    }
public void openNewActivity(){
    Intent intent = new Intent(this, DashboardActivity.class);
    startActivity(intent);
}
}


