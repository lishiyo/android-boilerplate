package uk.co.ribot.androidboilerplate.data;

import rx.Observable;
import rx.functions.Action0;
import uk.co.ribot.androidboilerplate.data.local.DatabaseHelper;
import uk.co.ribot.androidboilerplate.data.local.PreferencesHelper;
import uk.co.ribot.androidboilerplate.data.model.Ribot;
import uk.co.ribot.androidboilerplate.data.remote.RibotsService;
import uk.co.ribot.androidboilerplate.util.EventPosterHelper;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

/**
 * The "Magic Box" that pools and transforms data from different sources (service, db, sharedprefs, events)
 * to emit observables that conform to a stable public API.
 */
@Singleton
public class DataManager {

    private final RibotsService mRibotsService;
    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final EventPosterHelper mEventPoster;

    @Inject
    public DataManager(RibotsService ribotsService, PreferencesHelper preferencesHelper,
                       DatabaseHelper databaseHelper, EventPosterHelper eventPosterHelper) {
        mRibotsService = ribotsService;
        mPreferencesHelper = preferencesHelper;
        mDatabaseHelper = databaseHelper;
        mEventPoster = eventPosterHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

	/**
     * Fetch ribots from retrofit then save to db, emitting the ribot of every successful insertion.
     *
     * @return single ribot for every insertion
     */
    public Observable<Ribot> syncRibots() {
        // emits a single Ribot for every successful insertion
        return mRibotsService
                .getRibots()
                .concatMap(ribots -> mDatabaseHelper.setRibots(ribots));
    }

	/**
     * Fetch from network and save to db, returning the network ribots.
     *
     * @return the network's ribots
     */
    public Observable<List<Ribot>> fetchNetworkRibots() {
        return mRibotsService
                .getRibots()
                .doOnNext(ribots -> mDatabaseHelper.setRibots(ribots));
    }

	/**
     * Get ribots from db, or network if necessary.
     *
     * @return list of ribots from db
     */
    public Observable<List<Ribot>> getRibots() {
        return mDatabaseHelper
                .getRibots()
                .distinct()
                .flatMap(ribots -> {
                    // use flatMap instead of switchIfEmpty to force async query to finish before
                    // checking if empty
                    if (ribots.isEmpty()) {
                        fetchNetworkRibots();
                    }

                    return Observable.just(ribots);
                });
//                .switchIfEmpty(fetchNetworkRibots());
    }

	/**
	 * Fetch a single Ribot from network.
     *
     * @return
     */
    public Observable<Ribot> getRibot(final String ribotId) {
        // TODO: fetch from db first, then only if empty fetch from network
        return mRibotsService.getSingleRibot(ribotId);
    }

    /// Helper method to post events from doOnCompleted.
    private Action0 postEventAction(final Object event) {
        return () -> mEventPoster.postEventSafely(event);
    }

}
