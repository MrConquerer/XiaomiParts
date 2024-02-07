package org.lineageos.settings.dirac;

import android.media.audiofx.AudioEffect;

import java.util.UUID;

public final class DiracUtils {

    protected static DiracSound mDiracSound;
    private static boolean mInitialized;
    private static Context mContext;

    public static void initialize(Context context) {
        if (!mInitialized) {
            mContext = context;
            AudioEffect.Descriptor descriptor = new AudioEffect.Descriptor(
                    UUID.fromString("5b8e36a5-144a-4c38-b1d7-0002a5d5c51b"), // type
                    UUID.randomUUID(), // uuid
                    "org.lineageos.settings.dirac", // connectMode
                    "DiracSound", // name
                    "Dirac Research AB" // implementor
            );
            mDiracSound = new DiracSound(descriptor, 0, 0);
            mInitialized = true;
        }
    }

    protected static void setMusic(boolean enable) {
        mDiracSound.setParameter(DiracSound.DIRACSOUND_PARAM_MUSIC, enable ? 1 : 0);
    }

    protected static boolean isDiracEnabled() {
        return mDiracSound != null && mDiracSound.getParameter(DiracSound.DIRACSOUND_PARAM_MUSIC) == 1;
    }

    protected static void setLevel(String preset) {
        String[] level = preset.split("\\s*,\\s*");

        for (int band = 0; band <= level.length - 1; band++) {
            mDiracSound.setParameter(new int[]{DiracSound.DIRACSOUND_PARAM_EQ_LEVEL, band},
                    String.valueOf(level[band]).getBytes());
        }
    }

    protected static void setHeadsetType(int paramInt) {
         mDiracSound.setParameter(DiracSound.DIRACSOUND_PARAM_HEADSET_TYPE, paramInt);
    }

    protected static void setHifiMode(int paramInt) {
         mDiracSound.setParameter(DiracSound.DIRACSOUND_PARAM_HIFI, paramInt);
    }

    protected static boolean getHifiMode() {
         return mDiracSound.getParameter(DiracSound.DIRACSOUND_PARAM_HIFI) == 1;
    }
}
