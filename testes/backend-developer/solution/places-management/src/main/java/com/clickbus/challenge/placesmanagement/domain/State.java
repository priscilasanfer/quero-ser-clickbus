package com.clickbus.challenge.placesmanagement.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class State implements Comparable<State> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, message = "Name must not be less than 2 characters")
    private String name;

    @NotNull(message = "Abbreviation cannot be null")
    @Size(min = 2, max = 3, message = "Abbreviation must be equal to 2 characters\")")
    private String abbreviation;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    @Override
    public int compareTo(State that) {
        return ComparisonChain.start()
                .compare(this.name, that.name)
                .compare(this.id, that.id)
                .result();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;
        State state = (State) o;
        return Objects.equal(getId(), state.getId()) &&
                Objects.equal(getName(), state.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("name", abbreviation)
                .toString();
    }

}
