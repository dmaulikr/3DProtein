package com.ece1778.foldr.utils;

import com.ece1778.foldr.data.AminoAcidProperty;

/**
 * Created by Ding on 3/18/2015.
 */
public class AminoAcidPropertyMapper {
    public static final String Hydrophilic = "STCYNQ";
    public static final String Hydrophobic = "GAVLIMFWP";
    public static final String Acidic = "DE";
    public static final String Basic = "KRH";

    public AminoAcidProperty getProperty(char aminoAcid){
        CharSequence sequence = String.valueOf(aminoAcid).toUpperCase();

        if(Hydrophilic.contains(sequence)){
            return AminoAcidProperty.Hydrophilic;
        }else if(Hydrophobic.contains(sequence)){
            return AminoAcidProperty.Hydrophobic;
        }else if(Acidic.contains(sequence)){
            return AminoAcidProperty.Acidic;
        }else if(Basic.contains(sequence)){
            return AminoAcidProperty.Basic;
        }else{
            return AminoAcidProperty.None;
        }
    }
}
