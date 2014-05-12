package com.example.example;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import com.example.example.fragment.TestFragment;
import com.example.example.fsm.TransitManagerExample;

public class MyActivity extends FragmentActivity implements com.fsm.transit.bridge.FragmentActivity {
    /**
     * Called when the activity is first created.
     */
    private TransitManagerExample transitManagerExample;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        transitManagerExample = new TransitManagerExample(this);
        transitManagerExample.setCurrentContainer(R.id.list_container);
        transitManagerExample.switchFragment(TestFragment.class);

        View view = findViewById(R.id.btn_test);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transitManagerExample.getCurrentFragment();
            }
        });


    }
}
