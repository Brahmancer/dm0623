package toolVendor.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import toolVendor.data.tool.Tool;

/**
 * Utility class to get some calendar/time specific data.
 */
public class CalendarUtility {

    /**
     * Generates a format friendly starting date string so that we can take an intial hit up front to save us grief later 
     * 
     * @param inputString: Input string 
     * @return: Formatted date string as dd/MM/yy
     */
    public static String getStartDateString(String inputString){
        DateTimeFormatter formatter = CalendarUtility.generateFormatterBasedonDateString(inputString);
        LocalDate parsedDate = LocalDate.parse(inputString, formatter);
        formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        return parsedDate.format(formatter);
    }

    /**
     * Returns an ending date from a start date and a day count
     * 
     * @param startDateString: Starting date string represntation
     * @param numberOfDaysFromStart: The number of days ahead the end date lies
     * 
     * @return: A string representation of the end date that is n days ahead from the start date. 
     */
    public static String getEndDateString(String startDateString, int numberOfDaysFromStart) {
        String endDateString = "";

        // generate formatter then parse the date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        LocalDate parsedStartDate = LocalDate.parse(startDateString, formatter);

        // Generate the end date
        LocalDate endDate = parsedStartDate.plusDays(numberOfDaysFromStart);

        // Generate the end date string and return it        
        endDateString = endDate.format(formatter);
        return endDateString;
    }

    /**
     * Gets the # of chargable days based on the start day, # of days, and the tool
     * being rented. Chargable days start the day after the specified start date and 
     * go up to and include the date specified by start date + n days from start. 
     * 
     * The Tool has rules on which days are chargable. 
     * 
     * @param startDateString: A string representation of the start date to rent
     * @param numberOfDaysFromStart: The number of days to rent 
     * @param toolToRent: A Tool object representing the tool to rent.
     * 
     * @return: The # of chargeable days for the input tool 
     */
    public static int getChargableDays(String startDateString, int numberOfDaysFromStart, Tool toolToRent) {
        int chargableDayCount = 0;

        // Generate a DateTimeFormatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");

        // Parse the start date as a local date.
        LocalDate parsedStartDate = LocalDate.parse(startDateString, formatter);

        // Start counting days
        for (int i = 1; i <= numberOfDaysFromStart; ++i) {
            // Get the date to examine.
            LocalDate dateToExamine = parsedStartDate.plusDays(i);

            // Check for holidays
            if (toolToRent.doHolidayRatesApply() && (isFourthOfJulyHoliday(dateToExamine) || isLaborDay(dateToExamine))) {
                // Increment the # of days
                chargableDayCount++;

                // Move forward in the loop and don't bother executing code below
                continue;
            }

            // If here, we just check for weekends

            // Get the day of week.
            DayOfWeek dayOfWeekForDate = dateToExamine.getDayOfWeek();

            // Check for weekend rate and if it's a weekend.
            if (toolToRent.doWeekendRatesApply() && (dayOfWeekForDate == DayOfWeek.SATURDAY || dayOfWeekForDate == DayOfWeek.SUNDAY) && !isFourthOfJulyHoliday(dateToExamine)) {
                chargableDayCount++;
                continue;
            }

            // If here, check for weekdays that aren't holidays.
            if (toolToRent.doWeekdayRatesApply() && dayOfWeekForDate != DayOfWeek.SATURDAY && dayOfWeekForDate != DayOfWeek.SUNDAY && !isLaborDay(dateToExamine) && !isFourthOfJulyHoliday(dateToExamine)) {
                // it's a chargable day
                chargableDayCount++;
            }
        }

        return chargableDayCount;
    }

