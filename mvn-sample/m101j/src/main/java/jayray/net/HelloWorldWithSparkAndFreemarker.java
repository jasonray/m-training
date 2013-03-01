package jayray.net;

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
                return getdata(request.queryParams("name"));
            }
        }

        );

    }

    private static Writer getdata(String name) {
        if (name==null) name="";

        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(HelloWorldFreemarker.class, "/");

        StringWriter writer = new StringWriter();
        try {
            Template helloTemplate = configuration.getTemplate("hello.ftl");
            Map<String, Object> helloMap = new HashMap<String, Object>();
            helloMap.put("name", name);
            helloTemplate.process(helloMap, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
        return writer;
    }


}