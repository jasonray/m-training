package jayray.net;

import com.mongodb.*;
import com.mongodb.util.JSON;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jasonray
 * Date: 3/9/13
 * Time: 2:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class hw3 {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB db = client.getDB("school");

        DBCollection studentsCollection = db.getCollection("students");
        System.out.println("number of students: " + studentsCollection.count());

        DBCursor studentsCursor = studentsCollection.find();
        while (studentsCursor.hasNext()) {
            DBObject studentRecord = studentsCursor.next();
            ArrayList<DBObject> scores = (ArrayList<DBObject>) studentRecord.get("scores");
            Object studentId =  studentRecord.get("_id");
            System.out.println("student " + studentId + "; " + scores.size());

            DBObject lowestHomeworkScoreRecord = null;

            for (DBObject score : scores) {
                String scoreType = (String) score.get("type");
                Double scoreValue = (Double) score.get("score");
                System.out.println(String.format("score: type=%s; score:%s", scoreType, scoreValue));

                if (scoreType.contentEquals("homework")) {
                    if (lowestHomeworkScoreRecord == null) {
                        lowestHomeworkScoreRecord = score;
                    } else {
                        Double lowestScoreValue = (Double) lowestHomeworkScoreRecord.get("score");
                        if (scoreValue < lowestScoreValue) {
                            lowestHomeworkScoreRecord = score;
                        }
                    }
                }
            }

            if (lowestHomeworkScoreRecord != null) {
                System.out.println("need to remove this score: " + lowestHomeworkScoreRecord);

                scores.remove(lowestHomeworkScoreRecord);

                BasicDBObject thisStudentFilter = new BasicDBObject("_id", studentId);
                BasicDBObject updateCommand = new BasicDBObject("scores", scores);
                studentsCollection.update(thisStudentFilter, updateCommand);
            }


        }

//        DBObject query = QueryBuilder.start("_id").is("100").get();
//        System.out.println("query: "  + JSON.serialize(query)  );
//        DBObject student = studentsCollection.findOne(query);
//
//        Object rawScores = student.get("scores");
//        System.out.println("raw scores: " + rawScores);
//        List<String> scores = (List<String>) rawScores;
//
//        for (String score : scores)  {
//
//        }

//        int currentStudent = -1;
//        while (cursor.hasNext()) {
//            DBObject record = cursor.next();
//
//
//            int recordStudentId = Integer.parseInt(record.get("student_id").toString());
//
//            if (currentStudent != recordStudentId) {
//                System.out.println("switching from " + currentStudent + " to " + recordStudentId + ", dropping this record");
//                grades.remove(record);
//                currentStudent = recordStudentId;
//                System.out.println("drop record=" + record);
//            } else {
//                System.out.println("keep record=" + record);
//            }
//
//        }

        System.out.println("number of students: " + studentsCollection.count());

    }
}
