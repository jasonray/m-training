package jayray.net;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jasonray
 * Date: 3/9/13
 * Time: 12:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class InsertIntoCollection {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB db = client.getDB("test");
        DBCollection fruitCollection = db.getCollection("w2collection");
        fruitCollection.drop();


        DBObject notAFruitObject = new BasicDBObject("not-a-fruit", "");
        fruitCollection.insert(notAFruitObject);

        List<String> fruits = Arrays.asList("apple", "banana", "coconut", "date", "entawak", "fig");

        for (String fruit : fruits) {
            DBObject fruitObject = new BasicDBObject("fruit", fruit);
            fruitCollection.insert(fruitObject);
        }


        QueryBuilder builder = QueryBuilder.start("fruit").exists(true);
        DBObject fieldSelection = new BasicDBObject("_id", false);

        System.out.println("filter: " + builder.get());

        DBCursor cursor = null;
        try {
            cursor = fruitCollection.find(builder.get(), fieldSelection);
            DBObject sort = new BasicDBObject("fruit", 1);
            cursor.sort(sort);

            while (cursor.hasNext()) {
                DBObject record = cursor.next();
                System.out.println("found this fruit" + record);
            }
        } finally {
            cursor.close();
        }

    }
}