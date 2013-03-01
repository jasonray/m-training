package jayray.net;

import com.mongodb.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: rayj
 * Date: 2/28/13
 * Time: 11:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class HelloWorldWithSparkAndFreemarker {
    public static void main(String[] args) {

        Spark.get(new Route("/") {
            @Override
            public Object handle(final Request request, final Response response) {
                return getView(request.queryParams("id"));
            }
        }

        );

    }

    private static Writer getView(String id) {
        if (id == null) id = "";

        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(HelloWorldFreemarker.class, "/");

        StringWriter writer = new StringWriter();
        try {
            Template helloTemplate = configuration.getTemplate("hello.ftl");
            helloTemplate.process(getData(), writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
        return writer;
    }

    private static DBObject getData() throws UnknownHostException {
        MongoClient client = new MongoClient(new ServerAddress("localhost", 27017));

        DB database = client.getDB("test");
        DBCollection collection = database.getCollection("things");

        DBObject data = collection.findOne();
        return data;
    }


}