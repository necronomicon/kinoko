package kinoko.world.user.stat;

import kinoko.server.packet.OutPacket;
import kinoko.util.Encodable;

import java.util.concurrent.ScheduledFuture;

public class TemporaryStatOption implements Encodable {
    public static final TemporaryStatOption EMPTY = new TemporaryStatOption();
    public int nOption;
    public int rOption;
    public int tOption;

    public DiceInfo diceInfo = DiceInfo.DEFAULT;
    public ScheduledFuture<?> statFuture;

    @Override
    public void encode(OutPacket outPacket) {
        outPacket.encodeShort(nOption);
        outPacket.encodeInt(rOption);
        outPacket.encodeInt(tOption);
    }
}
