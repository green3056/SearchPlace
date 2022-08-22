package com.green.SearchPlace.domain.rank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="SEARCHED_KEYWORD")
public class KeywordQuery {

    @Id
    @Column
    private String keyword;
    @Column
    private Long count;

    public void implementCount() {
        count += 1;
    }

}
