package com.tuto;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class MainActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        handleIntent(getIntent());
    }



    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        StringBuilder stringBuilder = new StringBuilder();
        PlaceholderFragment fragment =
            (PlaceholderFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        TextView text = (TextView) fragment.getView().findViewById(R.id.text);

        if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)){
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String[] types = tag.getTechList();
            stringBuilder.append("Types : ").append("\n");
            for(String type : types){
                stringBuilder.append(" - ").append(type).append("\n");
            }


//            NfcA nfcATag = NfcA.get(tag);
            IsoDep isoDepTag = IsoDep.get(tag);
            if(isoDepTag != null){
                try {
                    isoDepTag.connect();
//                    byte[] response = isoDepTag.transceive(SELECT_APDU);

                    isoDepTag.close();
                } catch (IOException e) {
                    stringBuilder.delete(0, stringBuilder.length()-1);
                    stringBuilder.append("ERROR : ").append(e.getStackTrace());
                }

            }


//                byte[] data = isoDepTag.getHistoricalBytes();
//                InputStreamReader inputStreamReader = new InputStreamReader(new ByteArrayInputStream(
//                    data));
//                BufferedReader reader = new BufferedReader(inputStreamReader);
//                String line = null;
//                try {
//                    while(( line = reader.readLine()) != null ){
//                        stringBuilder.append(line);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

        }
        text.setText(stringBuilder.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
}
