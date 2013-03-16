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

        initializeFruits(fruitCollection);
        printFruits(fruitCollection);

        System.out.println("updating fruits");
        DBObject queryByApple=QueryBuilder.start("_id").is("apple").get();
        DBObject updateAppleColor = new BasicDBObject("$set", new BasicDBObject("color", "red"));
        fruitCollection.update(queryByApple, updateAppleColor);

        printFruits(fruitCollection);
    }

    private static void initializeFruits(DBCollection fruitCollection) {
        fruitCollection.drop();

        List<String> fruits = Arrays.asList("apple", "banana", "coconut", "date", "entawak", "fig");
        for (String fruit : fruits) {
            DBObject fruitObject = new BasicDBObject("_id", fruit);
            fruitCollection.insert(fruitObject);
        }
    }

    private static void printFruits(DBCollection fruitCollection) {
        DBCursor cursor = null;
        try {
            cursor = fruitCollection.find();
            DBObject sort = new BasicDBObject("_id", 1);
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