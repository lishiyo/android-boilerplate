package uk.co.ribot.androidboilerplate.ui.detail;

import uk.co.ribot.androidboilerplate.data.model.Ribot;
import uk.co.ribot.androidboilerplate.ui.base.MvpView;

/**
 * Interface for any view implementing sign in.
 */
public interface RibotDetailMvpView extends MvpView {

	String getRibotId();

	/**
	 * Show the successfully fetched ribot (profile).
	 *
	 * @param ribot
	 */
	void showRibot(final Ribot ribot);

	/**
	 * Successful request for the ribot but it returned empty.
	 */
	void showEmptyRibot();

	/**
	 * Request for data failed.
	 */
	void showError(final Throwable throwable);

}
