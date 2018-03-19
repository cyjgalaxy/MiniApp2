package com.example.cyjga.miniapp2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cyjga on 2018-03-18.
 */

public class SearchActivity extends AppCompatActivity {

    private Context mContext;
    private ArrayList<Recipe> mRecipeList;

    String dietSelect;
    String servingSelect;
    String prepSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mContext = this;
        setTitle(super.getTitle());

        mRecipeList = Recipe.getRecipesFromFile("recipes.json", mContext);
        final ArrayList<Recipe> recipe = mRecipeList;
//        mRecipeList = (ArrayList<Recipe>) this.getIntent().getSerializableExtra("recipeList");
//        mRecipeList = Recipe.getRecipesFromFile("recipes.json", mContext);

        ArrayList<String> dietRestriction = getSearchRestrictions(recipe);
        String[] dietRes = new String[dietRestriction.size()+1];
        dietRes[0] = "All";
        for(int i=0;i<dietRestriction.size();i++)
            dietRes[i+1]=dietRestriction.get(i);

        final Spinner dietSpinner = findViewById(R.id.recipe_search_spinner_diet);
        ArrayAdapter<String> dietAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, dietRes);
        dietSpinner.setAdapter(dietAdapter);
        dietSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dietSelect = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dietSelect = null;
            }
        });

        final Spinner servingSpinner = findViewById(R.id.recipe_search_spinner_serving);
        String[] servingItems = new String[]{"All","less than 4","4-6","7-9","10 or more"};
        ArrayAdapter<String> servingAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, servingItems);
        servingSpinner.setAdapter(servingAdapter);
        servingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                servingSelect = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                servingSelect = null;
            }
        });

        final Spinner prepSpinner = findViewById(R.id.recipe_search_spinner_prep);
        String[] prepItems = new String[]{"All","30 minutes or less","1 hour or less","more than 1 hour"};
        ArrayAdapter<String> prepAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, prepItems);
        prepSpinner.setAdapter(prepAdapter);
        prepSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prepSelect = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                prepSelect = null;
            }
        });

//        ArrayList<Recipe> searchedRecipeList = onSearch(mRecipeList, dietSelect, servingSelect, prepSelect);

        Button searchButton = (Button) findViewById(R.id.recipe_search_button);
        searchButton.setText("Search");

        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent resultIntent = new Intent(mContext, ResultActivity.class);
                mRecipeList = recipe;
                ArrayList<Recipe> searchedRecipeList = mRecipeList;
                searchedRecipeList = onSearch(searchedRecipeList);
                Bundle bundle = new Bundle();
                bundle.putSerializable("searchedRecipeList",searchedRecipeList);
                //resultIntent.putExtra("recipeList",searchedRecipeList);
                //resultIntent.putExtra("searchedRecipeList",mRecipeList);
                resultIntent.putExtras(bundle);
                startActivity(resultIntent);
            }
        });

    }

    private ArrayList<String> getSearchRestrictions(ArrayList<Recipe> rList) {

        ArrayList<Recipe> rL = rList;
        ArrayList<String> dietRestrictions = new ArrayList<>();

        for (int i = 0; i < rL.size(); i++) {
            Recipe recipeAtIndex = rL.get(i);
            String dLabel = recipeAtIndex.dietLabel;

            if(!(dietRestrictions.contains(dLabel)))
                dietRestrictions.add(dLabel);
        }

        return dietRestrictions;
    }

    private int parseTime(String time) {

        String tempTime = time;
        int returnTime = 0;

        while(tempTime.contains("s"))
            tempTime.replace("s", "");

        if(tempTime.endsWith("hour"))
            returnTime += Integer.parseInt(tempTime.substring(0,tempTime.indexOf("hour")-2))*60;
        else {
            if(tempTime.contains("and")){
                returnTime += Integer.parseInt(tempTime.substring(0,tempTime.indexOf("hour")-2))*60;
                returnTime += Integer.parseInt(tempTime.substring(tempTime.indexOf("hour")+5,tempTime.indexOf("minute")-2));
            }
            else
                returnTime += Integer.parseInt(tempTime.substring(0,tempTime.indexOf("minute")-2));
        }
        return returnTime;
    }
    private ArrayList<Recipe> onSearch(ArrayList<Recipe> recRecipeList) {

        ArrayList<Recipe> recList = recRecipeList;
        ArrayList<Recipe> returnList = new ArrayList<Recipe>();
/*
        ArrayList<Recipe> dummyList1 = new ArrayList<Recipe>();
        ArrayList<Recipe> dummyList2 = new ArrayList<Recipe>();
        ArrayList<Recipe> dummyList3 = new ArrayList<Recipe>();
*/
        if(!dietSelect.equals("All")) {
            for (int i = recList.size()-1; i >= 0; i--) {
                if(!dietSelect.equals(recList.get(i).dietLabel)) {
                    recList.remove(i);
                }
            }
        }

//        if(!servingSelect.isEmpty()) {
            switch(servingSelect){
                case "less than 4":
                    for(int i = recList.size()-1; i >= 0; i--) {
                        if(!(recList.get(i).servings < 4)) {
                            recList.remove(i);
                        }
                    }
                    break;
                case "4-6":
                    for(int i = recList.size()-1; i >= 0; i--) {
                        if(!((recList.get(i).servings >= 4)&&(recList.get(i).servings <= 6)) ) {
                            recList.remove(i);
                        }
                    }
                    break;
                case "7-9":
                    for(int i = recList.size()-1; i >= 0; i--) {
                        if(!((recList.get(i).servings >= 7)&&(recList.get(i).servings <= 9)) ) {
                            recList.remove(i);
                        }
                    }
                    break;
                case "10 or more":
                    for(int i = recList.size()-1; i >= 0; i--) {
                        if(!(recList.get(i).servings > 10)) {
                            recList.remove(i);
                        }
                    }
                    break;
                default:
                    break;
            }
  //      }

    //    if(!prepSelect.isEmpty()){
            switch(prepSelect){
                case "30 minutes or less":
                    for(int i = recList.size()-1; i >= 0; i--) {
                        if(!(parseTime(recList.get(i).prepTime)<=30)) {
                            recList.remove(i);
                        }
                    }
                    break;
                case "1 hour or less":
                    for(int i = recList.size()-1; i >= 0; i--) {
                        if(!(parseTime(recList.get(i).prepTime)<=60)) {
                            recList.remove(i);
                        }
                    }
                    break;
                case "more than 1 hour":
                    for(int i = recList.size()-1; i >= 0; i--) {
                        if(!(parseTime(recList.get(i).prepTime)>60)) {
                            recList.remove(i);
                        }
                    }
                    break;
                default:
                    break;
            }
   //     }
        returnList = recList;
        return returnList;
    }

}
