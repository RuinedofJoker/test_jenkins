package org.joker.test_jenkins;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Data;

import java.util.Date;

/**
 * @author Gesi at 15:47 on 2025/6/10
 */
@Data
public class Employee {
    @CsvBindByName(column = "id")
    private String id;
    @CsvBindByName(column = "name")
    private String name;
    @CsvBindByName(column = "department")
    private String department;
    @CsvBindByName(column = "rank")
    private String rank;
    @CsvBindByName(column = "post")
    private String post;
    @CsvDate("MM/dd/yyyy HH:mm:ss")
    @CsvBindByName(column = "entry_date")
    private Date entryDate;
    @CsvDate("MM/dd/yyyy HH:mm:ss")
    @CsvBindByName(column = "create_date")
    private Date createDate;
    @CsvDate("MM/dd/yyyy HH:mm:ss")
    @CsvBindByName(column = "write_date")
    private Date writeDate;
    @CsvBindByName(column = "create_uid")
    private String createUid;
    @CsvBindByName(column = "write_uid")
    private String writeUid;
    @CsvBindByName(column = "t")
    private String t;
    @CsvBindByName(column = "is_deleted")
    private Boolean isDeleted;
    @CsvBindByName(column = "age")
    private Integer age;
    @CsvBindByName(column = "salary")
    private Double salary;
}
