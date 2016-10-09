/**
 * Created by Daylon on 9/28/2016.
 */
package teamHarambe;

public class Team {
    String name;
    double score;
    boolean isPlayingCurrently;

    public Team(String name, double score, boolean isPlayingCurrently) {
        this.name = name;
        this.score = score;
        this.isPlayingCurrently = isPlayingCurrently;
    }

    public String getName() {
        return name;
    }
}
