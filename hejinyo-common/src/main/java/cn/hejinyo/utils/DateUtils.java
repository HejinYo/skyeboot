package cn.hejinyo.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 日期处理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月21日 下午12:53:33
 */
public class DateUtils {
	/** 时间格式(yyyy-MM-dd) */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }
    
    /**
	 * @function:如果前者早于后者则返回true
	 * @param beforeTime
	 * @param afterTime
	 * @return
	 * @author: llh 2008-7-12 下午01:19:07
	 */
	public static boolean compareTime(Calendar beforeTime, Calendar afterTime)
	{
		boolean flag = false;
		if (beforeTime.before(afterTime))
		{
			flag = true;
		}
		return flag;
	}

	/**
	 * @function:比较两时间段，返回时间段重复的日期天数
	 * @param paramBeforeTime
	 * @param paramAfterTime
	 * @param standardBeforeTime
	 * @param standardAfterTime
	 * @return
	 * @author: llh 2008-7-14 上午10:28:47
	 */
	public static int compareTimesParagraph(Calendar paramBeforeTime, Calendar paramAfterTime, Calendar standardBeforeTime, Calendar standardAfterTime)
	{
		long pBeforeTime = paramBeforeTime.getTime().getTime();
		long pAfterTime = paramAfterTime.getTime().getTime();
		long sBeforeTime = standardBeforeTime.getTime().getTime();
		long sAfterTime = standardAfterTime.getTime().getTime();
		if (pBeforeTime < sBeforeTime)
		{
			if (pAfterTime < sAfterTime)
			{
				if (sBeforeTime < pAfterTime)
				{
					return differenceDaysToCount(standardBeforeTime, paramAfterTime).intValue();
				} else if (sBeforeTime > pAfterTime)
				{
					return 0;
				} else
				{
					return 1;
				}
			} else
			{
				return differenceDaysToCount(standardBeforeTime, standardAfterTime).intValue() == 0
							? 1
							: differenceDaysToCount(standardBeforeTime,
								standardAfterTime).intValue();
			}
		} else
		{
			if (pAfterTime > sAfterTime)
			{
				if (pBeforeTime < sAfterTime)
				{
					return differenceDaysToCount(paramBeforeTime, standardAfterTime).intValue();
				} else if (pBeforeTime > sAfterTime)
				{
					return 0;
				} else
				{
					return 1;
				}
			} else
			{
				return differenceDaysToCount(standardBeforeTime, standardAfterTime).intValue();
			}
		}
	}

	/**
	 * @function:返回日期相差几天
	 * @param startTime
	 * @param endTime
	 * @return
	 * @author: llh 2008-7-2 上午09:55:15
	 */
	public static Integer differenceDaysToCount(Calendar startTime, Calendar endTime)
	{
		long day = (endTime.getTimeInMillis() - startTime.getTimeInMillis())
					/ (1000 * 60 * 60 * 24);
		int intDay = new Long(day).intValue();
		if (intDay == 0)
		{
			return 1;
		} else
		{
			return intDay + 1;
		}
	}

	/**
	 * @function:将字符串转为Calendar类型
	 * @param dateTime 默认：当前系统时间
	 * @param formatString 默认：yyyy-MM-dd
	 * @return
	 * @author: llh 2008-7-2 上午09:27:41
	 */
	public static Calendar formatDateFromStringToCalendar(String dateTime, String formatString)
	{
		Calendar cal = Calendar.getInstance();
		if (dateTime == null || "".equals(dateTime))
		{
			cal.setTime(new Date());
		} else
		{
			if (formatString == null || "".equals(formatString))
			{
				formatString = "yyyy-MM-dd";
			}
			SimpleDateFormat df = new SimpleDateFormat(formatString);
			try
			{
				cal.setTime(df.parse(dateTime));
			} catch (ParseException e)
			{
				e.printStackTrace();
			}
		}
		if (formatString == null)
		{
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
		}
		return cal;
	}
	
	/**
	 * Parse formated date string
	 * 
	 * @author <a href="mailto:linhui.li@berheley.com">linhui.li </a>
	 * @param now String 日期时分 'yyyy-MM-dd HH:mm' 或 'yyyy-MM-dd'
	 * @return Date 日期
	 */
	public static Date getDate(String now)
	{
		// 若传过来的字符串是长度为10的类型：yyyy-MM-dd，则先把它改为16位的类型
		if (now != null && now.length() == DATE_PATTERN.length())
		{
			now = now + " 00:00:00";
		}
		DateFormat df = new SimpleDateFormat(DATE_TIME_PATTERN);
		Date date = null;
		try
		{
			date = ((now == null || now.length() != DATE_TIME_PATTERN.length())
						? null
						: df.parse(now));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * @function:将字符串转换为日期
	 * @param date
	 * @param format
	 * @return
	 * @author: lilh Jan 17, 2008 4:28:04 PM
	 */
	public static Date getDateByStr(String date, String format)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date d = new Date();
		try
		{
			d = dateFormat.parse(date);
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		return d;
	}

	/**
	 * @function: 返回当前时间，格式为“yyyy-mm-dd”
	 * @return
	 * @author: lilh 2009-2-24 上午09:50:36
	 */
	public static String getSimpleFormatedDayNow()
	{
		Date now = new Date();
		return getFormatedDayStr(now);
	}

	/**
	 * 返回当前时间，格式为“yyyy-MM-dd HH:mm:ss”
	 *
	 * @author <a href="mailto:linhui.li@berheley.com">linhui.li </a>
	 * @return String
	 */
	public static String getSimpleFormatedDateNow()
	{
		Date now = new Date();
		return getFormatedDateStr(now);
	}
	
	/**
	 * @param now
	 * @return 返回格式为 'yyyy-MM-dd' 的字符串
	 */
	public static String getFormatedDayStr(Date now)
	{
		DateFormat df = new SimpleDateFormat(DATE_PATTERN);
		String str = (now == null ? null : df.format(now));
		return str;
	}

	/**
	 * 通过时间、日期差得到修正的时间
	 * 
	 * @author <a href="mailto:songran.li@berheley.com">songran.li </a>
	 * @param today Date 时间
	 * @param interval int 日期差
	 * @return Date 修正时间
	 */
	public static Date getInterval(Date today, int interval)
	{
		long time = today.getTime();
		time += interval * 24 * 3600 * 1000;
		return new Date(time);
	}

	/**
	 * @param now
	 * @return 返回格式为 'yyyy-MM-dd HH:mm:ss' 的字符串
	 */
	public static String getFormatedDateStr(Date now)
	{
		DateFormat df = new SimpleDateFormat(DATE_TIME_PATTERN);
		String str = (now == null ? null : df.format(now));
		return str;
	}
	
}
