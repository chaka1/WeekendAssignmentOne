package com.example.chaka.weekendassignmentone.frag;


import com.example.chaka.weekendassignmentone.obj.Employee;

/**
 * Created by Chaka on 25/05/2015.
 */
public interface SwapFragmentActivityInterface {

    void swapFragment(int fragment, boolean manager, Employee parceable);

    void back();
}
