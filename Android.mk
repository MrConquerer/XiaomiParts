LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_CLASS := APPS

LOCAL_SRC_FILES := $(call all-java-files-under, src)

LOCAL_PACKAGE_NAME := XiaomiParts
LOCAL_CERTIFICATE := shared

LOCAL_STATIC_ANDROID_LIBRARIES := \
    androidx.core_core \
    androidx.preference_preference

LOCAL_PROGUARD_FLAG_FILES := proguard.flags

LOCAL_RESOURCE_DIR := \
    $(LOCAL_PATH)/res

ifneq ($(INCREMENTAL_BUILDS),)
    LOCAL_PROGUARD_ENABLED := disabled
endif

include frameworks/base/packages/SettingsLib/common.mk

include $(BUILD_PACKAGE)
