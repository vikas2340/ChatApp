package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;

import com.example.chatapp.Adapters.FragmentsAdapter;
import com.example.chatapp.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("message");
        myRef.setValue("hello,World!");

        auth = FirebaseAuth.getInstance();
        binding.viewPager.setAdapter(new FragmentsAdapter(getSupportFragmentManager()));
        binding.tablayout.setupWithViewPager(binding.viewPager);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {

                String value=dataSnapshot.getValue(String.class);
                Toast.makeText(MainActivity.this,""+value,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.settings:
                Intent i =new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(i);
                break;

            case R.id.logout:
                auth.signOut();
                Intent intent=new Intent(MainActivity.this,SignInActivity.class);
                startActivity(intent);
                break;

            case R.id.groupChat:
                Intent intent1=new Intent(MainActivity.this,GroupChatActivity.class);
                startActivity(intent1);
        }
        return true;
    }
}