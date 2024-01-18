package com.epam.esm.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
@Entity
@Table(name = "gift_certificates")
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificate implements Identifiable {
    @Id
    @Column(name = "gift_certificate_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer duration;

    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "last_updated_date")
    private LocalDateTime lastUpdateDate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "gift_certificate_tag",
            joinColumns = {@JoinColumn(name = "gift_certificate_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    private Set<Tag> tags;

    @Transient
    @OneToMany(mappedBy = "gift_certificate", cascade = CascadeType.ALL)
    private List<Order> orders;
}
