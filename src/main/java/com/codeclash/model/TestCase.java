package com.codeclash.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "test_cases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "problem_id", nullable = false)
    @JsonBackReference
    private Problem problem;

    @Column(name = "input_data", columnDefinition = "TEXT", nullable = false)
    private String inputData;

    @Column(name = "expected_output", columnDefinition = "TEXT", nullable = false)
    private String expectedOutput;

    @Builder.Default
    @Column(name = "is_hidden", nullable = false)
    private Boolean isHidden = false;

    @Builder.Default
    @Column(name = "order_index", nullable = false)
    private Integer orderIndex = 0;
}
