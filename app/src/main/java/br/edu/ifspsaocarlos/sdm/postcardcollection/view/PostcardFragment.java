package br.edu.ifspsaocarlos.sdm.postcardcollection.view;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import br.edu.ifspsaocarlos.sdm.postcardcollection.R;
import br.edu.ifspsaocarlos.sdm.postcardcollection.view.dummy.DummyContent;
import br.edu.ifspsaocarlos.sdm.postcardcollection.view.dummy.DummyContent.DummyItem;

/** A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener} interface. */
public class PostcardFragment extends Fragment {

    // Parâmetros passados na criação do Fragment
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 3;

    private OnListFragmentInteractionListener mListener;

    /* Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes). */
    public PostcardFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PostcardFragment newInstance(int columnCount) {
        PostcardFragment fragment = new PostcardFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        // TODO: enviar id do usuário para pegar seus postais no bd
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflando o layout desse Fragment
        View view = inflater.inflate(R.layout.fragment_postcard_list, container, false);

        // Configurando o RecyclerView...
        if (view instanceof RecyclerView) {
            Context context = view.getContext();    // RecyclerView ou Fragment?
            RecyclerView recyclerView = (RecyclerView) view;

            // Configura o LayoutManager do RecyclerView:
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            // Configura o Adapter que vai cuidar dos dados para o RecyclerView:
            recyclerView.setAdapter(new MyPostcardRecyclerViewAdapter(context, DummyContent.ITEMS, mListener));
            // context: vamos tentar assim..
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            Toast.makeText(context, "Collection Fragment Attached", Toast.LENGTH_SHORT).show();
            //throw new RuntimeException(context.toString()
            //        + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /* This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that activity.
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information. */

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);


    }
}
