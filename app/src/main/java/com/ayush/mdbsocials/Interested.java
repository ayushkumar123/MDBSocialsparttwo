package com.ayush.mdbsocials;

import android.widget.Button;

/**
 * Created by user on 22-Feb-18.
 */

public class Interested {
    public int counter = 1;
    private boolean countcheck = false;

    public boolean checker() {
        if (countcheck == false) {
            countcheck = true;

            return countcheck;
        } else
            countcheck = false;
            return countcheck;
        }

    public void count() {
            if (countcheck == true) {
                counter = counter + 1;
            } else {
                counter = counter;
            }
        }


}
