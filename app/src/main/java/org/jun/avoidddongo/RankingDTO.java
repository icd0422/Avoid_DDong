package org.jun.avoidddongo;

import java.time.LocalDateTime;

public class RankingDTO {


    private String name;

    private Long score;

    private String createdAt;

    public String getName() {
        return name;
    }

    public Long getScore() {
        return score;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "RankingDTO{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", createdAt=" + createdAt +
                '}';
    }
}
