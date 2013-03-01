package jayray.net;

import com.mongodb.*;

import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: rayj
 * Date: 2/28/13
 * Time: 9:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class HelloWorldMongoDbStyle {
    public static void main(String[] args) throws UnknownHostException {

        MongoClient client = new MongoClient(new ServerAddress("localhost", 27017));

        DB database = client.getDB("test");
        DBCollection collection = database.getCollection("things");

        DBObject document = collection.findOne();
        System.out.println(document);

    }
}
