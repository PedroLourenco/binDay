package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import day.bin.pedro.com.binday.R;
import model.PropertyInformation;

/**
 * Created by pedro on 28/07/16.
 */
public class PropertyInformationAdapter extends RecyclerView.Adapter<PropertyInformationAdapter.ViewHolder> {

    private List<PropertyInformation> properties;
    private int rowLayout;
    private Context context;

    public PropertyInformationAdapter(List<PropertyInformation> properties) {
        this.properties = properties;
        //this.rowLayout = rowLayout;
        //this.context = context;
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
