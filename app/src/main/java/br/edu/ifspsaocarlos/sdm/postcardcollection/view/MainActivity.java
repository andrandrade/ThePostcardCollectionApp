package br.edu.ifspsaocarlos.sdm.postcardcollection.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import br.edu.ifspsaocarlos.sdm.postcardcollection.R;
import br.edu.ifspsaocarlos.sdm.postcardcollection.utils.SessionUtils;
import br.edu.ifspsaocarlos.sdm.postcardcollection.view.login.LoginActivity;
import br.edu.ifspsaocarlos.sdm.postcardcollection.view.dummy.DummyContent;

import static br.edu.ifspsaocarlos.sdm.postcardcollection.utils.SessionUtils.USER_ID;

public class MainActivity extends AppCompatActivity implements PostcardFragment.OnListFragmentInteractionListener {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FrameLayout mMainFrame;
    Fragment collectionFragment = new PostcardFragment();
    //Fragment addItemFragment = new AddPostcardFragment();
    Fragment dashboardFragment = new DashboardFragment();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    setFragment(dashboardFragment);
                    return true;
                case R.id.navigation_add_item:
                    //setFragment(addItemFragment);
                    openPostcardActivity();
                    return true;
                case R.id.navigation_collection:
                    setFragment(collectionFragment);
                    return true;
            }
            return false;
        }
    };

    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(mMainFrame.getId(), fragment);
        transaction.commit();

    }

    private void openPostcardActivity() {
        Intent intent = new Intent(MainActivity.this, EditPostcardActivity.class);
        intent.putExtra(USER_ID, currentUser.getUid());
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.main_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        this.mAuth = FirebaseAuth.getInstance();
        mMainFrame = findViewById(R.id.main_content);
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.currentUser = mAuth.getCurrentUser();

        // Se o usuário ainda não está logado, redireciona para o Login
        if (currentUser == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            //finish();
        } else {
            // se já está logado, continua nesta tela
            //mTextMessage.setText(getString(R.string.title_home) + " User: " + currentUser.getEmail());
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            setFragment(dashboardFragment);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // TODO: oferecer SignOut ao usuário em algum lugar
        this.mAuth.signOut();
        super.onDestroy();
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
