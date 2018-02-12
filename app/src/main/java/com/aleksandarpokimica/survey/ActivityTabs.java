package com.aleksandarpokimica.survey;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.appinvite.AppInvite;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityTabs extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    TabHost tabHost;
    private AdView mAdView;
    TextView tvQuestion1, tvQuestion2, tvQuestion3, tvQuestion4, tvQuestion5;
    RadioButton rbAns11, rbAns12, rbAns13, rbAns14, rbAns21, rbAns22, rbAns23, rbAns24, rbAns31, rbAns32, rbAns33, rbAns34,
            rbAns41, rbAns42, rbAns43, rbAns44, rbAns51, rbAns52, rbAns53, rbAns54;
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    private String username;
    public static int randoms[] = new int[99999];
    public static double dRandoms[] = new double[99999];
    public ArrayList<Question> alQuestions = new ArrayList<Question>();
    private ArrayList<String> alIds = new ArrayList<String>();
    public static String dId1, dId2, dId3, dId4, dId5;
    private int ans1, ans2, ans3, ans4, ans5;
    public static String uid;
    private String q1, q2, q3, q4, q5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs2);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .addApi(AppInvite.API)
                .build();

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null){
            Intent i = new Intent(ActivityTabs.this, SignUpActivity.class);
            startActivity(i);
        }

        mAdView = (AdView) findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        tvQuestion1 = (TextView) findViewById(R.id.tvQuestion1);
        tvQuestion2 = (TextView) findViewById(R.id.tvQuestion2);
        tvQuestion3 = (TextView) findViewById(R.id.tvQuestion3);
        tvQuestion4 = (TextView) findViewById(R.id.tvQuestion4);
        tvQuestion5 = (TextView) findViewById(R.id.tvQuestion5);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();
        username = mFirebaseUser.getDisplayName();

        populateAnswers();

        tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        //Tab 1
        TabHost.TabSpec spec = tabHost.newTabSpec("Q1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Q1");
        tabHost.addTab(spec);

        //Tab 2
        spec = tabHost.newTabSpec("Q2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Q2");
        tabHost.addTab(spec);

        //Tab 3
        spec = tabHost.newTabSpec("Q3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Q3");
        tabHost.addTab(spec);

        //Tab 4
        spec = tabHost.newTabSpec("Q4");
        spec.setContent(R.id.tab4);
        spec.setIndicator("Q4");
        tabHost.addTab(spec);

        //Tab 5
        spec = tabHost.newTabSpec("Q5");
        spec.setContent(R.id.tab5);
        spec.setIndicator("Q5");
        tabHost.addTab(spec);

        Button b1 = (Button) findViewById(R.id.bSubmit1);
        Button b2 = (Button) findViewById(R.id.bSubmit2);
        Button b3 = (Button) findViewById(R.id.bSubmit3);
        Button b4 = (Button) findViewById(R.id.bSubmit4);
        Button b5 = (Button) findViewById(R.id.bSubmit5);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabHost.setCurrentTab(1);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabHost.setCurrentTab(2);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabHost.setCurrentTab(3);
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabHost.setCurrentTab(4);
            }
        });

        rbAns11 = (RadioButton) findViewById(R.id.rbAns11);
        rbAns12 = (RadioButton) findViewById(R.id.rbAns12);
        rbAns13 = (RadioButton) findViewById(R.id.rbAns13);
        rbAns14 = (RadioButton) findViewById(R.id.rbAns14);
        rbAns21 = (RadioButton) findViewById(R.id.rbAns21);
        rbAns22 = (RadioButton) findViewById(R.id.rbAns22);
        rbAns23 = (RadioButton) findViewById(R.id.rbAns23);
        rbAns24 = (RadioButton) findViewById(R.id.rbAns24);
        rbAns31 = (RadioButton) findViewById(R.id.rbAns31);
        rbAns32 = (RadioButton) findViewById(R.id.rbAns32);
        rbAns33 = (RadioButton) findViewById(R.id.rbAns33);
        rbAns34 = (RadioButton) findViewById(R.id.rbAns34);
        rbAns41 = (RadioButton) findViewById(R.id.rbAns41);
        rbAns42 = (RadioButton) findViewById(R.id.rbAns42);
        rbAns43 = (RadioButton) findViewById(R.id.rbAns43);
        rbAns44 = (RadioButton) findViewById(R.id.rbAns44);
        rbAns51 = (RadioButton) findViewById(R.id.rbAns51);
        rbAns52 = (RadioButton) findViewById(R.id.rbAns52);
        rbAns53 = (RadioButton) findViewById(R.id.rbAns53);
        rbAns54 = (RadioButton) findViewById(R.id.rbAns54);


        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAnswers();
            }
        });
    }

    private void updateAnswers() {
        uid = mFirebaseUser.getUid();

        if(rbAns11.isChecked()){
            ans1 = 1;
        }
        if(rbAns12.isChecked()){
            ans1 = 2;
        }
        if(rbAns13.isChecked()){
            ans1 = 3;
        }
        if(rbAns14.isChecked()){
            ans1 = 4;
        }
        if(rbAns21.isChecked()){
            ans2 = 1;
        }
        if(rbAns22.isChecked()){
            ans2 = 2;
        }
        if(rbAns23.isChecked()){
            ans2 = 3;
        }
        if(rbAns24.isChecked()){
            ans2 = 4;
        }
        if(rbAns31.isChecked()){
            ans3 = 1;
        }
        if(rbAns32.isChecked()){
            ans3 = 2;
        }
        if(rbAns33.isChecked()){
            ans3 = 3;
        }
        if(rbAns34.isChecked()){
            ans3 = 4;
        }
        if(rbAns41.isChecked()){
            ans4 = 1;
        }
        if(rbAns42.isChecked()){
            ans4 = 2;
        }
        if(rbAns43.isChecked()){
            ans4 = 3;
        }
        if(rbAns44.isChecked()){
            ans4 = 4;
        }
        if(rbAns51.isChecked()){
            ans5 = 1;
        }
        if(rbAns52.isChecked()){
            ans5 = 2;
        }
        if(rbAns53.isChecked()){
            ans5 = 3;
        }
        if(rbAns54.isChecked()){
            ans5 = 4;
        }

        Answer a1 = new Answer(uid, ans1);
        Answer a2 = new Answer(uid, ans2);
        Answer a3 = new Answer(uid, ans3);
        Answer a4 = new Answer(uid, ans4);
        Answer a5 = new Answer(uid, ans5);

        mFirebaseDatabaseReference.child("answers").child(dId1).push().setValue(a1);
        mFirebaseDatabaseReference.child("answers").child(dId2).push().setValue(a2);
        mFirebaseDatabaseReference.child("answers").child(dId3).push().setValue(a3);
        mFirebaseDatabaseReference.child("answers").child(dId4).push().setValue(a4);
        mFirebaseDatabaseReference.child("answers").child(dId5).push().setValue(a5);
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    private void populateAnswers() {


        DatabaseReference ref = mFirebaseDatabaseReference.child("questions");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String dQuestion;

                for(DataSnapshot questionSnapshot : dataSnapshot.getChildren()){

                    dQuestion = questionSnapshot.child("question").getValue(String.class);
                    String id = questionSnapshot.getKey();
                    Question q = new Question(dQuestion);
                    alIds.add(id);
                    alQuestions.add(q);

                }

                dRandoms[0] = Math.random() * alQuestions.size();
                dRandoms[1] = Math.random() * alQuestions.size();
                dRandoms[2] = Math.random() * alQuestions.size();
                dRandoms[3] = Math.random() * alQuestions.size();
                dRandoms[4] = Math.random() * alQuestions.size();

                randoms[0] = (int) dRandoms[0];
                randoms[1] = (int) dRandoms[1];
                while(randoms[0] == randoms[1]){
                    dRandoms[1] = Math.random() * alQuestions.size();
                    randoms[1] = (int) dRandoms[1];
                }
                randoms[2] = (int) dRandoms[2];
                while(randoms[2] == randoms[0] || randoms[2] == randoms[1]){
                    dRandoms[2] = Math.random() * alQuestions.size();
                    randoms[2] = (int) dRandoms[2];
                }
                randoms[3] = (int) dRandoms[3];
                while(randoms[3] == randoms[0] || randoms[3] == randoms[1] || randoms[3] == randoms[2]){
                    dRandoms[3] = Math.random() * alQuestions.size();
                    randoms[3] = (int) dRandoms[3];
                }
                randoms[4] = (int) dRandoms[4];
                while(randoms[4] == randoms[0] || randoms[4] == randoms[1] || randoms[4] == randoms[2] || randoms[4] == randoms[3]){
                    dRandoms[4] = Math.random() * alQuestions.size();
                    randoms[4] = (int) dRandoms[4];
                }

                q1 = alQuestions.get(randoms[0]).getQuestion();
                q2 = alQuestions.get(randoms[1]).getQuestion();
                q3 = alQuestions.get(randoms[2]).getQuestion();
                q4 = alQuestions.get(randoms[3]).getQuestion();
                q5 = alQuestions.get(randoms[4]).getQuestion();

                tvQuestion1.setText(q1);
                tvQuestion2.setText(q2);
                tvQuestion3.setText(q3);
                tvQuestion4.setText(q4);
                tvQuestion5.setText(q5);

                dId1 = alIds.get(randoms[0]);
                dId2 = alIds.get(randoms[1]);
                dId3 = alIds.get(randoms[2]);
                dId4 = alIds.get(randoms[3]);
                dId5 = alIds.get(randoms[4]);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                mAuth.signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                startActivity(new Intent(this, SignInActivity.class));
                return true;
            case R.id.submit_question_menu:
                Intent intent = new Intent(ActivityTabs.this, SubmitQuestionsActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }
}
