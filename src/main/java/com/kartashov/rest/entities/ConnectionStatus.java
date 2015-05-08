package com.kartashov.rest.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import java.io.IOException;

public class ConnectionStatus {

    public enum Status {
        Connected,
        Disconnected,
        Unknown
    }

    private final Status status;

    @JsonCreator
    public ConnectionStatus(@JsonProperty("status") Status status) {
        this.status = status;
    }

    @JsonProperty
    public Status getStatus() {
        return status;
    }

    public String toString() {
        return String.format("{ status: %s }", status);
    }

    @javax.persistence.Converter
    public static class Converter implements AttributeConverter<ConnectionStatus, String> {

        private final ObjectMapper mapper;

        public Converter() {
            mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        }

        @Override
        public String convertToDatabaseColumn(ConnectionStatus attribute) {
            try {
                return mapper.writeValueAsString(attribute);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Cannot serialize connection status", e);
            }
        }

        @Override
        public ConnectionStatus convertToEntityAttribute(String dbData) {
            if (dbData == null) {
                return null;
            }
            try {
                return mapper.readValue(dbData, ConnectionStatus.class);
            } catch (IOException e) {
                throw new RuntimeException("Cannot deserialize connection status", e);
            }
        }
    }
}
