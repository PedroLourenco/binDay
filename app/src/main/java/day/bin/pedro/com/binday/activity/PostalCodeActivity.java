package day.bin.pedro.com.binday.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import day.bin.pedro.com.binday.R;
import day.bin.pedro.com.binday.Util.DividerItemDecoration;
import day.bin.pedro.com.binday.adapter.PropertyInformationAdapter;
import day.bin.pedro.com.binday.model.PropertyInformation;
import day.bin.pedro.com.binday.rest.ApiClient;
import day.bin.pedro.com.binday.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostalCodeActivity extends AppCompatActivity {

    private static final String TAG = "PostalCodeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postal_code);

        final LinearLayout noResults = (LinearLayout) findViewById(R.id.layout_no_results);
        final TextView textNoResults = (TextView) findViewById(R.id.text_no_results);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.properties_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), R.drawable.line_divider, true, true));

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        ImageView searchPostCode = (ImageView) findViewById(R.id.buttonSearch);

        //String  postalCode =  "YO24 1NB";

        searchPostCode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                TextView postCode = (TextView) findViewById(R.id.postCodeSearch);

                if (postCode.getText().toString().isEmpty()) {
                    // PostCode empty
                    textNoResults.setText("Please insert your postal code!");

                    noResults.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                    Call<List<PropertyInformation>> call = apiService.getPropertiesForPostCode(postCode.getText().toString());

                    call.enqueue(new Callback<List<PropertyInformation>>() {
                        @Override
                        public void onResponse(Call<List<PropertyInformation>> call, Response<List<PropertyInformation>> response) {
                            List<PropertyInformation> result = response.body();

                            if (result.size() > 0) {
                                noResults.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);

                                PropertyInformationAdapter propertyInformationAdapter = new PropertyInformationAdapter(result);

                                recyclerView.setAdapter(propertyInformationAdapter);
                            } else {
                                textNoResults.setText("Please verify your postCode!");

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
