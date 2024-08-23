package com.hackathon.ecocycle.domain.recycling_method.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "recycling_method")
public class RecyclingMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recycling_method_id")
    private Long id;

    @Column(name = "category")
    private String category;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "recycling_methods", joinColumns = @JoinColumn(name = "recycling_method_id"))
    private List<String> imageUrlList;
}
