package uk.co.ribot.androidboilerplate.ui.main;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;
import rx.Observable;
import rx.Subscription;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.model.Ribot;
import uk.co.ribot.androidboilerplate.util.RxUtils;

/**
 * Created by connieli on 4/9/16.
 */
public class RibotsViewHolder extends RecyclerView.ViewHolder {

//	@Bind(R.id.view_hex_color)
//	View hexColorView;
	@Bind(R.id.view_hex_color)
	ImageView avatarView;

	@Bind(R.id.text_name)
	TextView nameTextView;

	@Bind(R.id.text_email)
	TextView emailTextView;

	@Nullable
	private Ribot mRibot;

	/**
	 * Click listener subscription. Subscribe when attached, when detached.
	 */
	private Subscription mSubscription;
	/**
	 * Cached clicks observable.
	 */
	private Observable<Void> mClicksObservable;

	public RibotsViewHolder(final View itemView) {
		super(itemView);
		ButterKnife.bind(this, itemView);

		setListeners(itemView);
	}

	/**
	 * Instantiate each viewholder with listeners. THe correct model is set in bindViewHolder.
	 *
	 * @param itemView
	 *      the viewholder
	 */
	private void setListeners(final View itemView) {
		// Same observable for all viewholder's click listeners
		mClicksObservable = RxView.clicks(itemView);

		itemView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
			@Override
			public void onViewAttachedToWindow(final View v) {
				Log.i("connie", "onViewAttachedToWindow: " + getModel().profile.name.first);
				RxUtils.logSubscriptionState("onViewAttachedToWindow", mSubscription);

				// unsubscribe old subscription and resubscribe
				mSubscription = mClicksObservable.subscribe(aVoid -> {
					Log.i("connie", "clicked! mRibot: " + getModel().profile.name.first);
				});
			}

			@Override
			public void onViewDetachedFromWindow(final View v) {
				Log.i("connie", "onViewDetachedFromWindow: " + getModel().profile.name.first);
				RxUtils.unsubscribe(mSubscription);
			}
		});
	}

	public void bindModel(final Ribot ribot) {
		mRibot = ribot;
		// bind data to viewholder
//		hexColorView.setBackgroundColor(Color.parseColor(ribot.profile.hexColor));

		Glide.with(avatarView.getContext())
				.load(ribot.profile.avatar)
				.centerCrop()
				.crossFade()
				.into(avatarView);

		nameTextView.setText(String.format("%s %s",
				ribot.profile.name.first, ribot.profile.name.last));
		emailTextView.setText(ribot.profile.email);
	}

	@Nullable
	public Ribot getModel() {
		return mRibot;
	}
}