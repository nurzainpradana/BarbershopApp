package com.firmansyah.barbershop.view.profile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.firmansyah.barbershop.R;
import com.firmansyah.barbershop.model.User;
import com.firmansyah.barbershop.util.Const;
import com.firmansyah.barbershop.util.SharePref;
import com.firmansyah.barbershop.viewmodel.BarbershopViewModel;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.firmansyah.barbershop.util.Const.IMAGE_USER_URL;

public class ProfileFragment extends Fragment{

    public User user;

    private TextView tvProfileName;
    private TextView tvProfileAddress;
    private TextView tvProfileNoPhone;
    private TextView tvProfileEmail;
    private TextView tvProfileBirthdate;
    private TextView tvProfilUsername;
    private CircleImageView ivProfilePicture;
    private Button btnEditProfile;

    SharePref sharePref;



    public ProfileFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new ProfileFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvProfileName = view.findViewById(R.id.tv_profile_name);
        tvProfilUsername = view.findViewById(R.id.tv_profile_username);
        ivProfilePicture = view.findViewById(R.id.iv_profile_frame_photo);

        BarbershopViewModel barbershopViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(BarbershopViewModel.class);

    }

    private void setView(List<User> users) {
        tvProfileName.setText(users.get(0).getmName().toUpperCase());
        tvProfileAddress.setText(users.get(0).getmAddress());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
        tvProfileBirthdate.setText(ft.format(users.get(0).getmDateOfBirth()));
        tvProfileEmail.setText(users.get(0).getmEmail());
        tvProfileNoPhone.setText(users.get(0).getmNoPhone());
        tvProfilUsername.setText(users.get(0).getmUsername());

        String urlPhoto = IMAGE_USER_URL + users.get(0).getmPhotoProfile();
        Picasso.get()
                .load(urlPhoto)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(ivProfilePicture);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }



}