    /**
     * Helper method to determine if the date counts as a 4th of July holiday. 
     * It's a 4th of July holiday when
     *  - It's the 4th and it's not on a weekend.
     *  - The 4th is on a Saturday and today is the 3rd.
     *  - The 4th is on a Sunday and today is the 5th.
     * 
     * @param dateToCheck: The date to check if it's a 4th of July holiday. 
     * 
     * @return: True if the date specified is a 4th of july holiday, false for otherwise.
     */
    private static boolean isFourthOfJulyHoliday(LocalDate dateToCheck) {
        if (dateToCheck != null) {
            // Get day of week, day, and month
            Month month = dateToCheck.getMonth();
            int day = dateToCheck.getDayOfMonth();
            DayOfWeek dayOfWeek = dateToCheck.getDayOfWeek();

            // Check for the actual forth of July.
            if (month == Month.JULY &&
                day == 4 &&
                dayOfWeek != DayOfWeek.SATURDAY &&
                dayOfWeek != DayOfWeek.SUNDAY) {
                // This is indeed the 4th of July holiday day
                return true;
            }
            // Check for the Friday before it 
            else if (month == Month.JULY && 
                     day == 3 && 
                     dayOfWeek == DayOfWeek.FRIDAY) {
                // It isn't exactly the 4th of July but the 4th lies on a Saturday
                // and today is the friday before it. 
                // This counts as a 4th of July holiday
                return true;
            }
            // Check the Monday after it.
            else if (month == Month.JULY &&
                     day == 5 &&
                     dayOfWeek == DayOfWeek.MONDAY) {
                // It isn't exactly the 4th of July but the 4th lies on a Sunday
                // and today is the Monday after it. 
                // This coutns as a 4th of July Holiday.
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method to determine if the date counts as a Labor Day holiday.
     * 
     * It's considered a labor day holiday if it's the first Monday of the month of September.
     * 
     * @param dateToCheck: date to verify if it's a Labord day holiday
     * 
     * @return: True if the date is a labor day holiday, false for otherwise.
     */
    private static boolean isLaborDay(LocalDate dateToCheck) {
        // Check for null 
        if (dateToCheck != null) {
            // Get day of week, day, and month
            Month month = dateToCheck.getMonth();
            int day = dateToCheck.getDayOfMonth();
            DayOfWeek dayOfWeek = dateToCheck.getDayOfWeek();

            // If the Month is September and the day is monday, the candidate is likely a Labor day. 
            // 
            // The possible values for Labor day are 1 through 7, since it's the first Monday of the month,
            // not the first monday on the first full week of September.
            //
            // If those condidtions are met, it's labor day!
            if (month == Month.SEPTEMBER &&
                dayOfWeek == DayOfWeek.MONDAY && 
                day <=7 && 
                day >=1){
                    // Hooray we found Labor day!
                    return true;
            }
        }
        
        return false;
    }

    /**
     * Helper function that allows some flexibility in generate some Date Fromatters
     * 
     * The output formatter will allow us to get a date then later convert back into a "dd/MM/yy" format
     * 
     * @param dateString: initial date string to generate a formatter for.
     * 
     * @return: A DateTimeFormatter for the initial date string.
     */
    public static DateTimeFormatter generateFormatterBasedonDateString(String dateString){
        DateTimeFormatter formatter = null;
        LocalDate testDateTime;

        // Check for expected MM/dd/yy
        try {
            formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
            testDateTime = LocalDate.parse(dateString, formatter);
        }
        catch (DateTimeParseException e)
        {
            formatter = null;
        }

        // Check for M/d/yy
        if (formatter == null){
            // Try a different pattern
            try {
                formatter = DateTimeFormatter.ofPattern("M/d/yy");
                testDateTime = LocalDate.parse(dateString, formatter);
            }
            catch (DateTimeParseException e){
                formatter = null;
            }
        }

        // Check for MM/d/yy
        if (formatter == null){
            // Try a different pattern
            try {
                formatter = DateTimeFormatter.ofPattern("MM/d/yy");
                testDateTime = LocalDate.parse(dateString, formatter);
            }
            catch (DateTimeParseException e){
                formatter = null;
            }
        }
        
        // Check for M/dd/yy
        if (formatter == null){
            // Try a different pattern
            try {
                formatter = DateTimeFormatter.ofPattern("M/dd/yy");
                testDateTime = LocalDate.parse(dateString, formatter);
            }
            catch (DateTimeParseException e){
                formatter = null;
            }
        }

        // Check for M/dd/yyyy
        if (formatter == null){
            // Try a different pattern
            try {
                formatter = DateTimeFormatter.ofPattern("M/dd/yyyy");
                testDateTime = LocalDate.parse(dateString, formatter);
            }
            catch (DateTimeParseException e){
                formatter = null;
            }
        }

        // Check for M/d/yyyy
        if (formatter == null){
            // Try a different pattern
            try {
                formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
                testDateTime = LocalDate.parse(dateString, formatter);
            }
            catch (DateTimeParseException e){
                formatter = null;
            }
        }

        // Check for MM/d/yyyy
        if (formatter == null){
            // Try a different pattern
            try {
                formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
                testDateTime = LocalDate.parse(dateString, formatter);
            }
            catch (DateTimeParseException e){
                formatter = null;
            }
        }

        // Check for MM/dd/yyyy
        if (formatter == null){
            // Try a different pattern
            try {
                formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                testDateTime = LocalDate.parse(dateString, formatter);
            }
            catch (DateTimeParseException e){
                formatter = null;
            }
        }

        return formatter;
    }
}
