package com.pwr.bzapps.plwordnetmobile.service.database.entity.properties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "tables")
public class TableEntity {
    @Id
    @Column(name = "TABLE_NAME")
    private String table_name;
    @Column(name = "TABLE_SCHEMA")
    private Date table_schema;
    @Column(name = "UPDATE_TIME")
    private Date update_time;

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public Date getTable_schema() {
        return table_schema;
    }

    public void setTable_schema(Date table_schema) {
        this.table_schema = table_schema;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}
