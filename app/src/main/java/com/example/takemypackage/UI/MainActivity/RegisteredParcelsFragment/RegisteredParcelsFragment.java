package com.example.takemypackage.UI.MainActivity.RegisteredParcelsFragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.takemypackage.Entities.Parcel;
import com.example.takemypackage.R;

public class RegisteredParcelsFragment extends Fragment {
   private RecyclerView _recyclerView;

   @Nullable
   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.registered_parcels_fragment, container, false);
      RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.registeredParcelsRecyclerView);
      recyclerView.setHasFixedSize(true);
      RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
      recyclerView.setLayoutManager(layoutManager);

      Parcel[] registeredParcels = new Parcel[5];
      RecyclerView.Adapter adapter = new RegisteredParcelsRecyclerViewAdapter(registeredParcels);
      recyclerView.setAdapter(adapter);

      return view;
   }
}
