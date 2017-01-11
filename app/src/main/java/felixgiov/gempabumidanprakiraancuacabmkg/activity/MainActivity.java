package felixgiov.gempabumidanprakiraancuacabmkg.activity;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;

import java.util.List;

import felixgiov.gempabumidanprakiraancuacabmkg.CustomBottomSheetDialog;
import felixgiov.gempabumidanprakiraancuacabmkg.ItemClickSupport;
import felixgiov.gempabumidanprakiraancuacabmkg.R;
import felixgiov.gempabumidanprakiraancuacabmkg.adapter.GempaAdapter;
import felixgiov.gempabumidanprakiraancuacabmkg.model.Datum;
import felixgiov.gempabumidanprakiraancuacabmkg.model.Example;
import felixgiov.gempabumidanprakiraancuacabmkg.rest.ApiClient;
import felixgiov.gempabumidanprakiraancuacabmkg.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final static String API_KEY = "baf1270d5a9c8f3dcf4de5c11f4ca392";
    private ProgressBar mProgressBar;
    private AdView mAdView;
    List<Datum> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Info Gempa Bumi BMKG");

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        final Handler h = new Handler();
        final int delay = 8000; //milliseconds

        h.postDelayed(new Runnable(){
            public void run(){
                if (isNetworkAvailable(getApplicationContext())) {

                } else {
                    Toast.makeText(getApplicationContext(), "Koneksi gagal. Mohon periksa koneksi internet anda, kemudian tekan refresh.", Toast.LENGTH_LONG).show();
                }
                h.postDelayed(this, delay);
            }
        }, delay);

        refreshRecyclerView();

        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this, "ca-app-pub-2029021861713537/7143027207");

        // Gets the ad view defined in layout/ad_fragment.xml with ad unit ID set in
        // values/strings.xml.
        mAdView = (AdView) findViewById(R.id.ad_view);

        // Create an ad request. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        // Start loading the ad in the background.
        mAdView.loadAd(adRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_refresh:

                refreshRecyclerView();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public void refreshRecyclerView(){

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Error obtaining data.", Toast.LENGTH_LONG).show();
            return;
        }

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.gempa_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<Example> call = apiService.getGempaTerkini("gempa-terkini",API_KEY);
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                data = response.body().getData();
                Toast.makeText(getApplicationContext(), "Refreshed.", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Number of responses received: " + data.size());
                recyclerView.setAdapter(new GempaAdapter(data, R.layout.list_item_gempa, getApplicationContext()));
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

        ItemClickSupport.addTo(recyclerView)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                        newInstance(data.get(position).getImg());
//                        CustomBottomSheetDialog bottomSheetDialog = CustomBottomSheetDialog.getInstance();
//                        bottomSheetDialog.show(getSupportFragmentManager(), "Custom Bottom Sheet");
                    //    Toast.makeText(MainActivity.this, ""+data.get(position).getWilayah(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void newInstance(String img) {
        CustomBottomSheetDialog bottomSheetDialog = CustomBottomSheetDialog.getInstance();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("img", img);
        bottomSheetDialog.setArguments(args);

        bottomSheetDialog.show(getSupportFragmentManager(), "Custom Bottom Sheet");
    }

    /** Called when leaving the activity */
    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

}
