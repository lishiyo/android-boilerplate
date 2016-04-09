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
     * Fetch ribots from retrofit then save to db, emitting on every insertion.
     *
     * @return
     */
    public Observable<Ribot> syncRibots() {
        // emits a single Ribot for every successful insertion
        return mRibotsService
                .getRibots()
                .concatMap(ribots -> mDatabaseHelper.setRibots(ribots));
    }

	/**
     * Get ribots from db.
     * @return
     */
    public Observable<List<Ribot>> getRibots() {
        return mDatabaseHelper.getRibots().distinct();
    }

    /// Helper method to post events from doOnCompleted.
    private Action0 postEventAction(final Object event) {
        return () -> mEventPoster.postEventSafely(event);
    }

}
