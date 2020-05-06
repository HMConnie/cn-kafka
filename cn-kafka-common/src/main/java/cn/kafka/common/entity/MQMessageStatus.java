package cn.kafka.common.entity;

public enum MQMessageStatus {
    WAIT_ACK(1), ACKED(2);

    private int id;

    private MQMessageStatus(int id) {
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public static MQMessageStatus getInstance(int id) {
        for (MQMessageStatus type: MQMessageStatus.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("cant instance MQMessageStatus for ID:" + id);
    }
}
