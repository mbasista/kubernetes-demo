package pl.demo.dbservice;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "content")
public class JsonContent {
    @Id
    private String id;
    @Type(type="text")
    private String content;
    @Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    @Setter(AccessLevel.NONE)
    private Long version;
}
