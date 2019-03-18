/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kyle.healthcare.bluetooth;

/**
 * Defines several constants used between {@link BluetoothChatService} and the UI.
 */
public interface Constants {

    // Message types sent from the BluetoothChatService Handler
    int MESSAGE_STATE_CHANGE = 1;
    int MESSAGE_READ = 2;
    int MESSAGE_WRITE = 3;
    int MESSAGE_DEVICE_NAME = 4;
    int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothChatService Handler
    String DEVICE_NAME = "device_name";
    String TOAST = "toast";

    //heart
    int HEART_RATE_RANGE = 30;
    int HEART_RATE_UNUSUAL = 20;

    //Fatigue
    int FATIGUE_RATE_RANGE = 30;
    int FATIGUE_RAGE_UNUSUAL = 20;

    int frag_id_homepage = 101;
    int frag_id_health = 102;
    int frag_id_driving = 103;
    int frag_id_center = 104;
    int frag_id_heart_rate = 105;
    int frag_id_fatigue_rate = 106;
    int frag_id_settings = 108;
}
