package day.bin.pedro.com.binday.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import day.bin.pedro.com.binday.R;
import day.bin.pedro.com.binday.model.PropertyInformation;

/**
 * Created by pedro on 28/07/16.
 */
public class PropertyInformationAdapter extends RecyclerView.Adapter<PropertyInformationAdapter.ViewHolder> implements RecyclerView.OnItemTouchListener {

    private List<PropertyInformation> properties;

    public PropertyInformationAdapter(List<PropertyInformation> properties) {
        this.properties = properties;
    }

    @Override
    public PropertyInformationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_property_information, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PropertyInformationAdapter.ViewHolder holder, int position) {
        holder.shortAddress.setText(properties.get(position).getShortAddress());
        holder.postcode.setText(properties.get(position).getPostcode());
    }

    @Override
    public int getItemCount() {
        return properties.size();
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
        TextView shortAddress;
        TextView postcode;

        public ViewHolder(View view) {
            super(view);

            shortAddress = (TextView) view.findViewById(R.id.shortAddress);
            postcode = (TextView) view.findViewById(R.id.postcode);
        }
    }
}
