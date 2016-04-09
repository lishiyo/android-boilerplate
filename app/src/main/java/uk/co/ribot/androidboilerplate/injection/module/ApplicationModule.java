package uk.co.ribot.androidboilerplate.injection.module;

import android.app.Application;
import android.content.Context;
import com.squareup.otto.Bus;
import dagger.Module;
import dagger.Provides;
import uk.co.ribot.androidboilerplate.data.remote.RibotsService;
import uk.co.ribot.androidboilerplate.injection.ApplicationContext;

import javax.inject.Singleton;

/**
 * Provide application-level dependencies.
 *
 * Use @Module to signal to Dagger to search within the available methods
 * for possible instance providers (@Provides annotation).
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton // means initialize once during entire app lifecycle
    Bus provideEventBus() {
        return new Bus();
    }

    @Provides
    @Singleton
    RibotsService provideRibotsService() {
        return RibotsService.Creator.newRibotsService();
    }

//    @Provides
//    @Singleton
//    Gson provideGson() {
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
//        return gsonBuilder.create();
//    }
}
