package pl.martyna.bakula.coursesscraper;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "historyCourses")
public class CourseHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer sourceId;
    @Enumerated(EnumType.STRING)
    private SourceType sourceType;
    private double price;
    @CreationTimestamp
    @Column(name = "date")
    private LocalDateTime date;

    public CourseHistoryEntity(Integer sourceId, SourceType sourceType, double price) {
        this.sourceId = sourceId;
        this.sourceType = sourceType;
        this.price = price;
    }

    public CourseHistoryEntity() {

    }

    public Integer getSourceId() {
        return sourceId;
    }
    public Integer getId() {
        return id;
    }
    public double getPrice() {
        return price;
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
