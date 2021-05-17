package backup;

/*
 * This Class implements DateValidator interface by using DateFormate and simpleDateFormate libraries
 * Implements isValid method of the interface
 */


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateValidatorUsingDateFormat implements DateValidator {
    private String dateFormat;
 
    public DateValidatorUsingDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
 
    @Override
    public boolean isValid(String dateStr) {
        DateFormat sdf = new SimpleDateFormat(this.dateFormat);
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
