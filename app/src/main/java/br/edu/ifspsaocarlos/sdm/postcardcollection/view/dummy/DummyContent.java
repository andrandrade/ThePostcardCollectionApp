package br.edu.ifspsaocarlos.sdm.postcardcollection.view.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * TODO: Replace all uses of this class before publishing your app. */
public class DummyContent {

    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static final String[] listaURLs = {
       "http://www.juntosabordo.com.br/wp-content/uploads/2012/08/2013/03/jardim.jpg",
       "http://toperambulando.com.br/Aventura/Pisa_Roma/Album/Roma110.jpg",
       "https://http2.mlstatic.com/carto-postal-espanha-madrid-no-57-D_NQ_NP_13726-MLB3303408955_102012-F.jpg",
       "http://static1.pureviagem.com.br/articles/3/40/33/@/17556-o-palacio-real-e-um-dos-principais-660x0-1.jpg",
       "https://catracalivre.com.br/wp-content/uploads/2013/09/berlim_alemanha.jpg",
       "http://1.bp.blogspot.com/_gte86pv7-hA/TNihfYiNGfI/AAAAAAAADR8/8RP27KPj1vs/s1600/Cart%C3%B5es_Postais_do_Rio_de_Janeiro_cristo.jpg",
       "https://http2.mlstatic.com/carto-postal-rio-de-janeiro-praia-de-copacabana-D_NQ_NP_18412-MLB20154499690_082014-F.jpg",
       "http://2.bp.blogspot.com/-aRqkIP4xQVk/VME9r0VkcLI/AAAAAAAACsg/FCnLkLDIjFM/s1600/rio%2Bde%2Bjaneiro.jpeg",
       "http://www.cacadoresdelendas.com.br/japao/wp-content/uploads/2016/08/Chureito-Chery-and-mt_fuji2.jpg",
       "http://i0.statig.com.br/fw/97/vt/xt/97vtxtc3x38x2bpiv7jq3fy0k.jpg",
       "http://1.bp.blogspot.com/_UHwihSuO1Wc/TDMV69jwyMI/AAAAAAAAB8I/l6MY2eoJo7A/s1600/japao2.jpg",
       "http://www.blueskyelearn.com/wp-content/uploads/2017/09/postcard-300x200.jpg"
    };

    private static final String[] listaPaises = {
            "Brazil", "Italy", "Spain", "Spain",
            "Germany", "Brazil", "Brazil", "Brazil",
            "Japan", "Japan", "Japan", "None"
    };

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItem createDummyItem(int position) {
        return new DummyItem(String.valueOf(position),
                            "Postcard " + position,
                             makeDetails(position),
                             makePhotoURL(position));
    }

    private static String makeDetails(int position) {
//        StringBuilder builder = new StringBuilder();
//        builder.append("Details about Item: ").append(position);
//        for (int i = 0; i < position; i++) {
//            builder.append("\nMore details information here.");
//        }
//        return builder.toString();
        return listaPaises[position % 12];
    }

    private static String makePhotoURL(int position) {
        return listaURLs[position % 12];
    }

    // A dummy item representing a piece of content.
    public static class DummyItem {
        public final String id;
        public final String content;
        public final String details;
        public final String photourl;

        private DummyItem(String id, String content, String details, String photourl) {
            this.id = id;
            this.content = content;
            this.details = details;
            this.photourl = photourl;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
