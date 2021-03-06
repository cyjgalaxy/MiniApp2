package com.example.cyjga.miniapp2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.squareup.picasso.Picasso;

/**
 * Created by cyjga on 2018-03-18.
 */

public class RecipeAdapter extends BaseAdapter {

    // adapter takes the app itself and a list of data to display
    private Context mContext;
    private ArrayList<Recipe> mRecipeList;
    private LayoutInflater mInflater;

    // constructor
    public RecipeAdapter(Context mContextReceived, ArrayList<Recipe> mRecipeListReceived){

        // initialize instances variables
        mContext = mContextReceived;
        mRecipeList = mRecipeListReceived;
        mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    // methods
    // a list of methods we need to override

    // gives you the number of recipes in the data source
    @Override
    public int getCount(){
        return mRecipeList.size();
    }

    // returns the item at specific position in the data source

    @Override
    public Object getItem(int position){
        return mRecipeList.get(position);
    }

    // returns the row id associated with the specific position in the list
    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;

        // check if the view already exists
        // if yes, you don't need to inflate and findViewbyID again
        if (convertView == null){
            // inflate
            convertView = mInflater.inflate(R.layout.recipe_item_list, parent, false);
            // add the views to the holder
            holder = new ViewHolder();
            // views
            holder.titleTextView = convertView.findViewById(R.id.recipe_list_title);
            holder.servingTextView = convertView.findViewById(R.id.recipe_list_servings);
            holder.thumbnailImageView = convertView.findViewById(R.id.recipe_list_image);
            holder.prepTimeTextView = convertView.findViewById(R.id.recipe_list_prep_time);
            holder.resultButton = convertView.findViewById(R.id.recipe_list_Button);

            // add the holder to the view
            // for future use
            convertView.setTag(holder);
        }
        else{
            // get the view holder from converview
            holder = (ViewHolder)convertView.getTag();
        }
        // get relavate subview of the row view
        TextView titleTextView = holder.titleTextView;
        TextView servingTextView = holder.servingTextView;
        ImageView thumbnailImageView = holder.thumbnailImageView;
        TextView prepTimeTextView = holder.prepTimeTextView;
        final Button resultButton = holder.resultButton;

        // get corresonpinding recipe for each row
        Recipe recipe = (Recipe) getItem(position);

        // update the row view's textviews and imageview to display the information

        // titleTextView
        titleTextView.setText(recipe.title);
        titleTextView.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        titleTextView.setTextSize(18);

        // servingTextView
        servingTextView.setText(recipe.servings + " servings");
        servingTextView.setTextSize(14);
        servingTextView.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));

        prepTimeTextView.setText(recipe.prepTime);
        servingTextView.setTextSize(14);
        servingTextView.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));

        final String someLongText = "The instruction for "+recipe.title+" can be found here!";
/*
        final Notification.Builder builder = new Notification.Builder(mContext);
        builder.setStyle(new Notification.BigTextStyle(builder).bigText(someLongText).setBigContentTitle("Cooking Instruction")
                                                .setSummaryText("The Instruction is here!"))
                .setContentTitle("Cooking Instruction")
                .setContentText("Summary")
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setAutoCancel(true);

        final NotificationManager nm = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
*/

        resultButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addNotification(someLongText);
            }
        });


        // imageView
        // use Picasso library to load image from the image url
        Picasso.with(mContext).load(recipe.image).into(thumbnailImageView);

        return convertView;
    }
    private void addNotification(String someLongText) {
        Notification.Builder builder =
                new Notification.Builder(mContext);
        builder.setStyle(new Notification.BigTextStyle(builder).bigText(someLongText).setBigContentTitle("Cooking Instruction")
                                .setSummaryText("The Instruction is here!"))
                        .setContentTitle("Cooking Instruction")
                        .setContentText("Summary")
                        .setSmallIcon(android.R.drawable.sym_def_app_icon)
                        .setAutoCancel(true);

        Intent notificationIntent = new Intent(mContext, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    // viewHolder
    // is used to customize what you want to put into the view
    // it depends on the layout design of your row
    // this will be a private static class you have to define
    private static class ViewHolder{
        public TextView titleTextView;
        public TextView servingTextView;
        public ImageView thumbnailImageView;
        public TextView prepTimeTextView;
        public Button resultButton;
    }


    // intent is used to pass information between activities
    // intent -> pacakge
    // sender, receiver

}
