package com.example.cyjga.miniapp2;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cyjga on 2018-03-18.
 */
public class ResultActivity extends AppCompatActivity {

    private Context mContext;
    private ListView mListView;
    private ArrayList<Recipe> mRecipeList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mContext = this;
        setTitle(super.getTitle());
        mRecipeList = (ArrayList<Recipe>) this.getIntent().getSerializableExtra("searchedRecipeList");
        String dSelect = this.getIntent().getExtras().getString("dietSelect");
        String pSelect = this.getIntent().getExtras().getString("prepSelect");
        String sSelect = this.getIntent().getExtras().getString("servingSelect");

        ArrayList<Recipe> mResultRecipeList = onSearch(mRecipeList,pSelect,dSelect,sSelect);


        TextView howManyFoundText = findViewById(R.id.recipe_search_found);

        howManyFoundText.setText(Integer.toString(mResultRecipeList.size())+" Results Found");


        RecipeAdapter adapter = new RecipeAdapter(mContext, mResultRecipeList);

        mListView = findViewById(R.id.recipe_list_view);
        mListView.setAdapter(adapter);

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
    private ArrayList<Recipe> onSearch(ArrayList<Recipe> recRecipeList, String prepSelect, String dietSelect, String servingSelect) {

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
