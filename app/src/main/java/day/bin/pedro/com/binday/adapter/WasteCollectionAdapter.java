package day.bin.pedro.com.binday.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import day.bin.pedro.com.binday.R;
import day.bin.pedro.com.binday.model.PropertyInformation;
import day.bin.pedro.com.binday.model.WasteCollection;

/**
 * Created by pedro on 30/07/16.
 */
public class WasteCollectionAdapter extends RecyclerView.Adapter<WasteCollectionAdapter.ViewHolder> implements RecyclerView.OnItemTouchListener {
    private List<WasteCollection> wasteCollection;

    public WasteCollectionAdapter(List<WasteCollection> wasteCollection) {
        this.wasteCollection = wasteCollection;
    }

    @Override
    public WasteCollectionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_waste_collection, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WasteCollectionAdapter.ViewHolder holder, int position) {
        holder.wasteTypeDescription.setText(wasteCollection.get(position).getWasteTypeDescription());

        if (wasteCollection.get(position).getCollectionAvailable().equals("N")) {
            holder.materialsCollectedLayout.setVisibility(View.GONE);
            holder.nextCollectionLayout.setVisibility(View.GONE);
            holder.wastTypeLayout.setVisibility(View.GONE);

            ViewGroup.LayoutParams params = holder.wasteLayout.getLayoutParams();
            // Changes the height and width to the specified *pixels*
            params.height = RecyclerView.LayoutParams.MATCH_PARENT;

            holder.wasteLayout.setLayoutParams(params);


            holder.collectionNotAvialable.setText("Collection Not Available!");
            holder.collectionNotAvialable.setVisibility(View.VISIBLE);
        } else {
            ViewGroup.LayoutParams params = holder.wasteLayout.getLayoutParams();
            // Changes the height and width to the specified *pixels*
            params.height = RecyclerView.LayoutParams.WRAP_CONTENT;

            holder.wasteLayout.setLayoutParams(params);

            holder.collectionNotAvialable.setVisibility(View.GONE);
            holder.materialsCollectedLayout.setVisibility(View.VISIBLE);
            holder.nextCollectionLayout.setVisibility(View.VISIBLE);
            holder.wastTypeLayout.setVisibility(View.VISIBLE);

            holder.wasteType.setText(wasteCollection.get(position).getWasteType());
            holder.mterialsCollected.setText(wasteCollection.get(position).getMaterialsCollected());

            if(!wasteCollection.get(position).getNextCollection().toString().isEmpty()) {
                String jsonDate = wasteCollection.get(position).getNextCollection();

                Long datetimestamp = Long.parseLong(jsonDate.replaceAll("\\D", ""));
                Date date = new Date(datetimestamp);
                DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                String dateFormatted = formatter.format(date);

                holder.nextCollection.setText(dateFormatted);
            }
        }

        if (wasteCollection.get(position).getWasteType().equals("GREEN")) {
            holder.binImage.setImageResource(R.drawable.bin_green);
        } else if (wasteCollection.get(position).getWasteType().equals("GREY BIN/SACK")) {
            holder.binImage.setImageResource(R.drawable.bin_gray);
        } else {
            holder.binImage.setImageResource(R.drawable.bin_blue);
        }
    }

    @Override
    public int getItemCount() {
        return wasteCollection.size();
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView wasteTypeDescription;
        TextView collectionday;
        TextView nextCollection;
        TextView wasteType;
        TextView mterialsCollected;
        ImageView binImage;
        TextView collectionNotAvialable;
        LinearLayout materialsCollectedLayout;
        LinearLayout wastTypeLayout;
        LinearLayout nextCollectionLayout;
        LinearLayout wasteLayout;

        public ViewHolder(View view) {
            super(view);

            wasteTypeDescription = (TextView) view.findViewById(R.id.wasteTypeDescription);
            collectionday = (TextView) view.findViewById(R.id.collectionday);
            nextCollection = (TextView) view.findViewById(R.id.nextCollection);
            wasteType = (TextView) view.findViewById(R.id.wasteType);
            mterialsCollected = (TextView) view.findViewById(R.id.materialsCollected);
            binImage = (ImageView) view.findViewById(R.id.binImage);
            collectionNotAvialable = (TextView) view.findViewById(R.id.collectionNotAvailable);
            materialsCollectedLayout = (LinearLayout) view.findViewById(R.id.materialsCollectedLayout);
            wastTypeLayout = (LinearLayout) view.findViewById(R.id.wastTypeLayout);
            nextCollectionLayout = (LinearLayout) view.findViewById(R.id.nextCollectionLayout);
            wasteLayout = (LinearLayout) view.findViewById(R.id.wasteLayout);
        }
    }
}
