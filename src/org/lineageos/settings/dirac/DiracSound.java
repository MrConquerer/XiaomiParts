package org.lineageos.settings.dirac;

import android.media.audiofx.AudioEffect;

import java.util.UUID;

public class DiracSound extends AudioEffect {

    private static final int DIRACSOUND_PARAM_HEADSET_TYPE = 1;
    private static final int DIRACSOUND_PARAM_EQ_LEVEL = 2;
    private static final int DIRACSOUND_PARAM_MUSIC = 4;
    private static final int DIRACSOUND_PARAM_HIFI = 8;

    private static final UUID EFFECT_TYPE_DIRACSOUND =
            UUID.fromString("5b8e36a5-144a-4c38-b1d7-0002a5d5c51b");
    private static final String TAG = "DiracSound";

    // Create a descriptor for DiracSound effect
    private static final AudioEffect.Descriptor DESCRIPTOR = new AudioEffect.Descriptor(
            EFFECT_TYPE_DIRACSOUND, // type
            UUID.randomUUID(), // uuid
            "org.lineageos.settings.dirac", // connectMode
            "DiracSound", // name
            "Dirac Research AB" // implementor
    );

    public DiracSound(int priority, int audioSession) {
        // Use the descriptor to create the effect
        super(DESCRIPTOR, priority, audioSession);
    }

    public void setMusic(int enable) throws IllegalStateException,
            IllegalArgumentException, UnsupportedOperationException {
        checkStatus(native_setParameter(DIRACSOUND_PARAM_MUSIC, enable));
    }

    public int getMusic() throws IllegalStateException,
            IllegalArgumentException, UnsupportedOperationException {
        int[] value = new int[1];
        checkStatus(native_getParameter(DIRACSOUND_PARAM_MUSIC, value));
        return value[0];
    }

    public void setHeadsetType(int type) throws IllegalStateException,
            IllegalArgumentException, UnsupportedOperationException {
        checkStatus(native_setParameter(DIRACSOUND_PARAM_HEADSET_TYPE, type));
    }

    public void setLevel(int band, float level) throws IllegalStateException,
            IllegalArgumentException, UnsupportedOperationException {
        checkStatus(native_setParameter(new int[]{DIRACSOUND_PARAM_EQ_LEVEL, band},
                String.valueOf(level).getBytes()));
    }

    public void setHifiMode(int mode) throws IllegalStateException,
            IllegalArgumentException, UnsupportedOperationException {
        checkStatus(native_setParameter(DIRACSOUND_PARAM_HIFI, mode));
    }

    private native int native_setParameter(int param, int value);
    private native int native_setParameter(int[] param, byte[] value);
    private native int native_getParameter(int param, int[] value);
}
