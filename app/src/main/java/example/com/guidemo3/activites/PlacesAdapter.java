package example.com.guidemo3.activites;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guidemo3.R;

import java.util.List;

/**
 * Created by Prateek on 3/19/2018.
 */

public class PlacesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER=0;
    private static final int TYPE_ITEM=1;
    private List<PlacesData> placesDataList;

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;

        }
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder
    {
        public View view;
        private final TextView textView;

        public HeaderViewHolder(View itemView)
        {
            super(itemView);
            view = itemView;

            textView = (TextView)itemView.findViewById(R.id.header);
        }

    }


    public class ItemViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title,address,status;


        public ItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.place_title);
            address=(TextView)itemView.findViewById(R.id.place_address);
            status = (TextView)itemView.findViewById(R.id.place_status);
        }
    }

    public PlacesAdapter (List<PlacesData> placesDataList)
    {
        this.placesDataList=placesDataList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == TYPE_HEADER)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_item,parent,false);
            return new HeaderViewHolder(view);
        }

        else if(viewType == TYPE_ITEM)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.places_row_list,parent,false);
            return new ItemViewHolder(view);
        }
        else
            return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HeaderViewHolder) {

            //set the Value from List to corresponding UI component as shown below.
            //((HeaderViewHolder) holder).textView.setText("Header Location");

            //similarly bind other UI components or perform operations
        }
        else if (holder instanceof ItemViewHolder) {

            PlacesData placesData = placesDataList.get(position-1);
            ((ItemViewHolder)holder).title.setText(placesData.getTitle());
            ((ItemViewHolder)holder).address.setText(placesData.getAddress());
            ((ItemViewHolder)holder).status.setText(placesData.getStatus());
        }
    }

    @Override
    public int getItemCount() {
        return placesDataList.size()+1;
    }
}
