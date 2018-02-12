package com.aleksandarpokimica.survey;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class AdminAdapter extends ArrayAdapter<Question> {

    private Activity activity;
    private ViewHolder mHolder;
    public ArrayList<Question> item = new ArrayList<Question>();
    private DatabaseReference mFirebaseDatabaseReference;

    public AdminAdapter(Activity activity, int textViewResourceId, ArrayList<Question> items) {
        super(activity, textViewResourceId, items);
        this.item = items;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        View v = convertView;

        if (v == null) {
            LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.admin_panel_row, null);

            mHolder = new ViewHolder();
            v.setTag(mHolder);

            mHolder.adminQuestion = (EditText) v.findViewById(R.id.etQuestionAdmin);
            mHolder.adminDelete = (Button) v.findViewById(R.id.bDelete);
            mHolder.adminSave = (Button) v.findViewById(R.id.bSave);
        }
        else {
            mHolder =  (ViewHolder) v.getTag();
        }

        mHolder.adminDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String uid = item.get(position).getUid();
                mFirebaseDatabaseReference.child("questions").child(uid).removeValue();
                Toast.makeText(getContext(), "Question deleted.", Toast.LENGTH_LONG).show();
            }
        });

        mHolder.adminSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String uid = item.get(position).getUid();
                String editQuestion = mHolder.adminQuestion.getText().toString();
                mFirebaseDatabaseReference.child("questions").child(uid).setValue(editQuestion);
                Toast.makeText(getContext(), "Question changed.", Toast.LENGTH_LONG).show();
            }
        });

        mHolder.adminQuestion.setText(item.get(position).getQuestion());
        return v;
    }

    private class ViewHolder {
        private EditText adminQuestion;
        private Button adminDelete;
        private Button adminSave;
    }
}
