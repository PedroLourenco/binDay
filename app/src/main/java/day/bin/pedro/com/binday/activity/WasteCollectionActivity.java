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
        String posstCode = "";
        Bundle extras = getIntent().getExtras();

        // Add admob to activity
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        if (extras != null) {
            uprn = extras.getString(PostalCodeActivity.EXTRA_UPRN);
            address = extras.getString(PostalCodeActivity.EXTRA_ADDRESS);
            posstCode = extras.getString(PostalCodeActivity.EXTRA_POSTCODE);
        }

        TextView address_value = (TextView) findViewById(R.id.waste_address_value);
        address_value.setText(address + ", " + posstCode);

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
