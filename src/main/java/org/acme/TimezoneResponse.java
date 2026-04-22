package org.acme;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "timezone_response")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimezoneResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String timezone;
    public String dateTime;
    public String dayOfWeek;

    public TimezoneResponse() {
    }

    public TimezoneResponse(String timezone, String dateTime, String dayOfWeek) {
        this.timezone = timezone;
        this.dateTime = dateTime;
        this.dayOfWeek = dayOfWeek;
    }
}