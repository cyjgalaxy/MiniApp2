package com.example.cyjga.miniapp2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    //private ArrayList<Recipe> mRecipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        this.setTitle("What to cook?");

        //mRecipeList = Recipe.getRecipesFromFile("recipes.json", mContext);

        Button button = (Button) findViewById(R.id.activity_main_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent searchIntent = new Intent(mContext, SearchActivity.class);
//                searchIntent.putExtra("recipeList",mRecipeList);
                startActivity(searchIntent);
            }
        });
    }

}
