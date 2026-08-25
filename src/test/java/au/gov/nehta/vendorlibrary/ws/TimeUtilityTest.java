package au.gov.nehta.vendorlibrary.ws;

import org.junit.Assert;
import org.junit.Test;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class TimeUtilityTest {

    @Test
    public void getXMLGregorianDate_parsesEightDigitDate() {
        XMLGregorianCalendar cal = TimeUtility.getXMLGregorianDate("19420819");
        Assert.assertEquals(1942, cal.getYear());
        Assert.assertEquals(8, cal.getMonth());
        Assert.assertEquals(19, cal.getDay());
        Assert.assertTrue(cal.isValid());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getXMLGregorianDate_rejectsNull() {
        TimeUtility.getXMLGregorianDate(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getXMLGregorianDate_rejectsWrongLength() {
        TimeUtility.getXMLGregorianDate("1942081");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getXMLGregorianDate_rejectsNonDigits() {
        TimeUtility.getXMLGregorianDate("1942081x");
    }

    @Test
    public void getXMLGregorianDateTime_parsesSpacedDateTime() {
        XMLGregorianCalendar cal = TimeUtility.getXMLGregorianDateTime("20240513 14:30:52");
        Assert.assertEquals(2024, cal.getYear());
        Assert.assertEquals(5, cal.getMonth());
        Assert.assertEquals(13, cal.getDay());
        Assert.assertEquals(14, cal.getHour());
        Assert.assertEquals(30, cal.getMinute());
        Assert.assertEquals(52, cal.getSecond());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getXMLGregorianDateTime_rejectsDateOnly() {
        TimeUtility.getXMLGregorianDateTime("20240513");
    }

    @Test
    public void now_matchesCalendarInDefaultTimeZone() {
        Calendar c = Calendar.getInstance(TimeZone.getDefault());
        String n = TimeUtility.now();
        Assert.assertEquals(17, n.length());
        String prefix = String.format("%1$tY%1$tm%1$td", c);
        Assert.assertTrue(n.startsWith(prefix));
    }

    @Test
    public void getDateAsYYYYMMDD_formatsDate() {
        GregorianCalendar gc = new GregorianCalendar(2011, Calendar.JUNE, 7, 12, 0, 0);
        gc.setTimeZone(TimeZone.getTimeZone("UTC"));
        Assert.assertEquals("20110607", TimeUtility.getDateAsYYYYMMDD(gc.getTime()));
    }

    @Test
    public void nowXMLGregorianCalendar_isValid() {
        XMLGregorianCalendar cal = TimeUtility.nowXMLGregorianCalendar();
        Assert.assertNotNull(cal);
        Assert.assertTrue(cal.isValid());
    }

    @Test
    public void nowDate_isRecent() {
        long before = System.currentTimeMillis();
        Date d = TimeUtility.nowDate();
        long after = System.currentTimeMillis();
        Assert.assertTrue(d.getTime() >= before && d.getTime() <= after);
    }
}
