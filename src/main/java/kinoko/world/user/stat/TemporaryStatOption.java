package kinoko.world.user.stat;

import kinoko.server.packet.OutPacket;
import kinoko.util.Encodable;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class TemporaryStatOption implements Encodable {
    public static final TemporaryStatOption EMPTY = TemporaryStatOption.of(0, 0, 0);
    public final int nOption;
    public final int rOption;
    public final int tOption;

    public final DiceInfo diceInfo;
    public final Instant expireTime;

    public TemporaryStatOption(int nOption, int rOption, int tOption, DiceInfo diceInfo, Instant expireTime) {
        this.nOption = nOption;
        this.rOption = rOption;
        this.tOption = tOption;
        this.diceInfo = diceInfo;
        this.expireTime = expireTime;
    }

    public TemporaryStatOption(int nOption, int rOption, int tOption, DiceInfo diceInfo) {
        this(nOption, rOption, tOption, diceInfo, tOption == 0 ? Instant.MAX : Instant.now().plus(tOption, ChronoUnit.MILLIS));
    }

    public TemporaryStatOption(int nOption, int rOption, int tOption, Instant expireTime) {
        this(nOption, rOption, tOption, DiceInfo.DEFAULT, expireTime);
    }

    public TemporaryStatOption(int nOption, int rOption, int tOption) {
        this(nOption, rOption, tOption, DiceInfo.DEFAULT);
    }

    public TemporaryStatOption update(int newNOption) {
        return new TemporaryStatOption(
                newNOption,
                rOption,
                tOption != 0 ? (int) (getRemainingMillis()) : 0,
                diceInfo,
                expireTime
        );
    }

    public final DiceInfo getDiceInfo() {
        return diceInfo;
    }

    public final Instant getExpireTime() {
        return expireTime;
    }

    public final long getRemainingMillis() {
        return expireTime.toEpochMilli() - Instant.now().toEpochMilli();
    }

    @Override
    public void encode(OutPacket outPacket) {
        outPacket.encodeShort(nOption);
        outPacket.encodeInt(rOption);
        outPacket.encodeInt(tOption != 0 ? tOption : Integer.MAX_VALUE);
    }

    public static TemporaryStatOption of(int nOption, int rOption, int tOption) {
        return new TemporaryStatOption(nOption, rOption, tOption);
    }

    public static TemporaryStatOption ofMobSkill(int nOption, int skillId, int slv, int tOption) {
        final int rOption = skillId | (slv << 16);
        return new TemporaryStatOption(nOption, rOption, tOption);
    }

    public static TemporaryStatOption ofDice(int nOption, int rOption, int tOption, DiceInfo diceInfo) {
        return new TemporaryStatOption(nOption, rOption, tOption, diceInfo);
    }
}
