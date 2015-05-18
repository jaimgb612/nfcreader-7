package com.tuto;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.logging.Logger;


public class MainActivity extends ActionBarActivity {

//    private NfcAdapter nfcAdapter;
//    private static final String TAG = MainActivity.class.getSimpleName();
//    private NfcAdapter mNfcAdapter;
//
//    private PendingIntent mNfcPendingIntent;
//    private IntentFilter[] mNdefExchangeFilters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
//        intiliazeNFCHandling();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent, mNdefExchangeFilters, null);
        handleIntent(getIntent());
    }

//
//    // Initialize NFC to Handle data exchange.
//    private void intiliazeNFCHandling(){
//        Log.i(TAG, "Initializing NFC");
//
//        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
//
//        // Handle all of our received NFC intents in this activity.
//        mNfcPendingIntent = PendingIntent.getActivity(this, 0,
//                                                      new Intent(this, getClass()).addFlags(
//                                                          Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
//
//        // Intent filters for reading a note from a tag or exchanging over p2p.
//        final IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
//        try {
//            ndefDetected.addDataType("text/plain");
//        } catch (IntentFilter.MalformedMimeTypeException e) { }
//        mNdefExchangeFilters = new IntentFilter[] { ndefDetected };
//
//    }


    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        StringBuilder stringBuilder = new StringBuilder();
        if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)){
            //Parcelable[] rawMsgs = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

            String[] types = tag.getTechList();

            NfcA nfcATag = NfcA.get(tag);
            IsoDep isoDepTag = IsoDep.get(tag);



                byte[] data = isoDepTag.getHistoricalBytes();
                InputStreamReader inputStreamReader = new InputStreamReader(new ByteArrayInputStream(
                    data));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = null;
                try {
                    while(( line = reader.readLine()) != null ){
                        stringBuilder.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }
        Log.i(MainActivity.class.getSimpleName(),stringBuilder.toString());

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
