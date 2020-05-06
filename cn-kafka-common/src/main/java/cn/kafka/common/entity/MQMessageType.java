package cn.kafka.common.entity;

public enum MQMessageType {
    ACK(2), SEND(1);

    private int id;

    private MQMessageType(int id) {
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public static MQMessageType getInstance(int id) {
        for (MQMessageType type: MQMessageType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("cant instance MQMessageType for ID:" + id);
    }
}
