package jayray.net;

import com.mongodb.*;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: jasonray
 * Date: 3/9/13
 * Time: 2:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class hw2 {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB db = client.getDB("students");
        DBCollection grades = db.getCollection("grades");

        System.out.println("number of scores: " + grades.count());

        DBObject query = QueryBuilder.start("type").is("homework").get();
        DBCursor cursor = grades.find(query);

        DBObject sort = new BasicDBObject("student_id", 1).append("score", 1);
        cursor.sort(sort);

        int currentStudent = -1;
        while (cursor.hasNext()) {
            DBObject record = cursor.next();


            int recordStudentId = Integer.parseInt(record.get("student_id").toString());

            if (currentStudent != recordStudentId) {
                System.out.println("switching from " + currentStudent + " to " + recordStudentId + ", dropping this record");
                grades.remove(record);
                currentStudent = recordStudentId;
                System.out.println("drop record=" + record);
            } else {
                System.out.println("keep record=" + record);
            }

        }

        System.out.println("number of scores: " + grades.count());

    }
}
