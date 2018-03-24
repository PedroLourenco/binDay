package day.bin.pedro.com.binday.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import day.bin.pedro.com.binday.R;
import day.bin.pedro.com.binday.Util.ConvertWeekDays;
import day.bin.pedro.com.binday.Util.DividerItemDecoration;
import day.bin.pedro.com.binday.adapter.PropertyInformationAdapter;
import day.bin.pedro.com.binday.adapter.WasteCollectionAdapter;
import day.bin.pedro.com.binday.model.PropertyInformation;
import day.bin.pedro.com.binday.model.WasteCollection;
import day.bin.pedro.com.binday.rest.ApiClient;
import day.bin.pedro.com.binday.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WasteCollectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waste_collection);

        String uprn = "";
        String address = "";
        String postCode = "";
        Bundle extras = getIntent().getExtras();

        // Add admob to activity
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        if (extras != null) {
            uprn = extras.getString(PostalCodeActivity.EXTRA_UPRN);
            address = extras.getString(PostalCodeActivity.EXTRA_ADDRESS);
            postCode = extras.getString(PostalCodeActivity.EXTRA_POSTCODE);
        }

        TextView addressValue = (TextView) findViewById(R.id.waste_address_value);
        addressValue.setText(address + ", " + postCode);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.wasteCollectionView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        if (recyclerView != null) {
            recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), R.drawable.line_divider, false, true));
        }

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<WasteCollection>> call = apiService.getWasteCollectionDatabyUprn(uprn);

        call.enqueue(new Callback<List<WasteCollection>>() {
            @Override
            public void onResponse(Call<List<WasteCollection>> call, Response<List<WasteCollection>> response) {
                List<WasteCollection> result = response.body();

                if (result.size() > 0) {
                    Long nextCollectionDate = Long.parseLong(result.get(0).getNextCollection().replaceAll("\\D", ""));
                    Long aux;

                    // Get next collection date
                    for (int i = 1; i < result.size(); i++) {
                        aux =  Long.parseLong(result.get(i).getNextCollection().replaceAll("\\D", ""));

                        if (nextCollectionDate > aux) {
                            nextCollectionDate = aux;
                        }
                    }

                    Date date = new Date(nextCollectionDate);
                    DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

                    TextView nextCollection = (TextView) findViewById(R.id.nextCollectionDate);
                    nextCollection.setText(formatter.format(new Date(nextCollectionDate)));

                    //TODO only show data if CollectionAvailable = true
                    TextView weekDay = (TextView) findViewById(R.id.wasteWeekDay);
                    weekDay.setText(ConvertWeekDays.getWeekDay(result.get(0).getCollectionday()));
                    WasteCollectionAdapter wasteCollectionAdapter = new WasteCollectionAdapter(result);
                    recyclerView.setAdapter(wasteCollectionAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<WasteCollection>> call, Throwable t) {

            }
        });
    }
}
