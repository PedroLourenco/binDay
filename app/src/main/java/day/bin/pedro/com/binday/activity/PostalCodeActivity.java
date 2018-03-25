package day.bin.pedro.com.binday.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;

import day.bin.pedro.com.binday.R;
import day.bin.pedro.com.binday.Util.DividerItemDecoration;
import day.bin.pedro.com.binday.Util.RecyclerItemClickListener;
import day.bin.pedro.com.binday.adapter.PropertyInformationAdapter;
import day.bin.pedro.com.binday.model.PropertyInformation;
import day.bin.pedro.com.binday.rest.ApiClient;
import day.bin.pedro.com.binday.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostalCodeActivity extends AppCompatActivity {

    private static final String TAG = "PostalCodeActivity";
    public static final String EXTRA_UPRN = "EXTRA_UPRN";
    public static final String EXTRA_ADDRESS = "EXTRA_ADDRESS";
    public static final String EXTRA_POSTCODE = "EXTRA_POSTCODE";
    private List<PropertyInformation> result;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postal_code);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // Add admob to activity
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        final LinearLayout noResults = (LinearLayout) findViewById(R.id.layout_no_results);
        final TextView textNoResults = (TextView) findViewById(R.id.text_no_results);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.properties_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), R.drawable.line_divider, false, true));

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        // Add click event listener to the recyclerView
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getBaseContext(), WasteCollectionActivity.class);
                        intent.putExtra(EXTRA_UPRN, result.get(position).getUprn());
                        intent.putExtra(EXTRA_ADDRESS, result.get(position).getShortAddress());
                        intent.putExtra(EXTRA_POSTCODE, result.get(position).getPostcode());
                        startActivity(intent);
                    }
                })
        );

        ImageView searchPostCode = (ImageView) findViewById(R.id.buttonSearch);

        if (searchPostCode != null) {
            searchPostCode.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    // Check if no view has focus:
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                    // Perform action on click
                    EditText postCode = (EditText) findViewById(R.id.postCodeSearch);

                    // Show the address list taht match the post code or message
                    if (postCode.getText().toString().isEmpty()) {
                        // PostCode empty
                        textNoResults.setText(R.string.insertPostCode);

                        noResults.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        // Track postcode search
                        Bundle params = new Bundle();
                        params.putString("postcode", postCode.getText().toString());
                        mFirebaseAnalytics.logEvent("searchPostcode", params);

                        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                        Call<List<PropertyInformation>> call = apiService.getPropertiesForPostCode(postCode.getText().toString());

                        call.enqueue(new Callback<List<PropertyInformation>>() {
                            @Override
                            public void onResponse(Call<List<PropertyInformation>> call, Response<List<PropertyInformation>> response) {
                                result = response.body();

                                if (result.size() > 0) {
                                    noResults.setVisibility(View.GONE);
                                    recyclerView.setVisibility(View.VISIBLE);

                                    PropertyInformationAdapter propertyInformationAdapter = new PropertyInformationAdapter(result);

                                    recyclerView.setAdapter(propertyInformationAdapter);
                                } else {
                                    textNoResults.setText(R.string.verifyPostCode);

                                    noResults.setVisibility(View.VISIBLE);
                                    recyclerView.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onFailure(Call<List<PropertyInformation>> call, Throwable t) {
                                // Log error here since request failed
                                Log.e(TAG, t.toString());
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.help:
                // Open the help activity
                Intent intent = new Intent(getBaseContext(), HelpActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
