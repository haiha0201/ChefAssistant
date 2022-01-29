package com.HaiHa.ChefAssistant.ui.detailedRecipe;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.HaiHa.ChefAssistant.Helper;
import com.HaiHa.ChefAssistant.models.Food.FoodViewModel;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.unity3d.player.R;
import com.unity3d.player.UnityPlayerActivity;

public class ExtensionFragment extends Fragment {
    private FoodViewModel foodViewModel;
    private  YouTubePlayerView youTubePlayerView;

    public static ExtensionFragment newInstance() {
        return new ExtensionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_extension, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imageView = view.findViewById(R.id.imageView);
        TextView name = (TextView) view.findViewById(R.id.ingredientMealNameTextView);
        TextView area = (TextView) view.findViewById(R.id.ingredientMealAreaTextView);
        youTubePlayerView= view.findViewById(R.id.youtube_player);

        getLifecycle().addObserver(youTubePlayerView);
        foodViewModel = new ViewModelProvider(requireActivity()).get(FoodViewModel.class);
        foodViewModel.getSelectedItem().observe(requireActivity(), food -> {
            if (food != null) {
                youTubePlayerView.getYouTubePlayerWhenReady(new YouTubePlayerCallback() {
                    @Override
                    public void onYouTubePlayer(@NonNull YouTubePlayer youTubePlayer) {
                        try {
                            String videoID = food.mealYoutubeURL.split("=")[1];
                            youTubePlayer.cueVideo(videoID, 0);
                            youTubePlayer.pause();
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "Can't retrieve youtube video", Toast.LENGTH_SHORT).show();
                            Log.d("Extension fragment", e.toString());
                        }
                    }
                });
                try {
                    name.setText(food.mealName);
                    area.setText(food.mealArea);
                    Helper.SetImage(imageView, food);
                }
                catch (Exception e)
                {
                    Toast.makeText(getContext(), "ERROR: Some information cannot be displayed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button button = (Button) view.findViewById(R.id.openARButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UnityPlayerActivity.class);
                startActivity(intent);
            }
        });
    }
}
