package uk.co.ribot.androidboilerplate.util;

import android.util.Log;
import rx.Subscription;

import java.util.List;

/**
 * RxJava utils.
 */
public class RxUtils {

	/**
	 * Safe unsubscribe a list of subscriptions.
	 *
	 * @param subscriptions
	 *      the list of subscriptions
	 */
	public static void unsubscribe(final List<Subscription> subscriptions) {
		for (Subscription subscription : subscriptions) {
			if (subscription != null && !subscription.isUnsubscribed()) {
				subscription.unsubscribe();
			}
		}
	}

	/**
	 * Safe unsubscribe one or more subscriptions.
	 *
	 * @param subscriptions
	 *      one or more subscriptions
	 */
	public static void unsubscribe(final Subscription... subscriptions) {
		for (Subscription subscription : subscriptions) {
			if (subscription != null && !subscription.isUnsubscribed()) {
				subscription.unsubscribe();
			}
		}
	}

	/**
	 * Log the subscription state, unsubscribe if still subscribe.
	 * @param subscription
	 */
	public static void logSubscriptionState(final String tag, final Subscription subscription) {
		if (subscription != null && subscription.isUnsubscribed()) {
			Log.i("connie", tag + ": not null, already unsubscribed");
		} else if (subscription == null) {
			Log.i("connie", tag + ": subscription is NULL ");
		} else {
			Log.i("connie", tag + ": subscription is not null && still subscribed, unsubscribing...");
			RxUtils.unsubscribe(subscription);
		}
	}
}
