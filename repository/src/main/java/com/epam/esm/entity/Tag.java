package com.epam.esm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@Entity
@Builder
@Table(name = "tags")
@ToString(exclude = "giftCertificates")
@EqualsAndHashCode(exclude = "giftCertificates")
@NoArgsConstructor
@AllArgsConstructor
public class Tag implements Identifiable, Comparable<Tag> {
    @Id
    @Column(name = "tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "name",
            nullable = false,
            unique = true)
    private String name;

    @ManyToMany(mappedBy = "tags")
    Set<GiftCertificate> giftCertificates;

    @Override
    public int compareTo(Tag o) {
        return this.getName().compareTo(o.getName());
    }
}
