/*
 * Copyright 2011 NEHTA
 * Copyright 2021-2026 ADHA (Australian Digital Health Agency)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package au.gov.nehta.vendorlibrary.ws;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DateTime utility class to facilitate the usage of {@link java.util.Date} and
 * {@link javax.xml.datatype.XMLGregorianCalendar} for webservice calls.
 */
public final class TimeUtility {

    /**
     * DateTime format as string. [yyyyMMdd HH:mm:ss]
     */
    public static final String DATE_FORMAT_NOW = "yyyyMMdd HH:mm:ss";

    /**
     * Regular expression for DateTime format as string. [[yyyyMMdd HH:mm:ss]
     */
    public static final String DATE_FORMAT_NOW_REGX = "^([0-9]{4})([0-1][0-9])"
            + "([0-3][0-9])\\s([0-1][0-9]|[2][0-3]):([0-5][0-9]):([0-5][0-9])$";

    /**
     * Compact Date format as string [yyyymmdd].
     */
    public static final String DATE_FORMAT = "yyyyMMdd";

    /**
     * Regular expression for compact Date format as string [yyyymmdd].
     */
    public static final String DATE_FORMAT_REGX = "^(19|20)\\d\\d(0[1-9]|1[012])"
            + "(0[1-9]|[12][0-9]|3[01])$";

    /**
     * Compact DateTime format as string. [yyyyMMddHHmmss]
     */
    public static final String COMPACT_DATE_TIME_FORMAT = "yyyyMMddHHmmss";

    /**
     * Length of Date time string as YYYYMMDDHHMM.
     */
    public static final int DATE_TIME_LENGTH = COMPACT_DATE_TIME_FORMAT.length();

    private static final DateTimeFormatter FORMATTER_NOW = DateTimeFormatter.ofPattern(DATE_FORMAT_NOW);

    private static final DateTimeFormatter FORMATTER_DATE = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private static final Logger LOGGER = Logger.getLogger(TimeUtility.class
            .getName());

    private TimeUtility() {
    }

    /**
     * This method provided the XMLGregorian Date for the provided date in
     * yyyyMMdd HH:mm:ss.
     *
     * @param date as string in yyyyMMdd HH:mm:ss format
     * @return date as XMLGregorianCalendar instance. Returns null in an event
     *         of error. Invoking method must check for null.
     */
    public static XMLGregorianCalendar getXMLGregorianDateTime(
            final String date) {
        if (date == null) {
            throw new IllegalArgumentException("date must not be null");
        }
        try {
            if (date.matches(DATE_FORMAT_NOW_REGX)) {
                LocalDateTime dateTime = LocalDateTime.parse(date, FORMATTER_NOW);
                XMLGregorianCalendar cal = DatatypeFactory.newInstance()
                        .newXMLGregorianCalendar(new GregorianCalendar());
                cal.setYear(dateTime.getYear());
                cal.setMonth(dateTime.getMonthValue());
                cal.setDay(dateTime.getDayOfMonth());
                cal.setHour(dateTime.getHour());
                cal.setMinute(dateTime.getMinute());
                cal.setSecond(dateTime.getSecond());
                return cal;
            }
        } catch (DatatypeConfigurationException | DateTimeException ex) {
            throw new IllegalArgumentException("Incorrect date format"
                    + date + ". Must be set in " + DATE_FORMAT + " format", ex);
        }
        throw new IllegalArgumentException("Incorrect date format"
                + date + ". Must be set in "
                + DATE_FORMAT + " format");
    }

    /**
     * This method provides the XMLGregorianCalendar Date for the provided
     * date String in yyyymmdd format .
     *
     * @param date in yyyymmdd format
     * @return XMLGregorianCalendar date. Return null in an event of error.
     *         Invoking method must check for null.
     */
    public static XMLGregorianCalendar getXMLGregorianDate(final String date) {
        if (date == null) {
            throw new IllegalArgumentException("date must not be null");
        }
        try {
            if (date.matches(DATE_FORMAT_REGX)) {
                LocalDate localDate = LocalDate.parse(date, FORMATTER_DATE);
                XMLGregorianCalendar cal = DatatypeFactory.newInstance()
                        .newXMLGregorianCalendar(new GregorianCalendar());
                cal.setYear(localDate.getYear());
                cal.setMonth(localDate.getMonthValue());
                cal.setDay(localDate.getDayOfMonth());
                cal.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
                return cal;
            }
        } catch (DatatypeConfigurationException | DateTimeException ex) {
            throw new IllegalArgumentException("Incorrect date format"
                    + date + ". Must be set in " + DATE_FORMAT + " format", ex);
        }
        throw new IllegalArgumentException("Incorrect date format "
                + date + ". Must be set in " + DATE_FORMAT + " format");
    }

    /**
     * Returns current time as string [yyyyMMdd HH:mm:ss].
     *
     * @return the current system time.
     */
    public static String now() {
        return LocalDateTime.now().format(FORMATTER_NOW);
    }

    /**
     * Get current time as {@link java.util.Date}.
     *
     * @return current dateTime as {@link java.util.Date}
     */
    public static Date nowDate() {
        return Date.from(Instant.now());
    }

    /**
     * Get the Date in DATE_FORMAT pattern (Mandatory) .
     *
     * @param date the date to be parsed.
     * @return the date in YYYYMMDD format.
     */
    public static String getDateAsYYYYMMDD(final Date date) {
        if (date == null) {
            return null;
        }
        return Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .format(FORMATTER_DATE);
    }

    /**
     * Returns current time as XMLGregorianCalendar instance.
     *
     * @return returns null in an event of DatatypeConfigurationException.
     */
    public static XMLGregorianCalendar nowXMLGregorianCalendar() {
        try {
            return DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(new GregorianCalendar());
        } catch (DatatypeConfigurationException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
