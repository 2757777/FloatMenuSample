/*
 * Copyright (c) 2016, Shanghai YUEWEN Information Technology Co., Ltd.
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *  Neither the name of Shanghai YUEWEN Information Technology Co., Ltd. nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY SHANGHAI YUEWEN INFORMATION TECHNOLOGY CO., LTD. AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE REGENTS AND CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package com.yw.game.floatmenu.demo;

import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;

public class FloatMenuInServiceActivity extends AppCompatActivity implements ServiceConnectionManager.QdServiceConnection {
    private FloatMenuService mFloatMenuService;
    private ServiceConnectionManager mServiceConnectionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_menu_in_service);
        startFloatMenu(this);

    }

    public void startFloatMenu(Context context) {
        if (mFloatMenuService != null) {
            mFloatMenuService.showFloat();
            return;
        }
        if (mServiceConnectionManager == null) {
            mServiceConnectionManager = new ServiceConnectionManager(context, FloatMenuService.class, this);
            mServiceConnectionManager.bindToService();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mFloatMenuService != null)
            mFloatMenuService.showFloat();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFloatMenuService.hideFloat();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFloatMenuService != null) {
            mFloatMenuService.hideFloat();
            mFloatMenuService.destroyFloat();
        }
        if (mServiceConnectionManager != null)
            mServiceConnectionManager.unbindFromService();
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mFloatMenuService = ((FloatMenuService.FloatMenuServiceBinder) service).getService();
        if (mFloatMenuService != null) {
            mFloatMenuService.showFloat();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mFloatMenuService = null;
    }
}
