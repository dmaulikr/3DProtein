package com.ece1778.foldr.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ding on 3/29/2015.
 */
public class Protein {
    private String code;
    private String image;
    private final List<FastaFragment> sequences;

    public Protein(String code, String image){
        this.code = code;
        this.image = image;
        this.sequences = new ArrayList<>();
    }

    public String getCode(){
        return this.code;
    }

    public String getImage(){
        return this.image;
    }

    public void addFragment(FastaFragment fragment){
        this.sequences.add(fragment);
    }

    public List<FastaFragment> getFragments(){
        return this.sequences;
    }
}
