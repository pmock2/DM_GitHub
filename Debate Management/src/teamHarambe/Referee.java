package teamHarambe;

/**
 * Created by Daylon on 10/7/2016.
 */

public class Referee {
    int id;
    boolean isSuperReferee;

    public Referee(int id, boolean isSuperReferee) {
        this.id = id;
        this.isSuperReferee = isSuperReferee;
    }

    public int getId() {
        return id;
    }
}
