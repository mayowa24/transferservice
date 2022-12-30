package com.mcode.transferservice.models.entities;

import com.mcode.transferservice.enums.TransactionStatus;
import com.mcode.transferservice.enums.TransferProvider;
import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;

@EntityScan("com.mcode.transferservice.models.entities")
@Entity
@Getter
@Setter
@Table
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;
    @Column
    @Enumerated(EnumType.STRING)
    private TransferProvider transferProvider;
    @Column
    private String reference;
    @Column
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    @Column
    private String externalReference;
    @Column
    private String bankName;
    @Column
    private String bankCode;
    @Column
    private String accountNumber;
    private String accountName;

}
