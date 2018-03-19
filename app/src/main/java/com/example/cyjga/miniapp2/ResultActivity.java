package com.example.cyjga.miniapp2;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cyjga on 2018-03-18.
 */
public class ResultActivity extends AppCompatActivity {

    private Context mContext;
    private ListView mListView;
    private ArrayList<Recipe> mResultRecipeList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mContext = this;
        setTitle(super.getTitle());
        Bundle bundle = this.getIntent().getExtras();
        mResultRecipeList = (ArrayList<Recipe>) bundle.getSerializable("searchedRecipeList");

        TextView howManyFoundText = findViewById(R.id.recipe_search_found);

        howManyFoundText.setText(Integer.toString(mResultRecipeList.size())+" Results Found");

        RecipeAdapter adapter = new RecipeAdapter(mContext, mResultRecipeList);

        mListView = findViewById(R.id.recipe_list_view);
        mListView.setAdapter(adapter);

    }
}
