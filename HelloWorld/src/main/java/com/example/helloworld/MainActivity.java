package com.example.helloworld;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
    public final static String EXTRA_MESSAGE = "com.example.helloworld.MESSAGE";


    private static final String METHOD_NAME = "Connecter";

    private static final String NAMESPACE = "http://tempuri.org/";

    private static final String URL = "http://localhost:5645/ServiceAuthentification.svc";

    final String SOAP_ACTION = "http://tempuri.org/IServiceAuthentification/Connecter";

    TextView tv;
    StringBuilder sb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        tv = new TextView(this);
        sb = new StringBuilder();
        call();
        tv.setText(sb.toString());
        setContentView(tv);




        /*setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
    }

    public void call() {
        try {

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            request.addProperty("Name", "Qing");

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);


            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapPrimitive result = (SoapPrimitive)envelope.getResponse();

            //to get the data
            String resultData = result.toString();
            // 0 is the first object of data


            sb.append(resultData + "\n");
        } catch (Exception e) {
            sb.append("Error:\n" + e.getMessage() + "\n");
        }

    }
}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_search:
                //openSearch();
                return true;
            case R.id.action_settings:
                //openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

    /**
     * Called when the user clicks the Send button
     */
    public void sendMessage(View view){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText)findViewById(R.id.edit_message);
        intent.putExtra(EXTRA_MESSAGE, editText.getText().toString());
        startActivity(intent);
    }
}
