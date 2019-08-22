package org.bonestudio.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements BlankFragment.OnFragmentInteractionListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        long dateTime = System.currentTimeMillis();
        Bundle bundle = new Bundle();
        bundle.putLong("dateTime", dateTime);

        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.findFragmentByTag("fragment") == null)
        {
            BlankFragment blankFragment = BlankFragment.newInstance();
            fragmentManager.beginTransaction()
                    .add(R.id.container, blankFragment, "fragment")
                    .commit();
            blankFragment.setArguments(bundle);
        }
    }

    @Override
    public void onFragmentInteraction()
    {
        finish();
    }
}
