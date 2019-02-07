package com.draos.nekretnine.nekretnineui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class HelpFragment extends Fragment {

    private HelpFragment.OnFragmentInteractionListener listener;
    SessionManager session;

    public static HelpFragment newInstance() {
        return new HelpFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Session class instance
        session = new SessionManager(this.getContext());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Help");

        View view = inflater.inflate(R.layout.fragment_help, container, false);

        TextView tv = (TextView) view.findViewById(R.id.help);
        tv.append("If you are a new user, here you can find all the necessary information about the application.\n" +
                "The purpose of the Real Estate application is to facilitate the search for real estate users through sophisticated searches.\n" +
                "If you own a real estate you can post an ad on our site!\n" +
                "\n" +
                "Is it necessary to create an account?\n" +
                "It is not necessary to create a user account to view and search the ads. If you want to place an ad, creating an account is mandatory.\n" +
                "\n" +
                "How do I create an account?\n" +
                "You can create a user account by going to the user shortcut at the bottom of the screen.\n" +
                "\n" +
                "How do I set up an ad?\n" +
                "To create an ad, you need to create an account. After creating your account, sign in with your data, after which you will be able to create your ad by clicking the 'Create advert' button.\n" +
                "\n" +
                "What options do I have as a registered user?\n" +
                "As a registered user, you have the ability to post ads and view your ads, and edit your account.\n" +
                "\n" +
                "How can I contact others?\n" +
                "By leaving your personal information, other users will be able to contact you by phone number or e-mail. Similarly, by clicking on the ad you can contact real estate owners.\n" +
                "\n" +
                "How do I search real estate?\n" +
                "Search for properties by going to a search shortcut at the bottom of the screen. Search is possible depending on category (rent / sale), price, city, settlement and number of rooms.\n" +
                "\n" +
                "I've seen a real estate that I like, but I still want to keep looking. Can I remember it?\n" +
                "You can remember it by clicking on the 'Star' icon next to an ad / property. You can check it out later by clicking the 'Favorite' icon (Star) at the bottom of the screen.");

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HelpFragment.OnFragmentInteractionListener) {
            listener = (HelpFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnFragmentInteractionListener {
    }


}