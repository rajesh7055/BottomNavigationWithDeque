package com.android.bottomnavibackpress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.android.bottomnavibackpress.Fragment.DashboardFragment;
import com.android.bottomnavibackpress.Fragment.HomeFragment;
import com.android.bottomnavibackpress.Fragment.NotificationFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayDeque;
import java.util.Deque;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Deque<Integer> integerDeque = new ArrayDeque<>(3);
    boolean flag=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bootom_navigation);

        integerDeque.push(R.id.bn_home);

        loadFragment(new HomeFragment());

        bottomNavigationView.setSelectedItemId(R.id.bn_home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id=item.getItemId();
                if (integerDeque.contains(id)){
                    if (id==R.id.bn_home){
                        if (integerDeque.size()!=1){
                            if (flag){
                                integerDeque.addFirst(R.id.bn_home);
                                flag=false;
                            }
                        }
                    }
                    //Remove selected id from deque list
                    integerDeque.remove(id);
                }
                //Push selected id in deque list
                integerDeque.push(id);
                loadFragment(getFragment(item.getItemId()));
                return true;
            }
        });
    }

    private Fragment getFragment(int itemId) {
        switch (itemId){
            case R.id.bn_dashboard:
                bottomNavigationView.getMenu().getItem(0).setChecked(true);
                return new DashboardFragment();
            case R.id.bn_home:
                bottomNavigationView.getMenu().getItem(1).setChecked(true);
                return new HomeFragment();
            case R.id.bn_notofication:
                bottomNavigationView.getMenu().getItem(2).setChecked(true);
                return new NotificationFragment();
        }

        bottomNavigationView.getMenu().getItem(1).setChecked(true);
        return new HomeFragment();
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, fragment, fragment.getClass().getSimpleName())
                .commit();
    }

    @Override
    public void onBackPressed() {
        //pop to previous fragment
        integerDeque.pop();
        if (!integerDeque.isEmpty()){
            //When deque list is not empty, load fragment
            try {
                loadFragment(getFragment(integerDeque.peekFirst()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            //finish activity
            finish();
        }
    }
}