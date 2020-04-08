package com.example.myapplication.adapter.configure;

import com.example.myapplication.activity.base.BaseEditableAppCompatActivity;
import com.example.myapplication.adapter.BaseRecycleAdapter;
import com.example.myapplication.entity.Profile;

//public class HelpSentenceEditableAdapterTEST extends RecyclerView.Adapter<HelpSentenceEditableAdapterTEST.ItemViewHolder>  {
public class ProfileEditableAdapter extends
        BaseRecycleAdapter<BaseEditableAppCompatActivity, Profile, BaseRecycleAdapter.ItemViewHolder> {

    public ProfileEditableAdapter(BaseEditableAppCompatActivity context) {
        super(context);
    }



}
