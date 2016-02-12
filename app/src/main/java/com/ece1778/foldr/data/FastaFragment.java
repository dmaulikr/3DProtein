package com.ece1778.foldr.data;

/**
 * Created by Ding on 3/18/2015.
 */
public class FastaFragment {
    private final CharSequence sequence;
    private final FastaType type;

    public FastaFragment(CharSequence sequence, FastaType type){
        this.sequence = sequence;
        this.type = type;
    }

    public CharSequence getSequence(){
        return this.sequence;
    }

    public FastaType getType(){
        return this.type;
    }
}
