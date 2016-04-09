package uk.co.ribot.androidboilerplate.test.common.injection.component;

import dagger.Component;
import uk.co.ribot.androidboilerplate.injection.component.ApplicationComponent;
import uk.co.ribot.androidboilerplate.test.common.injection.module.ApplicationTestModule;

import javax.inject.Singleton;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {

}
