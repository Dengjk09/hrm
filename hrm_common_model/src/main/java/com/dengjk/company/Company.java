package com.dengjk.company;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author Dengjk
 * @create 2019-04-13 16:41
 * @desc
 **/
@Table(name = "company")
@Data
@Entity
public class Company {

    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "version")
    private String version;


    @Column(name = "renewalDate")
    @DateTimeFormat(pattern="yyyy:MM:dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime renewalDate;

}
