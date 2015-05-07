package com.kartashov.rest.entities;

import javax.persistence.*;

@Entity
public class Unit {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    @Convert(converter = ConnectionStatus.Converter.class)
    private ConnectionStatus connectionStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ConnectionStatus getConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(ConnectionStatus connectionStatus) {
        this.connectionStatus = connectionStatus;
    }
}
