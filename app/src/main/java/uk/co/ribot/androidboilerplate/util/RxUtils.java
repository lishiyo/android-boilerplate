package uk.co.ribot.androidboilerplate.util;

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
}
