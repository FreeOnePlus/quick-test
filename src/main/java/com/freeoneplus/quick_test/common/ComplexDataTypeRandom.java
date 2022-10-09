package com.freeoneplus.quick_test.common;

import com.freeoneplus.quick_test.utils.ConfigUtils;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 复杂数据类型的随机生成核心方法
 * -----该方法需要统一改动-----
 */
@Component
public class ComplexDataTypeRandom {

    private final String CONF_PATH = "conf/basic.properties";
    private Integer defaultYear = ConfigUtils.readIntConfig(CONF_PATH, "default-birthday-year");
    private Integer defaultMonth = ConfigUtils.readIntConfig(CONF_PATH, "default-birthday-month");
    private Integer defaultDay = ConfigUtils.readIntConfig(CONF_PATH, "default-birthday-day");
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
            ConfigUtils.readStringConfig(CONF_PATH,"default-time-format")
    );

    /**
     * 随机获取一个日期类型字符串,默认值为YYYY/MM/dd
     * @param type
     * @param beginDateStr
     * @param endDateStr
     * @return
     */
    public String getRandomDate(int type, String beginDateStr, String endDateStr) {
        LocalDate beginDate = checkDateFormat(beginDateStr, 0);
        LocalDate endDate = checkDateFormat(endDateStr, 1);

        int minDay = (int) beginDate.toEpochDay();
        int maxDay = (int) endDate.toEpochDay();

        long randomDay = minDay + Math.getExponent(Math.random() * (maxDay - minDay));
        LocalDate randomBirthDate = LocalDate.ofEpochDay(randomDay);
        DateTimeFormatter dtf;
        if (type == 0) {
            dtf = DateTimeFormatter.ofPattern("YYYY-MM-dd");
        } else if (type == 1) {
            dtf = DateTimeFormatter.ofPattern("YYYYMMdd");
        } else {
            dtf = DateTimeFormatter.ofPattern("YYYY/MM/dd");
        }
        return randomBirthDate.format(dtf);
    }

    /**
     * 随机生成一个日期时间 默认格式为格式为yyyy-mm-dd HH:MM:ss
     * @param beginDateStr
     * @param endDateStr
     * @return
     * @throws Exception
     */
    public String getRandomTime(String beginDateStr, String endDateStr){
        Date endTime = null;
        try {
            endTime = simpleDateFormat.parse(beginDateStr);
            long endSeconds = endTime.getTime();

            Date startTime = simpleDateFormat.parse(endDateStr);
            long startSeconds = startTime.getTime();

            long realSeconds = startSeconds + (long) ((endSeconds - startSeconds) * Math.random());

            String realTime = simpleDateFormat.format(realSeconds);
            return realTime;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 内部日期规整校验方法
     * 传参为空值或者不符合传值规则，则返回默认值
     *
     * @param dateStr 日期字符串，缺省值为 null
     * @param type    传入值类型【0-起始日期/other-结束日期】
     * @return LocalDate类型的日期对象
     */
    private LocalDate checkDateFormat(String dateStr, int type) {
        if (dateStr == null) {
            return type == 0 ? LocalDate.of(defaultYear, defaultMonth, defaultDay) : LocalDate.now().plusDays(-1);
        } else if (dateStr.matches("^[1-2]{1}[0-9]{3}(-|/|\\| ){0,1}[0-1]{1}[0-9]{1}(-|/|\\| ){0,1}[0-3]{1}[0-9]{1}$")) {
            dateStr = dateStr.replaceAll("(-|/|\\| )", "");
            return LocalDate.of(Integer.parseInt(dateStr.substring(0, 4)),
                    Integer.parseInt(dateStr.substring(4, 6)),
                    Integer.parseInt(dateStr.substring(6, 8)));
        } else {
            return type == 0 ? LocalDate.of(defaultYear, defaultMonth, defaultDay) : LocalDate.now().plusDays(-1);
        }

    }
}
