package com.ece1778.foldr.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.ece1778.foldr.FoldrApplication;
import com.ece1778.foldr.data.Protein;
import com.ece1778.foldr.game.ProteinManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by Ding on 3/29/2015.
 */
public class ProteinArrayAdapter extends BaseAdapter {

    private final ArrayList<Protein> proteins;
    private final AssetManager assetManager;
    private final String LevelFolder = "levels";
    private final Context context;

    public ProteinArrayAdapter(Context context){
        this.context = context;
        this.proteins = new ArrayList<>();
        this.assetManager = FoldrApplication.getContext().getAssets();
    }

    public void addProtein(Protein protein){
        this.proteins.add(protein);
        this.notifyDataSetChanged();
    }

    public void addProteins(Collection<Protein> proteinCollection){
        proteins.addAll(proteinCollection);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.proteins.size();
    }

    @Override
    public Object getItem(int position) {
        return this.proteins.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Protein protein = this.proteins.get(position);
        ImageView imageView = new ImageView(this.context);
        imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(10,10,10,10);
        try {
            InputStream stream = this.assetManager.open(LevelFolder + "/" + protein.getImage());
            Drawable drawable = Drawable.createFromStream(stream, null);
            imageView.setImageDrawable(drawable);
        } catch (IOException e) {
            Log.e(ProteinArrayAdapter.class.getSimpleName(),e.getMessage());
        }

        return imageView;
    }
}
