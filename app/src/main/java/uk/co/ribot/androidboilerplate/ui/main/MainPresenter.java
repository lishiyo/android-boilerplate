package uk.co.ribot.androidboilerplate.ui.main;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;
import uk.co.ribot.androidboilerplate.data.DataManager;
import uk.co.ribot.androidboilerplate.ui.base.BasePresenter;
import uk.co.ribot.androidboilerplate.util.RxUtils;

import javax.inject.Inject;

/**
 * MainActivity Presenter:
 *
 * fetch ribots data from DataManager
 *  => transform to presentation models
 *  => pass to activity/frag implementing MainMvpView
 */
public class MainPresenter extends BasePresenter<MainMvpView> {
	/**
     * Magic box of data.
     */
    private final DataManager mDataManager;
	/**
     * Subscription to {@link #mDataManager}.
     */
    private Subscription mRibotsSubscription;

    @Inject
    public MainPresenter(final DataManager dataManager) {
        // init with real or mock datamanager singleton
        mDataManager = dataManager;
    }

    @Override
    public void detachView() {
        super.detachView();

        RxUtils.unsubscribe(mRibotsSubscription);
    }

	/**
     * Load ribots from db into view, throws error if view not attached.
     */
    public void loadRibots() {
        checkViewAttached();

        mRibotsSubscription = mDataManager
                .getRibots() // from db
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ribots -> {
                    if (ribots.isEmpty()) {
                        getMvpView().showRibotsEmpty();
                    } else {
                        getMvpView().showRibots(ribots);
                    }
                }, throwable -> {
                    Timber.e(throwable, "There was an error loading the ribots.");
                    getMvpView().showError();
                });
    }

}
