package com.aleksandarpokimica.survey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SubmitQuestionsActivity extends AppCompatActivity {

    private EditText etQuestion;
    private Button bSubmit;
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;
    String uid;
    public static String todayDate;
    public static ArrayList<Question> alQuestions = new ArrayList<Question>();
    private boolean checker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit_questions);

        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        todayDate = sdf.format(new Date());

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = mFirebaseDatabaseReference.child("questions");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String qDate;
                String qUid;
                String qQuestion = "placeholder";

                for(DataSnapshot questionSnapshot : dataSnapshot.getChildren()){

                    qDate = questionSnapshot.child("date").getValue(String.class);
                    qUid = questionSnapshot.child("uid").getValue(String.class);

                    Question q = new Question(qDate, qQuestion, qUid);
                    alQuestions.add(q);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        etQuestion = (EditText) findViewById(R.id.etQuestion);
        bSubmit = (Button) findViewById(R.id.bSubmit);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            uid = mFirebaseUser.getUid();
        }

        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
                todayDate = sdf.format(new Date());
                String question = etQuestion.getText().toString();
                uid = mFirebaseUser.getUid();

                Question q = new Question(todayDate, question, uid);

                if(question.isEmpty()){

                    Toast.makeText(SubmitQuestionsActivity.this, "Please fill in the question.", Toast.LENGTH_LONG).show();

                }
                else{

                    String sDate = "", sUid = "";

                    for(int i = 0; i<alQuestions.size(); i++){
                        sDate = alQuestions.get(i).getDate();
                        sUid = alQuestions.get(i).getUid();
                        if(sDate.equals(todayDate) && sUid.equals(uid)){
                            checker = false;
                            break;
                        }
                        else{
                            checker = true;
                        }
                    }
                    if(checker){
                        mFirebaseDatabaseReference.child("questions").push().setValue(q);
                        Toast.makeText(SubmitQuestionsActivity.this, "Thank you for submitting your question.", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(SubmitQuestionsActivity.this, "You can only submit one question per day.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
