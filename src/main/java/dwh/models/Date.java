package dwh.models;

public class Date {
    private int year;
    private int month;
    private int day;

    public Date(int day,int month,int year)
    {
        if(day<=31) this.day=day;
        if(month<=12)  this.month=month;
        this.year=year;
    }
    public Date(){

    }
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        if(month<=12) this.month = month;
    }

    public int getDay() {
         return day;
    }

    public void setDay(int day) {
        if(month<=12) this.day = day;
    }
    public boolean before(Date date)
    {//2020<2021
        if(year<date.getYear())
        {
            return true;
        }
        //2020=2020
        if(year==date.getYear())
        {
            //11>date 10
            if(month>date.getMonth())
            {
                return false;
            }
            //11=11
            if(month==date.getMonth())
            {//20=20
                if(day==date.getDay())
                {
                    return false;
                }
                //20<21
                if(day<date.getDay()){
                    return true;
                }//20>19
                return false;
            }
            //11<12
            return true;
        }
            return false;
    }





}
