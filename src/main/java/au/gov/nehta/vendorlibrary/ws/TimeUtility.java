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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class TimeUtility {

    public static final String DATE_FORMAT_NOW = "yyyyMMdd HH:mm:ss";
    public static final String DATE_FORMAT_NOW_REGX = "^([0-9]{4})([0-1][0-9])"
            + "([0-3][0-9])\\s([0-1][0-9]|[2][0-3]):([0-5][0-9]):([0-5][0-9])$";
    public static final String DATE_FORMAT = "yyyyMMdd";
    public static final String DATE_FORMAT_REGX = "^(19|20)\\d\\d(0[1-9]|1[012])"
            + "(0[1-9]|[12][0-9]|3[01])$";
    public static final String COMPACT_DATE_TIME_FORMAT = "yyyyMMddHHmmss";
    public static final int DATE_TIME_LENGTH = COMPACT_DATE_TIME_FORMAT.length();
    private static final Logger LOGGER = Logger.getLogger(TimeUtility.class.getName());

    private TimeUtility() {
    }

    public static XMLGregorianCalendar getXMLGregorianDateTime(
            final String date) {
        if (date == null) {
            throw new IllegalArgumentException("date must not be null");
        }
        try {
            if (date.matches(DATE_FORMAT_NOW_REGX)) {
                XMLGregorianCalendar cal = DatatypeFactory.newInstance()
                        .newXMLGregorianCalendar(new GregorianCalendar());
                SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_NOW);
                Date simpleDate = format.parse(date);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(simpleDate);

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int min = calendar.get(Calendar.MINUTE);
                int second = calendar.get(Calendar.SECOND);

                cal.setDay(day);
                cal.setYear(year);
                cal.setMonth(month + 1);
                cal.setHour(hour);
                cal.setMinute(min);
                cal.setSecond(second);
                return cal;
            }
        } catch (DatatypeConfigurationException | ParseException ex) {
            throw new IllegalArgumentException("Incorrect date format"
                    + date + ". Must be set in " + DATE_FORMAT + " format", ex);
        }
        throw new IllegalArgumentException("Incorrect date format"
                + date + ". Must be set in "
                + DATE_FORMAT + " format");
    }

    public static XMLGregorianCalendar getXMLGregorianDate(final String date) {
        if (date == null) {
            throw new IllegalArgumentException("date must not be null");
        }
        try {
            XMLGregorianCalendar cal = DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(new GregorianCalendar());
            if (date.matches(DATE_FORMAT_REGX)) {

                SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
                Calendar calendar = Calendar.getInstance();

                Date simpleDate = format.parse(date);

                calendar.setTime(simpleDate);

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                cal.setDay(day);
                cal.setYear(year);
                cal.setMonth(month + 1);

                cal.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
                return cal;
            }
        } catch (ParseException | DatatypeConfigurationException ex) {
            throw new IllegalArgumentException("Incorrect date format"
                    + date + ". Must be set in " + DATE_FORMAT + " format", ex);
        }
        throw new IllegalArgumentException("Incorrect date format "
                + date + ". Must be set in " + DATE_FORMAT + " format");
    }

    public static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }

    public static Date nowDate() {
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    public static String getDateAsYYYYMMDD(final Date date) {
        String dateTime = null;
        if (date != null) {
            SimpleDateFormat dateformatYYYYMMDD = new SimpleDateFormat(
                    TimeUtility.DATE_FORMAT);
            dateTime = dateformatYYYYMMDD.format(date);
        }
        return dateTime;
    }

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
