package uk.co.ribot.androidboilerplate.ui.detail;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;
import uk.co.ribot.androidboilerplate.data.DataManager;
import uk.co.ribot.androidboilerplate.ui.base.BasePresenter;
import uk.co.ribot.androidboilerplate.util.RxUtils;

import javax.inject.Inject;

/**
 * SignInView Presenter
 */
public class RibotDetailPresenter extends BasePresenter<RibotDetailMvpView> {
	/**
	 * Magic box of data.
	 */
	private final DataManager mDataManager;
	/**
	 * Subscription to DataManager
	 */
	private Subscription mRibotDetailSubscription;

	@Inject
	public RibotDetailPresenter(final DataManager dataManager) {
		// init with real or mock datamanager singleton
		mDataManager = dataManager;
	}

	/**
	 * Main function - load a single ribot.
	 */
	public void loadRibot(final String ribotId) {
		checkViewAttached();

		mRibotDetailSubscription = mDataManager
				.getRibot(ribotId)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(ribot -> {
					if (ribot == null) {
						getMvpView().showEmptyRibot();
					} else {
						getMvpView().showRibot(ribot);
					}
				}, throwable -> {
					Timber.e(throwable, "There was an error loading the ribots.");
					getMvpView().showError(throwable);
				});
	}

	@Override
	public void detachView() {
		super.detachView();

		RxUtils.unsubscribe(mRibotDetailSubscription);
	}
}
