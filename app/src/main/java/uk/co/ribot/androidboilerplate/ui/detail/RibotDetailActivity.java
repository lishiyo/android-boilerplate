package uk.co.ribot.androidboilerplate.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import butterknife.ButterKnife;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.model.Ribot;
import uk.co.ribot.androidboilerplate.ui.base.BaseActivity;
import uk.co.ribot.androidboilerplate.ui.base.Presenter;

import javax.inject.Inject;

/**
 * Created by connieli on 4/9/16.
 */
public class RibotDetailActivity extends BaseActivity implements RibotDetailMvpView {
	private final static String EXTRA_RIBOT_ID = "extra_ribot_id";

	@Inject
	RibotDetailPresenter mRibotPresenter;

	private String mRibotId;

	public static Intent getStartIntent(final Context context, final String ribotId) {
		final Intent intent = new Intent(context, RibotDetailActivity.class);
		intent.putExtra(EXTRA_RIBOT_ID, ribotId);
		return intent;
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		getActivityComponent().inject(this);
		setContentView(R.layout.activity_ribot_detail);
		ButterKnife.bind(this);

		mRibotId = savedInstanceState.getString(EXTRA_RIBOT_ID, "");

		// Init the presenter and register self as view
		mRibotPresenter.attachView(this);
		mRibotPresenter.loadRibot(mRibotId);
	}

	@Override
	public Presenter getPresenter() {
		return mRibotPresenter;
	}

	@Override
	public String getRibotId() {
		return mRibotId;
	}

	/**
	 * ====== Presenter callbacks ======
	 */

	@Override
	public void showRibot(final Ribot ribot) {
		Log.d("connie", "showRibot: " + ribot.toString());
	}

	@Override
	public void showEmptyRibot() {

	}

	@Override
	public void showError() {

	}
}
