package jayray.net;

import com.mongodb.*;

import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: jasonray
 * Date: 3/9/13
 * Time: 8:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class HelloWorldWithMongo {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient(new ServerAddress("localhost", 27017));

        DB database = client.getDB("test");
        DBCollection collection = database.getCollection("account");

        DBObject document = collection.findOne();
        System.out.println(document);

    }

}
