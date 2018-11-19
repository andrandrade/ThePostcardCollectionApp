package br.edu.ifspsaocarlos.sdm.postcardcollection.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import br.edu.ifspsaocarlos.sdm.postcardcollection.R;
import br.edu.ifspsaocarlos.sdm.postcardcollection.view.PostcardFragment.OnListFragmentInteractionListener;
import br.edu.ifspsaocarlos.sdm.postcardcollection.view.dummy.DummyContent.DummyItem;

import java.net.URI;
import java.net.URL;
import java.util.List;

/* {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type. */

public class MyPostcardRecyclerViewAdapter extends RecyclerView.Adapter<MyPostcardRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final Context mContext;

    public MyPostcardRecyclerViewAdapter(Context context, List<DummyItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        mContext = context; // vamos tentar assim..
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.postcard_card_view, parent, false);

        // View view = LayoutInflater.from(parent.getContext())
        //             .inflate(R.layout.fragment_postcard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Para cada item da lista...
        holder.mItem = mValues.get(position);

        //TODO: setar a url real de cada imagem
//        holder.mImageView.setImageURI(mValues.get(position).photourl));
//        holder.mImageView.setImageResource(R.mipmap.postcard_1);

//        Glide.with(myFragment)
//                .load(url)
//                .centerCrop()
//                .placeholder(R.drawable.loading_spinner)
//                .crossFade()
//                .into(myImageView);

        Glide.with(mContext)
                .load(mValues.get(position).photourl)
                .into(holder.mImageView);
                //.placeholder(R.drawable.ic_crop_original_black_24dp)
                // Por que não consigo usar?


        holder.mTitleView.setText(mValues.get(position).content);
        holder.mContentView.setText(mValues.get(position).details);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;                // CardView
        public final ImageView mImageView;      // Foto do postal
        public final TextView  mTitleView;       // Título
        public final TextView  mContentView;     // País
        public DummyItem       mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.postcard_photo);
            mTitleView = (TextView) view.findViewById(R.id.postcard_title);
            mContentView = (TextView) view.findViewById(R.id.postcard_country);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
