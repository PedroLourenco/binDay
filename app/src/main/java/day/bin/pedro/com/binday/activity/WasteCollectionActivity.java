package day.bin.pedro.com.binday.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import day.bin.pedro.com.binday.R;
import day.bin.pedro.com.binday.Util.DateUtils;
import day.bin.pedro.com.binday.Util.DividerItemDecoration;
import day.bin.pedro.com.binday.adapter.WasteCollectionAdapter;
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
                    Long nextCollectionDateTemp;
                    String nexCollectionType = "";

                    // Get next collection date
                    for (int i = 1; i < result.size(); i++) {
                        nextCollectionDateTemp =  Long.parseLong(result.get(i).getNextCollection().replaceAll("\\D", ""));

                        if (nextCollectionDate > nextCollectionDateTemp) {
                            nextCollectionDate = nextCollectionDateTemp;
                            nexCollectionType = result.get(i).getWasteType();
                        }
                    }

                    Date dates = new Date(Long.parseLong(nextCollectionDate.toString()));

                    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                    calendar.setTime(dates);

                    DateFormat formatter = new SimpleDateFormat("EEEE d MMM yyyy");

                    TextView nextCollectionDateLabel = (TextView) findViewById(R.id.nextCollectionDate);
                    nextCollectionDateLabel.setText(formatter.format(DateUtils.convertTimeToLocal("Europe/London", calendar).getTime()));

                    TextView nextCollectionTypeLabel = (TextView) findViewById(R.id.nextCollectionType);
                    nextCollectionTypeLabel.setText(nexCollectionType.toString());

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
