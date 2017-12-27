package filters;

import akka.stream.Materializer;
import play.Logger;
import play.mvc.Filter;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

public class RequestLoggingFilter extends Filter {

  private static final Logger.ALogger LOGGER = Logger.of(RequestLoggingFilter.class);

  @Inject
  public RequestLoggingFilter(Materializer mat) {
    super(mat);
  }

  @Override
  public CompletionStage<Result> apply(Function<Http.RequestHeader, CompletionStage<Result>> nextFilter, Http.RequestHeader requestHeader) {

    long startTime = System.currentTimeMillis();

    return nextFilter.apply(requestHeader).thenApply(result -> {

      long requestTime = System.currentTimeMillis() - startTime;

      LOGGER.info("{} forwarded-for {} {} {} took {}ms and returned {}", requestHeader.remoteAddress(), requestHeader.getHeader("X-Forwarded-For"), requestHeader.method(), requestHeader.uri(), requestTime, result.status());

      return result;
    });
  }
}