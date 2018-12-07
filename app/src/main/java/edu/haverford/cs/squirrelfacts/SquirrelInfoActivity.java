package edu.haverford.cs.squirrelfacts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import java.net.URL;

public class SquirrelInfoActivity extends AppCompatActivity {
    private ImageView mSquirrelPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_squirrel_info);
        Intent i = getIntent();
        TextView name = (TextView) findViewById(R.id.squirrelName);
        TextView location = (TextView) findViewById(R.id.squirrelLocation);
        mSquirrelPic = (ImageView)findViewById(R.id.squirrelPic);
        name.setText(i.getStringExtra("name"));
        location.setText(i.getStringExtra("location"));
        new SquirrelImageLoader(mSquirrelPic).execute(i.getStringExtra("picture"));
        return;

    }

    class SquirrelImageLoader extends AsyncTask<String, Void, Bitmap> {
        Bitmap mBitmap;
        private ImageView mView;

        public SquirrelImageLoader(ImageView imageView){
            mView = imageView;
        }


        /**
         * Takes Image URL and returns a bitmap which can then be used to fill in the
         * image view.
         * @param strings
         * @return A bitmap to be used to fill squirrel info
         */
        @Override
        protected Bitmap doInBackground(String... strings) {

            try{

                URL url = new URL(strings[0]);
                mBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

            }catch (Exception e){

                e.printStackTrace();
                try{mBitmap = BitmapFactory.decodeStream(
                        (new URL("https://upload.wikimedia.org/wikipedia/commons/thumb/0/0c/Black_Squirrel.jpg/220px-Black_Squirrel.jpg")).openConnection().getInputStream());}
                catch(Exception e1){e1.printStackTrace();}
            }



            return mBitmap;
        }

        /**
         * Sets up loaded image.
         */
        @Override
        protected void onPostExecute(Bitmap bm)
        {
            if(bm!=null)mView.setImageBitmap(bm);
        }
    }
}
