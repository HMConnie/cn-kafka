package cn.kafka.common.utils;

import org.apache.commons.lang.time.DateUtils;

import java.util.Date;

public class DateUtil {
    private DateUtil() {

    }

    /**
     * 根据发送次数，计算下次发送的时间
     *
     * @param sendCnt
     * @return
     */
    public static Date getNextSendTime(Long sendCnt) {
        double cnt = 1;
        for (int i = 1; i < sendCnt; i++) {
            cnt = cnt * 1.5;
            if (cnt > 60 * 60 * 24 * 3) {
                break;
            }
        }
        return DateUtils.addSeconds(new Date(), Long.valueOf(Math.round(cnt)).intValue());
    }
}
