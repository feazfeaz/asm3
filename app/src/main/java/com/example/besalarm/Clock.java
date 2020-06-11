package com.example.besalarm;

import java.util.Calendar;

public class Clock {
    private String name;
    private int DatabaseId;
    private Calendar calendar;

    public static final String MONDAY = "Monday";
    public static final String TUESTDAY = "Tuestday";
    public static final String WEDNESDAY = "Wednesday";
    public static final String THURSDAY = "Thursday";
    public static final String FRIDAY = "Friday";
    public static final String SATURDAY = "Saturday";
    public static final String SUNDAY = "Sunday";

    private Boolean isActive;
    private boolean isActiveOnMonday;
    private boolean isActiveOnTuestday;
    private boolean isActiveOnWednesday;
    private boolean isActiveOnThursday;
    private boolean isActiveOnFriday;
    private boolean isActiveOnSaturday;
    private boolean isActiveOnSunday;

    private Clock(){};

    public Clock(int DatabaseId, String name, Calendar calendar){
        this.DatabaseId = DatabaseId;
        this.name = name;
        this.calendar = calendar;

        this.isActive = true;
        this.isActiveOnMonday = true;
        this.isActiveOnTuestday = true;
        this.isActiveOnWednesday = true;
        this.isActiveOnThursday = true;
        this.isActiveOnFriday = true;
        this.isActiveOnSaturday = true;
        this.isActiveOnSunday = true;
    }

    public void setActiveDayOfWeek(String dayOfWeek,boolean isActiveDay){
        if(dayOfWeek.equalsIgnoreCase(this.MONDAY)){
            this.isActiveOnMonday = isActiveDay;
        }else if(dayOfWeek.equalsIgnoreCase(this.TUESTDAY)){
            this.isActiveOnTuestday = isActiveDay;
        }else if(dayOfWeek.equalsIgnoreCase(this.WEDNESDAY)){
            this.isActiveOnWednesday = isActiveDay;
        }else if(dayOfWeek.equalsIgnoreCase(this.THURSDAY)){
            this.isActiveOnThursday = isActiveDay;
        }else if(dayOfWeek.equalsIgnoreCase(this.FRIDAY)){
            this.isActiveOnFriday = isActiveDay;
        }else if(dayOfWeek.equalsIgnoreCase(this.SATURDAY)){
            this.isActiveOnSaturday = isActiveDay;
        }else if(dayOfWeek.equalsIgnoreCase(this.SUNDAY)){
            this.isActiveOnSunday = isActiveDay;
        }
    }
    public boolean getActiveDayOfWeek(String dayOfWeek){
        if(dayOfWeek.equalsIgnoreCase(this.MONDAY)){
            return this.isActiveOnMonday;
        }else if(dayOfWeek.equalsIgnoreCase(this.TUESTDAY)){
            return this.isActiveOnTuestday;
        }else if(dayOfWeek.equalsIgnoreCase(this.WEDNESDAY)){
            return this.isActiveOnWednesday;
        }else if(dayOfWeek.equalsIgnoreCase(this.THURSDAY)){
            return this.isActiveOnThursday;
        }else if(dayOfWeek.equalsIgnoreCase(this.FRIDAY)){
            return this.isActiveOnFriday;
        }else if(dayOfWeek.equalsIgnoreCase(this.SATURDAY)){
            return this.isActiveOnSaturday;
        }else{
            return this.isActiveOnSunday;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public int getDatabaseId() {
        return DatabaseId;
    }

    public void setDatabaseId(int databaseId) {
        DatabaseId = databaseId;
    }
}
