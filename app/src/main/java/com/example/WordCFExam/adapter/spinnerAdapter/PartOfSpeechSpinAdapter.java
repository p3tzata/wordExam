package com.example.WordCFExam.adapter.spinnerAdapter;

import android.content.Context;

import com.example.WordCFExam.entity.PartOfSpeech;

import java.util.List;

public class PartOfSpeechSpinAdapter extends BaseSpinAdapter<PartOfSpeech> {

    public PartOfSpeechSpinAdapter(Context context, int textViewResourceId, List<PartOfSpeech> values) {
        super(context, textViewResourceId, values);
    }


    /*
    // Your sent context
    private Context context;
    // Your custom values for the spinner
    private List<PartOfSpeech> values;

    public partOfSpeechSpinAdapter(Context context, int textViewResourceId,
                                   List<PartOfSpeech> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public PartOfSpeech getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = (TextView) super.getView(position, convertView, parent);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(values.get(position).getLabelText());

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setText(values.get(position).getLabelText());

        return label;
    }
    */
}
