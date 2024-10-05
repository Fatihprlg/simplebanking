package com.eteration.simplebanking.model;


// This class is a place holder you can change the complete implementation

import com.eteration.simplebanking.common.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class TransactionStatus {
    @Enumerated(EnumType.STRING)
    private Status status;
    private String body;

    public TransactionStatus(Status status, String body) {
        this.status = status;
        this.body = body;
    }
}