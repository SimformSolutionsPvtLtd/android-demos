package com.simformsolutions.constraintlayout;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaystoreAppActivity extends AppCompatActivity implements RatingBar.OnRatingBarChangeListener {

    @BindView(R.id.ivAddToWishList)
    ImageView ivAddToWishList;

    @BindView(R.id.ivCancelAppInstallation)
    ImageView ivCancelAppInstallation;

    @BindView(R.id.btnSubmitUserRating)
    Button btnSubmitUserRating;

    @BindView(R.id.btnReadMore)
    Button btnReadMore;

    @BindView(R.id.btnViewAllReviews)
    Button btnViewAllReviews;

    @BindView(R.id.tvAppDescription)
    TextView tvAppDescription;

    @BindView(R.id.tvInstallApp)
    TextView tvInstallApp;

    @BindView(R.id.tvUserRateMessage)
    TextView tvUserRateMessage;

    @BindView(R.id.tvRateThisAppLabel)
    TextView tvRateThisAppLabel;

    @BindView(R.id.tvDownloadingTextLabel)
    TextView tvDownloadingTextLabel;

    @BindView(R.id.pbInstallProgressBar)
    public ProgressBar pbInstallProgressBar;

    @BindView(R.id.clUserReview2)
    public ConstraintLayout clUserReview2;

    @BindView(R.id.rbRateThisApp)
    public RatingBar rbRateThisApp;

    private Boolean isAppWishListed = false;
    private Boolean isDescFullDisplayed = false;
    private String[] ratingTexts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playstore_app);
        ButterKnife.bind(this);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingToolBar);
        collapsingToolbarLayout.setTitle(null);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name_label));

        ratingTexts = getResources().getStringArray(R.array.rating_texts);

        rbRateThisApp.setOnRatingBarChangeListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.playstore_app_menu, menu);
        return true;
    }

    public void setWishlist(View view) {
        if (isAppWishListed) {
            ivAddToWishList.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
            showToast(getString(R.string.strRemovedFromWishlist));
            isAppWishListed = false;
        } else {
            ivAddToWishList.setImageResource(R.drawable.ic_bookmark_black_24dp);
            showToast(getString(R.string.strAddedToWishlist));
            isAppWishListed = true;
        }
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void setDescription(View view) {
        if (isDescFullDisplayed) {
            tvAppDescription.setText(getString(R.string.app_desc));
            btnReadMore.setText(getResources().getString(R.string.read_more));
            isDescFullDisplayed = false;
        } else {
            tvAppDescription.setText(getString(R.string.desc_full));
            btnReadMore.setText(getResources().getString(R.string.read_less));
            isDescFullDisplayed = true;
        }
    }

    public void submitRating(View view) {
        tvRateThisAppLabel.setText(getString(R.string.rated_text));
        btnSubmitUserRating.setVisibility(View.GONE);
        tvUserRateMessage.setVisibility(View.GONE);
        rbRateThisApp.setEnabled(false);
    }

    public void downloading(View view) {
        if (tvInstallApp.getVisibility() == View.GONE) {
            tvInstallApp.setVisibility(View.VISIBLE);
            tvDownloadingTextLabel.setVisibility(View.GONE);
            pbInstallProgressBar.setVisibility(View.GONE);
            ivCancelAppInstallation.setVisibility(View.GONE);
        } else {
            tvInstallApp.setVisibility(View.GONE);
            tvDownloadingTextLabel.setVisibility(View.VISIBLE);
            pbInstallProgressBar.setVisibility(View.VISIBLE);
            ivCancelAppInstallation.setVisibility(View.VISIBLE);
        }
    }

    public void viewReviews(View view) {
        if (clUserReview2.getVisibility() == View.GONE) {
            clUserReview2.setVisibility(View.VISIBLE);
            btnViewAllReviews.setText(getString(R.string.show_less));
        } else {
            clUserReview2.setVisibility(View.GONE);
            btnViewAllReviews.setText(getString(R.string.show_more));
        }
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        btnSubmitUserRating.setEnabled(true);
        tvUserRateMessage.setVisibility(View.VISIBLE);
        int rate = (int) rating;
        tvUserRateMessage.setText(ratingTexts[rate - 1]);
    }
}
