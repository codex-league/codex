package pub.codex.common.utils;

import cn.hutool.core.lang.Singleton;

/**
 * 雪花算法工具类
 */
public class SnowFlakeUtil {
    private static final long START_STMP = 1420041600000L;
    private static final long SEQUENCE_BIT = 9L;
    private static final long MACHINE_BIT = 2L;
    private static final long DATACENTER_BIT = 2L;
    private static final long MAX_SEQUENCE = 511L;
    private static final long MAX_MACHINE_NUM = 3L;
    private static final long MAX_DATACENTER_NUM = 3L;
    private static final long MACHINE_LEFT = 9L;
    private static final long DATACENTER_LEFT = 11L;
    private static final long TIMESTMP_LEFT = 13L;
    private long datacenterId;
    private long machineId;
    private long sequence = 0L;
    private long lastStmp = -1L;

    public SnowFlakeUtil(long datacenterId, long machineId) {
        if (datacenterId <= 3L && datacenterId >= 0L) {
            if (machineId <= 3L && machineId >= 0L) {
                this.datacenterId = datacenterId;
                this.machineId = machineId;
            } else {
                throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
            }
        } else {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
    }

    public synchronized long nextId() {
        long currStmp = this.getNewstmp();
        if (currStmp < this.lastStmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        } else {
            if (currStmp == this.lastStmp) {
                this.sequence = this.sequence + 1L & 511L;
                if (this.sequence == 0L) {
                    currStmp = this.getNextMill();
                }
            } else {
                this.sequence = 0L;
            }

            this.lastStmp = currStmp;
            return currStmp - 1420041600000L << 13 | this.datacenterId << 11 | this.machineId << 9 | this.sequence;
        }
    }

    private long getNextMill() {
        long mill;
        for(mill = this.getNewstmp(); mill <= this.lastStmp; mill = this.getNewstmp()) {
        }

        return mill;
    }

    private long getNewstmp() {
        return System.currentTimeMillis();
    }

    public static Long defaultSnowFlakeId() {
        return ((SnowFlakeUtil)Singleton.get(SnowFlakeUtil.class, new Object[]{1L, 1L})).nextId();
    }

}