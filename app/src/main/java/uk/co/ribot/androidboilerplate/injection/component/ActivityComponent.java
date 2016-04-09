package uk.co.ribot.androidboilerplate.injection.component;

import dagger.Component;
import uk.co.ribot.androidboilerplate.injection.PerActivity;
import uk.co.ribot.androidboilerplate.injection.module.ActivityModule;
import uk.co.ribot.androidboilerplate.ui.detail.RibotDetailActivity;
import uk.co.ribot.androidboilerplate.ui.main.MainActivity;

/**
 * This component injects dependencies to all Activities across the application.
 *
 * Inject class used in Dagger 2 is called a component - it assigns refs
 * in activities, services, fragments to have access to singletons.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

	// EACH concrete activity, service, fragment that can be added should be declared
    // with individual inject() methods.

    void inject(MainActivity activity);

    void inject(RibotDetailActivity activity);
}
