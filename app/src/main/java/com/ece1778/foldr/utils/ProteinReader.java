package com.ece1778.foldr.utils;

import com.ece1778.foldr.data.FastaFragment;
import com.ece1778.foldr.data.FastaType;
import com.ece1778.foldr.data.Protein;

import java.io.FileReader;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by Ding on 3/29/2015.
 */
public class ProteinReader {
    public static Protein readProteinFile(InputStream stream){
        Scanner scanner = new Scanner(stream);
        String code = scanner.nextLine();
        String image = scanner.nextLine();

        Protein protein = new Protein(code, image);
        String fasta = scanner.nextLine();
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            String[] parts = line.split(":");

            FastaType type = parts[0].equals("A") ? FastaType.AlphaHelix : FastaType.BetaSheet;
            int start = Integer.parseInt(parts[1]);
            int end = Integer.parseInt(parts[2]);

            FastaFragment fragment = new FastaFragment(fasta.subSequence(start -1, end -1), type);
            protein.addFragment(fragment);
        }

        return protein;
    }
}
