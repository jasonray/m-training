package jayray.net;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

/**
 * Created with IntelliJ IDEA.
 * User: rayj
 * Date: 2/28/13
 * Time: 10:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class HelloWorldSpark {
    public static void main(String[] args) {
        Spark.get(new Route("/") {
            @Override
            public Object handle(final Request request, final Response response) {
                return "hello world from spark";
            }
        });
    }
}

