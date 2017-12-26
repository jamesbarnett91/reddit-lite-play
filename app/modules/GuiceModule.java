package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.name.Names;
import play.Configuration;
import play.Environment;
import services.SubredditService;

public class GuiceModule extends AbstractModule {

  private final Configuration configuration;

  @Inject
  public GuiceModule(Environment environment, Configuration configuration) {
    this.configuration = configuration;
  }

  @Override
  protected void configure() {

    bindConstant().annotatedWith(Names.named("apiConnectionTimeout")).to(configuration.getString("apiConnectionTimeoutMs"));

    bind(SubredditService.class);

    requestInjection(this);
  }


}
