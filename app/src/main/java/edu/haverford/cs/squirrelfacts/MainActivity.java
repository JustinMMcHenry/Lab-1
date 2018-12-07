package edu.haverford.cs.squirrelfacts;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.os.Bundle;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class GetNewSquirrelsTask extends AsyncTask<String, Void, SquirrelList> {


    private MainActivity mRef;
    public GetNewSquirrelsTask(MainActivity ac)
    {
        super();
        mRef = ac;
    }

    /**
     * Takes string and returns JSON interpretable string.
     * @param mUrl
     * @return Returns string with JSON.
     */
    private String getData(String mUrl)
    {
        String out = null;

        try
        {
            URL url = new URL(mUrl);
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
            connect.setRequestMethod("GET");

            InputStream in = new BufferedInputStream(connect.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            try{

              String next = null;

              while((next = reader.readLine())!=null){
                  if(out==null)out=next+"\n";
                  else out+=next+"\n";
              }

            }catch(Exception e){e.printStackTrace();}
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return out;
    }


    /**
     * Downloads a and parses list of of squirrels
     * @param strings
     * @return Returns a squirrel list formed from the result
     */
    @Override
    protected SquirrelList doInBackground(String... strings) {

        SquirrelList out = new SquirrelList();

        String toParse = getData(strings[0]);
        try {

            JSONArray stuff = new JSONArray(toParse);
            for(int i=0; i<stuff.length();i++)
            {
                JSONObject sq = stuff.getJSONObject(i);

                Squirrel toAdd = new Squirrel(sq.getString("name"),sq.getString("location"),sq.getString("picture"));
                out.addToFront(toAdd);
            }

        }catch(Exception e){e.printStackTrace();}
        return out;
    }

    @Override
    protected void onPostExecute(SquirrelList squirrels) {
        super.onPostExecute(squirrels);

        mRef.aSyncDone(squirrels);
    }
}


public class MainActivity extends AppCompatActivity {


    private GetNewSquirrelsTask mTask;
    private SquirrelList mList;
    public final static int ADD_CODE = 88;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.squirrel_list);


        Squirrel s = new Squirrel("Black Squirrel",
                "Haverford, PA",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0c/Black_Squirrel.jpg/220px-Black_Squirrel.jpg");
        mList = (new SquirrelList());

        try {
            mTask = new GetNewSquirrelsTask(this);//.execute("https://raw.githubusercontent.com/kmicinski/squirreldata/master/squirrels.json");
            mTask.execute("https://raw.githubusercontent.com/kmicinski/squirreldata/master/squirrels.json");

        }catch(Exception e){e.printStackTrace();}

        SquirrelListAdapter adapter = new SquirrelListAdapter(this, mList);
        listView.setAdapter(adapter);
    }


    /**
     * Creates squirrels!
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==ADD_CODE && resultCode==RESULT_OK) {
            String name = data.getStringExtra("name");
            String loc = data.getStringExtra("location");
            String pic = data.getStringExtra("picture");
            mList.addToFront(new Squirrel(name,loc,pic));
        }
    }

    public void aSyncDone(SquirrelList toAdd)
    {
        mList.addAll(toAdd);
    }
}
